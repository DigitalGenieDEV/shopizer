package com.salesmanager.core.business.alibaba.param;

public class AlibabaCreateOrderPreviewResultModel {

    private Long discountFee;

    /**
     * @return 计算完货品金额后再次进行的减免金额. 单位: 分
     */
    public Long getDiscountFee() {
        return discountFee;
    }

    /**
     *     计算完货品金额后再次进行的减免金额. 单位: 分     *
     *        
     *    
     */
    public void setDiscountFee(Long discountFee) {
        this.discountFee = discountFee;
    }

    private String[] tradeModeNameList;

    /**
     * @return 当前交易可以支持的交易方式列表。某些场景的创建订单接口需要使用。
     */
    public String[] getTradeModeNameList() {
        return tradeModeNameList;
    }

    /**
     *     当前交易可以支持的交易方式列表。某些场景的创建订单接口需要使用。     *
     *        
     *    
     */
    public void setTradeModeNameList(String[] tradeModeNameList) {
        this.tradeModeNameList = tradeModeNameList;
    }

    private Boolean status;

    /**
     * @return 状态
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     *     状态     *
     *        
     *    
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    private Boolean taoSampleSinglePromotion;

    /**
     * @return 是否有淘货源单品优惠  false:有单品优惠   true：没有单品优惠
     */
    public Boolean getTaoSampleSinglePromotion() {
        return taoSampleSinglePromotion;
    }

    /**
     *     是否有淘货源单品优惠  false:有单品优惠   true：没有单品优惠     *
     *        
     *    
     */
    public void setTaoSampleSinglePromotion(Boolean taoSampleSinglePromotion) {
        this.taoSampleSinglePromotion = taoSampleSinglePromotion;
    }

    private Long sumPayment;

    /**
     * @return 订单总费用, 单位为分.
     */
    public Long getSumPayment() {
        return sumPayment;
    }

    /**
     *     订单总费用, 单位为分.     *
     *        
     *    
     */
    public void setSumPayment(Long sumPayment) {
        this.sumPayment = sumPayment;
    }

    private String message;

    /**
     * @return 返回信息
     */
    public String getMessage() {
        return message;
    }

    /**
     *     返回信息     *
     *        
     *    
     */
    public void setMessage(String message) {
        this.message = message;
    }

    private Long sumCarriage;

    /**
     * @return 总运费信息, 单位为分.
     */
    public Long getSumCarriage() {
        return sumCarriage;
    }

    /**
     *     总运费信息, 单位为分.     *
     *        
     *    
     */
    public void setSumCarriage(Long sumCarriage) {
        this.sumCarriage = sumCarriage;
    }

    private String resultCode;

    /**
     * @return 返回码
     */
    public String getResultCode() {
        return resultCode;
    }

    /**
     *     返回码     *
     *        
     *    
     */
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    private Long sumPaymentNoCarriage;

    /**
     * @return 不包含运费的货品总费用, 单位为分.
     */
    public Long getSumPaymentNoCarriage() {
        return sumPaymentNoCarriage;
    }

    /**
     *     不包含运费的货品总费用, 单位为分.     *
     *        
     *    
     */
    public void setSumPaymentNoCarriage(Long sumPaymentNoCarriage) {
        this.sumPaymentNoCarriage = sumPaymentNoCarriage;
    }

    private Long additionalFee;

    /**
     * @return 附加费,单位，分
     */
    public Long getAdditionalFee() {
        return additionalFee;
    }

    /**
     *     附加费,单位，分     *
     *        
     *    
     */
    public void setAdditionalFee(Long additionalFee) {
        this.additionalFee = additionalFee;
    }

    private String flowFlag;

    /**
     * @return 订单下单流程
     */
    public String getFlowFlag() {
        return flowFlag;
    }

    /**
     *     订单下单流程     *
     *        
     *    
     */
    public void setFlowFlag(String flowFlag) {
        this.flowFlag = flowFlag;
    }

    private AlibabaCreateOrderPreviewResultCargoModel[] cargoList;

    /**
     * @return 规格信息
     */
    public AlibabaCreateOrderPreviewResultCargoModel[] getCargoList() {
        return cargoList;
    }

    /**
     *     规格信息     *
     *        
     *    
     */
    public void setCargoList(AlibabaCreateOrderPreviewResultCargoModel[] cargoList) {
        this.cargoList = cargoList;
    }

    private AlibabaTradePromotionModel[] shopPromotionList;

    /**
     * @return 可用店铺级别优惠列表
     */
    public AlibabaTradePromotionModel[] getShopPromotionList() {
        return shopPromotionList;
    }

    /**
     *     可用店铺级别优惠列表     *
     *        
     *    
     */
    public void setShopPromotionList(AlibabaTradePromotionModel[] shopPromotionList) {
        this.shopPromotionList = shopPromotionList;
    }

    private TradeModelExtensionList[] tradeModelList;

    /**
     * @return 当前交易可以支持的交易方式列表。结果可以参照1688下单预览页面的交易方式。
     */
    public TradeModelExtensionList[] getTradeModelList() {
        return tradeModelList;
    }

    /**
     *     当前交易可以支持的交易方式列表。结果可以参照1688下单预览页面的交易方式。     *
     *        
     *    
     */
    public void setTradeModelList(TradeModelExtensionList[] tradeModelList) {
        this.tradeModelList = tradeModelList;
    }

    private ComAlibabaOceanOpenplatformBizTradeResultPayChannel[] payChannelList;

    /**
     * @return 当前交易支持的支付渠道，name：渠道名字，amountLimit：可用额度金额，单位为分 ，null表示没有额度概念
     */
    public ComAlibabaOceanOpenplatformBizTradeResultPayChannel[] getPayChannelList() {
        return payChannelList;
    }

    /**
     *     当前交易支持的支付渠道，name：渠道名字，amountLimit：可用额度金额，单位为分 ，null表示没有额度概念     *
     * 参数示例：<pre>[{"name":"alipay"}]</pre>     
     *    
     */
    public void setPayChannelList(ComAlibabaOceanOpenplatformBizTradeResultPayChannel[] payChannelList) {
        this.payChannelList = payChannelList;
    }

    private PayChaneelList[] payChannelInfos;

    /**
     * @return 当前交易支持的支付渠道信息
     */
    public PayChaneelList[] getPayChannelInfos() {
        return payChannelInfos;
    }

    /**
     *     当前交易支持的支付渠道信息     *
     * 参数示例：<pre>[]</pre>     
     *    
     */
    public void setPayChannelInfos(PayChaneelList[] payChannelInfos) {
        this.payChannelInfos = payChannelInfos;
    }

}
