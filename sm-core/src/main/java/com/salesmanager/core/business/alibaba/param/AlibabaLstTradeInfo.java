package com.salesmanager.core.business.alibaba.param;

public class AlibabaLstTradeInfo {

    private String lstWarehouseType;

    /**
     * @return 零售通仓库类型。customer：虚仓；cainiao：实仓
     */
    public String getLstWarehouseType() {
        return lstWarehouseType;
    }

    /**
     *     零售通仓库类型。customer：虚仓；cainiao：实仓     *
     * 参数示例：<pre>cainiao</pre>     
     *
     */
    public void setLstWarehouseType(String lstWarehouseType) {
        this.lstWarehouseType = lstWarehouseType;
    }

}
