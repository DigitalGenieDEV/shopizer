//tmpzk
package com.salesmanager.core.model.catalog.product.dataservice;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BIBuyerInfoRequest {

    @JsonProperty("the_last_n_days")
    private Integer lastNDays;

    @JsonProperty("begin_date")
    private String beginDate;

    @JsonProperty("end_date")
    private String endData;

    @JsonProperty("user_tag")
    private String userTag;

    public Integer getLastNDays() {
        return lastNDays;
    }
    public void setLastNDays(Integer lastNDays) {
        this.lastNDays = lastNDays;
    }

    public String getBeginDate() {
        return beginDate;
    }
    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndData() {
        return endData;
    }
    public void setEndData(String endData) {
        this.endData = endData;
    }

    public String getUserTag() {
        return userTag;
    }
    public void setUserTag(String userTag) {
        this.userTag = userTag;
    }
}
