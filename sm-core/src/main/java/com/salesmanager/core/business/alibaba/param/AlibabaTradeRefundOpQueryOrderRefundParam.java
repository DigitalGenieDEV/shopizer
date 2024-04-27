package com.salesmanager.core.business.alibaba.param;

import com.salesmanager.core.business.alibaba.rawsdk.client.APIId;
import com.salesmanager.core.business.alibaba.rawsdk.common.AbstractAPIRequest;


public class AlibabaTradeRefundOpQueryOrderRefundParam extends AbstractAPIRequest<AlibabaTradeRefundOpQueryOrderRefundResult> {

    public AlibabaTradeRefundOpQueryOrderRefundParam() {
        super();
        oceanApiId = new APIId("com.alibaba.trade", "alibaba.trade.refund.OpQueryOrderRefund", 1);
    }

    private String refundId;

    /**
     * @return 退款单业务主键 TQ+ID
     */
    public String getRefundId() {
        return refundId;
    }

    /**
     *     退款单业务主键 TQ+ID     *
     * 参数示例：<pre>TQ11173622***991577</pre>     
     *
     */
    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    private Boolean needTimeOutInfo;

    /**
     * @return 需要退款单的超时信息
     */
    public Boolean getNeedTimeOutInfo() {
        return needTimeOutInfo;
    }

    /**
     *     需要退款单的超时信息     *
     * 参数示例：<pre>true</pre>     
     *
     */
    public void setNeedTimeOutInfo(Boolean needTimeOutInfo) {
        this.needTimeOutInfo = needTimeOutInfo;
    }

    private Boolean needOrderRefundOperation;

    /**
     * @return 需要退款单伴随的所有退款操作信息
     */
    public Boolean getNeedOrderRefundOperation() {
        return needOrderRefundOperation;
    }

    /**
     *     需要退款单伴随的所有退款操作信息     *
     * 参数示例：<pre>true</pre>     
     *
     */
    public void setNeedOrderRefundOperation(Boolean needOrderRefundOperation) {
        this.needOrderRefundOperation = needOrderRefundOperation;
    }

}
