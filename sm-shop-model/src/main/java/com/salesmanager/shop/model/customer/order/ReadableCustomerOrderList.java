package com.salesmanager.shop.model.customer.order;

import com.salesmanager.shop.model.entity.ReadableList;

import java.io.Serializable;
import java.util.List;

public class ReadableCustomerOrderList extends ReadableList implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private List<ReadableCustomerOrder> customerOrders;

    public List<ReadableCustomerOrder> getCustomerOrders() {
        return customerOrders;
    }

    public void setCustomerOrders(List<ReadableCustomerOrder> customerOrders) {
        this.customerOrders = customerOrders;
    }
}
