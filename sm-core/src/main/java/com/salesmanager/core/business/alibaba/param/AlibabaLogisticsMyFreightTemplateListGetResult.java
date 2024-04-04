package com.salesmanager.core.business.alibaba.param;

public class AlibabaLogisticsMyFreightTemplateListGetResult {

    private AlibabaLogisticsFreightTemplate[] result;

    /**
     * @return 返回结果
     */
    public AlibabaLogisticsFreightTemplate[] getResult() {
        return result;
    }

    /**
     * 设置返回结果     *
          
     * 此参数必填
     */
    public void setResult(AlibabaLogisticsFreightTemplate[] result) {
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
     * 设置错误码     *
          
     * 此参数必填
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    private String errorMsg;

    /**
     * @return 错误描述
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * 设置错误描述     *
          
     * 此参数必填
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
