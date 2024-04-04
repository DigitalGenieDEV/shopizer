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
     * 设置查询结果     *
          
     * 此参数必填
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
     * 设置错误码     *
          
     * 此参数必填
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
     * 设置错误描述信息     *
          
     * 此参数必填
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
     * 设置补充错误描述信息     *
          
     * 此参数必填
     */
    public void setExtErrorMessage(String extErrorMessage) {
        this.extErrorMessage = extErrorMessage;
    }

}
