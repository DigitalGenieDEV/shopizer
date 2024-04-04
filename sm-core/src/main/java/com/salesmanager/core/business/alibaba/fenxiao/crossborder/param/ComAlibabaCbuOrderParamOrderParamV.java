package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

import java.util.Date;

public class ComAlibabaCbuOrderParamOrderParamV {

    private String orderId;

    /**
     * @return 订单id
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 设置订单id     *
     * 参数示例：<pre>1234</pre>     
     * 此参数必填
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    private String productId;

    /**
     * @return 商品id，子单或主子和一订单必填
     */
    public String getProductId() {
        return productId;
    }

    /**
     * 设置商品id，子单或主子和一订单必填     *
     * 参数示例：<pre>12</pre>     
     * 此参数必填
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    private String productName;

    /**
     * @return 商品名称，子单或主子和一订单必填
     */
    public String getProductName() {
        return productName;
    }

    /**
     * 设置商品名称，子单或主子和一订单必填     *
     * 参数示例：<pre>手套</pre>     
     * 此参数必填
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    private String skuId;

    /**
     * @return skuId，子单或主子和一订单必填
     */
    public String getSkuId() {
        return skuId;
    }

    /**
     * 设置skuId，子单或主子和一订单必填     *
     * 参数示例：<pre>adsds1213</pre>     
     * 此参数必填
     */
    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    private String skuName;

    /**
     * @return sku名称，子单或主子和一订单必填
     */
    public String getSkuName() {
        return skuName;
    }

    /**
     * 设置sku名称，子单或主子和一订单必填     *
     * 参数示例：<pre>绿色</pre>     
     * 此参数必填
     */
    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    private Long buyAmount;

    /**
     * @return 购买数量
     */
    public Long getBuyAmount() {
        return buyAmount;
    }

    /**
     * 设置购买数量     *
     * 参数示例：<pre>2</pre>     
     * 此参数必填
     */
    public void setBuyAmount(Long buyAmount) {
        this.buyAmount = buyAmount;
    }

    private Date createTime;

    /**
     * @return 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间     *
     * 参数示例：<pre>2023-10-01 10:10:10</pre>     
     * 此参数必填
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    private String outMemberId;

    /**
     * @return 下游用户id
     */
    public String getOutMemberId() {
        return outMemberId;
    }

    /**
     * 设置下游用户id     *
     * 参数示例：<pre>1212113</pre>     
     * 此参数必填
     */
    public void setOutMemberId(String outMemberId) {
        this.outMemberId = outMemberId;
    }

    private Date payTime;

    /**
     * @return 支付时间
     */
    public Date getPayTime() {
        return payTime;
    }

    /**
     * 设置支付时间     *
     * 参数示例：<pre>2023-10-01 10:10:10</pre>     
     * 此参数必填
     */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    private Date endTime;

    /**
     * @return 结束时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置结束时间     *
     * 参数示例：<pre>2023-10-01 10:10:10</pre>     
     * 此参数必填
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    private Long paidFee;

    /**
     * @return 实付金额 分
     */
    public Long getPaidFee() {
        return paidFee;
    }

    /**
     * 设置实付金额 分     *
     * 参数示例：<pre>200</pre>     
     * 此参数必填
     */
    public void setPaidFee(Long paidFee) {
        this.paidFee = paidFee;
    }

    private String refundStatus;

    /**
     * @return 退款状态 0-未退款（退款关闭，未申请） 1-退款
     */
    public String getRefundStatus() {
        return refundStatus;
    }

    /**
     * 设置退款状态 0-未退款（退款关闭，未申请） 1-退款     *
     * 参数示例：<pre>0</pre>     
     * 此参数必填
     */
    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    private ComAlibabaCbuOrderParamOrderParamV[] subOrderList;

    /**
     * @return 子单详情，无则不传
     */
    public ComAlibabaCbuOrderParamOrderParamV[] getSubOrderList() {
        return subOrderList;
    }

    /**
     * 设置子单详情，无则不传     *
     * 参数示例：<pre>子单详情</pre>     
     * 此参数必填
     */
    public void setSubOrderList(ComAlibabaCbuOrderParamOrderParamV[] subOrderList) {
        this.subOrderList = subOrderList;
    }

    private String status;

    /**
     * @return payed-已支付  success-交易成功 close-交易关闭
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置payed-已支付  success-交易成功 close-交易关闭     *
     * 参数示例：<pre>success</pre>     
     * 此参数必填
     */
    public void setStatus(String status) {
        this.status = status;
    }

    private ComAlibabaCbuOrderParamSubOrderParam[] subOrderParamList;

    /**
     * @return 一主多子需传子单信息
     */
    public ComAlibabaCbuOrderParamSubOrderParam[] getSubOrderParamList() {
        return subOrderParamList;
    }

    /**
     * 设置一主多子需传子单信息     *
     * 参数示例：<pre>子单信息</pre>     
     * 此参数必填
     */
    public void setSubOrderParamList(ComAlibabaCbuOrderParamSubOrderParam[] subOrderParamList) {
        this.subOrderParamList = subOrderParamList;
    }

}
