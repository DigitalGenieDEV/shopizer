package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class ProductSearchQueryProductDetailModelChannelSkuPrice {

    private Long skuId;

    /**
     * @return sku id
     */
    public Long getSkuId() {
        return skuId;
    }

    /**
     *     sku id     *
     * 参数示例：<pre>435234325</pre>     
     *    
     */
    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    private String currentPrice;

    /**
     * @return 渠道价格
     */
    public String getCurrentPrice() {
        return currentPrice;
    }

    /**
     *     渠道价格     *
     * 参数示例：<pre>23.41</pre>     
     *    
     */
    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

}
