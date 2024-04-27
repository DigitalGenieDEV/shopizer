package com.salesmanager.core.business.alibaba.param;


import com.salesmanager.core.business.alibaba.rawsdk.client.APIId;
import com.salesmanager.core.business.alibaba.rawsdk.common.AbstractAPIRequest;

public class TradeReceivegoodsConfirmParam extends AbstractAPIRequest<TradeReceivegoodsConfirmResult> {

    public TradeReceivegoodsConfirmParam() {
        super();
        oceanApiId = new APIId("com.alibaba.trade", "trade.receivegoods.confirm", 1);
    }

    private Long orderId;

    /**
     * @return 订单ID
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     *     订单ID     *
     * 参数示例：<pre>56623232655125698</pre>     
     *    
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    private long[] orderEntryIds;

    /**
     * @return 子订单ID
     */
    public long[] getOrderEntryIds() {
        return orderEntryIds;
    }

    /**
     *     子订单ID     *
     * 参数示例：<pre>562356635566365512</pre>     
     *    
     */
    public void setOrderEntryIds(long[] orderEntryIds) {
        this.orderEntryIds = orderEntryIds;
    }

}
