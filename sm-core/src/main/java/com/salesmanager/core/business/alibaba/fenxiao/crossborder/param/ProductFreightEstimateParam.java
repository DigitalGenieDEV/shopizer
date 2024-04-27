package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

import com.salesmanager.core.business.alibaba.rawsdk.client.APIId;
import com.salesmanager.core.business.alibaba.rawsdk.common.AbstractAPIRequest;


public class ProductFreightEstimateParam extends AbstractAPIRequest<ProductFreightEstimateResult> {

    public ProductFreightEstimateParam() {
        super();
        oceanApiId = new APIId("com.alibaba.fenxiao.crossborder", "product.freight.estimate", 1);
    }

    private ProductFreightEstimateProductFreightQueryParamsNew productFreightQueryParamsNew;

    /**
     * @return 入参
     */
    public ProductFreightEstimateProductFreightQueryParamsNew getProductFreightQueryParamsNew() {
        return productFreightQueryParamsNew;
    }

    /**
     *     入参     *
     * 参数示例：<pre>如下</pre>     
     *
     */
    public void setProductFreightQueryParamsNew(ProductFreightEstimateProductFreightQueryParamsNew productFreightQueryParamsNew) {
        this.productFreightQueryParamsNew = productFreightQueryParamsNew;
    }

}
