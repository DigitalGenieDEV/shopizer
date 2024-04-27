package com.salesmanager.core.business.alibaba.param;

import com.salesmanager.core.business.alibaba.rawsdk.client.APIId;
import com.salesmanager.core.business.alibaba.rawsdk.common.AbstractAPIRequest;



public class AlibabaAlipayUrlGetParam extends AbstractAPIRequest<AlibabaAlipayUrlGetResult> {

    public AlibabaAlipayUrlGetParam() {
        super();
        oceanApiId = new APIId("com.alibaba.trade", "alibaba.alipay.url.get", 1);
    }

    private long[] orderIdList;

    /**
     */
    public long[] getOrderIdList() {
        return orderIdList;
    }

    /**
     */
    public void setOrderIdList(long[] orderIdList) {
        this.orderIdList = orderIdList;
    }

}
