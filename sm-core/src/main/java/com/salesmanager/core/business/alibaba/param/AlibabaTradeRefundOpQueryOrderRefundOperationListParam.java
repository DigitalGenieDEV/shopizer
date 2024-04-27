package com.salesmanager.core.business.alibaba.param;


import com.salesmanager.core.business.alibaba.rawsdk.client.APIId;
import com.salesmanager.core.business.alibaba.rawsdk.common.AbstractAPIRequest;

public class AlibabaTradeRefundOpQueryOrderRefundOperationListParam extends AbstractAPIRequest<AlibabaTradeRefundOpQueryOrderRefundOperationListResult> {

    public AlibabaTradeRefundOpQueryOrderRefundOperationListParam() {
        super();
        oceanApiId = new APIId("com.alibaba.trade", "alibaba.trade.refund.OpQueryOrderRefundOperationList", 1);
    }

    private String refundId;

    /**
     * @return 退款单Id
     */
    public String getRefundId() {
        return refundId;
    }

    /**
     *     退款单Id     *
     * 参数示例：<pre>TQ1043162**46961198</pre>     
     *
     */
    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    private String pageNo;

    /**
     * @return 当前页号
     */
    public String getPageNo() {
        return pageNo;
    }

    /**
     *     当前页号     *
     * 参数示例：<pre>1</pre>     
     *
     */
    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    private String pageSize;

    /**
     * @return 页大小
     */
    public String getPageSize() {
        return pageSize;
    }

    /**
     *     页大小     *
     * 参数示例：<pre>100</pre>     
     *
     */
    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

}
