package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class ProductSearchQueryProductDetailResutResultModel {

    private Boolean success;

    /**
     * @return 
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    private String code;

    /**
     * @return 
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setCode(String code) {
        this.code = code;
    }

    private String message;

    /**
     * @return 
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setMessage(String message) {
        this.message = message;
    }

    private ProductSearchQueryProductDetailModelProductDetailModel result;

    /**
     * @return 
     */
    public ProductSearchQueryProductDetailModelProductDetailModel getResult() {
        return result;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setResult(ProductSearchQueryProductDetailModelProductDetailModel result) {
        this.result = result;
    }

}
