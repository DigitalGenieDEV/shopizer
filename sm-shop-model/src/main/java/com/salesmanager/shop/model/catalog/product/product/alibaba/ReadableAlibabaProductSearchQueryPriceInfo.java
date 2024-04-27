package com.salesmanager.shop.model.catalog.product.product.alibaba;

public class ReadableAlibabaProductSearchQueryPriceInfo {


    private String promotionPrice;

    public String getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(String promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    private String price;

    /**
     * @return 批发价
     */
    public String getPrice() {
        return price;
    }

    /**
     *     批发价     *
     * 参数示例：<pre>10</pre>
     *    
     */
    public void setPrice(String price) {
        this.price = price;
    }

    private String jxhyPrice;

    /**
     * @return 精选货源价
     */
    public String getJxhyPrice() {
        return jxhyPrice;
    }

    /**
     *     精选货源价     *
     * 参数示例：<pre>10</pre>
     *    
     */
    public void setJxhyPrice(String jxhyPrice) {
        this.jxhyPrice = jxhyPrice;
    }

    private String pfJxhyPrice;

    /**
     * @return 批发精选货源价
     */
    public String getPfJxhyPrice() {
        return pfJxhyPrice;
    }

    /**
     *     批发精选货源价     *
     * 参数示例：<pre>10</pre>
     *    
     */
    public void setPfJxhyPrice(String pfJxhyPrice) {
        this.pfJxhyPrice = pfJxhyPrice;
    }

    private String consignPrice;

    /**
     * @return 一件代发价
     */
    public String getConsignPrice() {
        return consignPrice;
    }

    /**
     *     一件代发价     *
     * 参数示例：<pre>10</pre>
     *    
     */
    public void setConsignPrice(String consignPrice) {
        this.consignPrice = consignPrice;
    }

}
