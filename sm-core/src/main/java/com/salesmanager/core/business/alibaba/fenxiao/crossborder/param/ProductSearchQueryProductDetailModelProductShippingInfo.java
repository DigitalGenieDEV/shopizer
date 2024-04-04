package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class ProductSearchQueryProductDetailModelProductShippingInfo {

    private String sendGoodsAddressText;

    /**
     * @return 
     */
    public String getSendGoodsAddressText() {
        return sendGoodsAddressText;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setSendGoodsAddressText(String sendGoodsAddressText) {
        this.sendGoodsAddressText = sendGoodsAddressText;
    }

    private Double weight;

    /**
     * @return 重，单位kg
     */
    public Double getWeight() {
        return weight;
    }

    /**
     * 设置重，单位kg     *
     * 参数示例：<pre>200</pre>     
     * 此参数必填
     */
    public void setWeight(Double weight) {
        this.weight = weight;
    }

    private Double width;

    /**
     * @return 宽，单位cm
     */
    public Double getWidth() {
        return width;
    }

    /**
     * 设置宽，单位cm     *
     * 参数示例：<pre>1</pre>     
     * 此参数必填
     */
    public void setWidth(Double width) {
        this.width = width;
    }

    private Double height;

    /**
     * @return 高，单位cm
     */
    public Double getHeight() {
        return height;
    }

    /**
     * 设置高，单位cm     *
     * 参数示例：<pre>3</pre>     
     * 此参数必填
     */
    public void setHeight(Double height) {
        this.height = height;
    }

    private Double length;

    /**
     * @return 长，单位cm
     */
    public Double getLength() {
        return length;
    }

    /**
     * 设置长，单位cm     *
     * 参数示例：<pre>2</pre>     
     * 此参数必填
     */
    public void setLength(Double length) {
        this.length = length;
    }

    private ComAlibabaCbuOfferModelSkuShippingInfo[] skuShippingInfoList;

    /**
     * @return sku物流规格信息
     */
    public ComAlibabaCbuOfferModelSkuShippingInfo[] getSkuShippingInfoList() {
        return skuShippingInfoList;
    }

    /**
     * 设置sku物流规格信息     *
     * 参数示例：<pre>sku信息</pre>     
     * 此参数必填
     */
    public void setSkuShippingInfoList(ComAlibabaCbuOfferModelSkuShippingInfo[] skuShippingInfoList) {
        this.skuShippingInfoList = skuShippingInfoList;
    }

    private String shippingTimeGuarantee;

    /**
     * @return 发货保障
     */
    public String getShippingTimeGuarantee() {
        return shippingTimeGuarantee;
    }

    /**
     * 设置发货保障     *
     * 参数示例：<pre>shipIn24Hours-24小时发货 shipIn48Hours-48小时发货</pre>     
     * 此参数必填
     */
    public void setShippingTimeGuarantee(String shippingTimeGuarantee) {
        this.shippingTimeGuarantee = shippingTimeGuarantee;
    }

}
