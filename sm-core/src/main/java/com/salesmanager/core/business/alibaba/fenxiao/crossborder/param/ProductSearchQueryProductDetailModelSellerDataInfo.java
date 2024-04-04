package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class ProductSearchQueryProductDetailModelSellerDataInfo {

    private String tradeMedalLevel;

    /**
     * @return 卖家交易勋章
     */
    public String getTradeMedalLevel() {
        return tradeMedalLevel;
    }

    /**
     * 设置卖家交易勋章     *
     * 参数示例：<pre>5</pre>     
     * 此参数必填
     */
    public void setTradeMedalLevel(String tradeMedalLevel) {
        this.tradeMedalLevel = tradeMedalLevel;
    }

    private String compositeServiceScore;

    /**
     * @return 综合服务分
     */
    public String getCompositeServiceScore() {
        return compositeServiceScore;
    }

    /**
     * 设置综合服务分     *
     * 参数示例：<pre>3.5</pre>     
     * 此参数必填
     */
    public void setCompositeServiceScore(String compositeServiceScore) {
        this.compositeServiceScore = compositeServiceScore;
    }

    private String logisticsExperienceScore;

    /**
     * @return 物流体验分
     */
    public String getLogisticsExperienceScore() {
        return logisticsExperienceScore;
    }

    /**
     * 设置物流体验分     *
     * 参数示例：<pre>4.5</pre>     
     * 此参数必填
     */
    public void setLogisticsExperienceScore(String logisticsExperienceScore) {
        this.logisticsExperienceScore = logisticsExperienceScore;
    }

    private String disputeComplaintScore;

    /**
     * @return 纠纷解决分
     */
    public String getDisputeComplaintScore() {
        return disputeComplaintScore;
    }

    /**
     * 设置纠纷解决分     *
     * 参数示例：<pre>3.0</pre>     
     * 此参数必填
     */
    public void setDisputeComplaintScore(String disputeComplaintScore) {
        this.disputeComplaintScore = disputeComplaintScore;
    }

    private String offerExperienceScore;

    /**
     * @return 商品体验分
     */
    public String getOfferExperienceScore() {
        return offerExperienceScore;
    }

    /**
     * 设置商品体验分     *
     * 参数示例：<pre>4.0</pre>     
     * 此参数必填
     */
    public void setOfferExperienceScore(String offerExperienceScore) {
        this.offerExperienceScore = offerExperienceScore;
    }

    private String consultingExperienceScore;

    /**
     * @return 咨询体验分
     */
    public String getConsultingExperienceScore() {
        return consultingExperienceScore;
    }

    /**
     * 设置咨询体验分     *
     * 参数示例：<pre>5.0</pre>     
     * 此参数必填
     */
    public void setConsultingExperienceScore(String consultingExperienceScore) {
        this.consultingExperienceScore = consultingExperienceScore;
    }

}
