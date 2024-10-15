package com.salesmanager.shop.store.controller.payment.facade;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.fulfillment.service.FulfillmentMainOrderService;
import com.salesmanager.core.business.fulfillment.service.FulfillmentSubOrderService;
import com.salesmanager.core.business.services.catalog.pricing.PricingService;
import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.business.services.customer.order.CustomerOrderService;
import com.salesmanager.core.business.services.order.OrderService;
import com.salesmanager.core.business.services.payments.combine.CombinePaymentService;
import com.salesmanager.core.business.services.payments.combine.CombineTransactionService;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.customer.order.CustomerOrder;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.Order;
import com.salesmanager.core.model.order.orderstatus.OrderStatus;
import com.salesmanager.core.model.payments.CombineTransaction;
import com.salesmanager.core.model.payments.Payment;
import com.salesmanager.core.model.payments.PaymentType;
import com.salesmanager.core.model.payments.TransactionType;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.customer.order.transaction.ReadableCombineTransaction;
import com.salesmanager.shop.populator.customer.ReadableCombineTransactionPopulator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("paymentCallbackFacade")
public class PaymentAuthorizeFacadeImpl implements PaymentAuthorizeFacade {

    public static final Logger LOG = LoggerFactory.getLogger(PaymentAuthorizeFacadeImpl.class);

    @Inject
    private CombinePaymentService combinePaymentService;

    @Inject
    private ReadableCombineTransactionPopulator trxPopulator;

    @Inject
    private CombineTransactionService combineTransactionService;

    @Inject
    private CustomerOrderService customerOrderService;

    @Inject
    private CustomerService customerService;

    @Inject
    private PricingService pricingService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private FulfillmentSubOrderService fulfillmentSubOrderService;

    @Autowired
    private FulfillmentMainOrderService fulfillmentMainOrderService;
    @Override
    public ReadableCombineTransaction processNicepayAuthorizeResponse(
            Map<String, String> responseMap,
            MerchantStore store,
            Language language
    ) throws Exception {
        Long orderId = Long.valueOf(responseMap.get("orderId"));
        CustomerOrder customerOrder = null;
        Order order = null;
        BigDecimal orderAmount = null;
        if (responseMap.get("paymentMode") == null) {
            customerOrder = customerOrderService.getCustomerOrder(orderId);
            if (customerOrder == null) {
                throw new ServiceException("customer order [" + responseMap.get("orderId") + "] not found");
            }
            orderAmount = customerOrder.getTotal();
        } else {
            // partial payment
            Long customerOrderId = orderService.findCustomerOrderIdByOrderId(orderId);
            customerOrder = customerOrderService.getCustomerOrder(customerOrderId);
            if (customerOrder == null) {
                throw new ServiceException("customer order [" + responseMap.get("orderId") + "] not found");
            }
            order = orderService.getOrder(orderId, (MerchantStore) null);
            orderAmount = order.getTotal();
        }
        checkPayAmount(responseMap, orderAmount);

        Customer customer = customerService.getById(customerOrder.getCustomerId());

        Payment payment = new Payment();
        payment.setPaymentType(PaymentType.NICEPAY);
        payment.setTransactionType(TransactionType.AUTHORIZECAPTURE);
        payment.setModuleName("Nicepay");
        payment.setAmount(orderAmount);
        payment.setPaymentMetaData(responseMap);

        CombineTransaction combineTransaction = combinePaymentService.processPaymentNextTransaction(customerOrder, order, customer, store, payment);

        ReadableCombineTransaction transaction = new ReadableCombineTransaction();
        trxPopulator.populate(combineTransaction, transaction, store, language);

        // if pay amount is equals total customer order amount, then order status is PAYMENT_COMPLETED, else if PARTIAL_PAYMENT
        OrderStatus nextOrderStatus = processNextCustomerOrderStatus(order, customerOrder);
        customerOrderService.updateCustomerOrderStatus(customerOrder, order, nextOrderStatus);

        return transaction;
    }

    /**
     * if pay amount not equals order amount, then throw exception
     */
    private void checkPayAmount(Map<String, String> responseMap, BigDecimal orderAmount) throws ServiceException {
        // check pay amount and order amount
        String payAmount = responseMap.get("amount");
        BigDecimal pay = new BigDecimal(payAmount);
        if (pay.compareTo(orderAmount) != 0) {
            LOG.error("pay amount is not equals to order amount. pay amount:{}, order amount:{}, response detail:{}", pay.toPlainString(), orderAmount.toPlainString(), responseMap);
            throw new ServiceException("pay amount is not equals to order amount. pay amount:" + pay.toPlainString() + ", order amount:" + orderAmount.toPlainString());
        }
    }

    private OrderStatus processNextCustomerOrderStatus(Order order, CustomerOrder customerOrder) throws ServiceException {
        // check is completed or partial_payment
        OrderStatus nextOrderStatus = OrderStatus.PARTIAL_PAYMENT;
        if (order != null) {
            List<CombineTransaction> combineTransactions = combineTransactionService.listCombineTransactions(customerOrder);
            List<CombineTransaction> paymentCombineTransactions = combineTransactions.stream()
                    .filter(each -> TransactionType.AUTHORIZECAPTURE.equals(each.getTransactionType()) || TransactionType.CAPTURE.equals(each.getTransactionType()))
                    .collect(Collectors.toList());
            BigDecimal totalPayAmount = BigDecimal.ZERO;
            for (CombineTransaction paymentCombineTransaction : paymentCombineTransactions) {
                totalPayAmount = totalPayAmount.add(paymentCombineTransaction.getAmount());
            }
            if (totalPayAmount.compareTo(customerOrder.getTotal()) == 0) {
                nextOrderStatus = OrderStatus.PAYMENT_COMPLETED;
            }
        }
        return nextOrderStatus;
    }

    @Override
    public ReadableCombineTransaction saveNicepayToken(Customer customer, CustomerOrder customerOrder, String ncToken, MerchantStore store, Language language) throws ServiceException, ConversionException {
        Payment payment = new Payment();
        payment.setPaymentType(PaymentType.NICEPAY);
        payment.setTransactionType(TransactionType.AUTHORIZE);
        payment.setModuleName("Nicepay");

        Map<String,String> paymentMetadata = new HashMap<>();
        paymentMetadata.put("nctoken", ncToken);

        payment.setPaymentMetaData(paymentMetadata);

        CombineTransaction combineTransaction = combinePaymentService.processPaymentNextTransaction(customerOrder, null, customer, store, payment);

        ReadableCombineTransaction transaction = new ReadableCombineTransaction();

        trxPopulator.populate(combineTransaction, transaction, store, language);

        return transaction;
    }
}
