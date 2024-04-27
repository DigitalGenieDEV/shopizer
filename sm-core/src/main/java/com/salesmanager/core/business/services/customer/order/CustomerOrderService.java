package com.salesmanager.core.business.services.customer.order;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.customer.order.*;
import com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCart;
import com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem;
import com.salesmanager.core.model.payments.Payment;
import com.salesmanager.core.model.reference.language.Language;

import java.util.List;

public interface CustomerOrderService extends SalesManagerEntityService<Long, CustomerOrder> {

    CustomerOrderTotalSummary calculateCustomerOrderTotal(CustomerOrderSummary customerOrderSummary, Customer customer, Language language) throws ServiceException;

    CustomerOrderTotalSummary calculateCustomerOrder(CustomerOrderSummary customerOrderSummary, Customer customer, Language language) throws Exception;

    CustomerOrderTotalSummary calculateCustomerOrderTotal(CustomerOrderSummary customerOrderSummary, Language language) throws ServiceException;

    CustomerOrderTotalSummary calculateCustomerShoppingCartTotal(final CustomerShoppingCart cart, final Customer customer, final Language language) throws ServiceException;

    CustomerOrderTotalSummary calculateCustomerShoppingCartTotal(final CustomerShoppingCart cart, final Language language) throws ServiceException;

    CustomerOrder getCustomerOrder(Long id);

    CustomerOrderList getCustomerOrders(CustomerOrderCriteria criteria);

    void saveOrUpdate(CustomerOrder customerOrder) throws ServiceException;

    CustomerOrder processCustomerOrder(CustomerOrder customerOrder, Customer customer, List<CustomerShoppingCartItem> items, Payment payment) throws ServiceException;


}
