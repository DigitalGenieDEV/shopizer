package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

import com.salesmanager.core.business.alibaba.rawsdk.client.APIId;
import com.salesmanager.core.business.alibaba.rawsdk.common.AbstractAPIRequest;


public class ProductSearchKeywordQueryParam extends AbstractAPIRequest<ProductSearchKeywordQueryResult> {

    public ProductSearchKeywordQueryParam() {
        super();
        oceanApiId = new APIId("com.alibaba.fenxiao.crossborder", "product.search.keywordQuery", 1);
    }

    private ProductSearchKeywordQueryParamOfferQueryParam offerQueryParam;

    /**
     * @return 
     */
    public ProductSearchKeywordQueryParamOfferQueryParam getOfferQueryParam() {
        return offerQueryParam;
    }

    /**
     *          *
     *        
     *
     */
    public void setOfferQueryParam(ProductSearchKeywordQueryParamOfferQueryParam offerQueryParam) {
        this.offerQueryParam = offerQueryParam;
    }

}
