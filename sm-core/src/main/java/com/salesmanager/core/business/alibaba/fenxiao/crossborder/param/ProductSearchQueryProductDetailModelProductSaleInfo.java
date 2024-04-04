package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class ProductSearchQueryProductDetailModelProductSaleInfo {

    private ProductSearchQueryProductDetailModelPriceRange[] priceRanges;

    /**
     * @return 
     */
    public ProductSearchQueryProductDetailModelPriceRange[] getPriceRanges() {
        return priceRanges;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setPriceRanges(ProductSearchQueryProductDetailModelPriceRange[] priceRanges) {
        this.priceRanges = priceRanges;
    }

    private Integer amountOnSale;

    /**
     * @return 商品库存
     */
    public Integer getAmountOnSale() {
        return amountOnSale;
    }

    /**
     * 设置商品库存     *
     * 参数示例：<pre>1</pre>     
     * 此参数必填
     */
    public void setAmountOnSale(Integer amountOnSale) {
        this.amountOnSale = amountOnSale;
    }

    private ProductSearchQueryProductDetailModelPriceRangeV[] priceRangeList;

    /**
     * @return 价格区间
     */
    public ProductSearchQueryProductDetailModelPriceRangeV[] getPriceRangeList() {
        return priceRangeList;
    }

    /**
     * 设置价格区间     *
     * 参数示例：<pre>1</pre>     
     * 此参数必填
     */
    public void setPriceRangeList(ProductSearchQueryProductDetailModelPriceRangeV[] priceRangeList) {
        this.priceRangeList = priceRangeList;
    }

    private Integer quoteType;

    /**
     * @return 普通报价-FIXED_PRICE("0"),SKU规格报价-SKU_PRICE("1"),SKU区间报价（商品维度）-SKU_PRICE_RANGE_FOR_OFFER("2"),SKU区间报价（SKU维度）-SKU_PRICE_RANGE("3")，
     */
    public Integer getQuoteType() {
        return quoteType;
    }

    /**
     * 设置普通报价-FIXED_PRICE("0"),SKU规格报价-SKU_PRICE("1"),SKU区间报价（商品维度）-SKU_PRICE_RANGE_FOR_OFFER("2"),SKU区间报价（SKU维度）-SKU_PRICE_RANGE("3")，     *
     * 参数示例：<pre>1</pre>     
     * 此参数必填
     */
    public void setQuoteType(Integer quoteType) {
        this.quoteType = quoteType;
    }

    private String consignPrice;

    /**
     * @return 分销价，一件代发价格
     */
    public String getConsignPrice() {
        return consignPrice;
    }

    /**
     * 设置分销价，一件代发价格     *
     * 参数示例：<pre>1</pre>     
     * 此参数必填
     */
    public void setConsignPrice(String consignPrice) {
        this.consignPrice = consignPrice;
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
     * 参数示例：<pre>1</pre>     
     * 此参数必填
     */
    public void setJxhyPrice(String jxhyPrice) {
        this.jxhyPrice = jxhyPrice;
    }

}
