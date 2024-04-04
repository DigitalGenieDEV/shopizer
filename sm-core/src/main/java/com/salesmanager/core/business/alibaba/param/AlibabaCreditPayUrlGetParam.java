package com.salesmanager.core.business.alibaba.param;


import com.salesmanager.core.business.alibaba.rawsdk.client.APIId;
import com.salesmanager.core.business.alibaba.rawsdk.common.AbstractAPIRequest;

public class AlibabaCreditPayUrlGetParam extends AbstractAPIRequest<AlibabaCreditPayUrlGetResult> {

    public AlibabaCreditPayUrlGetParam() {
        super();
        oceanApiId = new APIId("com.alibaba.trade", "alibaba.creditPay.url.get", 1);
    }

    private long[] orderIdList;

    /**
     * @return 订单Id列表,最多批量30个订单，订单过多会导致超时，建议一次10个订单
     */
    public long[] getOrderIdList() {
        return orderIdList;
    }

    /**
     * 设置订单Id列表,最多批量30个订单，订单过多会导致超时，建议一次10个订单     *
     * 参数示例：<pre>[111111,22222333]</pre>     
     * 此参数必填
     */
    public void setOrderIdList(long[] orderIdList) {
        this.orderIdList = orderIdList;
    }

}
