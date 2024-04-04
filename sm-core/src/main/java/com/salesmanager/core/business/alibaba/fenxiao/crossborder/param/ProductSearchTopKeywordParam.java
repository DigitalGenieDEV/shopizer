package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

import com.salesmanager.core.business.alibaba.rawsdk.client.APIId;
import com.salesmanager.core.business.alibaba.rawsdk.common.AbstractAPIRequest;


public class ProductSearchTopKeywordParam extends AbstractAPIRequest<ProductSearchTopKeywordResult> {

    public ProductSearchTopKeywordParam() {
        super();
        oceanApiId = new APIId("com.alibaba.fenxiao.crossborder", "product.search.topKeyword", 1);
    }

    private AlibabaCbuOfferParamTopSeKeywordParam topSeKeywordParam;

    /**
     * @return 
     */
    public AlibabaCbuOfferParamTopSeKeywordParam getTopSeKeywordParam() {
        return topSeKeywordParam;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setTopSeKeywordParam(AlibabaCbuOfferParamTopSeKeywordParam topSeKeywordParam) {
        this.topSeKeywordParam = topSeKeywordParam;
    }

}
