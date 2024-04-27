package com.salesmanager.core.business.alibaba.param;

public class AlibabaTradePromotionModel {

    private String promotionId;

    /**
     * @return 优惠券ID
     */
    public String getPromotionId() {
        return promotionId;
    }

    /**
     *     优惠券ID     *
     *        
     *
     */
    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

    private Boolean selected;

    /**
     * @return 是否默认选中
     */
    public Boolean getSelected() {
        return selected;
    }

    /**
     *     是否默认选中     *
     *        
     *
     */
    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    private String text;

    /**
     * @return 优惠券名称
     */
    public String getText() {
        return text;
    }

    /**
     *     优惠券名称     *
     *        
     *
     */
    public void setText(String text) {
        this.text = text;
    }

    private String desc;

    /**
     * @return 优惠券描述
     */
    public String getDesc() {
        return desc;
    }

    /**
     *     优惠券描述     *
     *        
     *
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    private Boolean freePostage;

    /**
     * @return 是否免邮
     */
    public Boolean getFreePostage() {
        return freePostage;
    }

    /**
     *     是否免邮     *
     *        
     *
     */
    public void setFreePostage(Boolean freePostage) {
        this.freePostage = freePostage;
    }

    private Long discountFee;

    /**
     * @return 减去金额，单位为分
     */
    public Long getDiscountFee() {
        return discountFee;
    }

    /**
     *     减去金额，单位为分     *
     *        
     *
     */
    public void setDiscountFee(Long discountFee) {
        this.discountFee = discountFee;
    }

}
