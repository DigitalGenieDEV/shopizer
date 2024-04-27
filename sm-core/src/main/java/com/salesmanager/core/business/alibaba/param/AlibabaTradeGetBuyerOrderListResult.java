package com.salesmanager.core.business.alibaba.param;

public class AlibabaTradeGetBuyerOrderListResult {

    private AlibabaOpenplatformTradeModelTradeInfo[] result;

    /**
     * @return 查询返回列表
     */
    public AlibabaOpenplatformTradeModelTradeInfo[] getResult() {
        return result;
    }

    /**
     *     查询返回列表     *
          
     *    
     */
    public void setResult(AlibabaOpenplatformTradeModelTradeInfo[] result) {
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

    private Long totalRecord;

    /**
     * @return 总记录数
     */
    public Long getTotalRecord() {
        return totalRecord;
    }

    /**
     *     总记录数     *
          
     *    
     */
    public void setTotalRecord(Long totalRecord) {
        this.totalRecord = totalRecord;
    }

}
