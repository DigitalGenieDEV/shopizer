package com.salesmanager.core.model.customer.order;

import com.salesmanager.core.model.common.EntityList;

import java.util.List;

public class CustomerOrderList extends EntityList {

    private static final long serialVersionUID = -5566665949958066609L;

    private List<CustomerOrder> customerOrders;

    public List<CustomerOrder> getCustomerOrders() {
        return customerOrders;
    }

    public void setCustomerOrders(List<CustomerOrder> customerOrders) {
        this.customerOrders = customerOrders;
    }
}
