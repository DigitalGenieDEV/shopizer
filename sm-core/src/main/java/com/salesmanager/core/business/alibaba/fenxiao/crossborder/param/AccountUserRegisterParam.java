package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

import com.salesmanager.core.business.alibaba.rawsdk.client.APIId;
import com.salesmanager.core.business.alibaba.rawsdk.common.AbstractAPIRequest;


public class AccountUserRegisterParam extends AbstractAPIRequest<AccountUserRegisterResult> {

    public AccountUserRegisterParam() {
        super();
        oceanApiId = new APIId("com.alibaba.fenxiao.crossborder", "account.user.register", 1);
    }

    private AccountUserRegisterParamCountryAccount countryAccount;

    /**
     * @return 账号注册入参
     */
    public AccountUserRegisterParamCountryAccount getCountryAccount() {
        return countryAccount;
    }

    /**
     * 设置账号注册入参     *
     * 参数示例：<pre>{     "country": "japan",     "site": "sniff",     "outLoginId": "18899993333",     "outMemberId": "c5b9e8a658554771852063f3a44d4e3d",     "mobile": "18899993333",     "mobileArea": "JP",     "ip": "11.11.11.11",     "email": "123@163.com" }</pre>     
     * 此参数必填
     */
    public void setCountryAccount(AccountUserRegisterParamCountryAccount countryAccount) {
        this.countryAccount = countryAccount;
    }

}
