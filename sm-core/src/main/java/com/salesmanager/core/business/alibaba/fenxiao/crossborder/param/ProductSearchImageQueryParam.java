package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

import com.salesmanager.core.business.alibaba.rawsdk.client.APIId;
import com.salesmanager.core.business.alibaba.rawsdk.common.AbstractAPIRequest;


public class ProductSearchImageQueryParam extends AbstractAPIRequest<ProductSearchImageQueryResult> {

    public ProductSearchImageQueryParam() {
        super();
        oceanApiId = new APIId("com.alibaba.fenxiao.crossborder", "product.search.imageQuery", 1);
    }

    private ProductSearchImageQueryParamOfferQueryParam offerQueryParam;

    /**
     * @return 
     */
    public ProductSearchImageQueryParamOfferQueryParam getOfferQueryParam() {
        return offerQueryParam;
    }

    /**
     *          *
     *        
     *
     */
    public void setOfferQueryParam(ProductSearchImageQueryParamOfferQueryParam offerQueryParam) {
        this.offerQueryParam = offerQueryParam;
    }

}
