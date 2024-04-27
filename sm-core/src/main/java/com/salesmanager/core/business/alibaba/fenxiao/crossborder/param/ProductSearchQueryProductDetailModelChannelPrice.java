package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class ProductSearchQueryProductDetailModelChannelPrice {

    private ProductSearchQueryProductDetailModelChannelSkuPrice[] channelSkuPriceList;

    /**
     * @return 渠道sku价格列表
     */
    public ProductSearchQueryProductDetailModelChannelSkuPrice[] getChannelSkuPriceList() {
        return channelSkuPriceList;
    }

    /**
     *     渠道sku价格列表     *
     * 参数示例：<pre>如下</pre>     
     *    
     */
    public void setChannelSkuPriceList(ProductSearchQueryProductDetailModelChannelSkuPrice[] channelSkuPriceList) {
        this.channelSkuPriceList = channelSkuPriceList;
    }

}
