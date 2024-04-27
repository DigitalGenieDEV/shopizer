package com.salesmanager.core.business.alibaba.param;

public class AlibabaTradeGetBuyerViewResult {

    private AlibabaOpenplatformTradeModelTradeInfo result;

    /**
     * @return 订单详情信息
     */
    public AlibabaOpenplatformTradeModelTradeInfo getResult() {
        return result;
    }

    /**
     *     订单详情信息     *
          
     *
     */
    public void setResult(AlibabaOpenplatformTradeModelTradeInfo result) {
        this.result = result;
    }

    private String errorCode;

    /**
     * @return 错误代码
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     *     错误代码     *
          
     *
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    private String errorMessage;

    /**
     * @return 错误描述
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     *     错误描述     *
          
     *
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    private String success;

    /**
     * @return 是否成功
     */
    public String getSuccess() {
        return success;
    }

    /**
     *     是否成功     *
          
     *
     */
    public void setSuccess(String success) {
        this.success = success;
    }

}
