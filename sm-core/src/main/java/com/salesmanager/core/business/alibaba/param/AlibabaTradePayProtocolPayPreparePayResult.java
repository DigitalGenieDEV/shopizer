package com.salesmanager.core.business.alibaba.param;

public class AlibabaTradePayProtocolPayPreparePayResult {

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

    private ComAlibabaOceanOpenplatformBizTradeResultTradeWithholdPreparePayResultMresult result;

    /**
     * @return 扣款返回值
     */
    public ComAlibabaOceanOpenplatformBizTradeResultTradeWithholdPreparePayResultMresult getResult() {
        return result;
    }

    /**
     *     扣款返回值     *
          
     *    
     */
    public void setResult(ComAlibabaOceanOpenplatformBizTradeResultTradeWithholdPreparePayResultMresult result) {
        this.result = result;
    }

}
