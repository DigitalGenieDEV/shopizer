package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class AlibabaOpenapiSharedCommonResultModel {

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
     * 参数示例：<pre>200</pre>     
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
     * 参数示例：<pre>null</pre>     
     * 此参数必填
     */
    public void setMessage(String message) {
        this.message = message;
    }

    private String result;

    /**
     * @return 8.0
     */
    public String getResult() {
        return result;
    }

    /**
     * 设置8.0     *
     * 参数示例：<pre>商品中国国内预估运费</pre>     
     * 此参数必填
     */
    public void setResult(String result) {
        this.result = result;
    }

}
