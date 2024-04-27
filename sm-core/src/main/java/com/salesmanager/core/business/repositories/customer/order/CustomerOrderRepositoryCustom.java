package com.salesmanager.core.business.repositories.customer.order;

import com.salesmanager.core.model.customer.order.CustomerOrderCriteria;
import com.salesmanager.core.model.customer.order.CustomerOrderList;

public interface CustomerOrderRepositoryCustom {

    CustomerOrderList listCustomerOrders(CustomerOrderCriteria criteria);
}
