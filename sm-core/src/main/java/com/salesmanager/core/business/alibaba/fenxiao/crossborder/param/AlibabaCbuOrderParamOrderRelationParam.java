package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class AlibabaCbuOrderParamOrderRelationParam {

    private String orderId;

    /**
     * @return 外部机构子订单ID
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 设置外部机构子订单ID     *
     * 参数示例：<pre>4590347523948375</pre>     
     * 此参数必填
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    private String parentOrderId;

    /**
     * @return 外部机构主订单ID
     */
    public String getParentOrderId() {
        return parentOrderId;
    }

    /**
     * 设置外部机构主订单ID     *
     * 参数示例：<pre>4590347523948370</pre>     
     * 此参数必填
     */
    public void setParentOrderId(String parentOrderId) {
        this.parentOrderId = parentOrderId;
    }

    private Long purchaseOrderId;

    /**
     * @return 1688采购子订单ID
     */
    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    /**
     * 设置1688采购子订单ID     *
     * 参数示例：<pre>3209572465452734</pre>     
     * 此参数必填
     */
    public void setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    private Long purchaseParentOrderId;

    /**
     * @return 1688采购子订单ID
     */
    public Long getPurchaseParentOrderId() {
        return purchaseParentOrderId;
    }

    /**
     * 设置1688采购子订单ID     *
     * 参数示例：<pre>3209572465452730</pre>     
     * 此参数必填
     */
    public void setPurchaseParentOrderId(Long purchaseParentOrderId) {
        this.purchaseParentOrderId = purchaseParentOrderId;
    }

    private Long appKey;

    /**
     * @return 
     */
    public Long getAppKey() {
        return appKey;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setAppKey(Long appKey) {
        this.appKey = appKey;
    }

    private Long agentUserId;

    /**
     * @return 
     */
    public Long getAgentUserId() {
        return agentUserId;
    }

    /**
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
     */
    public void setAgentUserId(Long agentUserId) {
        this.agentUserId = agentUserId;
    }

}
