//tmpzk
package com.salesmanager.core.model.catalog.product.dataservice;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BITrafficInfoResult {

    @JsonProperty("ALL_UV")
    private Integer allUV;

    @JsonProperty("NEW_UV")
    private Integer newUV;

    @JsonProperty("OLD_UV")
    private Integer oldUV;

    @JsonProperty("PC_UV")
    private Integer pcUV;

    @JsonProperty("MOBILE_UV")
    private Integer mobileUV;


    public Integer getAllUV() {
        return allUV;
    }
    public void setAllUV(Integer allUV) { this.allUV = allUV; }

    public Integer getNewUV() {
        return newUV;
    }
    public void setNewUV(Integer newUV) { this.newUV = newUV; }

    public Integer getOldUV() {
        return oldUV;
    }
    public void setOldUV(Integer oldUV) { this.oldUV = oldUV; }

    public Integer getPcUV() { return pcUV; }
    public void setPcUV(Integer pcUV) { this.pcUV = pcUV; }

    public Integer getMobileUV() { return mobileUV; }
    public void setMobileUV(Integer mobileUV) { this.mobileUV = mobileUV; }

}
