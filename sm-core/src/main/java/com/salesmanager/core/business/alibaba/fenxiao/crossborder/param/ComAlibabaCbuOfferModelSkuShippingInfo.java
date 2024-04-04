package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class ComAlibabaCbuOfferModelSkuShippingInfo {

    private String specId;

    /**
     * @return 规格id
     */
    public String getSpecId() {
        return specId;
    }

    /**
     * 设置规格id     *
     * 参数示例：<pre>2b36920d5139fd431a2030090d1e2599</pre>     
     * 此参数必填
     */
    public void setSpecId(String specId) {
        this.specId = specId;
    }

    private Long skuId;

    /**
     * @return skuId
     */
    public Long getSkuId() {
        return skuId;
    }

    /**
     * 设置skuId     *
     * 参数示例：<pre>5104790281451</pre>     
     * 此参数必填
     */
    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    private Double width;

    /**
     * @return 宽,单位cm
     */
    public Double getWidth() {
        return width;
    }

    /**
     * 设置宽,单位cm     *
     * 参数示例：<pre>1</pre>     
     * 此参数必填
     */
    public void setWidth(Double width) {
        this.width = width;
    }

    private Double length;

    /**
     * @return 长,单位cm
     */
    public Double getLength() {
        return length;
    }

    /**
     * 设置长,单位cm     *
     * 参数示例：<pre>2</pre>     
     * 此参数必填
     */
    public void setLength(Double length) {
        this.length = length;
    }

    private Double height;

    /**
     * @return 高,单位cm
     */
    public Double getHeight() {
        return height;
    }

    /**
     * 设置高,单位cm     *
     * 参数示例：<pre>3</pre>     
     * 此参数必填
     */
    public void setHeight(Double height) {
        this.height = height;
    }

    private Long weight;

    /**
     * @return 重,单位kg
     */
    public Long getWeight() {
        return weight;
    }

    /**
     * 设置重,单位kg     *
     * 参数示例：<pre>4</pre>     
     * 此参数必填
     */
    public void setWeight(Long weight) {
        this.weight = weight;
    }

}
