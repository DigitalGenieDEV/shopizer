package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class ProductSearchQueryProductDetailModelSkuInfo {

    private Integer amountOnSale;

    /**
     * @return 库存
     */
    public Integer getAmountOnSale() {
        return amountOnSale;
    }

    /**
     * 设置库存     *
     * 参数示例：<pre>库存</pre>     
     * 此参数必填
     */
    public void setAmountOnSale(Integer amountOnSale) {
        this.amountOnSale = amountOnSale;
    }

    private String price;

    /**
     * @return 价格
     */
    public String getPrice() {
        return price;
    }

    /**
     * 设置价格     *
     * 参数示例：<pre>价格</pre>     
     * 此参数必填
     */
    public void setPrice(String price) {
        this.price = price;
    }

    private String jxhyPrice;

    /**
     * @return 精选货源价格
     */
    public String getJxhyPrice() {
        return jxhyPrice;
    }

    /**
     * 设置精选货源价格     *
     * 参数示例：<pre>精选货源价格</pre>     
     * 此参数必填
     */
    public void setJxhyPrice(String jxhyPrice) {
        this.jxhyPrice = jxhyPrice;
    }

    private Long skuId;

    /**
     * @return sku
     */
    public Long getSkuId() {
        return skuId;
    }

    /**
     * 设置sku     *
     * 参数示例：<pre>sku</pre>     
     * 此参数必填
     */
    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    private String specId;

    /**
     * @return specid
     */
    public String getSpecId() {
        return specId;
    }

    /**
     * 设置specid     *
     * 参数示例：<pre>specid</pre>     
     * 此参数必填
     */
    public void setSpecId(String specId) {
        this.specId = specId;
    }

    private ProductSearchQueryProductDetailModelSkuAttribute[] skuAttributes;

    /**
     * @return 属性
     */
    public ProductSearchQueryProductDetailModelSkuAttribute[] getSkuAttributes() {
        return skuAttributes;
    }

    /**
     * 设置属性     *
     * 参数示例：<pre>属性</pre>     
     * 此参数必填
     */
    public void setSkuAttributes(ProductSearchQueryProductDetailModelSkuAttribute[] skuAttributes) {
        this.skuAttributes = skuAttributes;
    }

    private String pfJxhyPrice;

    /**
     * @return 皮阿法精选货源价
     */
    public String getPfJxhyPrice() {
        return pfJxhyPrice;
    }

    /**
     * 设置皮阿法精选货源价     *
     * 参数示例：<pre>1</pre>     
     * 此参数必填
     */
    public void setPfJxhyPrice(String pfJxhyPrice) {
        this.pfJxhyPrice = pfJxhyPrice;
    }

    private String consignPrice;

    /**
     * @return 分销价格，一件代发价
     */
    public String getConsignPrice() {
        return consignPrice;
    }

    /**
     * 设置分销价格，一件代发价     *
     * 参数示例：<pre>1</pre>     
     * 此参数必填
     */
    public void setConsignPrice(String consignPrice) {
        this.consignPrice = consignPrice;
    }

    private String cargoNumber;

    /**
     * @return sku级别
     */
    public String getCargoNumber() {
        return cargoNumber;
    }

    /**
     * 设置sku级别     *
     * 参数示例：<pre>1</pre>     
     * 此参数必填
     */
    public void setCargoNumber(String cargoNumber) {
        this.cargoNumber = cargoNumber;
    }

}
