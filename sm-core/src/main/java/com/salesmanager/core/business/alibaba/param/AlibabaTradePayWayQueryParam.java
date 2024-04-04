package com.salesmanager.core.business.alibaba.param;

import com.salesmanager.core.business.alibaba.rawsdk.client.APIId;
import com.salesmanager.core.business.alibaba.rawsdk.common.AbstractAPIRequest;


public class AlibabaTradePayWayQueryParam extends AbstractAPIRequest<AlibabaTradePayWayQueryResult> {

    public AlibabaTradePayWayQueryParam() {
        super();
        oceanApiId = new APIId("com.alibaba.trade", "alibaba.trade.payWay.query", 1);
    }

    private String orderId;

    /**
     * @return 订单号
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 设置订单号     *
     * 参数示例：<pre>123123</pre>     
     * 此参数必填
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

}
