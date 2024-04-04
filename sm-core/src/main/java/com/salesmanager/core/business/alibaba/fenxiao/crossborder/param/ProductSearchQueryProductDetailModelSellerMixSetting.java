package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class ProductSearchQueryProductDetailModelSellerMixSetting {

    private Boolean isGeneralHunpi;

    /**
     * @return 是否普通混批
     */
    public Boolean getIsGeneralHunpi() {
        return isGeneralHunpi;
    }

    /**
     * 设置是否普通混批     *
     * 参数示例：<pre>true</pre>     
     * 此参数必填
     */
    public void setIsGeneralHunpi(Boolean isGeneralHunpi) {
        this.isGeneralHunpi = isGeneralHunpi;
    }

    private Integer mixAmount;

    /**
     * @return 混批金额
     */
    public Integer getMixAmount() {
        return mixAmount;
    }

    /**
     * 设置混批金额     *
     * 参数示例：<pre>100</pre>     
     * 此参数必填
     */
    public void setMixAmount(Integer mixAmount) {
        this.mixAmount = mixAmount;
    }

    private Integer mixNumber;

    /**
     * @return 混批数量
     */
    public Integer getMixNumber() {
        return mixNumber;
    }

    /**
     * 设置混批数量     *
     * 参数示例：<pre>2</pre>     
     * 此参数必填
     */
    public void setMixNumber(Integer mixNumber) {
        this.mixNumber = mixNumber;
    }

    private Boolean generalHunpi;

    /**
     * @return 是否普通混批
     */
    public Boolean getGeneralHunpi() {
        return generalHunpi;
    }

    /**
     * 设置是否普通混批     *
     * 参数示例：<pre>true</pre>     
     * 此参数必填
     */
    public void setGeneralHunpi(Boolean generalHunpi) {
        this.generalHunpi = generalHunpi;
    }

}
