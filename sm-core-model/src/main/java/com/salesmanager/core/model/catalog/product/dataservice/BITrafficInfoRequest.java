package com.salesmanager.core.model.catalog.product.dataservice;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BITrafficInfoRequest {

    @JsonProperty("the_last_n_days")
    private Integer lastNDays;

    @JsonProperty("begin_date")
    private String beginDate;

    @JsonProperty("end_date")
    private String endData;

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
}
