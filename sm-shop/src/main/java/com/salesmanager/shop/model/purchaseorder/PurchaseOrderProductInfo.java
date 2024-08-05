package com.salesmanager.shop.model.purchaseorder;

import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.order.orderproduct.OrderProduct;

public class PurchaseOrderProductInfo {

    private OrderProduct orderProduct;

    private Product product;

    public OrderProduct getOrderProduct() {
        return orderProduct;
    }

    public void setOrderProduct(OrderProduct orderProduct) {
        this.orderProduct = orderProduct;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
