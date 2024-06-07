package com.salesmanager.core.business.services.payments.combine;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.customer.order.CustomerOrder;
import com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.payments.CombineTransaction;
import com.salesmanager.core.model.payments.Payment;
import com.salesmanager.core.model.payments.PaymentMethod;
import com.salesmanager.core.model.system.IntegrationConfiguration;
import com.salesmanager.core.model.system.IntegrationModule;
import com.salesmanager.core.modules.integration.payment.model.CombinePaymentModule;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 合并支付
 */
public interface CombinePaymentService {

    List<IntegrationModule> getPaymentMethods(MerchantStore store) throws ServiceException;

    Map<String, IntegrationConfiguration> getCombinePaymentModulesConfigured(MerchantStore store) throws ServiceException;

    CombineTransaction processPayment(MerchantStore store, Customer customer, List<CustomerShoppingCartItem> items, Payment payment, CustomerOrder customerOrder) throws ServiceException;

    CombineTransaction processRefund(CustomerOrder customerOrder, Customer customer, MerchantStore store, BigDecimal amount) throws ServiceException;

    IntegrationModule getPaymentMethodByCode(MerchantStore store, String name) throws ServiceException;

    void savePaymentModuleConfiguration(IntegrationConfiguration configuration, MerchantStore store) throws ServiceException;

    IntegrationConfiguration getPaymentConfiguration(String moduleCode, MerchantStore store) throws ServiceException;

    void removePaymentModuleConfiguration(String moduleCode, MerchantStore store) throws ServiceException;

    CombineTransaction processCapturePayment(CustomerOrder customerOrder, Customer customer, MerchantStore store) throws ServiceException;

    CombineTransaction processAuthorizeAndCapturePayment(CustomerOrder customerOrder, Customer customer, MerchantStore store) throws ServiceException;

    CombineTransaction processPostPayment(CustomerOrder customerOrder, Customer customer, MerchantStore store, Payment payment) throws ServiceException;

    CombineTransaction initTransaction(CustomerOrder customerOrder, Customer customer, Payment payment, MerchantStore store ) throws ServiceException;

//    CombineTransaction initTransaction(Customer customer, Payment payment, MerchantStore store) throws ServiceException;

    List<PaymentMethod> getAcceptedPaymentMethods(MerchantStore merchantStore) throws ServiceException;

    CombineTransaction processPaymentNextTransaction(CustomerOrder customerOrder, Customer customer, MerchantStore store, Payment payment) throws ServiceException;

    CombinePaymentModule getPaymentModule(String paymentModuleCode) throws ServiceException;

//    CombineTransaction processPayment(Customer customer, Payment payment, CustomerOrder customerOrder, MerchantStore store) throws ServiceException;

}
