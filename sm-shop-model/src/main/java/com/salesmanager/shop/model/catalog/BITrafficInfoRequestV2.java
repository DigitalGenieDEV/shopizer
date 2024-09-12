//tmpzk
package com.salesmanager.shop.model.catalog;

import java.io.Serializable;

public class BITrafficInfoRequestV2 implements Serializable  {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Integer lastNDays;
    private String beginDate;
    private String endData;

    public Integer getLastNDays() {return lastNDays;}
    public void setLastNDays(Integer lastNDays) {this.lastNDays = lastNDays;}

    public String getBeginDate() {return beginDate;}
    public void setBeginDate(String beginDate) {this.beginDate = beginDate;}

    public String getEndData() {return endData;}
    public void setEndData(String endData) {this.endData = endData;}
}
