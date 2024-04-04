package com.salesmanager.core.business.alibaba.param;

public class PushMessageConfirmResult {

    private Boolean isSuccess;

    /**
     * @return 操作是否成功
     */
    public Boolean getIsSuccess() {
        return isSuccess;
    }

    /**
     * 设置操作是否成功     *
          
     * 此参数必填
     */
    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

}
