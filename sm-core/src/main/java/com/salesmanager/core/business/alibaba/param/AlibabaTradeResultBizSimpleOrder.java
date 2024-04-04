package com.salesmanager.core.business.alibaba.param;

public class AlibabaTradeResultBizSimpleOrder {

    private Long postFee;

    /**
     * @return 运费
     */
    public Long getPostFee() {
        return postFee;
    }

    /**
     * 设置运费     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setPostFee(Long postFee) {
        this.postFee = postFee;
    }

    private Long orderAmmount;

    /**
     * @return 订单实付款金额，单位为分
     */
    public Long getOrderAmmount() {
        return orderAmmount;
    }

    /**
     * 设置订单实付款金额，单位为分     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setOrderAmmount(Long orderAmmount) {
        this.orderAmmount = orderAmmount;
    }

    private String payChannel;

    /**
     * @return 支付渠道
     */
    public String getPayChannel() {
        return payChannel;
    }

    /**
     * 设置支付渠道     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    private Long discount;

    /**
     * @return 折扣
     */
    public Long getDiscount() {
        return discount;
    }

    /**
     * 设置折扣     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    private String message;

    /**
     * @return 描述信息
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置描述信息     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setMessage(String message) {
        this.message = message;
    }

    private String promotionId;

    /**
     * @return 优惠id
     */
    public String getPromotionId() {
        return promotionId;
    }

    /**
     * 设置优惠id     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

    private Long sumPaymentNoCarriageFromClient;

    /**
     * @return 外部提交过来的中金额，不包括运费
     */
    public Long getSumPaymentNoCarriageFromClient() {
        return sumPaymentNoCarriageFromClient;
    }

    /**
     * 设置外部提交过来的中金额，不包括运费     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setSumPaymentNoCarriageFromClient(Long sumPaymentNoCarriageFromClient) {
        this.sumPaymentNoCarriageFromClient = sumPaymentNoCarriageFromClient;
    }

    private String resultCode;

    /**
     * @return 返回码
     */
    public String getResultCode() {
        return resultCode;
    }

    /**
     * 设置返回码     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    private Long subUserId;

    /**
     * @return 创建订单的子账号UserId
     */
    public Long getSubUserId() {
        return subUserId;
    }

    /**
     * 设置创建订单的子账号UserId     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setSubUserId(Long subUserId) {
        this.subUserId = subUserId;
    }

    private Boolean mergePay;

    /**
     * @return 订单是否能合并支付
     */
    public Boolean getMergePay() {
        return mergePay;
    }

    /**
     * 设置订单是否能合并支付     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setMergePay(Boolean mergePay) {
        this.mergePay = mergePay;
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
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setSuccess(Boolean success) {
        this.success = success;
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
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    private Boolean chooseFreeFreight;

    /**
     * @return 是否选择了免运费的优惠
     */
    public Boolean getChooseFreeFreight() {
        return chooseFreeFreight;
    }

    /**
     * 设置是否选择了免运费的优惠     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setChooseFreeFreight(Boolean chooseFreeFreight) {
        this.chooseFreeFreight = chooseFreeFreight;
    }

}
