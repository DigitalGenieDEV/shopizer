package com.salesmanager.core.business.alibaba.param;

public class AccountPeriodListBuyerViewResult {

    private String totalCount;

    /**
     * @return 总数据条数
     */
    public String getTotalCount() {
        return totalCount;
    }

    /**
     * 设置总数据条数     *
     * 参数示例：<pre>100</pre>     
     * 此参数必填
     */
    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    private AlibabaOceanOpenplatformBizTradeCommonModelAccountPeriodInfo[] accountPeriodList;

    /**
     * @return 授信列表
     */
    public AlibabaOceanOpenplatformBizTradeCommonModelAccountPeriodInfo[] getAccountPeriodList() {
        return accountPeriodList;
    }

    /**
     * 设置授信列表     *
     * 参数示例：<pre>[]</pre>     
     * 此参数必填
     */
    public void setAccountPeriodList(AlibabaOceanOpenplatformBizTradeCommonModelAccountPeriodInfo[] accountPeriodList) {
        this.accountPeriodList = accountPeriodList;
    }

}
