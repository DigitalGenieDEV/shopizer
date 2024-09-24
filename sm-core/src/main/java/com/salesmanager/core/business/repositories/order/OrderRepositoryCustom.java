package com.salesmanager.core.business.repositories.order;

import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.OrderCriteria;
import com.salesmanager.core.model.order.OrderCustomerCriteria;
import com.salesmanager.core.model.order.OrderList;

import java.util.Map;


public interface OrderRepositoryCustom {

	OrderList listByStore(MerchantStore store, OrderCriteria criteria);
	OrderList listOrders(MerchantStore store, OrderCriteria criteria);
	OrderList listByCustomer(Customer customer, OrderCustomerCriteria criteria);
	Map<String, Integer> countCustomerOrderByType(Customer customer, OrderCustomerCriteria criteria);
}
