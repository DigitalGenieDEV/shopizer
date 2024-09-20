package com.salesmanager.core.model.catalog.product.dataservice;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BIMemberInfoResult {

    @JsonProperty("ALL_MEMBERS_NUM")
    private Integer allMembersNum;

    @JsonProperty("PERSONAL_MEMBERS_NUM")
    private Integer personalMembersNum;

    @JsonProperty("CORP_MEMBERS_NUM")
    private Integer corpMembersNum;

    @JsonProperty("CORP_PURCHASE_MEMBERS_NUM")
    private Integer corpPurchaseMembersNum;

    @JsonProperty("CORP_SALE_MEMBERS_NUM")
    private Integer corpSaleMembersNum;

    @JsonProperty("BIZ_PARTNERS_NUM")
    private Integer bizPartnersNum;

    public Integer getAllMembersNum() {
        return allMembersNum;
    }
    public void setAllMembersNum(Integer allMembersNum) {
        this.allMembersNum = allMembersNum;
    }

    public Integer getPersonalMembersNum() {
        return personalMembersNum;
    }
    public void setPersonalMembersNum(Integer personalMembersNum) {
        this.personalMembersNum = personalMembersNum;
    }

    public Integer getCorpMembersNum() {
        return corpMembersNum;
    }
    public void setCorpMembersNum(Integer corpMembersNum) {
        this.corpMembersNum = corpMembersNum;
    }

    public Integer getCorpPurchaseMembersNum() {
        return corpPurchaseMembersNum;
    }
    public void setCorpPurchaseMembersNum(Integer corpPurchaseMembersNum) {
        this.corpPurchaseMembersNum = corpPurchaseMembersNum;
    }

    public Integer getCorpSaleMembersNum() {
        return corpSaleMembersNum;
    }
    public void setCorpSaleMembersNum(Integer corpSaleMembersNum) {
        this.corpSaleMembersNum = corpSaleMembersNum;
    }

    public Integer getBizPartnersNum() {
        return bizPartnersNum;
    }
    public void setBizPartnersNum(Integer bizPartnersNum) { this.bizPartnersNum = bizPartnersNum; }

}
