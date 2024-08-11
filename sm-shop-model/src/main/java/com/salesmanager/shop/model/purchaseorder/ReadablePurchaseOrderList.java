package com.salesmanager.shop.model.purchaseorder;

import com.salesmanager.shop.model.entity.ReadableList;

import java.util.List;

public class ReadablePurchaseOrderList extends ReadableList {

    private List<ReadablePurchaseOrder> purchaseOrders;

    public List<ReadablePurchaseOrder> getPurchaseOrders() {
        return purchaseOrders;
    }

    public void setPurchaseOrders(List<ReadablePurchaseOrder> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }
}
