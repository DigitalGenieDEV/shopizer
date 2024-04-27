package com.salesmanager.core.business.alibaba.param;

public class AccountPeriodListBuyerViewResult {

    private String totalCount;

    /**
     * @return
     */
    public String getTotalCount() {
        return totalCount;
    }

    /**
     */
    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    private AlibabaOceanOpenplatformBizTradeCommonModelAccountPeriodInfo[] accountPeriodList;

    /**
     */
    public AlibabaOceanOpenplatformBizTradeCommonModelAccountPeriodInfo[] getAccountPeriodList() {
        return accountPeriodList;
    }

    /**
     */
    public void setAccountPeriodList(AlibabaOceanOpenplatformBizTradeCommonModelAccountPeriodInfo[] accountPeriodList) {
        this.accountPeriodList = accountPeriodList;
    }

}
