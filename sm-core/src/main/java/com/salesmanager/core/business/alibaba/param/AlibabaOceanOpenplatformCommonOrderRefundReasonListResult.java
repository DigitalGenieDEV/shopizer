package com.salesmanager.core.business.alibaba.param;

public class AlibabaOceanOpenplatformCommonOrderRefundReasonListResult {

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
     *    
     */
    public void setMessage(String message) {
        this.message = message;
    }

    private AlibabaOceanOpenplatformBizTradeResultOrderRefundReasonListResult result;

    /**
     * @return 结果
     */
    public AlibabaOceanOpenplatformBizTradeResultOrderRefundReasonListResult getResult() {
        return result;
    }

    /**
     *     结果     *
     *    
     *    
     */
    public void setResult(AlibabaOceanOpenplatformBizTradeResultOrderRefundReasonListResult result) {
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
     *    
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }

}
