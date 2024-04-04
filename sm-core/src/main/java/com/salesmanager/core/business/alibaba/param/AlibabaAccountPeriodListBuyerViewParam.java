package com.salesmanager.core.business.alibaba.param;


import com.salesmanager.core.business.alibaba.rawsdk.common.AbstractAPIRequest;
import com.salesmanager.core.business.alibaba.rawsdk.client.APIId;


public class AlibabaAccountPeriodListBuyerViewParam extends AbstractAPIRequest<AlibabaAccountPeriodListBuyerViewResult> {

    public AlibabaAccountPeriodListBuyerViewParam() {
        super();
        oceanApiId = new APIId("com.alibaba.trade", "alibaba.accountPeriod.list.buyerView", 1);
    }

    private Long pageIndex;

    /**
     * @return 页码
     */
    public Long getPageIndex() {
        return pageIndex;
    }

    /**
     * 设置页码     *
     * 参数示例：<pre>1</pre>     
     * 此参数必填
     */
    public void setPageIndex(Long pageIndex) {
        this.pageIndex = pageIndex;
    }

    private String sellerLoginId;

    /**
     * @return 卖家ID，不填则查询全部
     */
    public String getSellerLoginId() {
        return sellerLoginId;
    }

    /**
     * 设置卖家ID，不填则查询全部     *
     * 参数示例：<pre>alitestforisv01</pre>     
     * 此参数必填
     */
    public void setSellerLoginId(String sellerLoginId) {
        this.sellerLoginId = sellerLoginId;
    }

}
