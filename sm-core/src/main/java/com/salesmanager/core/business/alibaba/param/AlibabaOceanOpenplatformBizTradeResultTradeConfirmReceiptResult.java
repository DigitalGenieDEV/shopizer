package com.salesmanager.core.business.alibaba.param;

public class AlibabaOceanOpenplatformBizTradeResultTradeConfirmReceiptResult {

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

    private String errorInfo;

    /**
     * @return 
     */
    public String getErrorInfo() {
        return errorInfo;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    private String errorCode;

    /**
     * @return 
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

}
