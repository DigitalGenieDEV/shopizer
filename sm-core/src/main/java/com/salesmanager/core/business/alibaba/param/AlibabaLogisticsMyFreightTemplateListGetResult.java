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
     *     返回结果     *
          
     *
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
     *     错误码     *
          
     *
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
     *     错误描述     *
          
     *
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
