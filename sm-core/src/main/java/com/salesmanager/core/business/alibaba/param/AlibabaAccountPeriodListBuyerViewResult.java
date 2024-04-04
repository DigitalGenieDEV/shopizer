package com.salesmanager.core.business.alibaba.param;

public class AlibabaAccountPeriodListBuyerViewResult {

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
     * @return 错误信息
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * 设置错误信息     *
          
     * 此参数必填
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    private AccountPeriodListBuyerViewResult resultList;

    /**
     * @return 返回数据结果
     */
    public AccountPeriodListBuyerViewResult getResultList() {
        return resultList;
    }

    /**
     * 设置返回数据结果     *
          
     * 此参数必填
     */
    public void setResultList(AccountPeriodListBuyerViewResult resultList) {
        this.resultList = resultList;
    }

}
