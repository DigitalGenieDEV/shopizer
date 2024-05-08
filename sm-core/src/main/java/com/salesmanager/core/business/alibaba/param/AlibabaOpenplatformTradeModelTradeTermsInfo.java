package com.salesmanager.core.business.alibaba.param;

import java.math.BigDecimal;
import java.util.Date;

public class AlibabaOpenplatformTradeModelTradeTermsInfo {

    private String payStatus;

    /**
     * @return 支付状态。国际站：WAIT_PAY(未支付),PAYER_PAID(已完成支付),PART_SUCCESS(部分支付成功),PAY_SUCCESS(支付成功),CLOSED(风控关闭),CANCELLED(支付撤销),SUCCESS(成功),FAIL(失败)。
    1688:1(未付款);2(已付款);4(全额退款);6(卖家有收到钱，回款完成) ;7(未创建外部支付单);8 (付款前取消) ; 9(正在支付中);12(账期支付,待到账)
     */
    public String getPayStatus() {
        return payStatus;
    }

    /**
     *     支付状态。国际站：WAIT_PAY(未支付),PAYER_PAID(已完成支付),PART_SUCCESS(部分支付成功),PAY_SUCCESS(支付成功),CLOSED(风控关闭),CANCELLED(支付撤销),SUCCESS(成功),FAIL(失败)。
    1688:1(未付款);2(已付款);4(全额退款);6(卖家有收到钱，回款完成) ;7(未创建外部支付单);8 (付款前取消) ; 9(正在支付中);12(账期支付,待到账)     *
     *        
     *
     */
    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    private Date payTime;

    /**
     * @return 完成阶段支付时间
     */
    public Date getPayTime() {
        return payTime;
    }

    /**
     *     完成阶段支付时间     *
     *        
     *
     */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    private String payWay;

    /**
     * @return 支付方式。
    国际站：ECL(融资支付),CC(信用卡),TT(线下TT),ACH(echecking支付)。
    1688:1-支付宝,2-网商银行信任付,3-诚e赊,4-银行转账,5-赊销宝,6-电子承兑票据,7-账期支付,8-合并支付渠道,9-无打款,10-零售通赊购,13-支付平台,12-声明付款
     */
    public String getPayWay() {
        return payWay;
    }

    /**
     *     支付方式。
    国际站：ECL(融资支付),CC(信用卡),TT(线下TT),ACH(echecking支付)。
    1688:1-支付宝,2-网商银行信任付,3-诚e赊,4-银行转账,5-赊销宝,6-电子承兑票据,7-账期支付,8-合并支付渠道,9-无打款,10-零售通赊购,13-支付平台,12-声明付款     *
     *        
     *
     */
    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    private BigDecimal phasAmount;

    /**
     * @return 付款额
     */
    public BigDecimal getPhasAmount() {
        return phasAmount;
    }

    /**
     *     付款额     *
     *        
     *
     */
    public void setPhasAmount(BigDecimal phasAmount) {
        this.phasAmount = phasAmount;
    }

    private Long phase;

    /**
     * @return 阶段单id
     */
    public Long getPhase() {
        return phase;
    }

    /**
     *     阶段单id     *
     *        
     *
     */
    public void setPhase(Long phase) {
        this.phase = phase;
    }

    private String phaseCondition;

    /**
     * @return 阶段条件，1688无此内容
     */
    public String getPhaseCondition() {
        return phaseCondition;
    }

    /**
     *     阶段条件，1688无此内容     *
     *        
     *
     */
    public void setPhaseCondition(String phaseCondition) {
        this.phaseCondition = phaseCondition;
    }

    private String phaseDate;

    /**
     * @return 阶段时间，1688无此内容
     */
    public String getPhaseDate() {
        return phaseDate;
    }

    /**
     *     阶段时间，1688无此内容     *
     *        
     *
     */
    public void setPhaseDate(String phaseDate) {
        this.phaseDate = phaseDate;
    }

    private Boolean cardPay;

    /**
     * @return 是否银行卡支付
     */
    public Boolean getCardPay() {
        return cardPay;
    }

    /**
     *     是否银行卡支付     *
     *        
     *
     */
    public void setCardPay(Boolean cardPay) {
        this.cardPay = cardPay;
    }

    private Boolean expressPay;

    /**
     * @return 是否快捷支付
     */
    public Boolean getExpressPay() {
        return expressPay;
    }

    /**
     *     是否快捷支付     *
     *        
     *
     */
    public void setExpressPay(Boolean expressPay) {
        this.expressPay = expressPay;
    }

    private String payWayDesc;

    /**
     * @return 支付方式
     */
    public String getPayWayDesc() {
        return payWayDesc;
    }

    /**
     *     支付方式     *
     * 参数示例：<pre>支付宝</pre>     
     *
     */
    public void setPayWayDesc(String payWayDesc) {
        this.payWayDesc = payWayDesc;
    }

}