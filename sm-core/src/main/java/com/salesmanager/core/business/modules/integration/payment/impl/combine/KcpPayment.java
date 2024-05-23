package com.salesmanager.core.business.modules.integration.payment.impl.combine;

import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.customer.order.CustomerOrder;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.payments.CombineTransaction;
import com.salesmanager.core.model.payments.Payment;
import com.salesmanager.core.model.system.IntegrationConfiguration;
import com.salesmanager.core.model.system.IntegrationModule;
import com.salesmanager.core.modules.integration.IntegrationException;
import com.salesmanager.core.modules.integration.payment.model.CombinePaymentModule;

import java.math.BigDecimal;

public class KcpPayment implements CombinePaymentModule {

    @Override
    public void validateModuleConfiguration(IntegrationConfiguration integrationConfiguration, MerchantStore store) throws IntegrationException {

    }

    @Override
    public CombineTransaction initTransaction(MerchantStore store, Customer customer, CustomerOrder order, BigDecimal amount, Payment payment, IntegrationConfiguration configuration, IntegrationModule module) throws IntegrationException {
        return null;
    }

    @Override
    public CombineTransaction authorize(MerchantStore store, Customer customer, CustomerOrder order, BigDecimal amount, Payment payment, IntegrationConfiguration configuration, IntegrationModule module) throws IntegrationException {
        return null;
    }

    @Override
    public CombineTransaction capture(MerchantStore store, Customer customer, CustomerOrder order, CombineTransaction capturableTransaction, IntegrationConfiguration configuration, IntegrationModule module) throws IntegrationException {
        return null;
    }

    @Override
    public CombineTransaction authorizeAndCapture(MerchantStore store, Customer customer, CustomerOrder order, BigDecimal amount, Payment payment, IntegrationConfiguration configuration, IntegrationModule module) throws IntegrationException {
        return null;
    }

    @Override
    public CombineTransaction refund(boolean partial, MerchantStore store, CombineTransaction transaction, CustomerOrder order, BigDecimal amount, IntegrationConfiguration configuration, IntegrationModule module) throws IntegrationException {
        return null;
    }
}
