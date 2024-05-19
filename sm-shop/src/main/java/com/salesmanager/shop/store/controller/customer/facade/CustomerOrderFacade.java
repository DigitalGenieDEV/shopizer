package com.salesmanager.shop.store.controller.customer.facade;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.customer.order.CustomerOrder;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.customer.order.PersistableCustomerOrder;
import com.salesmanager.shop.model.customer.order.ReadableCustomerOrder;
import com.salesmanager.shop.model.customer.order.ReadableCustomerOrderConfirmation;
import com.salesmanager.shop.model.customer.order.ReadableCustomerOrderList;
import com.salesmanager.shop.model.customer.order.transaction.ReadableCombineTransaction;
import com.salesmanager.shop.model.order.transaction.ReadableTransaction;

import java.util.Locale;

public interface CustomerOrderFacade {

    CustomerOrder processCustomerOrder(PersistableCustomerOrder customerOrder, Customer customer, Language language, Locale locale) throws ServiceException;

    ReadableCustomerOrderConfirmation orderConfirmation(CustomerOrder customerOrder, Customer customer, Language language) throws Exception;

    ReadableCustomerOrderList getReadableCustomerOrderList(Customer customer, int start, int maxCount, Language language) throws Exception;

    ReadableCustomerOrder getReadableCustomerOrder(Long customerOrderId, MerchantStore merchantStore, Language language);

    ReadableCombineTransaction captureCustomerOrder(MerchantStore store, CustomerOrder customerOrder, Customer customer, Language language) throws Exception;

    ReadableCombineTransaction authorizeCaptureCustomerOrder(MerchantStore store, CustomerOrder customerOrder, Customer customer, Language language) throws Exception;
}
