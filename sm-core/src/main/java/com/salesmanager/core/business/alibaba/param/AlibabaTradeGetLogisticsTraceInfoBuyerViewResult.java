package com.salesmanager.core.business.alibaba.param;

public class AlibabaTradeGetLogisticsTraceInfoBuyerViewResult {

    private AlibabaLogisticsOpenPlatformLogisticsTrace[] logisticsTrace;

    /**
     * @return 跟踪单详情
     */
    public AlibabaLogisticsOpenPlatformLogisticsTrace[] getLogisticsTrace() {
        return logisticsTrace;
    }

    /**
     *     跟踪单详情     *
          
     *    
     */
    public void setLogisticsTrace(AlibabaLogisticsOpenPlatformLogisticsTrace[] logisticsTrace) {
        this.logisticsTrace = logisticsTrace;
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
