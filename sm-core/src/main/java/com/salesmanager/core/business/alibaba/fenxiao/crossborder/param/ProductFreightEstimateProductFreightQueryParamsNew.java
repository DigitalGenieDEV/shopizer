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
     * 设置商品ID     *
     * 参数示例：<pre>111111111</pre>     
     * 此参数必填
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
     * 设置中国省份编码     *
     * 参数示例：<pre>如浙江省330000</pre>     
     * 此参数必填
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
     * 设置中国城市编码     *
     * 参数示例：<pre>如杭州市330100</pre>     
     * 此参数必填
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
     * 设置中国地区编码     *
     * 参数示例：<pre>如滨江区330108</pre>     
     * 此参数必填
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
     * 设置购买件数     *
     * 参数示例：<pre>3</pre>     
     * 此参数必填
     */
    public void setTotalNum(Long totalNum) {
        this.totalNum = totalNum;
    }

}
