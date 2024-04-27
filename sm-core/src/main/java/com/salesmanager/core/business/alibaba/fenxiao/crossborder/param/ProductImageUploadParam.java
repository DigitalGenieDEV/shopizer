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
     *          *
     *        
     *
     */
    public void setUploadImageParam(ProductImageUploadParamUploadImageParam uploadImageParam) {
        this.uploadImageParam = uploadImageParam;
    }

}
