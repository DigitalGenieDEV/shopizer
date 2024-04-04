package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

import com.salesmanager.core.business.alibaba.rawsdk.client.APIId;
import com.salesmanager.core.business.alibaba.rawsdk.common.AbstractAPIRequest;


public class TradeCrossLogisticsOrderSyncParam extends AbstractAPIRequest<TradeCrossLogisticsOrderSyncResult> {

    public TradeCrossLogisticsOrderSyncParam() {
        super();
        oceanApiId = new APIId("com.alibaba.fenxiao.crossborder", "trade.cross.logisticsOrderSync", 1);
    }

    private AlibabaCbuOrderOutParamOrderLogisticsParam orderLogisticsParam;

    /**
     * @return 
     */
    public AlibabaCbuOrderOutParamOrderLogisticsParam getOrderLogisticsParam() {
        return orderLogisticsParam;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setOrderLogisticsParam(AlibabaCbuOrderOutParamOrderLogisticsParam orderLogisticsParam) {
        this.orderLogisticsParam = orderLogisticsParam;
    }

}
