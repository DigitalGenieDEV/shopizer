package com.salesmanager.shop.model.customer.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.shop.model.order.shipping.PersistableDeliveryAddress;
import com.salesmanager.shop.model.order.transaction.PersistablePayment;
import com.salesmanager.shop.model.order.v1.Order;

public class PersistableCustomerOrder extends Order {


    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private PersistablePayment payment;

    private PersistableDeliveryAddress address;

    private Long shippingQuote;
    @JsonIgnore
    private Long customerShoppingCartId;
    @JsonIgnore
    private Long customerId;


    public PersistablePayment getPayment() {
        return payment;
    }

    public void setPayment(PersistablePayment payment) {
        this.payment = payment;
    }

    public Long getShippingQuote() {
        return shippingQuote;
    }

    public void setShippingQuote(Long shippingQuote) {
        this.shippingQuote = shippingQuote;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerShoppingCartId() {
        return customerShoppingCartId;
    }

    public void setCustomerShoppingCartId(Long customerShoppingCartId) {
        this.customerShoppingCartId = customerShoppingCartId;
    }

    public PersistableDeliveryAddress getAddress() {
        return address;
    }

    public void setAddress(PersistableDeliveryAddress address) {
        this.address = address;
    }
}
