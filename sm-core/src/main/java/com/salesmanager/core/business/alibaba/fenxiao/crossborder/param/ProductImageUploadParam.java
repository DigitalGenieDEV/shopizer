package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

import com.salesmanager.core.business.alibaba.rawsdk.client.APIId;
import com.salesmanager.core.business.alibaba.rawsdk.common.AbstractAPIRequest;


public class ProductImageUploadParam extends AbstractAPIRequest<ProductImageUploadResult> {

    public ProductImageUploadParam() {
        super();
        oceanApiId = new APIId("com.alibaba.fenxiao.crossborder", "product.image.upload", 1);
    }

    private ProductImageUploadParamUploadImageParam uploadImageParam;

    /**
     * @return 
     */
    public ProductImageUploadParamUploadImageParam getUploadImageParam() {
        return uploadImageParam;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setUploadImageParam(ProductImageUploadParamUploadImageParam uploadImageParam) {
        this.uploadImageParam = uploadImageParam;
    }

}
