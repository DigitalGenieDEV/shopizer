package com.salesmanager.core.business.alibaba.param;

import java.math.BigDecimal;

public class AlibabaOpenplatformTradeModelPayEntryItemDesc {

    private String serviceAttriribute;

    /**
     * @return 服务描述
     */
    public String getServiceAttriribute() {
        return serviceAttriribute;
    }

    /**
     * 设置服务描述     *
     * 参数示例：<pre>改颜色</pre>     
     * 此参数必填
     */
    public void setServiceAttriribute(String serviceAttriribute) {
        this.serviceAttriribute = serviceAttriribute;
    }

    private String serviceName;

    /**
     * @return 服务名称
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * 设置服务名称     *
     * 参数示例：<pre>包装</pre>     
     * 此参数必填
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    private BigDecimal servicePrice;

    /**
     * @return 单价 （单位元）
     */
    public BigDecimal getServicePrice() {
        return servicePrice;
    }

    /**
     * 设置单价 （单位元）     *
     * 参数示例：<pre>1</pre>     
     * 此参数必填
     */
    public void setServicePrice(BigDecimal servicePrice) {
        this.servicePrice = servicePrice;
    }

}
