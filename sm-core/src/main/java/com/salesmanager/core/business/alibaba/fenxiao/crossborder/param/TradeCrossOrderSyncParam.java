package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

import com.salesmanager.core.business.alibaba.rawsdk.client.APIId;
import com.salesmanager.core.business.alibaba.rawsdk.common.AbstractAPIRequest;


public class TradeCrossOrderSyncParam extends AbstractAPIRequest<TradeCrossOrderSyncResult> {

    public TradeCrossOrderSyncParam() {
        super();
        oceanApiId = new APIId("com.alibaba.fenxiao.crossborder", "trade.cross.orderSync", 1);
    }

    private ComAlibabaCbuOrderParamOrderParamV orderParam;

    /**
     * @return 订单详情
     */
    public ComAlibabaCbuOrderParamOrderParamV getOrderParam() {
        return orderParam;
    }

    /**
     *     订单详情     *
     *        
     *
     */
    public void setOrderParam(ComAlibabaCbuOrderParamOrderParamV orderParam) {
        this.orderParam = orderParam;
    }

}
