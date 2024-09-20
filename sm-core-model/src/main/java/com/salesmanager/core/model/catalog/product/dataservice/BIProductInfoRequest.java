package com.salesmanager.core.model.catalog.product.dataservice;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BIProductInfoRequest {

    @JsonProperty("lastNDays")
    private Integer lastNDays;

    @JsonProperty("startDate")
    private String startDate;

    @JsonProperty("endData")
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
