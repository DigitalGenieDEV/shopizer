package com.salesmanager.core.business.alibaba.param;

import com.salesmanager.core.business.alibaba.rawsdk.client.APIId;
import com.salesmanager.core.business.alibaba.rawsdk.common.AbstractAPIRequest;


public class AlibabaTradeCreateRefundParam extends AbstractAPIRequest<AlibabaTradeCreateRefundResult> {

    public AlibabaTradeCreateRefundParam() {
        super();
        oceanApiId = new APIId("com.alibaba.trade", "alibaba.trade.createRefund", 1);
    }

    private Long orderId;

    /**
     * @return 主订单
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     *     主订单     *
     * 参数示例：<pre>  </pre>     
     *    
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    private long[] orderEntryIds;

    /**
     * @return 子订单
     */
    public long[] getOrderEntryIds() {
        return orderEntryIds;
    }

    /**
     *     子订单     *
     *    
     *    
     */
    public void setOrderEntryIds(long[] orderEntryIds) {
        this.orderEntryIds = orderEntryIds;
    }

    private String disputeRequest;

    /**
     * @return 退款/退款退货。只有已收到货，才可以选择退款退货。
     */
    public String getDisputeRequest() {
        return disputeRequest;
    }

    /**
     *     退款/退款退货。只有已收到货，才可以选择退款退货。     *
     * 参数示例：<pre>退款:"refund"; 退款退货:"returnRefund"</pre>     
     *    
     */
    public void setDisputeRequest(String disputeRequest) {
        this.disputeRequest = disputeRequest;
    }

    private Long applyPayment;

    /**
     * @return 退款金额（单位：分）。不大于实际付款金额；等待卖家发货时，必须为商品的实际付款金额。
     */
    public Long getApplyPayment() {
        return applyPayment;
    }

    /**
     *     退款金额（单位：分）。不大于实际付款金额；等待卖家发货时，必须为商品的实际付款金额。     *
     *    
     *    
     */
    public void setApplyPayment(Long applyPayment) {
        this.applyPayment = applyPayment;
    }

    private Long applyCarriage;

    /**
     * @return 退运费金额（单位：分）。
     */
    public Long getApplyCarriage() {
        return applyCarriage;
    }

    /**
     *     退运费金额（单位：分）。     *
     *    
     *    
     */
    public void setApplyCarriage(Long applyCarriage) {
        this.applyCarriage = applyCarriage;
    }

    private Long applyReasonId;

    /**
     * @return 退款原因id（从API getRefundReasonList获取）	
     */
    public Long getApplyReasonId() {
        return applyReasonId;
    }

    /**
     *     退款原因id（从API getRefundReasonList获取）	     *
     *    
     *    
     */
    public void setApplyReasonId(Long applyReasonId) {
        this.applyReasonId = applyReasonId;
    }

    private String description;

    /**
     * @return 退款申请理由，2-150字
     */
    public String getDescription() {
        return description;
    }

    /**
     *     退款申请理由，2-150字     *
     *    
     *    
     */
    public void setDescription(String description) {
        this.description = description;
    }

    private String goodsStatus;

    /**
     * @return 货物状态	
     */
    public String getGoodsStatus() {
        return goodsStatus;
    }

    /**
     *     货物状态	     *
     * 参数示例：<pre> 售中等待卖家发货:"refundWaitSellerSend"; 售中等待买家收货:"refundWaitBuyerReceive"; 售中已收货（未确认完成交易）:"refundBuyerReceived" 售后未收货:"aftersaleBuyerNotReceived"; 售后已收到货:"aftersaleBuyerReceived"</pre>     
     *    
     */
    public void setGoodsStatus(String goodsStatus) {
        this.goodsStatus = goodsStatus;
    }

    private String[] vouchers;

    /**
     * @return 凭证图片URLs。1-5张，必须使用API uploadRefundVoucher返回的“图片域名/相对路径”
     */
    public String[] getVouchers() {
        return vouchers;
    }

    /**
     *     凭证图片URLs。1-5张，必须使用API uploadRefundVoucher返回的“图片域名/相对路径”     *
     * 参数示例：<pre> [https://cbu01.alicdn.com/img/ibank/2019/901/930/11848039109.jpg]</pre>     
     *    
     */
    public void setVouchers(String[] vouchers) {
        this.vouchers = vouchers;
    }

    private AlibabaOceanOpenplatformBizTradeCommonModelOrderEntryCountModel[] orderEntryCountList;

    /**
     * @return 子订单退款数量。仅在售中买家已收货（退款退货）时，可指定退货数量；默认，全部退货。
     */
    public AlibabaOceanOpenplatformBizTradeCommonModelOrderEntryCountModel[] getOrderEntryCountList() {
        return orderEntryCountList;
    }

    /**
     *     子订单退款数量。仅在售中买家已收货（退款退货）时，可指定退货数量；默认，全部退货。     *
     * 参数示例：<pre> [{"id":586683458996743215,"count":1}]</pre>     
     *    
     */
    public void setOrderEntryCountList(AlibabaOceanOpenplatformBizTradeCommonModelOrderEntryCountModel[] orderEntryCountList) {
        this.orderEntryCountList = orderEntryCountList;
    }

}
