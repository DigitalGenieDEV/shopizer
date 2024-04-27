package com.salesmanager.core.business.alibaba.param;

public class AlibabaTradeOrderRateInfo {

    private Integer buyerRateStatus;

    /**
     * @return 买家评价状态(4:已评论,5:未评论,6;不需要评论)
     */
    public Integer getBuyerRateStatus() {
        return buyerRateStatus;
    }

    /**
     *     买家评价状态(4:已评论,5:未评论,6;不需要评论)     *
     *        
     *
     */
    public void setBuyerRateStatus(Integer buyerRateStatus) {
        this.buyerRateStatus = buyerRateStatus;
    }

    private Integer sellerRateStatus;

    /**
     * @return 卖家评价状态(4:已评论,5:未评论,6;不需要评论)
     */
    public Integer getSellerRateStatus() {
        return sellerRateStatus;
    }

    /**
     *     卖家评价状态(4:已评论,5:未评论,6;不需要评论)     *
     *        
     *
     */
    public void setSellerRateStatus(Integer sellerRateStatus) {
        this.sellerRateStatus = sellerRateStatus;
    }

    private AlibabaOrderRateDetail[] buyerRateList;

    /**
     * @return 卖家給买家的评价
     */
    public AlibabaOrderRateDetail[] getBuyerRateList() {
        return buyerRateList;
    }

    /**
     *     卖家給买家的评价     *
     *        
     *
     */
    public void setBuyerRateList(AlibabaOrderRateDetail[] buyerRateList) {
        this.buyerRateList = buyerRateList;
    }

    private AlibabaOrderRateDetail[] sellerRateList;

    /**
     * @return 买家給卖家的评价
     */
    public AlibabaOrderRateDetail[] getSellerRateList() {
        return sellerRateList;
    }

    /**
     *     买家給卖家的评价     *
     *        
     *
     */
    public void setSellerRateList(AlibabaOrderRateDetail[] sellerRateList) {
        this.sellerRateList = sellerRateList;
    }

}
