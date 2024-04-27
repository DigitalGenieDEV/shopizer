package com.salesmanager.core.business.alibaba.param;

public class ComAlibabaOceanOpenplatformBizTradeParamOutAddress {

    private ComAlibabaOceanOpenplatformBizTradeParamPlace province;

    /**
     * @return 省
     */
    public ComAlibabaOceanOpenplatformBizTradeParamPlace getProvince() {
        return province;
    }

    /**
     *     省     *
     * 参数示例：<pre>{"name":"四川省","code":"51000"}</pre>     
     *    
     */
    public void setProvince(ComAlibabaOceanOpenplatformBizTradeParamPlace province) {
        this.province = province;
    }

    private ComAlibabaOceanOpenplatformBizTradeParamPlace city;

    /**
     * @return 市
     */
    public ComAlibabaOceanOpenplatformBizTradeParamPlace getCity() {
        return city;
    }

    /**
     *     市     *
     * 参数示例：<pre>{}</pre>     
     *    
     */
    public void setCity(ComAlibabaOceanOpenplatformBizTradeParamPlace city) {
        this.city = city;
    }

    private ComAlibabaOceanOpenplatformBizTradeParamPlace area;

    /**
     * @return 区
     */
    public ComAlibabaOceanOpenplatformBizTradeParamPlace getArea() {
        return area;
    }

    /**
     *     区     *
     * 参数示例：<pre>{}</pre>     
     *    
     */
    public void setArea(ComAlibabaOceanOpenplatformBizTradeParamPlace area) {
        this.area = area;
    }

    private ComAlibabaOceanOpenplatformBizTradeParamPlace town;

    /**
     * @return 镇/街道
     */
    public ComAlibabaOceanOpenplatformBizTradeParamPlace getTown() {
        return town;
    }

    /**
     *     镇/街道     *
     * 参数示例：<pre>{}</pre>     
     *    
     */
    public void setTown(ComAlibabaOceanOpenplatformBizTradeParamPlace town) {
        this.town = town;
    }

    private String address;

    /**
     * @return 详细地址
     */
    public String getAddress() {
        return address;
    }

    /**
     *     详细地址     *
     * 参数示例：<pre>网商路699号</pre>     
     *    
     */
    public void setAddress(String address) {
        this.address = address;
    }

    private String postCode;

    /**
     * @return 邮编
     */
    public String getPostCode() {
        return postCode;
    }

    /**
     *     邮编     *
     * 参数示例：<pre>511304</pre>     
     *    
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

}
