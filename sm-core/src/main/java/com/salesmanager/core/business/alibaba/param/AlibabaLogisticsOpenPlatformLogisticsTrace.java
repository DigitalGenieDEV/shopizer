package com.salesmanager.core.business.alibaba.param;

public class AlibabaLogisticsOpenPlatformLogisticsTrace {

    private String logisticsId;

    /**
     * @return 物流编号，如BX110096003841234
     */
    public String getLogisticsId() {
        return logisticsId;
    }

    /**
     *     物流编号，如BX110096003841234     *
     *        
     *
     */
    public void setLogisticsId(String logisticsId) {
        this.logisticsId = logisticsId;
    }

    private Long orderId;

    /**
     * @return 订单编号
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     *     订单编号     *
     *        
     *
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    private String logisticsBillNo;

    /**
     * @return 物流单编号，如480330616596
     */
    public String getLogisticsBillNo() {
        return logisticsBillNo;
    }

    /**
     *     物流单编号，如480330616596     *
     *        
     *
     */
    public void setLogisticsBillNo(String logisticsBillNo) {
        this.logisticsBillNo = logisticsBillNo;
    }

    private AlibabaLogisticsOpenPlatformLogisticsStep[] logisticsSteps;

    /**
     * @return 物流跟踪步骤
     */
    public AlibabaLogisticsOpenPlatformLogisticsStep[] getLogisticsSteps() {
        return logisticsSteps;
    }

    /**
     *     物流跟踪步骤     *
     *        
     *
     */
    public void setLogisticsSteps(AlibabaLogisticsOpenPlatformLogisticsStep[] logisticsSteps) {
        this.logisticsSteps = logisticsSteps;
    }

    private AlibabaLogisticsOpenPlatformTraceNode[] traceNodeList;

    /**
     * @return 物流周转节点
     */
    public AlibabaLogisticsOpenPlatformTraceNode[] getTraceNodeList() {
        return traceNodeList;
    }

    /**
     *     物流周转节点     *
     *        
     *
     */
    public void setTraceNodeList(AlibabaLogisticsOpenPlatformTraceNode[] traceNodeList) {
        this.traceNodeList = traceNodeList;
    }

}
