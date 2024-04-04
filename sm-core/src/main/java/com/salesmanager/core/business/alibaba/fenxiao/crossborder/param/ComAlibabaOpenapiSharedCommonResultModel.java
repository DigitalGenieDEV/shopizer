package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class ComAlibabaOpenapiSharedCommonResultModel {

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
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置code     *
     * 参数示例：<pre>200</pre>     
     * 此参数必填
     */
    public void setCode(String code) {
        this.code = code;
    }

    private String message;

    /**
     * @return 返回信息
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置返回信息     *
     * 参数示例：<pre>成功</pre>     
     * 此参数必填
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
