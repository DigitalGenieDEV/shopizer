package com.salesmanager.core.business.alibaba.param;

public class AlibabaOceanOpenplatformBizTradeParamTradeWithholdPreparePayParam {

    private String accountType;

    /**
     * @return 
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     *          *
     *        
     *
     */
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    private Long buyerId;

    /**
     * @return 
     */
    public Long getBuyerId() {
        return buyerId;
    }

    /**
     *          *
     *        
     *
     */
    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    private Long orderId;

    /**
     * @return 
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     *          *
     *        
     *
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    private AlibabaOceanOpenplatformCommonCallerInfo callerInfo;

    /**
     * @return 
     */
    public AlibabaOceanOpenplatformCommonCallerInfo getCallerInfo() {
        return callerInfo;
    }

    /**
     *          *
     *        
     *
     */
    public void setCallerInfo(AlibabaOceanOpenplatformCommonCallerInfo callerInfo) {
        this.callerInfo = callerInfo;
    }

    private String opRequestId;

    /**
     * @return 
     */
    public String getOpRequestId() {
        return opRequestId;
    }

    /**
     *          *
     *        
     *
     */
    public void setOpRequestId(String opRequestId) {
        this.opRequestId = opRequestId;
    }

    private String payChannel;

    /**
     * @return 跨境宝支付传入kjpayV2
     */
    public String getPayChannel() {
        return payChannel;
    }

    /**
     *     跨境宝支付传入kjpayV2     *
     * 参数示例：<pre>kjpayV2</pre>     
     *
     */
    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    private Long payAmount;

    /**
     * @return 付款总金额,单位分
     */
    public Long getPayAmount() {
        return payAmount;
    }

    /**
     *     付款总金额,单位分     *
     * 参数示例：<pre>123</pre>     
     *
     */
    public void setPayAmount(Long payAmount) {
        this.payAmount = payAmount;
    }

}
