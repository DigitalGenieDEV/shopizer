package com.salesmanager.core.business.alibaba.param;

public class AlibabaTradeCreateCrossOrderResult {

    private AlibabaTradeCrossResult result;

    /**
     * @return 创建订单结果
     */
    public AlibabaTradeCrossResult getResult() {
        return result;
    }

    /**
     *     创建订单结果     *
          
     *
     */
    public void setResult(AlibabaTradeCrossResult result) {
        this.result = result;
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

    private String code;

    /**
     * @return 错误码
     */
    public String getCode() {
        return code;
    }

    /**
     *     错误码     *
          
     *
     */
    public void setCode(String code) {
        this.code = code;
    }

    private String message;

    /**
     * @return 错误信息
     */
    public String getMessage() {
        return message;
    }

    /**
     *     错误信息     *
          
     *
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
