package com.salesmanager.core.business.alibaba.param;

public class ComAlibabaOceanOpenplatformBizTradeResultPayChannel {

    private String channel;

    /**
     * @return 支付渠道
     */
    public String getChannel() {
        return channel;
    }

    /**
     * 设置支付渠道     *
     * 参数示例：<pre>alipay</pre>     
     * 此参数必填
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    private Boolean avaliable;

    /**
     * @return 是否支持
     */
    public Boolean getAvaliable() {
        return avaliable;
    }

    /**
     * 设置是否支持     *
     * 参数示例：<pre>true</pre>     
     * 此参数必填
     */
    public void setAvaliable(Boolean avaliable) {
        this.avaliable = avaliable;
    }

}
