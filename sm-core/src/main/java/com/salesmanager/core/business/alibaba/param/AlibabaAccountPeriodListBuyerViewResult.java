package com.salesmanager.core.business.alibaba.param;

public class AlibabaAccountPeriodListBuyerViewResult {

    private Boolean success;

    /**
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    private String errorCode;

    /**
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    private String errorMsg;

    /**
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    private AccountPeriodListBuyerViewResult resultList;

    /**
     */
    public AccountPeriodListBuyerViewResult getResultList() {
        return resultList;
    }

    /**
     */
    public void setResultList(AccountPeriodListBuyerViewResult resultList) {
        this.resultList = resultList;
    }

}
