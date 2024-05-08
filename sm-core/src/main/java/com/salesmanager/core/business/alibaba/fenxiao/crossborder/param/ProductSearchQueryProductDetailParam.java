package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

import com.salesmanager.core.business.alibaba.rawsdk.client.APIId;
import com.salesmanager.core.business.alibaba.rawsdk.common.AbstractAPIRequest;


public class ProductSearchQueryProductDetailParam extends AbstractAPIRequest<ProductSearchQueryProductDetailResult> {

    public ProductSearchQueryProductDetailParam() {
        super();
        oceanApiId = new APIId("com.alibaba.fenxiao.crossborder", "product.search.queryProductDetail", 1);
    }

    private ProductSearchQueryProductDetailParamOfferDetailParam offerDetailParam;

    /**
     * @return 
     */
    public ProductSearchQueryProductDetailParamOfferDetailParam getOfferDetailParam() {
        return offerDetailParam;
    }

    /**
     *          *
     *        
     *
     */
    public void setOfferDetailParam(ProductSearchQueryProductDetailParamOfferDetailParam offerDetailParam) {
        this.offerDetailParam = offerDetailParam;
    }

}