package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

import com.salesmanager.core.business.alibaba.rawsdk.client.APIId;
import com.salesmanager.core.business.alibaba.rawsdk.common.AbstractAPIRequest;


public class PoolProductPullParam extends AbstractAPIRequest<PoolProductPullResult> {

    public PoolProductPullParam() {
        super();
        oceanApiId = new APIId("com.alibaba.fenxiao.crossborder", "pool.product.pull", 1);
    }

    private PoolProductPullOfferPoolQueryParam offerPoolQueryParam;

    /**
     * @return 
     */
    public PoolProductPullOfferPoolQueryParam getOfferPoolQueryParam() {
        return offerPoolQueryParam;
    }

    /**
     *          *
     *        
     *
     */
    public void setOfferPoolQueryParam(PoolProductPullOfferPoolQueryParam offerPoolQueryParam) {
        this.offerPoolQueryParam = offerPoolQueryParam;
    }

}
