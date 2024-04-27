package com.salesmanager.core.business.alibaba.param;

public class AlibabaTradeGetLogisticsInfosBuyerViewResult {

    private AlibabaLogisticsOpenPlatformLogisticsOrder[] result;

    /**
     * @return 返回结果
     */
    public AlibabaLogisticsOpenPlatformLogisticsOrder[] getResult() {
        return result;
    }

    /**
     *     返回结果     *
          
     *
     */
    public void setResult(AlibabaLogisticsOpenPlatformLogisticsOrder[] result) {
        this.result = result;
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

    private String errorMessage;

    /**
     * @return 错误信息
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     *     错误信息     *
          
     *
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
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

}
