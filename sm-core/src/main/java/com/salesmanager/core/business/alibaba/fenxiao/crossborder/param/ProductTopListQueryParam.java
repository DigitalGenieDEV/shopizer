package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

import com.salesmanager.core.business.alibaba.rawsdk.client.APIId;
import com.salesmanager.core.business.alibaba.rawsdk.common.AbstractAPIRequest;


public class ProductTopListQueryParam extends AbstractAPIRequest<ProductTopListQueryResult> {

    public ProductTopListQueryParam() {
        super();
        oceanApiId = new APIId("com.alibaba.fenxiao.crossborder", "product.topList.query", 1);
    }

    private ProductTopListQueryRankQueryParams rankQueryParams;

    /**
     * @return 
     */
    public ProductTopListQueryRankQueryParams getRankQueryParams() {
        return rankQueryParams;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setRankQueryParams(ProductTopListQueryRankQueryParams rankQueryParams) {
        this.rankQueryParams = rankQueryParams;
    }

}
