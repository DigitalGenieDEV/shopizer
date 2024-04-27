package com.salesmanager.core.business.alibaba.param;

public class AlibabaLogisticsOpenPlatformLogisticsOrder {

    private String logisticsId;

    /**
     * @return 物流信息ID
     */
    public String getLogisticsId() {
        return logisticsId;
    }

    /**
     *     物流信息ID     *
     *        
     *
     */
    public void setLogisticsId(String logisticsId) {
        this.logisticsId = logisticsId;
    }

    private String logisticsBillNo;

    /**
     * @return 物流单号，运单号
     */
    public String getLogisticsBillNo() {
        return logisticsBillNo;
    }

    /**
     *     物流单号，运单号     *
     *        
     *
     */
    public void setLogisticsBillNo(String logisticsBillNo) {
        this.logisticsBillNo = logisticsBillNo;
    }

    private String orderEntryIds;

    /**
     * @return 订单号列表，无子订单的等于主订单编号，否则为对应子订单列表
     */
    public String getOrderEntryIds() {
        return orderEntryIds;
    }

    /**
     *     订单号列表，无子订单的等于主订单编号，否则为对应子订单列表     *
     * 参数示例：<pre>129232515787615400,129232515788615400,129232515789615400,129232515790615400</pre>     
     *
     */
    public void setOrderEntryIds(String orderEntryIds) {
        this.orderEntryIds = orderEntryIds;
    }

    private String status;

    /**
     * @return 物流状态。WAITACCEPT:未受理;CANCEL:已撤销;ACCEPT:已受理;TRANSPORT:运输中;NOGET:揽件失败;SIGN:已签收;UNSIGN:签收异常
     */
    public String getStatus() {
        return status;
    }

    /**
     *     物流状态。WAITACCEPT:未受理;CANCEL:已撤销;ACCEPT:已受理;TRANSPORT:运输中;NOGET:揽件失败;SIGN:已签收;UNSIGN:签收异常     *
     *        
     *
     */
    public void setStatus(String status) {
        this.status = status;
    }

    private String logisticsCompanyId;

    /**
     * @return 物流公司ID
     */
    public String getLogisticsCompanyId() {
        return logisticsCompanyId;
    }

    /**
     *     物流公司ID     *
     *        
     *
     */
    public void setLogisticsCompanyId(String logisticsCompanyId) {
        this.logisticsCompanyId = logisticsCompanyId;
    }

    private String logisticsCompanyName;

    /**
     * @return 物流公司编码
     */
    public String getLogisticsCompanyName() {
        return logisticsCompanyName;
    }

    /**
     *     物流公司编码     *
     *        
     *
     */
    public void setLogisticsCompanyName(String logisticsCompanyName) {
        this.logisticsCompanyName = logisticsCompanyName;
    }

    private String remarks;

    /**
     * @return 备注
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     *     备注     *
     *        
     *
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    private String serviceFeature;

    /**
     * @return 
     */
    public String getServiceFeature() {
        return serviceFeature;
    }

    /**
     *          *
     *        
     *
     */
    public void setServiceFeature(String serviceFeature) {
        this.serviceFeature = serviceFeature;
    }

    private String gmtSystemSend;

    /**
     * @return 
     */
    public String getGmtSystemSend() {
        return gmtSystemSend;
    }

    /**
     *          *
     *        
     *
     */
    public void setGmtSystemSend(String gmtSystemSend) {
        this.gmtSystemSend = gmtSystemSend;
    }

    private AlibabaLogisticsOpenPlatformLogisticsSendGood[] sendGoods;

    /**
     * @return 商品信息
     */
    public AlibabaLogisticsOpenPlatformLogisticsSendGood[] getSendGoods() {
        return sendGoods;
    }

    /**
     *     商品信息     *
     *        
     *
     */
    public void setSendGoods(AlibabaLogisticsOpenPlatformLogisticsSendGood[] sendGoods) {
        this.sendGoods = sendGoods;
    }

    private AlibabaLogisticsOpenPlatformLogisticsReceiver receiver;

    /**
     * @return 收件人信息
     */
    public AlibabaLogisticsOpenPlatformLogisticsReceiver getReceiver() {
        return receiver;
    }

    /**
     *     收件人信息     *
     *        
     *
     */
    public void setReceiver(AlibabaLogisticsOpenPlatformLogisticsReceiver receiver) {
        this.receiver = receiver;
    }

    private AlibabaLogisticsOpenPlatformLogisticsSender sender;

    /**
     * @return 发件人信息
     */
    public AlibabaLogisticsOpenPlatformLogisticsSender getSender() {
        return sender;
    }

    /**
     *     发件人信息     *
     *        
     *
     */
    public void setSender(AlibabaLogisticsOpenPlatformLogisticsSender sender) {
        this.sender = sender;
    }

    private ComAlibabaOceanOpenplatformBizLogisticsCommonModelOpenPlatformLogisticsOrderSendGood[] logisticsOrderGoods;

    /**
     * @return 物流订单跟商品关系模型
     */
    public ComAlibabaOceanOpenplatformBizLogisticsCommonModelOpenPlatformLogisticsOrderSendGood[] getLogisticsOrderGoods() {
        return logisticsOrderGoods;
    }

    /**
     *     物流订单跟商品关系模型     *
     * 参数示例：<pre>{}</pre>     
     *
     */
    public void setLogisticsOrderGoods(ComAlibabaOceanOpenplatformBizLogisticsCommonModelOpenPlatformLogisticsOrderSendGood[] logisticsOrderGoods) {
        this.logisticsOrderGoods = logisticsOrderGoods;
    }

}
