package com.salesmanager.shop.store.controller.payment.facade;

import java.util.Map;

public class NicepayAuthorizeResponse {

    private String tid;

    private String orderId;

    private String signature;

    private String status;

    private String ediDate;

    private String paidAt;

    private String amount;

    private String channel;

    private String currency;

    private String rawResp;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEdiDate() {
        return ediDate;
    }

    public void setEdiDate(String ediDate) {
        this.ediDate = ediDate;
    }

    public String getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(String paidAt) {
        this.paidAt = paidAt;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getRawResp() {
        return rawResp;
    }

    public void setRawResp(String rawResp) {
        this.rawResp = rawResp;
    }
}
