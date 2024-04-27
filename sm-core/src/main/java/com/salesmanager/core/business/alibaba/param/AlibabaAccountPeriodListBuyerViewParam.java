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
     * @return
     */
    public Long getPageIndex() {
        return pageIndex;
    }

    /**
     */
    public void setPageIndex(Long pageIndex) {
        this.pageIndex = pageIndex;
    }

    private String sellerLoginId;

    /**
     */
    public String getSellerLoginId() {
        return sellerLoginId;
    }

    /**
     */
    public void setSellerLoginId(String sellerLoginId) {
        this.sellerLoginId = sellerLoginId;
    }

}
