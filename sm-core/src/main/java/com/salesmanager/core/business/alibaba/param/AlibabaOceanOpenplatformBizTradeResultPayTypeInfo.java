package com.salesmanager.core.business.alibaba.param;

public class AlibabaOceanOpenplatformBizTradeResultPayTypeInfo {

    private Long code;

    /**
     * @return 支付渠道编码
     */
    public Long getCode() {
        return code;
    }

    /**
     * 设置支付渠道编码     *
     * 参数示例：<pre>1</pre>     
     * 此参数必填
     */
    public void setCode(Long code) {
        this.code = code;
    }

    private String name;

    /**
     * @return 支付渠道名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置支付渠道名称     *
     * 参数示例：<pre>支付宝</pre>     
     * 此参数必填
     */
    public void setName(String name) {
        this.name = name;
    }

}
