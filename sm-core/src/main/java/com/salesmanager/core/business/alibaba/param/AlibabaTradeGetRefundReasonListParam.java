package com.salesmanager.core.business.alibaba.param;

import com.salesmanager.core.business.alibaba.rawsdk.client.APIId;
import com.salesmanager.core.business.alibaba.rawsdk.common.AbstractAPIRequest;


public class AlibabaTradeGetRefundReasonListParam extends AbstractAPIRequest<AlibabaTradeGetRefundReasonListResult> {

    public AlibabaTradeGetRefundReasonListParam() {
        super();
        oceanApiId = new APIId("com.alibaba.trade", "alibaba.trade.getRefundReasonList", 1);
    }

    private Long orderId;

    /**
     * @return 主订单id
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     *     主订单id     *
     *    
     *
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    private long[] orderEntryIds;

    /**
     * @return 子订单id
     */
    public long[] getOrderEntryIds() {
        return orderEntryIds;
    }

    /**
     *     子订单id     *
     *    
     *
     */
    public void setOrderEntryIds(long[] orderEntryIds) {
        this.orderEntryIds = orderEntryIds;
    }

    private String goodsStatus;

    /**
     * @return 货物状态
     */
    public String getGoodsStatus() {
        return goodsStatus;
    }

    /**
     *     货物状态     *
     * 参数示例：<pre>售中等待买家发货:”refundWaitSellerSend"; 售中等待买家收货:"refundWaitBuyerReceive"; 售中已收货（未确认完成交易）:"refundBuyerReceived" 售后未收货:"aftersaleBuyerNotReceived"; 售后已收到货:"aftersaleBuyerReceived"</pre>     
     *
     */
    public void setGoodsStatus(String goodsStatus) {
        this.goodsStatus = goodsStatus;
    }

}
