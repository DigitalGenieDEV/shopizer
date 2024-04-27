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
     *          *
     *        
     *
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
     *     重，单位kg     *
     * 参数示例：<pre>200</pre>     
     *
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
     *     宽，单位cm     *
     * 参数示例：<pre>1</pre>     
     *
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
     *     高，单位cm     *
     * 参数示例：<pre>3</pre>     
     *
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
     *     长，单位cm     *
     * 参数示例：<pre>2</pre>     
     *
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
     *     sku物流规格信息     *
     * 参数示例：<pre>sku信息</pre>     
     *
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
     *     发货保障     *
     * 参数示例：<pre>shipIn24Hours-24小时发货 shipIn48Hours-48小时发货</pre>     
     *
     */
    public void setShippingTimeGuarantee(String shippingTimeGuarantee) {
        this.shippingTimeGuarantee = shippingTimeGuarantee;
    }

}
