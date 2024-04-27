package com.salesmanager.core.business.alibaba.param;

public class AlibabaTradeAddresscodeParseResult {

    private AlibabaTradeReceiveAddress result;

    /**
     * @return 解析后的收获地址
     */
    public AlibabaTradeReceiveAddress getResult() {
        return result;
    }

    /**
     *     解析后的收获地址     *
          
     *
     */
    public void setResult(AlibabaTradeReceiveAddress result) {
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

}
