package com.salesmanager.core.business.alibaba.param;

public class AlibabaTradeReceiveAddressGetResult {

    private AlibabaTradeReceiveAddressResult result;

    /**
     * @return 返回结果
     */
    public AlibabaTradeReceiveAddressResult getResult() {
        return result;
    }

    /**
     *     返回结果     *
          
     *    
     */
    public void setResult(AlibabaTradeReceiveAddressResult result) {
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
     *     是否成功	     *
          
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
     * @return 错误信息
     */
    public String getMessage() {
        return message;
    }

    /**
     *     错误信息     *
          
     *    
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
