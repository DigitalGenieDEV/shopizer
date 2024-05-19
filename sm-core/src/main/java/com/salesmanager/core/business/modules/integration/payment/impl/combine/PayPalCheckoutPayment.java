package com.salesmanager.core.business.modules.integration.payment.impl.combine;

import com.paypal.http.HttpResponse;
import com.paypal.orders.Authorization;
import com.paypal.orders.PurchaseUnit;
import com.paypal.payments.Capture;
import com.paypal.payments.Refund;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.modules.integration.payment.impl.combine.paypal.AuthorizeOrder;
import com.salesmanager.core.business.modules.integration.payment.impl.combine.paypal.CaptureOrder;
import com.salesmanager.core.business.modules.integration.payment.impl.combine.paypal.CreateOrder;
import com.salesmanager.core.business.modules.integration.payment.impl.combine.paypal.RefundOrder;
import com.salesmanager.core.business.services.payments.combine.CombineTransactionService;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.customer.order.CustomerOrder;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.payments.CombineTransaction;
import com.salesmanager.core.model.payments.Payment;
import com.salesmanager.core.model.payments.PaymentType;
import com.salesmanager.core.model.payments.TransactionType;
import com.salesmanager.core.model.system.IntegrationConfiguration;
import com.salesmanager.core.model.system.IntegrationModule;
import com.salesmanager.core.modules.integration.IntegrationException;
import com.salesmanager.core.modules.integration.payment.model.CombinePaymentModule;
import org.apache.commons.lang3.Validate;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Date;

public class PayPalCheckoutPayment implements CombinePaymentModule {


    @Inject
    private CombineTransactionService combineTransactionService;

    @Override
    public void validateModuleConfiguration(IntegrationConfiguration integrationConfiguration, MerchantStore store) throws IntegrationException {
    }

