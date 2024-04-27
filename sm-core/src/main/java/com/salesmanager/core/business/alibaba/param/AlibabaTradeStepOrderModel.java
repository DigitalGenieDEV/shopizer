package com.salesmanager.core.business.alibaba.param;

import java.math.BigDecimal;
import java.util.Date;

public class AlibabaTradeStepOrderModel {

    private Long stepOrderId;

    /**
     * @return 阶段id
     */
    public Long getStepOrderId() {
        return stepOrderId;
    }

    /**
     *     阶段id     *
     *        
     *
     */
    public void setStepOrderId(Long stepOrderId) {
        this.stepOrderId = stepOrderId;
    }

    private String stepOrderStatus;

    /**
     * @return waitactivate  未开始（待激活）
    waitsellerpush 等待卖家推进
    success 本阶段完成
    settlebill 分账
    cancel 本阶段终止
    inactiveandcancel 本阶段未开始便终止
    waitbuyerpay 等待买家付款
    waitsellersend 等待卖家发货
    waitbuyerreceive 等待买家确认收货
    waitselleract 等待卖家XX操作
    waitbuyerconfirmaction 等待买家确认XX操作
     */
    public String getStepOrderStatus() {
        return stepOrderStatus;
    }

    /**
     *     waitactivate  未开始（待激活）
    waitsellerpush 等待卖家推进
    success 本阶段完成
    settlebill 分账
    cancel 本阶段终止
    inactiveandcancel 本阶段未开始便终止
    waitbuyerpay 等待买家付款
    waitsellersend 等待卖家发货
    waitbuyerreceive 等待买家确认收货
    waitselleract 等待卖家XX操作
    waitbuyerconfirmaction 等待买家确认XX操作     *
     *        
     *
     */
    public void setStepOrderStatus(String stepOrderStatus) {
        this.stepOrderStatus = stepOrderStatus;
    }

    private Integer stepPayStatus;

    /**
     * @return 1 未冻结/未付款
    2 已冻结/已付款
    4 已退款
    6 已转交易
    8 交易未付款被关闭
     */
    public Integer getStepPayStatus() {
        return stepPayStatus;
    }

    /**
     *     1 未冻结/未付款
    2 已冻结/已付款
    4 已退款
    6 已转交易
    8 交易未付款被关闭     *
     *        
     *
     */
    public void setStepPayStatus(Integer stepPayStatus) {
        this.stepPayStatus = stepPayStatus;
    }

    private Integer stepNo;

    /**
     * @return 阶段序列：1、2、3...
     */
    public Integer getStepNo() {
        return stepNo;
    }

    /**
     *     阶段序列：1、2、3...     *
     *        
     *
     */
    public void setStepNo(Integer stepNo) {
        this.stepNo = stepNo;
    }

    private Boolean lastStep;

    /**
     * @return 是否最后一个阶段
     */
    public Boolean getLastStep() {
        return lastStep;
    }

    /**
     *     是否最后一个阶段     *
     *        
     *
     */
    public void setLastStep(Boolean lastStep) {
        this.lastStep = lastStep;
    }

    private Boolean hasDisbursed;

    /**
     * @return 是否已打款给卖家
     */
    public Boolean getHasDisbursed() {
        return hasDisbursed;
    }

    /**
     *     是否已打款给卖家     *
     *        
     *
     */
    public void setHasDisbursed(Boolean hasDisbursed) {
        this.hasDisbursed = hasDisbursed;
    }

    private BigDecimal payFee;

    /**
     * @return 创建时需要付款的金额，不含运费
     */
    public BigDecimal getPayFee() {
        return payFee;
    }

    /**
     *     创建时需要付款的金额，不含运费     *
     *        
     *
     */
    public void setPayFee(BigDecimal payFee) {
        this.payFee = payFee;
    }

    private BigDecimal actualPayFee;

    /**
     * @return 应付款（含运费）= 单价×数量-单品优惠-店铺优惠+运费+修改的金额（除运费外，均指分摊后的金额）
     */
    public BigDecimal getActualPayFee() {
        return actualPayFee;
    }

    /**
     *     应付款（含运费）= 单价×数量-单品优惠-店铺优惠+运费+修改的金额（除运费外，均指分摊后的金额）     *
     *        
     *
     */
    public void setActualPayFee(BigDecimal actualPayFee) {
        this.actualPayFee = actualPayFee;
    }

    private BigDecimal discountFee;

    /**
     * @return 本阶段分摊的店铺优惠
     */
    public BigDecimal getDiscountFee() {
        return discountFee;
    }

    /**
     *     本阶段分摊的店铺优惠     *
     *        
     *
     */
    public void setDiscountFee(BigDecimal discountFee) {
        this.discountFee = discountFee;
    }

    private BigDecimal itemDiscountFee;

    /**
     * @return 本阶段分摊的单品优惠
     */
    public BigDecimal getItemDiscountFee() {
        return itemDiscountFee;
    }

    /**
     *     本阶段分摊的单品优惠     *
     *        
     *
     */
    public void setItemDiscountFee(BigDecimal itemDiscountFee) {
        this.itemDiscountFee = itemDiscountFee;
    }

    private BigDecimal price;

    /**
     * @return 本阶段分摊的单价
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     *     本阶段分摊的单价     *
     *        
     *
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    private Long amount;

    /**
     * @return 购买数量
     */
    public Long getAmount() {
        return amount;
    }

    /**
     *     购买数量     *
     *        
     *
     */
    public void setAmount(Long amount) {
        this.amount = amount;
    }

    private BigDecimal postFee;

    /**
     * @return 运费
     */
    public BigDecimal getPostFee() {
        return postFee;
    }

