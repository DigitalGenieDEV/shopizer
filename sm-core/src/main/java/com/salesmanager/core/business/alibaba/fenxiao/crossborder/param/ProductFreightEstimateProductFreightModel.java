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
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
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
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
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
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
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
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
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
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
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
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
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
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
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
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
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
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
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
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
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
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
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
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
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
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
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
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
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
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
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
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
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
     * 设置     *
     * 参数示例：<pre></pre>     
     * 此参数必填
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
     * 设置是否包邮，true-包邮 false-不包邮，不包邮freight读取     *
     * 参数示例：<pre>true</pre>     
     * 此参数必填
     */
    public void setFreePostage(Boolean freePostage) {
        this.freePostage = freePostage;
    }

}
