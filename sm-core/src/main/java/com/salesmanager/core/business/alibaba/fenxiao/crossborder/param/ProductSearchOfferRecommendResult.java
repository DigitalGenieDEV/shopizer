package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class ProductSearchOfferRecommendResult {

    private ComAlibabaOpenapiSharedCommonRecommendResultModel result;

    /**
     * @return 返回信息
     */
    public ComAlibabaOpenapiSharedCommonRecommendResultModel getResult() {
        return result;
    }

    /**
     * 设置返回信息     *
          
     * 此参数必填
     */
    public void setResult(ComAlibabaOpenapiSharedCommonRecommendResultModel result) {
        this.result = result;
    }

}
