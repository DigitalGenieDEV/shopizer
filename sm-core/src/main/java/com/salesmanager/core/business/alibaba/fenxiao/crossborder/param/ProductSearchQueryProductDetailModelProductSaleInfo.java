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
     *          *
     *        
     *    
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
     *     商品库存     *
     * 参数示例：<pre>1</pre>     
     *    
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
     *     价格区间     *
     * 参数示例：<pre>1</pre>     
     *    
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
     *     普通报价-FIXED_PRICE("0"),SKU规格报价-SKU_PRICE("1"),SKU区间报价（商品维度）-SKU_PRICE_RANGE_FOR_OFFER("2"),SKU区间报价（SKU维度）-SKU_PRICE_RANGE("3")，     *
     * 参数示例：<pre>1</pre>     
     *    
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
     *     分销价，一件代发价格     *
     * 参数示例：<pre>1</pre>     
     *    
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
     *     精选货源价格     *
     * 参数示例：<pre>1</pre>     
     *    
     */
    public void setJxhyPrice(String jxhyPrice) {
        this.jxhyPrice = jxhyPrice;
    }

}
