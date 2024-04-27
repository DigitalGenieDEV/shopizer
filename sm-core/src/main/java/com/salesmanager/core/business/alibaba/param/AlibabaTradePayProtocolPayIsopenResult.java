package com.salesmanager.core.business.alibaba.param;

public class AlibabaTradePayProtocolPayIsopenResult {

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
     * @return 错误消息
     */
    public String getMessage() {
        return message;
    }

    /**
     *     错误消息     *
          
     *    
     */
    public void setMessage(String message) {
        this.message = message;
    }

    private AlibabaOceanOpenplatformBizTradeResultTradeWithholdStatusResult result;

    /**
     * @return 签约状态
     */
    public AlibabaOceanOpenplatformBizTradeResultTradeWithholdStatusResult getResult() {
        return result;
    }

    /**
     *     签约状态     *
          
     *    
     */
    public void setResult(AlibabaOceanOpenplatformBizTradeResultTradeWithholdStatusResult result) {
        this.result = result;
    }

}
