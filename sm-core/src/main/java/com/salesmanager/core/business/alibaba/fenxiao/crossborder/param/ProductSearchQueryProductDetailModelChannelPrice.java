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
     * 设置渠道sku价格列表     *
     * 参数示例：<pre>如下</pre>     
     * 此参数必填
     */
    public void setChannelSkuPriceList(ProductSearchQueryProductDetailModelChannelSkuPrice[] channelSkuPriceList) {
        this.channelSkuPriceList = channelSkuPriceList;
    }

}