    @Override
    public CombineTransaction initTransaction(MerchantStore store, Customer customer, CustomerOrder customerOrder, BigDecimal amount, Payment payment, IntegrationConfiguration configuration, IntegrationModule module) throws IntegrationException {
        try {
            HttpResponse<com.paypal.orders.Order> orderHttpResponse = new CreateOrder().createOrder("AUTHORIZE",false);
            com.paypal.orders.Order order = null;

            if (orderHttpResponse.statusCode() == 201) {
                order = orderHttpResponse.result();
            }


            CombineTransaction combineTransaction = new CombineTransaction();
            combineTransaction.setAmount(amount);
            combineTransaction.setCustomerOrder(customerOrder);
            combineTransaction.setTransactionDate(new Date());
            combineTransaction.setTransactionType(TransactionType.AUTHORIZE);
            combineTransaction.setPaymentType(PaymentType.PAYPAL);
            combineTransaction.getTransactionDetails().put("PAYPAL_ORDER_ID", order.id());
            combineTransaction.getTransactionDetails().put("PAYPAL_INTENT", order.checkoutPaymentIntent());
            combineTransaction.getTransactionDetails().put("PAYPAL_CREATE_TIME", order.createTime());
            combineTransaction.getTransactionDetails().put("PAYPAL_EXPIRATION_TIME", order.expirationTime());
            return combineTransaction;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public CombineTransaction authorize(MerchantStore store, Customer customer, CustomerOrder customerOrder, BigDecimal amount, Payment payment, IntegrationConfiguration configuration, IntegrationModule module) throws IntegrationException {
        try {
            CombineTransaction transaction = combineTransactionService.lastCombineTransaction(customerOrder);
            String orderId = transaction.getTransactionDetails().get("PAYPAL_ORDER_ID");

            HttpResponse<com.paypal.orders.Order> orderAuthorizeHttpResponse = new AuthorizeOrder().authorizeOrder(orderId, false);

            String authId = "";
            if (orderAuthorizeHttpResponse.statusCode() == 201) {
                com.paypal.orders.Order order = orderAuthorizeHttpResponse.result();

                for (PurchaseUnit purchaseUnit : order.purchaseUnits()) {
                    for (Authorization authorization : purchaseUnit.payments().authorizations()) {
                        authId = authorization.id();
                    }
                }
            }

            CombineTransaction combineTransaction = new CombineTransaction();
            combineTransaction.setAmount(amount);
            combineTransaction.setCustomerOrder(customerOrder);
            combineTransaction.setTransactionDate(new Date());
            combineTransaction.setTransactionType(TransactionType.AUTHORIZE);
            combineTransaction.setPaymentType(PaymentType.PAYPAL);
            combineTransaction.getTransactionDetails().put("PAYPAL_AUTH_ID", authId);
            combineTransaction.getTransactionDetails().put("PAYPAL_ORDER_ID", orderId);
            return combineTransaction;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public CombineTransaction capture(MerchantStore store, Customer customer, CustomerOrder order, CombineTransaction capturableTransaction, IntegrationConfiguration configuration, IntegrationModule module) throws IntegrationException {
        try {
            Validate.notNull(capturableTransaction,"CombineTransaction cannot be null");
            Validate.notNull(capturableTransaction.getTransactionDetails().get("TRANSACTIONID"), "Transaction details must contain a TRANSACTIONID");
            Validate.notNull(order,"CustomerOrder must not be null");
            Validate.notNull(order.getCurrency(),"Order nust contain Currency object");

            String orderId = capturableTransaction.getTransactionDetails().get("PAYPAL_ORDER_ID");
            String authId = capturableTransaction.getTransactionDetails().get("PAYPAL_AUTH_ID");

            HttpResponse<Capture> orderHttpResponse = new CaptureOrder().captureAuthOrder(authId, false);

            String captureId = "";
            if (orderHttpResponse.statusCode() == 201) {
                captureId = orderHttpResponse.result().id();
            }

            CombineTransaction combineTransaction = new CombineTransaction();
            combineTransaction.setAmount(order.getTotal());
            combineTransaction.setCustomerOrder(order);
            combineTransaction.setTransactionType(TransactionType.CAPTURE);
            combineTransaction.setPaymentType(PaymentType.PAYPAL);
            combineTransaction.getTransactionDetails().put("PAYPAL_CAPTURE_ID", captureId);
            combineTransaction.getTransactionDetails().put("PAYPAL_ORDER_ID", orderId);
            return combineTransaction;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public CombineTransaction authorizeAndCapture(MerchantStore store, Customer customer, CustomerOrder customerOrder, BigDecimal amount, Payment payment, IntegrationConfiguration configuration, IntegrationModule module) throws IntegrationException {
        try {
            CombineTransaction transaction = combineTransactionService.lastCombineTransaction(customerOrder);
            String orderId = transaction.getTransactionDetails().get("PAYPAL_ORDER_ID");

            if (orderId == null) {
                throw new ServiceException("PayPal Order Id not exists");
            }

            HttpResponse<com.paypal.orders.Order> orderAuthorizeHttpResponse = new AuthorizeOrder().authorizeOrder(orderId, false);

            String authId = "";
            if (orderAuthorizeHttpResponse.statusCode() == 201) {
                com.paypal.orders.Order order = orderAuthorizeHttpResponse.result();

                for (PurchaseUnit purchaseUnit : order.purchaseUnits()) {
                    for (Authorization authorization : purchaseUnit.payments().authorizations()) {
                        authId = authorization.id();
                    }
                }
            }

            HttpResponse<Capture> orderHttpResponse = new CaptureOrder().captureAuthOrder(authId, false);

            String captureId = "";
            if (orderHttpResponse.statusCode() == 201) {
                captureId = orderHttpResponse.result().id();
            }


            CombineTransaction combineTransaction = new CombineTransaction();
            combineTransaction.setAmount(amount);
            combineTransaction.setCustomerOrder(customerOrder);
            combineTransaction.setTransactionDate(new Date());
            combineTransaction.setTransactionType(TransactionType.AUTHORIZECAPTURE);
            combineTransaction.setPaymentType(PaymentType.PAYPAL);
            combineTransaction.getTransactionDetails().put("PAYPAL_AUTH_ID", authId);
            combineTransaction.getTransactionDetails().put("PAYPAL_CAPTURE_ID", captureId);
            combineTransaction.getTransactionDetails().put("PAYPAL_ORDER_ID", orderId);
            return combineTransaction;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public CombineTransaction refund(boolean partial, MerchantStore store, CombineTransaction transaction, CustomerOrder order, BigDecimal amount, IntegrationConfiguration configuration, IntegrationModule module) throws IntegrationException {
        try {
            String orderId = transaction.getTransactionDetails().get("PAYPAL_ORDER_ID");
            String captureId = transaction.getTransactionDetails().get("PAYPAL_CAPTURE_ID");
            HttpResponse<Refund> refundHttpResponse = new RefundOrder().refundOrder(captureId, amount,false);

            Refund refund = null;
            if (refundHttpResponse.statusCode() == 201) {
                refund = refundHttpResponse.result();
            }

            CombineTransaction combineTransaction = new CombineTransaction();
            combineTransaction.setAmount(amount);
            combineTransaction.setCustomerOrder(order);
            combineTransaction.setTransactionDate(new Date());
            combineTransaction.setTransactionType(TransactionType.REFUND);
            combineTransaction.setPaymentType(PaymentType.PAYPAL);
            combineTransaction.getTransactionDetails().put("PAYPAL_ORDER_ID", orderId);
            combineTransaction.getTransactionDetails().put("PAYPAL_CAPTURE_ID", captureId);
            combineTransaction.getTransactionDetails().put("PAYPAL_REFUND_ID", refund.id());
            combineTransaction.getTransactionDetails().put("PAYPAL_REFUND_STATUS", refund.status());
            return combineTransaction;
        } catch (Exception e) {
        }
        return null;
    }
}
