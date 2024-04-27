package com.salesmanager.core.business.alibaba.param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class AlibabaTradeRefundOpOrderRefundModel {

    private Boolean aftersaleAgreeTimeout;

    /**
     * @return 售后超时标记
     */
    public Boolean getAftersaleAgreeTimeout() {
        return aftersaleAgreeTimeout;
    }

    /**
     *     售后超时标记     *
     *        
     *    
     */
    public void setAftersaleAgreeTimeout(Boolean aftersaleAgreeTimeout) {
        this.aftersaleAgreeTimeout = aftersaleAgreeTimeout;
    }

    private Boolean aftersaleAutoDisburse;

    /**
     * @return 售后自动打款
     */
    public Boolean getAftersaleAutoDisburse() {
        return aftersaleAutoDisburse;
    }

    /**
     *     售后自动打款     *
     *        
     *    
     */
    public void setAftersaleAutoDisburse(Boolean aftersaleAutoDisburse) {
        this.aftersaleAutoDisburse = aftersaleAutoDisburse;
    }

    private String alipayPaymentId;

    /**
     * @return 支付宝交易号
     */
    public String getAlipayPaymentId() {
        return alipayPaymentId;
    }

    /**
     *     支付宝交易号     *
     *        
     *    
     */
    public void setAlipayPaymentId(String alipayPaymentId) {
        this.alipayPaymentId = alipayPaymentId;
    }

    private Long applyCarriage;

    /**
     * @return 运费的申请退款金额，单位：分
     */
    public Long getApplyCarriage() {
        return applyCarriage;
    }

    /**
     *     运费的申请退款金额，单位：分     *
     *        
     *    
     */
    public void setApplyCarriage(Long applyCarriage) {
        this.applyCarriage = applyCarriage;
    }

    private Long applyExpect;

    /**
     * @return 买家原始输入的退款金额(可以为空)
     */
    public Long getApplyExpect() {
        return applyExpect;
    }

    /**
     *     买家原始输入的退款金额(可以为空)     *
     *        
     *    
     */
    public void setApplyExpect(Long applyExpect) {
        this.applyExpect = applyExpect;
    }

    private Long applyPayment;

    /**
     * @return 买家申请退款金额，单位：分
     */
    public Long getApplyPayment() {
        return applyPayment;
    }

    /**
     *     买家申请退款金额，单位：分     *
     *        
     *    
     */
    public void setApplyPayment(Long applyPayment) {
        this.applyPayment = applyPayment;
    }

    private String applyReason;

    /**
     * @return 申请原因
     */
    public String getApplyReason() {
        return applyReason;
    }

    /**
     *     申请原因     *
     *        
     *    
     */
    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }

    private Integer applyReasonId;

    /**
     * @return 申请原因ID
     */
    public Integer getApplyReasonId() {
        return applyReasonId;
    }

    /**
     *     申请原因ID     *
     *        
     *    
     */
    public void setApplyReasonId(Integer applyReasonId) {
        this.applyReasonId = applyReasonId;
    }

    private String applySubReason;

    /**
     * @return 二级退款原因
     */
    public String getApplySubReason() {
        return applySubReason;
    }

    /**
     *     二级退款原因     *
     *        
     *    
     */
    public void setApplySubReason(String applySubReason) {
        this.applySubReason = applySubReason;
    }

    private Integer applySubReasonId;

    /**
     * @return 二级退款原因Id
     */
    public Integer getApplySubReasonId() {
        return applySubReasonId;
    }

    /**
     *     二级退款原因Id     *
     *        
     *    
     */
    public void setApplySubReasonId(Integer applySubReasonId) {
        this.applySubReasonId = applySubReasonId;
    }

    private String asynErrCode;

    /**
     * @return 
     */
    public String getAsynErrCode() {
        return asynErrCode;
    }

    /**
     *          *
     *        
     *    
     */
    public void setAsynErrCode(String asynErrCode) {
        this.asynErrCode = asynErrCode;
    }

    private String asynSubErrCode;

    /**
     * @return 
     */
    public String getAsynSubErrCode() {
        return asynSubErrCode;
    }

    /**
     *          *
     *        
     *    
     */
    public void setAsynSubErrCode(String asynSubErrCode) {
        this.asynSubErrCode = asynSubErrCode;
    }

    private String buyerAlipayId;

    /**
     * @return 买家支付宝ID
     */
    public String getBuyerAlipayId() {
        return buyerAlipayId;
    }

    /**
     *     买家支付宝ID     *
     *        
     *    
     */
    public void setBuyerAlipayId(String buyerAlipayId) {
        this.buyerAlipayId = buyerAlipayId;
    }

    private String buyerLogisticsName;

    /**
     * @return 买家退货物流公司名
     */
    public String getBuyerLogisticsName() {
        return buyerLogisticsName;
    }

    /**
     *     买家退货物流公司名     *
     *        
     *    
     */
    public void setBuyerLogisticsName(String buyerLogisticsName) {
        this.buyerLogisticsName = buyerLogisticsName;
    }

    private String buyerMemberId;

    /**
     * @return 买家会员ID
     */
    public String getBuyerMemberId() {
        return buyerMemberId;
    }

    /**
     *     买家会员ID     *
     *        
     *    
     */
    public void setBuyerMemberId(String buyerMemberId) {
        this.buyerMemberId = buyerMemberId;
    }

    private Boolean buyerSendGoods;

    /**
     * @return 买家是否已经发货（如果有退货的流程）
     */
    public Boolean getBuyerSendGoods() {
        return buyerSendGoods;
    }

    /**
     *     买家是否已经发货（如果有退货的流程）     *
     *        
     *    
     */
    public void setBuyerSendGoods(Boolean buyerSendGoods) {
        this.buyerSendGoods = buyerSendGoods;
    }

    private Long buyerUserId;

    /**
     * @return 买家阿里帐号ID(包括淘宝帐号Id)
     */
    public Long getBuyerUserId() {
        return buyerUserId;
    }

    /**
     *     买家阿里帐号ID(包括淘宝帐号Id)     *
     *        
     *    
     */
    public void setBuyerUserId(Long buyerUserId) {
        this.buyerUserId = buyerUserId;
    }

    private Long canRefundPayment;

    /**
     * @return 最大能够退款金额，单位：分
     */
    public Long getCanRefundPayment() {
        return canRefundPayment;
    }

    /**
     *     最大能够退款金额，单位：分     *
     *        
     *    
     */
    public void setCanRefundPayment(Long canRefundPayment) {
        this.canRefundPayment = canRefundPayment;
    }

    private Boolean crmModifyRefund;

    /**
     * @return 是否小二修改过退款单
     */
    public Boolean getCrmModifyRefund() {
        return crmModifyRefund;
    }

    /**
     *     是否小二修改过退款单     *
     *        
     *    
     */
    public void setCrmModifyRefund(Boolean crmModifyRefund) {
        this.crmModifyRefund = crmModifyRefund;
    }

    private String disburseChannel;

    /**
     * @return 极速到账打款渠道
     */
    public String getDisburseChannel() {
        return disburseChannel;
    }

    /**
     *     极速到账打款渠道     *
     *        
     *    
     */
    public void setDisburseChannel(String disburseChannel) {
        this.disburseChannel = disburseChannel;
    }

    private Integer disputeRequest;

    /**
     * @return 售后退款要求
     */
    public Integer getDisputeRequest() {
        return disputeRequest;
    }

    /**
     *     售后退款要求     *
     *        
     *    
     */
    public void setDisputeRequest(Integer disputeRequest) {
        this.disputeRequest = disputeRequest;
    }

    private Integer disputeType;

    /**
     * @return 纠纷类型：售中退款 售后退款，默认为售中退款
     */
    public Integer getDisputeType() {
        return disputeType;
    }

    /**
     *     纠纷类型：售中退款 售后退款，默认为售中退款     *
     *        
     *    
     */
    public void setDisputeType(Integer disputeType) {
        this.disputeType = disputeType;
    }

    private Map extInfo;

    /**
     * @return 扩展信息
     */
    public Map getExtInfo() {
        return extInfo;
    }

    /**
     *     扩展信息     *
     *        
     *    
     */
    public void setExtInfo(Map extInfo) {
        this.extInfo = extInfo;
    }

    private String freightBill;

    /**
     * @return 运单号
     */
    public String getFreightBill() {
        return freightBill;
    }

    /**
     *     运单号     *
     *        
     *    
     */
    public void setFreightBill(String freightBill) {
        this.freightBill = freightBill;
    }

    private Long frozenFund;

    /**
     * @return 实际冻结账户金额,单位：分
     */
    public Long getFrozenFund() {
        return frozenFund;
    }

    /**
     *     实际冻结账户金额,单位：分     *
     *        
     *    
     */
    public void setFrozenFund(Long frozenFund) {
        this.frozenFund = frozenFund;
    }

    private Date gmtApply;

    /**
     * @return 申请退款时间
     */
    public Date getGmtApply() {
        return gmtApply;
    }

    /**
     *     申请退款时间     *
     *        
     *    
     */
    public void setGmtApply(Date gmtApply) {
        this.gmtApply = gmtApply;
    }

    private Date gmtCompleted;

    /**
     * @return 完成时间
     */
    public Date getGmtCompleted() {
        return gmtCompleted;
    }

    /**
     *     完成时间     *
     *        
     *    
     */
    public void setGmtCompleted(Date gmtCompleted) {
        this.gmtCompleted = gmtCompleted;
    }

    private Date gmtCreate;

    /**
     * @return 创建时间
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     *     创建时间     *
     *        
     *    
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    private Date gmtFreezed;

    /**
     * @return 该退款单超时冻结开始时间
     */
    public Date getGmtFreezed() {
        return gmtFreezed;
    }

    /**
     *     该退款单超时冻结开始时间     *
     *        
     *    
     */
    public void setGmtFreezed(Date gmtFreezed) {
        this.gmtFreezed = gmtFreezed;
    }

    private Date gmtModified;

    /**
     * @return 修改时间
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     *     修改时间     *
     *        
     *    
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    private Date gmtTimeOut;

    /**
     * @return 该退款单超时完成的时间期限
     */
    public Date getGmtTimeOut() {
        return gmtTimeOut;
    }

    /**
     *     该退款单超时完成的时间期限     *
     *        
     *    
     */
    public void setGmtTimeOut(Date gmtTimeOut) {
        this.gmtTimeOut = gmtTimeOut;
    }

    private Boolean goodsReceived;

    /**
     * @return 买家是否已收到货
     */
    public Boolean getGoodsReceived() {
        return goodsReceived;
    }

    /**
     *     买家是否已收到货     *
     *        
     *    
     */
    public void setGoodsReceived(Boolean goodsReceived) {
        this.goodsReceived = goodsReceived;
    }

    private Integer goodsStatus;

    /**
     * @return 1：买家未收到货
    2：买家已收到货
    3：买家已退货
     */
    public Integer getGoodsStatus() {
        return goodsStatus;
    }

    /**
     *     1：买家未收到货
    2：买家已收到货
    3：买家已退货     *
     *        
     *    
     */
    public void setGoodsStatus(Integer goodsStatus) {
        this.goodsStatus = goodsStatus;
    }

    private Long id;

    /**
     * @return 退款单编号
     */
    public Long getId() {
        return id;
    }

    /**
     *     退款单编号     *
     *        
     *    
     */
    public void setId(Long id) {
        this.id = id;
    }

    private String instantRefundType;

    /**
     * @return 极速到账退款类型
     */
    public String getInstantRefundType() {
        return instantRefundType;
    }

    /**
     *     极速到账退款类型     *
     *        
     *    
     */
    public void setInstantRefundType(String instantRefundType) {
        this.instantRefundType = instantRefundType;
    }

    private Boolean insufficientAccount;

    /**
     * @return 交易4.0退款余额不足
     */
    public Boolean getInsufficientAccount() {
        return insufficientAccount;
    }

    /**
     *     交易4.0退款余额不足     *
     *        
     *    
     */
    public void setInsufficientAccount(Boolean insufficientAccount) {
        this.insufficientAccount = insufficientAccount;
    }

    private Boolean insufficientBail;

    /**
     * @return 极速到账退款保证金不足
     */
    public Boolean getInsufficientBail() {
        return insufficientBail;
    }

    /**
     *     极速到账退款保证金不足     *
     *        
     *    
     */
    public void setInsufficientBail(Boolean insufficientBail) {
        this.insufficientBail = insufficientBail;
    }

    private Boolean newRefundReturn;

    /**
     * @return 是否新流程创建的退款退货
     */
    public Boolean getNewRefundReturn() {
        return newRefundReturn;
    }

    /**
     *     是否新流程创建的退款退货     *
     *        
     *    
     */
    public void setNewRefundReturn(Boolean newRefundReturn) {
        this.newRefundReturn = newRefundReturn;
    }

    private Boolean onlyRefund;

    /**
     * @return 是否仅退款
     */
    public Boolean getOnlyRefund() {
        return onlyRefund;
    }

    /**
     *     是否仅退款     *
     *        
     *    
     */
    public void setOnlyRefund(Boolean onlyRefund) {
        this.onlyRefund = onlyRefund;
    }

    private Map orderEntryCountMap;

    /**
     * @return 子订单退货数量
     */
    public Map getOrderEntryCountMap() {
        return orderEntryCountMap;
    }

    /**
     *     子订单退货数量     *
     *        
     *    
     */
    public void setOrderEntryCountMap(Map orderEntryCountMap) {
        this.orderEntryCountMap = orderEntryCountMap;
    }

    private List orderEntryIdList;

    /**
     * @return 退款单包含的订单明细，时间逆序排列
     */
    public List getOrderEntryIdList() {
        return orderEntryIdList;
    }

    /**
     *     退款单包含的订单明细，时间逆序排列     *
     *        
     *    
     */
    public void setOrderEntryIdList(List orderEntryIdList) {
        this.orderEntryIdList = orderEntryIdList;
    }

    private Long orderId;

    /**
     * @return 退款单对应的订单编号
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     *     退款单对应的订单编号     *
     *        
     *    
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    private Long prepaidBalance;

    /**
     * @return 极速退款垫资金额,该值不为空时,只代表该退款单可以走垫资流程,但不代表一定垫资成功
     */
    public Long getPrepaidBalance() {
        return prepaidBalance;
    }

    /**
     *     极速退款垫资金额,该值不为空时,只代表该退款单可以走垫资流程,但不代表一定垫资成功     *
     *        
     *    
     */
    public void setPrepaidBalance(Long prepaidBalance) {
        this.prepaidBalance = prepaidBalance;
    }

    private String productName;

    /**
     * @return 产品名称(退款单关联订单明细的货品名称)
     */
    public String getProductName() {
        return productName;
    }

    /**
     *     产品名称(退款单关联订单明细的货品名称)     *
     *        
     *    
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    private Long refundCarriage;

    /**
     * @return 运费的实际退款金额，单位：分
     */
    public Long getRefundCarriage() {
        return refundCarriage;
    }

    /**
     *     运费的实际退款金额，单位：分     *
     *        
     *    
     */
    public void setRefundCarriage(Long refundCarriage) {
        this.refundCarriage = refundCarriage;
    }

    private Boolean refundGoods;

    /**
     * @return 是否要求退货
     */
    public Boolean getRefundGoods() {
        return refundGoods;
    }

    /**
     *     是否要求退货     *
     *        
     *    
     */
    public void setRefundGoods(Boolean refundGoods) {
        this.refundGoods = refundGoods;
    }

    private String refundId;

    /**
     * @return 退款单逻辑主键
     */
    public String getRefundId() {
        return refundId;
    }

    /**
     *     退款单逻辑主键     *
     *        
     *    
     */
    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    private Long refundPayment;

    /**
     * @return 实际退款金额，单位：分
     */
    public Long getRefundPayment() {
        return refundPayment;
    }

    /**
     *     实际退款金额，单位：分     *
     *        
     *    
     */
    public void setRefundPayment(Long refundPayment) {
        this.refundPayment = refundPayment;
    }

    private String rejectReason;

    /**
     * @return 卖家拒绝原因
     */
    public String getRejectReason() {
        return rejectReason;
    }

    /**
     *     卖家拒绝原因     *
     *        
     *    
     */
    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    private Integer rejectReasonId;

    /**
     * @return 卖家拒绝原因Id
     */
    public Integer getRejectReasonId() {
        return rejectReasonId;
    }

    /**
     *     卖家拒绝原因Id     *
     *        
     *    
     */
    public void setRejectReasonId(Integer rejectReasonId) {
        this.rejectReasonId = rejectReasonId;
    }

    private Integer rejectTimes;

    /**
     * @return 退款单被拒绝的次数
     */
    public Integer getRejectTimes() {
        return rejectTimes;
    }

    /**
     *     退款单被拒绝的次数     *
     *        
     *    
     */
    public void setRejectTimes(Integer rejectTimes) {
        this.rejectTimes = rejectTimes;
    }

    private String sellerAlipayId;

    /**
     * @return 卖家支付宝ID
     */
    public String getSellerAlipayId() {
        return sellerAlipayId;
    }

    /**
     *     卖家支付宝ID     *
     *        
     *    
     */
    public void setSellerAlipayId(String sellerAlipayId) {
        this.sellerAlipayId = sellerAlipayId;
    }

    private Boolean sellerDelayDisburse;

    /**
     * @return 是否卖家延迟打款，即安全退款
     */
    public Boolean getSellerDelayDisburse() {
        return sellerDelayDisburse;
    }

    /**
     *     是否卖家延迟打款，即安全退款     *
     *        
     *    
     */
    public void setSellerDelayDisburse(Boolean sellerDelayDisburse) {
        this.sellerDelayDisburse = sellerDelayDisburse;
    }

    private String sellerMemberId;

    /**
     * @return 卖家会员ID
     */
    public String getSellerMemberId() {
        return sellerMemberId;
    }

    /**
     *     卖家会员ID     *
     *        
     *    
     */
    public void setSellerMemberId(String sellerMemberId) {
        this.sellerMemberId = sellerMemberId;
    }

    private String sellerMobile;

    /**
     * @return 收货人手机
     */
    public String getSellerMobile() {
        return sellerMobile;
    }

    /**
     *     收货人手机     *
     *        
     *    
     */
    public void setSellerMobile(String sellerMobile) {
        this.sellerMobile = sellerMobile;
    }

    private String sellerRealName;

    /**
     * @return 收货人姓名
     */
    public String getSellerRealName() {
        return sellerRealName;
    }

    /**
     *     收货人姓名     *
     *        
     *    
     */
    public void setSellerRealName(String sellerRealName) {
        this.sellerRealName = sellerRealName;
    }

    private String sellerReceiveAddress;

    /**
     * @return 买家退货时卖家收货地址
     */
    public String getSellerReceiveAddress() {
        return sellerReceiveAddress;
    }

    /**
     *     买家退货时卖家收货地址     *
     *        
     *    
     */
    public void setSellerReceiveAddress(String sellerReceiveAddress) {
        this.sellerReceiveAddress = sellerReceiveAddress;
    }

    private String sellerTel;

    /**
     * @return 收货人电话
     */
    public String getSellerTel() {
        return sellerTel;
    }

    /**
     *     收货人电话     *
     *        
     *    
     */
    public void setSellerTel(String sellerTel) {
        this.sellerTel = sellerTel;
    }

    private Long sellerUserId;

    /**
     * @return 卖家阿里帐号ID(包括淘宝帐号Id)
     */
    public Long getSellerUserId() {
        return sellerUserId;
    }

    /**
     *     卖家阿里帐号ID(包括淘宝帐号Id)     *
     *        
     *    
     */
    public void setSellerUserId(Long sellerUserId) {
        this.sellerUserId = sellerUserId;
    }

    private String status;

    /**
     * @return 退款状态
     */
    public String getStatus() {
        return status;
    }

    /**
     *     退款状态     *
     *        
     *    
     */
    public void setStatus(String status) {
        this.status = status;
    }

    private Boolean supportNewSteppay;

    /**
     * @return 是否支持交易4.0
     */
    public Boolean getSupportNewSteppay() {
        return supportNewSteppay;
    }

    /**
     *     是否支持交易4.0     *
     *        
     *    
     */
    public void setSupportNewSteppay(Boolean supportNewSteppay) {
        this.supportNewSteppay = supportNewSteppay;
    }

    private String taskStatus;

    /**
     * @return 工单子状态，没有流到CRM创建工单时为空
     */
    public String getTaskStatus() {
        return taskStatus;
    }

    /**
     *     工单子状态，没有流到CRM创建工单时为空     *
     *        
     *    
     */
    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    private Boolean timeOutFreeze;

    /**
     * @return 是否超时系统冻结，true代表冻结，false代表不冻结
     */
    public Boolean getTimeOutFreeze() {
        return timeOutFreeze;
    }

    /**
     *     是否超时系统冻结，true代表冻结，false代表不冻结     *
     *        
     *    
     */
    public void setTimeOutFreeze(Boolean timeOutFreeze) {
        this.timeOutFreeze = timeOutFreeze;
    }

    private String timeOutOperateType;

    /**
     * @return 超时后执行的动作
     */
    public String getTimeOutOperateType() {
        return timeOutOperateType;
    }

    /**
     *     超时后执行的动作     *
     *        
     *    
     */
    public void setTimeOutOperateType(String timeOutOperateType) {
        this.timeOutOperateType = timeOutOperateType;
    }

    private String tradeTypeStr;

    /**
     * @return 交易类型，用来替换枚举类型的tradeType
     */
    public String getTradeTypeStr() {
        return tradeTypeStr;
    }

    /**
     *     交易类型，用来替换枚举类型的tradeType     *
     *        
     *    
     */
    public void setTradeTypeStr(String tradeTypeStr) {
        this.tradeTypeStr = tradeTypeStr;
    }

    private AlibabaTradeRefundOpOrderRefundOperationModel[] refundOperationList;

    /**
     * @return 操作记录列表
     */
    public AlibabaTradeRefundOpOrderRefundOperationModel[] getRefundOperationList() {
        return refundOperationList;
    }

    /**
     *     操作记录列表     *
     *        
     *    
     */
    public void setRefundOperationList(AlibabaTradeRefundOpOrderRefundOperationModel[] refundOperationList) {
        this.refundOperationList = refundOperationList;
    }

    private String buyerLoginId;

    /**
     * @return 买家LoginId
     */
    public String getBuyerLoginId() {
        return buyerLoginId;
    }

    /**
     *     买家LoginId     *
     *        
     *    
     */
    public void setBuyerLoginId(String buyerLoginId) {
        this.buyerLoginId = buyerLoginId;
    }

    private String sellerLoginId;

    /**
     * @return 卖家LoginId
     */
    public String getSellerLoginId() {
        return sellerLoginId;
    }

    /**
     *     卖家LoginId     *
     *        
     *    
     */
    public void setSellerLoginId(String sellerLoginId) {
        this.sellerLoginId = sellerLoginId;
    }

}
