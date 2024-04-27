package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class ProductFreightEstimateProductFreightQueryParamsNew {

    private Long offerId;

    /**
     * @return 商品ID
     */
    public Long getOfferId() {
        return offerId;
    }

    /**
     *     商品ID     *
     * 参数示例：<pre>111111111</pre>     
     *
     */
    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    private String toProvinceCode;

    /**
     * @return 中国省份编码
     */
    public String getToProvinceCode() {
        return toProvinceCode;
    }

    /**
     *     中国省份编码     *
     * 参数示例：<pre>如浙江省330000</pre>     
     *
     */
    public void setToProvinceCode(String toProvinceCode) {
        this.toProvinceCode = toProvinceCode;
    }

    private String toCityCode;

    /**
     * @return 中国城市编码
     */
    public String getToCityCode() {
        return toCityCode;
    }

    /**
     *     中国城市编码     *
     * 参数示例：<pre>如杭州市330100</pre>     
     *
     */
    public void setToCityCode(String toCityCode) {
        this.toCityCode = toCityCode;
    }

    private String toCountryCode;

    /**
     * @return 中国地区编码
     */
    public String getToCountryCode() {
        return toCountryCode;
    }

    /**
     *     中国地区编码     *
     * 参数示例：<pre>如滨江区330108</pre>     
     *
     */
    public void setToCountryCode(String toCountryCode) {
        this.toCountryCode = toCountryCode;
    }

    private Long totalNum;

    /**
     * @return 购买件数
     */
    public Long getTotalNum() {
        return totalNum;
    }

    /**
     *     购买件数     *
     * 参数示例：<pre>3</pre>     
     *
     */
    public void setTotalNum(Long totalNum) {
        this.totalNum = totalNum;
    }

}
