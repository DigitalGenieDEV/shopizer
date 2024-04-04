package com.salesmanager.core.business.alibaba.param;

public class AlibabaOceanOpenplatformBizTradeResultTradePayTypeResult {

    private AlibabaOceanOpenplatformBizTradeResultPayTypeInfo[] channels;

    /**
     * @return 可用支付渠道列表
     */
    public AlibabaOceanOpenplatformBizTradeResultPayTypeInfo[] getChannels() {
        return channels;
    }

    /**
     * 设置可用支付渠道列表     *
     * 参数示例：<pre>[]</pre>     
     * 此参数必填
     */
    public void setChannels(AlibabaOceanOpenplatformBizTradeResultPayTypeInfo[] channels) {
        this.channels = channels;
    }

    private String orderId;

    /**
     * @return 订单号
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 设置订单号     *
     * 参数示例：<pre>1231231211</pre>     
     * 此参数必填
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    private Long payFee;

    /**
     * @return 支付金额，单位分
     */
    public Long getPayFee() {
        return payFee;
    }

    /**
     * 设置支付金额，单位分     *
     * 参数示例：<pre>100</pre>     
     * 此参数必填
     */
    public void setPayFee(Long payFee) {
        this.payFee = payFee;
    }

    private String timeout;

    /**
     * @return 最晚支付时间
     */
    public String getTimeout() {
        return timeout;
    }

    /**
     * 设置最晚支付时间     *
     * 参数示例：<pre>2018-10-01 00:00:00</pre>     
     * 此参数必填
     */
    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

}
