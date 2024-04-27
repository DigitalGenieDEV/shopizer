package com.salesmanager.core.business.alibaba.param;

public class AlibabaTradeRefundBuyerQueryOrderRefundListResult {

    private AlibabaTradeRefundOpQueryOrderRefundListResult result;

    /**
     * @return 查询结果
     */
    public AlibabaTradeRefundOpQueryOrderRefundListResult getResult() {
        return result;
    }

    /**
     *     查询结果     *
          
     *    
     */
    public void setResult(AlibabaTradeRefundOpQueryOrderRefundListResult result) {
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

    private String errorMsg;

    /**
     * @return 错误信息
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     *     错误信息     *
          
     *    
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
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
