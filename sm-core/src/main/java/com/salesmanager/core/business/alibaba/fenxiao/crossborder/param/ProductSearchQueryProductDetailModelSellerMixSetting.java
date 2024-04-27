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
     *     是否普通混批     *
     * 参数示例：<pre>true</pre>     
     *
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
     *     混批金额     *
     * 参数示例：<pre>100</pre>     
     *
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
     *     混批数量     *
     * 参数示例：<pre>2</pre>     
     *
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
     *     是否普通混批     *
     * 参数示例：<pre>true</pre>     
     *
     */
    public void setGeneralHunpi(Boolean generalHunpi) {
        this.generalHunpi = generalHunpi;
    }

}
