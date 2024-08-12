package com.salesmanager.shop.model.order.shipping;

import com.salesmanager.shop.model.customer.DeliveryEntity;

public class PersistableDeliveryAddress extends DeliveryEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
