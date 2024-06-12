package com.salesmanager.shop.model.order.v1;

import com.salesmanager.shop.model.entity.ReadableList;
import com.salesmanager.shop.model.order.ReadableOrderProduct;

import java.io.Serializable;
import java.util.List;

public class ReadableOrderProductList extends ReadableList implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<ReadableOrderProduct> orderProducts;

    public List<ReadableOrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<ReadableOrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }
}
