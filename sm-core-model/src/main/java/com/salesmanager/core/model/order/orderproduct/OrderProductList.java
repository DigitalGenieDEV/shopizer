package com.salesmanager.core.model.order.orderproduct;

import com.salesmanager.core.model.common.EntityList;

import java.util.List;

public class OrderProductList extends EntityList {

    /**
     *
     */
    private static final long serialVersionUID = -6645927228659963628L;

    private List<OrderProduct> orderProducts;

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }
}
