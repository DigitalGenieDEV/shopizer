package com.salesmanager.core.business.services.customer.order;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.customer.order.CustomerOrderRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.business.services.customer.shoppingcart.CustomerShoppingCartService;
import com.salesmanager.core.business.services.merchant.MerchantStoreService;
import com.salesmanager.core.business.services.order.OrderService;
import com.salesmanager.core.business.services.payments.PaymentService;
import com.salesmanager.core.business.services.payments.TransactionService;
import com.salesmanager.core.business.services.payments.combine.CombinePaymentService;
import com.salesmanager.core.business.services.payments.combine.CombineTransactionService;
import com.salesmanager.core.model.common.UserContext;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.customer.order.*;
import com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCart;
import com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.Order;
import com.salesmanager.core.model.order.orderstatus.OrderStatus;
import com.salesmanager.core.model.order.orderstatus.OrderStatusHistory;
import com.salesmanager.core.model.payments.CombineTransaction;
import com.salesmanager.core.model.payments.Payment;
import com.salesmanager.core.model.payments.Transaction;
import com.salesmanager.core.model.payments.TransactionType;
import com.salesmanager.core.model.reference.language.Language;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@Service("customerOrderService")
public class CustomerOrderServiceImpl extends SalesManagerEntityServiceImpl<Long, CustomerOrder> implements CustomerOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerOrderServiceImpl.class);

    @Inject
    private CustomerService customerService;

    @Inject
    private CustomerShoppingCartService customerShoppingCartService;

    @Inject
    private PaymentService paymentService;

    @Inject
    private CombinePaymentService combinePaymentService;

    @Inject
    private CombineTransactionService combineTransactionService;

    @Inject
    private TransactionService transactionService;

    @Inject
    private OrderService orderService;

    @Inject
    private MerchantStoreService merchantStoreService;

    private final CustomerOrderRepository customerOrderRepository;

    @Inject
    public CustomerOrderServiceImpl(CustomerOrderRepository customerOrderRepository) {
        super(customerOrderRepository);
        this.customerOrderRepository = customerOrderRepository;
    }

    @Override
    public CustomerOrderTotalSummary calculateCustomerOrderTotal(CustomerOrderSummary customerOrderSummary, Customer customer, Language language) throws ServiceException {
        return null;
    }

    @Override
    public CustomerOrderTotalSummary calculateCustomerOrder(CustomerOrderSummary customerOrderSummary, Customer customer, Language language) throws Exception {
        return null;
    }

    @Override
    public CustomerOrderTotalSummary calculateCustomerOrderTotal(CustomerOrderSummary customerOrderSummary, Language language) throws ServiceException {
        return null;
    }

    @Override
    public CustomerOrderTotalSummary calculateCustomerShoppingCartTotal(CustomerShoppingCart cart, Customer customer, Language language) throws ServiceException {
        return null;
    }

    @Override
    public CustomerOrderTotalSummary calculateCustomerShoppingCartTotal(CustomerShoppingCart cart, Language language) throws ServiceException {
        return null;
    }

    @Override
    public CustomerOrder getCustomerOrder(Long id) {
        Validate.notNull(id, "CustomerOrder id cannot be null");
        return customerOrderRepository.findOne(id);
    }

    @Override
    public CustomerOrderList getCustomerOrders(CustomerOrderCriteria criteria) {
        return customerOrderRepository.listCustomerOrders(criteria);
    }

    @Override
    public void saveOrUpdate(CustomerOrder customerOrder) throws ServiceException {

    }

    @Override
    public void updateCustomerOrderStatus(CustomerOrder customerOrder, OrderStatus orderStatus) throws ServiceException {
        customerOrder.setStatus(orderStatus);
        saveOrUpdate(customerOrder);

        List<Order> orders = customerOrder.getOrders();

        for (Order order : orders) {
            OrderStatusHistory orderHistory = new OrderStatusHistory();
            orderHistory.setOrder(order);
            orderHistory.setStatus(OrderStatus.PROCESSED);
            orderHistory.setDateAdded(new Date());

            orderService.addOrderStatusHistory(order, orderHistory);

            order.setStatus(OrderStatus.PROCESSED);
            orderService.saveOrUpdate(order);
        }
    }

    @Override
    public CustomerOrder processCustomerOrder(CustomerOrder customerOrder, Customer customer, List<CustomerShoppingCartItem> items, Payment payment) throws ServiceException {
        return process(customerOrder, customer, items, payment, null);
    }

    private MerchantStore defaultStore() {
        return merchantStoreService.getById(1);
    }

    private CustomerOrder process(CustomerOrder customerOrder, Customer customer, List<CustomerShoppingCartItem> items, Payment payment, CombineTransaction combineTransaction) throws ServiceException {

        Validate.notNull(customerOrder, "CustomerOrder cannot be null");
        Validate.notNull(customer, "Customer cannot be null (even if anonymous order)");
        Validate.notEmpty(items, "CustomerShoppingCart items cannot be null");
        Validate.notNull(payment, "Payment cannot be null");

        UserContext context = UserContext.getCurrentInstance();
        if(context != null) {
            String ipAddress = context.getIpAddress();
            if(!StringUtils.isBlank(ipAddress)) {
                customerOrder.setIpAddress(ipAddress);
            }
        }


        // 合并支付处理
        CombineTransaction processCombineTransaction = combinePaymentService.processPayment(defaultStore(), customer, items, payment, customerOrder);

        this.create(customerOrder);

        if (combineTransaction != null) {
            combineTransaction.setCustomerOrder(customerOrder);
            if (combineTransaction.getId() == null || combineTransaction.getId() == 0) {
                combineTransactionService.create(combineTransaction);
            } else {
                combineTransactionService.update(combineTransaction);
            }
        }

        if (processCombineTransaction != null) {
            processCombineTransaction.setCustomerOrder(customerOrder);
            if (processCombineTransaction.getId() == null || processCombineTransaction.getId() == 0) {
                combineTransactionService.create(processCombineTransaction);
            } else {
                combineTransactionService.update(processCombineTransaction);
            }
        }

        // 商户订单合并支付交易流水
        for (Order order : customerOrder.getOrders()) {
            Transaction transaction = new Transaction();
            transaction.setAmount(order.getTotal());
            transaction.setTransactionDate(new Date());
            transaction.setTransactionType(TransactionType.COMBINESTAMP);
            transaction.setPaymentType(payment.getPaymentType());
            transaction.setOrder(order);
            transaction.getTransactionDetails().put("COMBINETX", String.valueOf(processCombineTransaction.getId()));
            transaction.getTransactionDetails().put("CUSTOMERORDER", String.valueOf(customerOrder.getId()));

            transactionService.create(transaction);
        }



        return customerOrder;
    }
}
