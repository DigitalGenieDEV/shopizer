package com.salesmanager.core.business.alibaba.param;

public class AlibabaTradeRefundOpQueryOrderRefundOperationListResult {

    private AlibabaTradeRefundOpQueryOrderRefundOperationListResult result;

    /**
     * @return 返回结果
     */
    public AlibabaTradeRefundOpQueryOrderRefundOperationListResult getResult() {
        return result;
    }

    /**
     *     返回结果     *
          
     *    
     */
    public void setResult(AlibabaTradeRefundOpQueryOrderRefundOperationListResult result) {
        this.result = result;
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

    private String extErrorMessage;

    /**
     * @return 附加错误信息
     */
    public String getExtErrorMessage() {
        return extErrorMessage;
    }

    /**
     *     附加错误信息     *
          
     *    
     */
    public void setExtErrorMessage(String extErrorMessage) {
        this.extErrorMessage = extErrorMessage;
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
