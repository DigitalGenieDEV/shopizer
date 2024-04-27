package com.salesmanager.core.business.alibaba.param;


import com.salesmanager.core.business.alibaba.rawsdk.client.APIId;
import com.salesmanager.core.business.alibaba.rawsdk.common.AbstractAPIRequest;

public class AlibabaTradeCancelParam extends AbstractAPIRequest<AlibabaTradeCancelResult> {

    public AlibabaTradeCancelParam() {
        super();
        oceanApiId = new APIId("com.alibaba.trade", "alibaba.trade.cancel", 1);
    }

    private String webSite;

    /**
     * @return 站点信息，指定调用的API是属于国际站（alibaba）还是1688网站（1688）
     */
    public String getWebSite() {
        return webSite;
    }

    /**
     *     站点信息，指定调用的API是属于国际站（alibaba）还是1688网站（1688）     *
     * 参数示例：<pre>1688</pre>     
     *
     */
    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    private Long tradeID;

    /**
     * @return 交易id，订单号
     */
    public Long getTradeID() {
        return tradeID;
    }

    /**
     *     交易id，订单号     *
     * 参数示例：<pre>123456</pre>     
     *
     */
    public void setTradeID(Long tradeID) {
        this.tradeID = tradeID;
    }

    private String cancelReason;

    /**
     * @return 原因描述；buyerCancel:买家取消订单;sellerGoodsLack:卖家库存不足;other:其它
     */
    public String getCancelReason() {
        return cancelReason;
    }

    /**
     *     原因描述；buyerCancel:买家取消订单;sellerGoodsLack:卖家库存不足;other:其它     *
     * 参数示例：<pre>other</pre>     
     *
     */
    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    private String remark;

    /**
     * @return 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     *     备注     *
     * 参数示例：<pre>备注</pre>     
     *
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

}
