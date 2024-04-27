package com.salesmanager.core.business.alibaba.param;

public class AlibabaOrderBizInfo {

    private Boolean odsCyd;

    /**
     * @return 是否采源宝订单
     */
    public Boolean getOdsCyd() {
        return odsCyd;
    }

    /**
     *     是否采源宝订单     *
     * 参数示例：<pre>true</pre>     
     *
     */
    public void setOdsCyd(Boolean odsCyd) {
        this.odsCyd = odsCyd;
    }

    private String accountPeriodTime;

    /**
     * @return 账期交易订单的到账时间
     */
    public String getAccountPeriodTime() {
        return accountPeriodTime;
    }

    /**
     *     账期交易订单的到账时间     *
     * 参数示例：<pre>yyyy-MM-dd HH:mm:ss</pre>     
     *
     */
    public void setAccountPeriodTime(String accountPeriodTime) {
        this.accountPeriodTime = accountPeriodTime;
    }

    private Boolean creditOrder;

    /**
     * @return 为true，表示下单时选择了诚e赊交易方式。注意不等同于“诚e赊支付”，支付时有可能是支付宝付款，具体支付方式查询tradeTerms.payWay
     */
    public Boolean getCreditOrder() {
        return creditOrder;
    }

    /**
     *     为true，表示下单时选择了诚e赊交易方式。注意不等同于“诚e赊支付”，支付时有可能是支付宝付款，具体支付方式查询tradeTerms.payWay     *
     * 参数示例：<pre>false</pre>     
     *
     */
    public void setCreditOrder(Boolean creditOrder) {
        this.creditOrder = creditOrder;
    }

    private AlibabaCreditOrderForDetail creditOrderDetail;

    /**
     * @return 诚e赊支付详情，只有使用诚e赊付款时返回
     */
    public AlibabaCreditOrderForDetail getCreditOrderDetail() {
        return creditOrderDetail;
    }

    /**
     *     诚e赊支付详情，只有使用诚e赊付款时返回     *
     *        
     *
     */
    public void setCreditOrderDetail(AlibabaCreditOrderForDetail creditOrderDetail) {
        this.creditOrderDetail = creditOrderDetail;
    }

    private AlibabaOrderPreOrderForRead preOrderInfo;

    /**
     * @return 预订单信息
     */
    public AlibabaOrderPreOrderForRead getPreOrderInfo() {
        return preOrderInfo;
    }

    /**
     *     预订单信息     *
     * 参数示例：<pre>{}</pre>     
     *
     */
    public void setPreOrderInfo(AlibabaOrderPreOrderForRead preOrderInfo) {
        this.preOrderInfo = preOrderInfo;
    }

    private AlibabaLstTradeInfo lstOrderInfo;

    /**
     * @return 零售通订单信息
     */
    public AlibabaLstTradeInfo getLstOrderInfo() {
        return lstOrderInfo;
    }

    /**
     *     零售通订单信息     *
     * 参数示例：<pre>{}</pre>     
     *
     */
    public void setLstOrderInfo(AlibabaLstTradeInfo lstOrderInfo) {
        this.lstOrderInfo = lstOrderInfo;
    }

    private String erpBuyerUserId;

    /**
     * @return ERP的用户ID
     */
    public String getErpBuyerUserId() {
        return erpBuyerUserId;
    }

    /**
     *     ERP的用户ID     *
     * 参数示例：<pre>U001012121</pre>     
     *
     */
    public void setErpBuyerUserId(String erpBuyerUserId) {
        this.erpBuyerUserId = erpBuyerUserId;
    }

    private String erpOrderId;

    /**
     * @return ERP的订单编号
     */
    public String getErpOrderId() {
        return erpOrderId;
    }

    /**
     *     ERP的订单编号     *
     * 参数示例：<pre>O123331</pre>     
     *
     */
    public void setErpOrderId(String erpOrderId) {
        this.erpOrderId = erpOrderId;
    }

    private String erpBuyerOrgId;

    /**
     * @return ERP组织ID
     */
    public String getErpBuyerOrgId() {
        return erpBuyerOrgId;
    }

    /**
     *     ERP组织ID     *
     * 参数示例：<pre>OG4331113</pre>     
     *
     */
    public void setErpBuyerOrgId(String erpBuyerOrgId) {
        this.erpBuyerOrgId = erpBuyerOrgId;
    }

    private Boolean isCz;

    /**
     * @return 是否加工定制订单
     */
    public Boolean getIsCz() {
        return isCz;
    }

    /**
     *     是否加工定制订单     *
     * 参数示例：<pre>false</pre>     
     *
     */
    public void setIsCz(Boolean isCz) {
        this.isCz = isCz;
    }

    private Boolean isDz;

    /**
     * @return 是否定制订单
     */
    public Boolean getIsDz() {
        return isDz;
    }

    /**
     *     是否定制订单     *
     * 参数示例：<pre>false</pre>     
     *
     */
    public void setIsDz(Boolean isDz) {
        this.isDz = isDz;
    }

    private Boolean dz;

    /**
     * @return 是否定制订单
     */
    public Boolean getDz() {
        return dz;
    }

    /**
     *     是否定制订单     *
     * 参数示例：<pre>false</pre>     
     *
     */
    public void setDz(Boolean dz) {
        this.dz = dz;
    }

}
