package com.salesmanager.core.business.alibaba.param;

public class ComAlibabaOceanOpenplatformBizTradeResultTradeWithholdPreparePayResultMresult {

    private Boolean isPaySuccess;

    /**
     * @return 支付是否成功
     */
    public Boolean getIsPaySuccess() {
        return isPaySuccess;
    }

    /**
     * 设置支付是否成功     *
     * 参数示例：<pre>true</pre>     
     * 此参数必填
     */
    public void setIsPaySuccess(Boolean isPaySuccess) {
        this.isPaySuccess = isPaySuccess;
    }

    private String payChannel;

    /**
     * @return 支付成功渠道
     */
    public String getPayChannel() {
        return payChannel;
    }

    /**
     * 设置支付成功渠道     *
     * 参数示例：<pre>Alipay</pre>     
     * 此参数必填
     */
    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    private Boolean paySuccess;

    /**
     * @return 支付是否成功
     */
    public Boolean getPaySuccess() {
        return paySuccess;
    }

    /**
     * 设置支付是否成功     *
     * 参数示例：<pre>true</pre>     
     * 此参数必填
     */
    public void setPaySuccess(Boolean paySuccess) {
        this.paySuccess = paySuccess;
    }

}
