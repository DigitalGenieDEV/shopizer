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
     *     订单总金额（单位分），一次创建多个订单时，该字段为空     *
     * 参数示例：<pre>100</pre>     
     *    
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
     *     订单ID，一次创建多个订单时，该字段为空     *
     * 参数示例：<pre>111111111</pre>     
     *    
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
     *     是否成功     *
     * 参数示例：<pre>true</pre>     
     *    
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
     *     错误码     *
     *        
     *    
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
     *     错误信息     *
     *        
     *    
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
     *     账期信息，非账期支付订单返回空     *
     *        
     *    
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
     *     失败商品信息     *
     *        
     *    
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
     *     运费，单位：分，一次创建多个订单时，该字段为空     *
     *        
     *    
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
     *     一次创建多个订单     *
     *        
     *    
     */
    public void setOrderList(AlibabaTradeResultBizSimpleOrder[] orderList) {
        this.orderList = orderList;
    }

}
