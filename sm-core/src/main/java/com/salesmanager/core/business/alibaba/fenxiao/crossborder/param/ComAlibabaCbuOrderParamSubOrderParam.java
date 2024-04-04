package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class ComAlibabaCbuOrderParamSubOrderParam {

    private String orderId;

    /**
     * @return 子单id
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 设置子单id     *
     * 参数示例：<pre>2</pre>     
     * 此参数必填
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    private String productId;

    /**
     * @return 1688商品id需明文
     */
    public String getProductId() {
        return productId;
    }

    /**
     * 设置1688商品id需明文     *
     * 参数示例：<pre>1</pre>     
     * 此参数必填
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    private String productName;

    /**
     * @return 1688商品名称需明文
     */
    public String getProductName() {
        return productName;
    }

    /**
     * 设置1688商品名称需明文     *
     * 参数示例：<pre>袜子</pre>     
     * 此参数必填
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    private String skuId;

    /**
     * @return 1688skuId需明文
     */
    public String getSkuId() {
        return skuId;
    }

    /**
     * 设置1688skuId需明文     *
     * 参数示例：<pre>1</pre>     
     * 此参数必填
     */
    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    private String skuName;

    /**
     * @return 1688sku名称需明文
     */
    public String getSkuName() {
        return skuName;
    }

    /**
     * 设置1688sku名称需明文     *
     * 参数示例：<pre>绿色</pre>     
     * 此参数必填
     */
    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    private Long buyAmount;

    /**
     * @return 子单购买数量
     */
    public Long getBuyAmount() {
        return buyAmount;
    }

    /**
     * 设置子单购买数量     *
     * 参数示例：<pre>1</pre>     
     * 此参数必填
     */
    public void setBuyAmount(Long buyAmount) {
        this.buyAmount = buyAmount;
    }

    private Long paidFee;

    /**
     * @return 实付金额 单位分
     */
    public Long getPaidFee() {
        return paidFee;
    }

    /**
     * 设置实付金额 单位分     *
     * 参数示例：<pre>100</pre>     
     * 此参数必填
     */
    public void setPaidFee(Long paidFee) {
        this.paidFee = paidFee;
    }

    private String refundStatus;

    /**
     * @return 退款状态 0-未退款或退款关闭 1-退款
     */
    public String getRefundStatus() {
        return refundStatus;
    }

    /**
     * 设置退款状态 0-未退款或退款关闭 1-退款     *
     * 参数示例：<pre>0</pre>     
     * 此参数必填
     */
    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

}
