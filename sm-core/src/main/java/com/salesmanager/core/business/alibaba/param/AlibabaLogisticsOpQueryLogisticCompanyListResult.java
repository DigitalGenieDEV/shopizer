package com.salesmanager.core.business.alibaba.param;

public class AlibabaLogisticsOpQueryLogisticCompanyListResult {

    private AlibabaLogisticsOpLogisticsCompanyModel[] result;

    /**
     * @return 物流公司列表
     */
    public AlibabaLogisticsOpLogisticsCompanyModel[] getResult() {
        return result;
    }

    /**
     *     物流公司列表     *
          
     *    
     */
    public void setResult(AlibabaLogisticsOpLogisticsCompanyModel[] result) {
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
     * @return 错误码描述
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     *     错误码描述     *
          
     *    
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    private String extErrorMessage;

    /**
     * @return 扩展错误码描述
     */
    public String getExtErrorMessage() {
        return extErrorMessage;
    }

    /**
     *     扩展错误码描述     *
          
     *    
     */
    public void setExtErrorMessage(String extErrorMessage) {
        this.extErrorMessage = extErrorMessage;
    }

}
