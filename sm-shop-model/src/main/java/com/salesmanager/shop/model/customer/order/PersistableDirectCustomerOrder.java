package com.salesmanager.shop.model.customer.order;

import com.salesmanager.shop.model.order.v1.Order;

public class PersistableDirectCustomerOrder extends PersistableCustomerOrder {

    private int quantity;

    private String sku;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
}
