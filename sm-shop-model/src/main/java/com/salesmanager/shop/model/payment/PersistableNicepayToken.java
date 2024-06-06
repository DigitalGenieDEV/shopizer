package com.salesmanager.shop.model.payment;

public class PersistableNicepayToken {

    private Long orderId;

    private String token;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
