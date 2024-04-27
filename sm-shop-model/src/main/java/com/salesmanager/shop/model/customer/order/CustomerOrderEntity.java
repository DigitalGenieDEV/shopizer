package com.salesmanager.shop.model.customer.order;

import com.salesmanager.core.model.payments.PaymentType;
import com.salesmanager.shop.model.entity.Entity;

import java.io.Serializable;

public class CustomerOrderEntity extends Entity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private PaymentType paymentType;
    private String paymentModule;
    private String shippingModule;

}
