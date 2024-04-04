package com.salesmanager.core.business.alibaba.param;

public class PayChaneelList {

    private String name;

    /**
     * @return 支付渠道
     */
    public String getName() {
        return name;
    }

    /**
     * 设置支付渠道     *
     * 参数示例：<pre>"alipay"</pre>     
     * 此参数必填
     */
    public void setName(String name) {
        this.name = name;
    }

    private Long amountLimit;

    /**
     * @return 可用额度金额，单位为分
     */
    public Long getAmountLimit() {
        return amountLimit;
    }

    /**
     * 设置可用额度金额，单位为分     *
     * 参数示例：<pre>100</pre>     
     * 此参数必填
     */
    public void setAmountLimit(Long amountLimit) {
        this.amountLimit = amountLimit;
    }

}
