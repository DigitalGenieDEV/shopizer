package com.salesmanager.shop.model.purchaseorder;

import com.salesmanager.shop.model.order.PersistableOrderProduct;

public class PersistablePurchaseOrder {

    private PersistableOrderProduct[] orderProducts;

    public PersistableOrderProduct[] getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(PersistableOrderProduct[] orderProducts) {
        this.orderProducts = orderProducts;
    }
}