    /**
     *     运费     *
     *        
     *
     */
    public void setPostFee(BigDecimal postFee) {
        this.postFee = postFee;
    }

    private BigDecimal adjustFee;

    /**
     * @return 修改价格修改的金额
     */
    public BigDecimal getAdjustFee() {
        return adjustFee;
    }

    /**
     *     修改价格修改的金额     *
     *        
     *
     */
    public void setAdjustFee(BigDecimal adjustFee) {
        this.adjustFee = adjustFee;
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

    private Date enterTime;

    /**
     * @return 开始时间
     */
    public Date getEnterTime() {
        return enterTime;
    }

    /**
     *     开始时间     *
     *        
     *
     */
    public void setEnterTime(Date enterTime) {
        this.enterTime = enterTime;
    }

    private Date payTime;

    /**
     * @return 付款时间
     */
    public Date getPayTime() {
        return payTime;
    }

    /**
     *     付款时间     *
     *        
     *
     */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    private Date sellerActionTime;

    /**
     * @return 卖家操作时间
     */
    public Date getSellerActionTime() {
        return sellerActionTime;
    }

    /**
     *     卖家操作时间     *
     *        
     *
     */
    public void setSellerActionTime(Date sellerActionTime) {
        this.sellerActionTime = sellerActionTime;
    }

    private Date endTime;

    /**
     * @return 本阶段结束时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     *     本阶段结束时间     *
     *        
     *
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    private String messagePath;

    /**
     * @return 卖家操作留言路径
     */
    public String getMessagePath() {
        return messagePath;
    }

    /**
     *     卖家操作留言路径     *
     *        
     *
     */
    public void setMessagePath(String messagePath) {
        this.messagePath = messagePath;
    }

    private String picturePath;

    /**
     * @return 卖家上传图片凭据路径
     */
    public String getPicturePath() {
        return picturePath;
    }

    /**
     *     卖家上传图片凭据路径     *
     *        
     *
     */
    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    private String message;

    /**
     * @return 卖家操作留言
     */
    public String getMessage() {
        return message;
    }

    /**
     *     卖家操作留言     *
     *        
     *
     */
    public void setMessage(String message) {
        this.message = message;
    }

    private Long templateId;

    /**
     * @return 使用的模板id
     */
    public Long getTemplateId() {
        return templateId;
    }

    /**
     *     使用的模板id     *
     *        
     *
     */
    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    private String stepName;

    /**
     * @return 当前步骤的名称
     */
    public String getStepName() {
        return stepName;
    }

    /**
     *     当前步骤的名称     *
     *        
     *
     */
    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    private String sellerActionName;

    /**
     * @return 卖家操作名称
     */
    public String getSellerActionName() {
        return sellerActionName;
    }

    /**
     *     卖家操作名称     *
     *        
     *
     */
    public void setSellerActionName(String sellerActionName) {
        this.sellerActionName = sellerActionName;
    }

    private Long buyerPayTimeout;

    /**
     * @return 买家不付款的超时时间(秒)
     */
    public Long getBuyerPayTimeout() {
        return buyerPayTimeout;
    }

    /**
     *     买家不付款的超时时间(秒)     *
     *        
     *
     */
    public void setBuyerPayTimeout(Long buyerPayTimeout) {
        this.buyerPayTimeout = buyerPayTimeout;
    }

    private Long buyerConfirmTimeout;

    /**
     * @return 买家不确认的超时时间
     */
    public Long getBuyerConfirmTimeout() {
        return buyerConfirmTimeout;
    }

    /**
     *     买家不确认的超时时间     *
     *        
     *
     */
    public void setBuyerConfirmTimeout(Long buyerConfirmTimeout) {
        this.buyerConfirmTimeout = buyerConfirmTimeout;
    }

    private Boolean needLogistics;

    /**
     * @return 是否需要物流
     */
    public Boolean getNeedLogistics() {
        return needLogistics;
    }

    /**
     *     是否需要物流     *
     *        
     *
     */
    public void setNeedLogistics(Boolean needLogistics) {
        this.needLogistics = needLogistics;
    }

    private Boolean needSellerAction;

    /**
     * @return 是否需要卖家操作和买家确认
     */
    public Boolean getNeedSellerAction() {
        return needSellerAction;
    }

    /**
     *     是否需要卖家操作和买家确认     *
     *        
     *
     */
    public void setNeedSellerAction(Boolean needSellerAction) {
        this.needSellerAction = needSellerAction;
    }

    private Boolean transferAfterConfirm;

    /**
     * @return 阶段结束是否打款
     */
    public Boolean getTransferAfterConfirm() {
        return transferAfterConfirm;
    }

    /**
     *     阶段结束是否打款     *
     *        
     *
     */
    public void setTransferAfterConfirm(Boolean transferAfterConfirm) {
        this.transferAfterConfirm = transferAfterConfirm;
    }

    private Boolean needSellerCallNext;

    /**
     * @return 是否需要卖家推进
     */
    public Boolean getNeedSellerCallNext() {
        return needSellerCallNext;
    }

    /**
     *     是否需要卖家推进     *
     *        
     *
     */
    public void setNeedSellerCallNext(Boolean needSellerCallNext) {
        this.needSellerCallNext = needSellerCallNext;
    }

    private Boolean instantPay;

    /**
     * @return 是否允许即时到帐
     */
    public Boolean getInstantPay() {
        return instantPay;
    }

    /**
     *     是否允许即时到帐     *
     *        
     *
     */
    public void setInstantPay(Boolean instantPay) {
        this.instantPay = instantPay;
    }

}
