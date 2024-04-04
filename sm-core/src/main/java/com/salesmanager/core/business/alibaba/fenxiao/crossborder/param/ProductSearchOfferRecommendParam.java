package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

import com.salesmanager.core.business.alibaba.rawsdk.client.APIId;
import com.salesmanager.core.business.alibaba.rawsdk.common.AbstractAPIRequest;


public class ProductSearchOfferRecommendParam extends AbstractAPIRequest<ProductSearchOfferRecommendResult> {

    public ProductSearchOfferRecommendParam() {
        super();
        oceanApiId = new APIId("com.alibaba.fenxiao.crossborder", "product.search.offerRecommend", 1);
    }

    private AlibabaCbuOfferParamRecommendOfferParam recommendOfferParam;

    /**
     * @return 
     */
    public AlibabaCbuOfferParamRecommendOfferParam getRecommendOfferParam() {
        return recommendOfferParam;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setRecommendOfferParam(AlibabaCbuOfferParamRecommendOfferParam recommendOfferParam) {
        this.recommendOfferParam = recommendOfferParam;
    }

}
