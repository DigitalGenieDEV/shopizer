package com.salesmanager.core.model.catalog.product.dataservice;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BIMemberInfoRequest {

    @JsonProperty("the_last_n_days")
    private Integer lastNDays;

    @JsonProperty("begin_date")
    private String startDate;

    @JsonProperty("end_date")
    private String endData;

    public Integer getLastNDays() {
        return lastNDays;
    }
    public void setLastNDays(Integer lastNDays) {
        this.lastNDays = lastNDays;
    }


    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndData() {
        return endData;
    }
    public void setEndData(String endData) {
        this.endData = endData;
    }
}
