//tmpzk
package com.salesmanager.core.model.catalog.product.dataservice;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BIBuyerInfoResult {

    @JsonProperty("PUR_AMT")
    private Double purchaseAmount;

    @JsonProperty("MEMBER_CNT")
    private Integer memberCount;

    @JsonProperty("BUYER_CNT")
    private Integer buyerCount;

    @JsonProperty("RPUR_MEMBER_CNT")
    private Integer rePurchaseBuyerCount;

    @JsonProperty("RPUR_RATIO")
    private Double rePurchaseRatio;

    @JsonProperty("AVG_RPUR_DAYS")
    private Double avgRePurchaseDays;


    public Double getPurchaseAmount() {
        return purchaseAmount;
    }
    public void setPurchaseAmount(Double purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public Integer getMemberCount() {
        return memberCount;
    }
    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    public Integer getBuyerCount() {
        return buyerCount;
    }
    public void setBuyerCount(Integer buyerCount) {
        this.buyerCount = buyerCount;
    }


    public Integer getRePurchaseBuyerCount() {
        return rePurchaseBuyerCount;
    }
    public void setRePurchaseBuyerCount(Integer rePurchaseBuyerCount) {
        this.rePurchaseBuyerCount = rePurchaseBuyerCount;
    }

    public Double getRePurchaseRatio() {
        return rePurchaseRatio;
    }
    public void setRePurchaseRatio(Double rePurchaseRatio) {
        this.rePurchaseRatio = rePurchaseRatio;
    }

    public Double getAvgRePurchaseDays() {
        return avgRePurchaseDays;
    }
    public void setAvgRePurchaseDays(Double avgRePurchaseDays) { this.avgRePurchaseDays = avgRePurchaseDays; }

}
