package com.salesmanager.shop.store.controller.customer.facade;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.customer.order.CustomerOrder;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.customer.order.PersistableCustomerOrder;

import java.util.Locale;

public interface CustomerOrderFacade {

    CustomerOrder processCustomerOrder(PersistableCustomerOrder customerOrder, Customer customer, Language language, Locale locale) throws ServiceException;;
}
