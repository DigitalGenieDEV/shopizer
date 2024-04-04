package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

import com.salesmanager.core.business.alibaba.rawsdk.client.APIId;
import com.salesmanager.core.business.alibaba.rawsdk.common.AbstractAPIRequest;


public class ProductSearchQuerySellerOfferListParam extends AbstractAPIRequest<ProductSearchQuerySellerOfferListResult> {

    public ProductSearchQuerySellerOfferListParam() {
        super();
        oceanApiId = new APIId("com.alibaba.fenxiao.crossborder", "product.search.querySellerOfferList", 1);
    }

    private ProductSearchQuerySellerOfferListParamOfferQueryParam offerQueryParam;

    /**
     * @return 
     */
    public ProductSearchQuerySellerOfferListParamOfferQueryParam getOfferQueryParam() {
        return offerQueryParam;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setOfferQueryParam(ProductSearchQuerySellerOfferListParamOfferQueryParam offerQueryParam) {
        this.offerQueryParam = offerQueryParam;
    }

}
