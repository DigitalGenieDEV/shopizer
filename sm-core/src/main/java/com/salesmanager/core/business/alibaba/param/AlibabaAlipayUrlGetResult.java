package com.salesmanager.core.business.alibaba.param;

public class AlibabaAlipayUrlGetResult {

    private String erroMsg;

    /**
     * @return 错误信息
     */
    public String getErroMsg() {
        return erroMsg;
    }

    /**
     *     错误信息     *
          
     *
     */
    public void setErroMsg(String erroMsg) {
        this.erroMsg = erroMsg;
    }

    private String payUrl;

    /**
     * @return 支付链接
     */
    public String getPayUrl() {
        return payUrl;
    }

    /**
     *     支付链接     *
          
     *
     */
    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }

    private Boolean success;

    /**
     * @return 是否成功
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     *     是否成功     *
          
     *
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    private String errorCode;

    /**
     * @return 错误码
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     *     错误码     *
          
     *
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

}
