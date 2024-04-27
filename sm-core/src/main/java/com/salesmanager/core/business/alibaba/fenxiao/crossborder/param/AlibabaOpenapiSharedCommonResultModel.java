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
     *     是否成功     *
     * 参数示例：<pre>true</pre>     
     *    
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
     *     错误码     *
     * 参数示例：<pre>200</pre>     
     *    
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
     *     错误描述     *
     * 参数示例：<pre>null</pre>     
     *    
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
     *     8.0     *
     * 参数示例：<pre>商品中国国内预估运费</pre>     
     *    
     */
    public void setResult(String result) {
        this.result = result;
    }

}
