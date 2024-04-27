package com.salesmanager.core.business.alibaba.param;


import com.salesmanager.core.business.alibaba.rawsdk.client.APIId;
import com.salesmanager.core.business.alibaba.rawsdk.common.AbstractAPIRequest;

public class AlibabaTradeRefundOpQueryBatchRefundByOrderIdAndStatusParam extends
        AbstractAPIRequest<AlibabaTradeRefundOpQueryBatchRefundByOrderIdAndStatusResult> {

    public AlibabaTradeRefundOpQueryBatchRefundByOrderIdAndStatusParam() {
        super();
        oceanApiId = new APIId("com.alibaba.trade", "alibaba.trade.refund.OpQueryBatchRefundByOrderIdAndStatus", 1);
    }

    private String orderId;

    /**
     * @return 订单id
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     *     订单id     *
     * 参数示例：<pre>151267031**8969811</pre>     
     *    
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    private String queryType;

    /**
     * @return 1：活动；3:退款成功（只支持退款中和退款成功）
     */
    public String getQueryType() {
        return queryType;
    }

    /**
     *     1：活动；3:退款成功（只支持退款中和退款成功）     *
     * 参数示例：<pre>3</pre>     
     *    
     */
    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

}
