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
     * 设置是否成功     *
          
     * 此参数必填
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
     * 设置错误码     *
          
     * 此参数必填
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
     * 设置错误消息     *
          
     * 此参数必填
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
     * 设置签约状态     *
          
     * 此参数必填
     */
    public void setResult(AlibabaOceanOpenplatformBizTradeResultTradeWithholdStatusResult result) {
        this.result = result;
    }

}
