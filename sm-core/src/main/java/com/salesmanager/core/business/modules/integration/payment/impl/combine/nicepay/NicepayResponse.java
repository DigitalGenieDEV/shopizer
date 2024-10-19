package com.salesmanager.core.business.modules.integration.payment.impl.combine.nicepay;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
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
    private List<Cancel> cancels;
    private List<Object> cashReceipts;


    @Getter
    @Setter
    @ToString
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
    }

    @Getter
    @Setter
    @ToString
    public static class Cancel {
        //         "tid": "UT0000104m01012303141557092002",
        //         "amount": 1004,
        //         "cancelledAt": "2023-03-14T16:37:24.000+0900",
        //         "reason": "고객요청",
        //         "receiptUrl": "https://npg.nicepay.co.kr/issue/IssueLoader.do?type=0&innerWin=Y&TID=UT0000104m01012303141557092002",
        //         "couponAmt": 0
        private String tid;
        private Integer amount;
        private String cancelledAt;
        private String reason;
        private String receiptUrl;
        private Integer couponAmt;
    }

}
