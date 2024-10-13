package com.salesmanager.shop.store.controller.customer.facade;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.customer.order.CustomerOrder;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.payments.Payment;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.customer.order.*;
import com.salesmanager.shop.model.customer.order.transaction.ReadableCombineTransaction;

import java.util.Locale;

public interface CustomerOrderFacade {

    CustomerOrder processCustomerOrder(PersistableCustomerOrder customerOrder, Customer customer, Language language, Locale locale) throws ServiceException;

    CustomerOrder processDirectCustomerOrder(PersistableDirectCustomerOrder customerOrder, Customer customer, MerchantStore merchantStore, Language language, Locale locale) throws ServiceException;

    ReadableCustomerOrderConfirmation orderConfirmation(CustomerOrder customerOrder, Customer customer, Language language, Long orderId) throws Exception;

    ReadableCustomerOrderList getReadableCustomerOrderList(Customer customer, int start, int maxCount, Language language) throws Exception;

    ReadableCustomerOrder getReadableCustomerOrder(Long customerOrderId, MerchantStore merchantStore, Language language);

    ReadableCombineTransaction captureCustomerOrder(MerchantStore store, CustomerOrder customerOrder, Customer customer, Language language) throws Exception;

    ReadableCombineTransaction authorizeCaptureCustomerOrder(MerchantStore store, CustomerOrder customerOrder, Customer customer, Language language) throws Exception;

    ReadableCombineTransaction processPostPayment(MerchantStore store, CustomerOrder customerOrder, Customer customer, Payment payment, Language language) throws Exception;
}
