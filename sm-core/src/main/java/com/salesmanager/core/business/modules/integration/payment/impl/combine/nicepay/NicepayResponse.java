package com.salesmanager.core.business.modules.integration.payment.impl.combine.nicepay;

import java.util.List;

public class NicepayResponse {
    private String resultCode;
    private String resultMsg;
    private String tid;
    private String cancelledTid;
    private String orderId;
    private String ediDate;
    private String signature;
    private String status;
    private String paidAt;
    private String failedAt;
    private String cancelledAt;
    private String payMethod;
    private int amount;
    private int balanceAmt;
    private String goodsName;
    private String mallReserved;
    private boolean useEscrow;
    private String currency;
    private String channel;
    private String approveNo;
    private String buyerName;
    private String buyerTel;
    private String buyerEmail;
    private String receiptUrl;
    private String mallUserId;
    private boolean issuedCashReceipt;
    private String coupon;
    private Card card;
    private String vbank;
    private List<Object> cancels;
    private List<Object> cashReceipts;


    public static class Card {
        private String cardCode;
        private String cardName;
        private String cardNum;
        private int cardQuota;
        private boolean isInterestFree;
        private String cardType;
        private boolean canPartCancel;
        private String acquCardCode;
        private String acquCardName;

        public String getCardCode() {
            return cardCode;
        }

        public void setCardCode(String cardCode) {
            this.cardCode = cardCode;
        }

        public String getCardName() {
            return cardName;
        }

        public void setCardName(String cardName) {
            this.cardName = cardName;
        }

        public String getCardNum() {
            return cardNum;
        }

        public void setCardNum(String cardNum) {
            this.cardNum = cardNum;
        }

        public int getCardQuota() {
            return cardQuota;
        }

        public void setCardQuota(int cardQuota) {
            this.cardQuota = cardQuota;
        }

        public boolean isInterestFree() {
            return isInterestFree;
        }

        public void setInterestFree(boolean interestFree) {
            isInterestFree = interestFree;
        }

        public String getCardType() {
            return cardType;
        }

        public void setCardType(String cardType) {
            this.cardType = cardType;
        }

        public boolean isCanPartCancel() {
            return canPartCancel;
        }

        public void setCanPartCancel(boolean canPartCancel) {
            this.canPartCancel = canPartCancel;
        }

        public String getAcquCardCode() {
            return acquCardCode;
        }

        public void setAcquCardCode(String acquCardCode) {
            this.acquCardCode = acquCardCode;
        }

        public String getAcquCardName() {
            return acquCardName;
        }

        public void setAcquCardName(String acquCardName) {
            this.acquCardName = acquCardName;
        }
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getCancelledTid() {
        return cancelledTid;
    }

    public void setCancelledTid(String cancelledTid) {
        this.cancelledTid = cancelledTid;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getEdiDate() {
        return ediDate;
    }

    public void setEdiDate(String ediDate) {
        this.ediDate = ediDate;
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

    public String getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(String paidAt) {
        this.paidAt = paidAt;
    }

    public String getFailedAt() {
        return failedAt;
    }

    public void setFailedAt(String failedAt) {
        this.failedAt = failedAt;
    }

    public String getCancelledAt() {
        return cancelledAt;
    }

    public void setCancelledAt(String cancelledAt) {
        this.cancelledAt = cancelledAt;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getBalanceAmt() {
        return balanceAmt;
    }

    public void setBalanceAmt(int balanceAmt) {
        this.balanceAmt = balanceAmt;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getMallReserved() {
        return mallReserved;
    }

    public void setMallReserved(String mallReserved) {
        this.mallReserved = mallReserved;
    }

    public boolean isUseEscrow() {
        return useEscrow;
    }

    public void setUseEscrow(boolean useEscrow) {
        this.useEscrow = useEscrow;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getApproveNo() {
        return approveNo;
    }

    public void setApproveNo(String approveNo) {
        this.approveNo = approveNo;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerTel() {
        return buyerTel;
    }

    public void setBuyerTel(String buyerTel) {
        this.buyerTel = buyerTel;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public String getReceiptUrl() {
        return receiptUrl;
    }

    public void setReceiptUrl(String receiptUrl) {
        this.receiptUrl = receiptUrl;
    }

    public String getMallUserId() {
        return mallUserId;
    }

    public void setMallUserId(String mallUserId) {
        this.mallUserId = mallUserId;
    }

    public boolean isIssuedCashReceipt() {
        return issuedCashReceipt;
    }

    public void setIssuedCashReceipt(boolean issuedCashReceipt) {
        this.issuedCashReceipt = issuedCashReceipt;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public String getVbank() {
        return vbank;
    }

    public void setVbank(String vbank) {
        this.vbank = vbank;
    }

    public List<Object> getCancels() {
        return cancels;
    }

    public void setCancels(List<Object> cancels) {
        this.cancels = cancels;
    }

    public List<Object> getCashReceipts() {
        return cashReceipts;
    }

    public void setCashReceipts(List<Object> cashReceipts) {
        this.cashReceipts = cashReceipts;
    }
}
