package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class ProductTopListQueryResultModel {

    private Boolean success;

    /**
     * @return 是否成功
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     * 设置是否成功     *
     * 参数示例：<pre>true</pre>     
     * 此参数必填
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    private String code;

    /**
     * @return 错误码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置错误码     *
     * 参数示例：<pre>S0000</pre>     
     * 此参数必填
     */
    public void setCode(String code) {
        this.code = code;
    }

    private String message;

    /**
     * @return 错误描述
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置错误描述     *
     * 参数示例：<pre>成功</pre>     
     * 此参数必填
     */
    public void setMessage(String message) {
        this.message = message;
    }

    private ProductTopListQueryRankModel result;

    /**
     * @return 结果
     */
    public ProductTopListQueryRankModel getResult() {
        return result;
    }

    /**
     * 设置结果     *
     * 参数示例：<pre>结果</pre>     
     * 此参数必填
     */
    public void setResult(ProductTopListQueryRankModel result) {
        this.result = result;
    }

}
