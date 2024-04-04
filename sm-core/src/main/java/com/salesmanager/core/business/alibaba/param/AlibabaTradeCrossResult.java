package com.salesmanager.core.business.alibaba.param;

public class AlibabaTradeCrossResult {

    private Long totalSuccessAmount;

    /**
     * @return 订单总金额（单位分），一次创建多个订单时，该字段为空
     */
    public Long getTotalSuccessAmount() {
        return totalSuccessAmount;
    }

    /**
     * 设置订单总金额（单位分），一次创建多个订单时，该字段为空     *
     * 参数示例：<pre>100</pre>     
     * 此参数必填
     */
    public void setTotalSuccessAmount(Long totalSuccessAmount) {
        this.totalSuccessAmount = totalSuccessAmount;
    }

    private String orderId;

    /**
     * @return 订单ID，一次创建多个订单时，该字段为空
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 设置订单ID，一次创建多个订单时，该字段为空     *
     * 参数示例：<pre>111111111</pre>     
     * 此参数必填
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    private Boolean success;

    /**
     * @return 是否成功
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     * 设置是否成功     *
     * 参数示例：<pre>true</pre>     
     * 此参数必填
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    private String code;

    /**
     * @return 错误码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置错误码     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setCode(String code) {
        this.code = code;
    }

    private String message;

    /**
     * @return 错误信息
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置错误信息     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setMessage(String message) {
        this.message = message;
    }

    private AlibabaTradeCrossPeriod accountPeriod;

    /**
     * @return 账期信息，非账期支付订单返回空
     */
    public AlibabaTradeCrossPeriod getAccountPeriod() {
        return accountPeriod;
    }

    /**
     * 设置账期信息，非账期支付订单返回空     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setAccountPeriod(AlibabaTradeCrossPeriod accountPeriod) {
        this.accountPeriod = accountPeriod;
    }

    private AlibabaTradeFastOffer[] failedOfferList;

    /**
     * @return 失败商品信息
     */
    public AlibabaTradeFastOffer[] getFailedOfferList() {
        return failedOfferList;
    }

    /**
     * 设置失败商品信息     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setFailedOfferList(AlibabaTradeFastOffer[] failedOfferList) {
        this.failedOfferList = failedOfferList;
    }

    private Long postFee;

    /**
     * @return 运费，单位：分，一次创建多个订单时，该字段为空
     */
    public Long getPostFee() {
        return postFee;
    }

    /**
     * 设置运费，单位：分，一次创建多个订单时，该字段为空     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setPostFee(Long postFee) {
        this.postFee = postFee;
    }

    private AlibabaTradeResultBizSimpleOrder[] orderList;

    /**
     * @return 一次创建多个订单
     */
    public AlibabaTradeResultBizSimpleOrder[] getOrderList() {
        return orderList;
    }

    /**
     * 设置一次创建多个订单     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setOrderList(AlibabaTradeResultBizSimpleOrder[] orderList) {
        this.orderList = orderList;
    }

}
