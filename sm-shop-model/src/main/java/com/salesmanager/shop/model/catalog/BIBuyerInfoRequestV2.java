package com.salesmanager.shop.model.catalog;

import java.io.Serializable;

public class BIBuyerInfoRequestV2 implements Serializable  {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Integer lastNDays;
    private String beginData;
    private String endData;
    private String userTag;

    public Integer getLastNDays() {return lastNDays;}
    public void setLastNDays(Integer lastNDays) {this.lastNDays = lastNDays;}

    public String getBeginData() {return beginData;}
    public void setBeginData(String beginData) {this.beginData = beginData;}

    public String getEndData() {return endData;}
    public void setEndData(String endData) {this.endData = endData;}

    public String getUserTag() {return userTag;}
    public void setUserTag(String userTag) {this.userTag = userTag;}
}
