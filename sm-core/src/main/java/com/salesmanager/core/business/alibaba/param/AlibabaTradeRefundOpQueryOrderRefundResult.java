package com.salesmanager.core.business.alibaba.param;

public class AlibabaTradeRefundOpQueryOrderRefundResult {

    private AlibabaTradeRefundResultOpQueryOrderRefund result;

    /**
     * @return 查询结果
     */
    public AlibabaTradeRefundResultOpQueryOrderRefund getResult() {
        return result;
    }

    /**
     *     查询结果     *
          
     *
     */
    public void setResult(AlibabaTradeRefundResultOpQueryOrderRefund result) {
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
     * @return 错误描述信息
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     *     错误描述信息     *
          
     *
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    private String extErrorMessage;

    /**
     * @return 补充错误描述信息
     */
    public String getExtErrorMessage() {
        return extErrorMessage;
    }

    /**
     *     补充错误描述信息     *
          
     *
     */
    public void setExtErrorMessage(String extErrorMessage) {
        this.extErrorMessage = extErrorMessage;
    }

}
