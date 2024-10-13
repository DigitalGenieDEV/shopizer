package com.salesmanager.core.modules.integration.payment.model;

import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.customer.order.CustomerOrder;
import com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.Order;
import com.salesmanager.core.model.payments.CombineTransaction;
import com.salesmanager.core.model.payments.Payment;
import com.salesmanager.core.model.payments.Transaction;
import com.salesmanager.core.model.shoppingcart.ShoppingCartItem;
import com.salesmanager.core.model.system.IntegrationConfiguration;
import com.salesmanager.core.model.system.IntegrationModule;
import com.salesmanager.core.modules.integration.IntegrationException;

import java.math.BigDecimal;
import java.util.List;

public interface CombinePaymentModule {

    /**
     *
     * validate payment module configuration
     *
     * @param integrationConfiguration
     * @param store
     * @throws IntegrationException
     */
    public void validateModuleConfiguration(IntegrationConfiguration integrationConfiguration, MerchantStore store) throws IntegrationException;

    /**
     *
     * init payment transaction
     *
     * @param store
     * @param customer
     * @param order
     * @param amount
     * @param payment
     * @param configuration
     * @param module
     * @return
     * @throws IntegrationException
     */
    public CombineTransaction initTransaction(MerchantStore store, Customer customer, CustomerOrder order, BigDecimal amount, Payment payment, IntegrationConfiguration configuration, IntegrationModule module)
            throws IntegrationException;

    /**
     * authorize payment transaction
     *
     * @param store
     * @param customer
     * @param order
     * @param amount
     * @param payment
     * @param configuration
     * @param module
     * @return
     * @throws IntegrationException
     */
    public CombineTransaction authorize(
            MerchantStore store, Customer customer, CustomerOrder order, BigDecimal amount, Payment payment, IntegrationConfiguration configuration, IntegrationModule module)
            throws IntegrationException;

    /**
     * capture payment transaction
     *
     * @param store
     * @param customer
     * @param order
     * @param capturableTransaction
     * @param configuration
     * @param module
     * @return
     * @throws IntegrationException
     */
    public CombineTransaction capture(
            MerchantStore store, Customer customer, CustomerOrder order, BigDecimal amount, CombineTransaction capturableTransaction, IntegrationConfiguration configuration, IntegrationModule module)
            throws IntegrationException;

    /**
     * authorize and capture payment transaction
     *
     * @param store
     * @param customer
     * @param order
     * @param amount
     * @param payment
     * @param configuration
     * @param module
     * @return
     * @throws IntegrationException
     */
    public CombineTransaction authorizeAndCapture(
            MerchantStore store, Customer customer, CustomerOrder order, BigDecimal amount, Payment payment, IntegrationConfiguration configuration, IntegrationModule module)
            throws IntegrationException;

    /**
     *
     * refund payment transaction
     *
     * @param partial
     * @param store
     * @param transaction
     * @param order
     * @param amount
     * @param configuration
     * @param module
     * @return
     * @throws IntegrationException
     */
    public CombineTransaction refund(
            boolean partial, MerchantStore store, CombineTransaction transaction, CustomerOrder order, BigDecimal amount, IntegrationConfiguration configuration, IntegrationModule module)
            throws IntegrationException;
}
