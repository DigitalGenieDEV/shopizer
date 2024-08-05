package com.salesmanager.shop.model.crossorder;

public class SupplierCrossOrderLogisticsMsg {

    private Long orderId;

    private String currentStatus;

    private String msgSendTime;

    private String buyerMemberId;

    private String sellerMemberId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getMsgSendTime() {
        return msgSendTime;
    }

    public void setMsgSendTime(String msgSendTime) {
        this.msgSendTime = msgSendTime;
    }

    public String getBuyerMemberId() {
        return buyerMemberId;
    }

    public void setBuyerMemberId(String buyerMemberId) {
        this.buyerMemberId = buyerMemberId;
    }

    public String getSellerMemberId() {
        return sellerMemberId;
    }

    public void setSellerMemberId(String sellerMemberId) {
        this.sellerMemberId = sellerMemberId;
    }
}
