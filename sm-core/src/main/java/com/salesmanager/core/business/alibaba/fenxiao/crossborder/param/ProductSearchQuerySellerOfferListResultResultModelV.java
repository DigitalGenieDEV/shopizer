package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class ProductSearchQuerySellerOfferListResultResultModelV {

    private Boolean success;

    /**
     * @return 正否正常
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     * 设置正否正常     *
     * 参数示例：<pre>正否正常</pre>     
     * 此参数必填
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    private String code;

    /**
     * @return 状态码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置状态码     *
     * 参数示例：<pre>状态码</pre>     
     * 此参数必填
     */
    public void setCode(String code) {
        this.code = code;
    }

    private String message;

    /**
     * @return 提示
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置提示     *
     * 参数示例：<pre>提示</pre>     
     * 此参数必填
     */
    public void setMessage(String message) {
        this.message = message;
    }

    private ProductSearchQuerySellerOfferListModelPageInfoV result;

    /**
     * @return 内容
     */
    public ProductSearchQuerySellerOfferListModelPageInfoV getResult() {
        return result;
    }

    /**
     * 设置内容     *
     * 参数示例：<pre>内容</pre>     
     * 此参数必填
     */
    public void setResult(ProductSearchQuerySellerOfferListModelPageInfoV result) {
        this.result = result;
    }

}
