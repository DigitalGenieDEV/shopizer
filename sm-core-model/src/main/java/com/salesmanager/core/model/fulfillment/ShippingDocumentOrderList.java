package com.salesmanager.core.model.fulfillment;

import com.salesmanager.core.model.common.EntityList;
import com.salesmanager.core.model.order.orderproduct.OrderProduct;

import java.util.List;

public class ShippingDocumentOrderList extends EntityList {

    /**
     *
     */
    private static final long serialVersionUID = -6645927228659963628L;

    private List<ShippingDocumentOrder> shippingDocumentOrders;

    public List<ShippingDocumentOrder> getShippingDocumentOrders() {
        return shippingDocumentOrders;
    }

    public void setShippingDocumentOrders(List<ShippingDocumentOrder> shippingDocumentOrders) {
        this.shippingDocumentOrders = shippingDocumentOrders;
    }
}
