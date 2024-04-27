package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class ProductFreightEstimateProductFreightModel {

    private Long offerId;

    /**
     * @return 
     */
    public Long getOfferId() {
        return offerId;
    }

    /**
     *          *
     *        
     *
     */
    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    private String freight;

    /**
     * @return 
     */
    public String getFreight() {
        return freight;
    }

    /**
     *          *
     *        
     *
     */
    public void setFreight(String freight) {
        this.freight = freight;
    }

    private Long templateId;

    /**
     * @return 
     */
    public Long getTemplateId() {
        return templateId;
    }

    /**
     *          *
     *        
     *
     */
    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    private Double singleProductWeight;

    /**
     * @return 
     */
    public Double getSingleProductWeight() {
        return singleProductWeight;
    }

    /**
     *          *
     *        
     *
     */
    public void setSingleProductWeight(Double singleProductWeight) {
        this.singleProductWeight = singleProductWeight;
    }

    private Double singleProductWidth;

    /**
     * @return 
     */
    public Double getSingleProductWidth() {
        return singleProductWidth;
    }

    /**
     *          *
     *        
     *
     */
    public void setSingleProductWidth(Double singleProductWidth) {
        this.singleProductWidth = singleProductWidth;
    }

    private Double singleProductHeight;

    /**
     * @return 
     */
    public Double getSingleProductHeight() {
        return singleProductHeight;
    }

    /**
     *          *
     *        
     *
     */
    public void setSingleProductHeight(Double singleProductHeight) {
        this.singleProductHeight = singleProductHeight;
    }

    private Double singleProductLength;

    /**
     * @return 
     */
    public Double getSingleProductLength() {
        return singleProductLength;
    }

    /**
     *          *
     *        
     *
     */
    public void setSingleProductLength(Double singleProductLength) {
        this.singleProductLength = singleProductLength;
    }

    private Integer templateType;

    /**
     * @return 
     */
    public Integer getTemplateType() {
        return templateType;
    }

    /**
     *          *
     *        
     *
     */
    public void setTemplateType(Integer templateType) {
        this.templateType = templateType;
    }

    private String templateName;

    /**
     * @return 
     */
    public String getTemplateName() {
        return templateName;
    }

    /**
     *          *
     *        
     *
     */
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    private Integer subTemplateType;

    /**
     * @return 
     */
    public Integer getSubTemplateType() {
        return subTemplateType;
    }

    /**
     *          *
     *        
     *
     */
    public void setSubTemplateType(Integer subTemplateType) {
        this.subTemplateType = subTemplateType;
    }

    private String subTemplateName;

    /**
     * @return 
     */
    public String getSubTemplateName() {
        return subTemplateName;
    }

    /**
     *          *
     *        
     *
     */
    public void setSubTemplateName(String subTemplateName) {
        this.subTemplateName = subTemplateName;
    }

    private String firstFee;

    /**
     * @return 
     */
    public String getFirstFee() {
        return firstFee;
    }

    /**
     *          *
     *        
     *
     */
    public void setFirstFee(String firstFee) {
        this.firstFee = firstFee;
    }

    private String firstUnit;

    /**
     * @return 
     */
    public String getFirstUnit() {
        return firstUnit;
    }

    /**
     *          *
     *        
     *
     */
    public void setFirstUnit(String firstUnit) {
        this.firstUnit = firstUnit;
    }

    private String nextFee;

    /**
     * @return 
     */
    public String getNextFee() {
        return nextFee;
    }

    /**
     *          *
     *        
     *
     */
    public void setNextFee(String nextFee) {
        this.nextFee = nextFee;
    }

    private String nextUnit;

    /**
     * @return 
     */
    public String getNextUnit() {
        return nextUnit;
    }

    /**
     *          *
     *        
     *
     */
    public void setNextUnit(String nextUnit) {
        this.nextUnit = nextUnit;
    }

    private String discount;

    /**
     * @return 
     */
    public String getDiscount() {
        return discount;
    }

    /**
     *          *
     *        
     *
     */
    public void setDiscount(String discount) {
        this.discount = discount;
    }

    private String chargeType;

    /**
     * @return 
     */
    public String getChargeType() {
        return chargeType;
    }

    /**
     *          *
     *        
     *
     */
    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    private Boolean freePostage;

    /**
     * @return 是否包邮，true-包邮 false-不包邮，不包邮freight读取
     */
    public Boolean getFreePostage() {
        return freePostage;
    }

    /**
     *     是否包邮，true-包邮 false-不包邮，不包邮freight读取     *
     * 参数示例：<pre>true</pre>     
     *
     */
    public void setFreePostage(Boolean freePostage) {
        this.freePostage = freePostage;
    }

}
