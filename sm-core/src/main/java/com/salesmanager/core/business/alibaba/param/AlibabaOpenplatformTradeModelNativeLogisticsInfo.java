package com.salesmanager.core.business.alibaba.param;

public class AlibabaOpenplatformTradeModelNativeLogisticsInfo {

    private String address;

    /**
     * @return 详细地址
     */
    public String getAddress() {
        return address;
    }

    /**
     *     详细地址     *
     * 参数示例：<pre>杭州市网商路699号</pre>     
     *
     */
    public void setAddress(String address) {
        this.address = address;
    }

    private String area;

    /**
     * @return 县，区
     */
    public String getArea() {
        return area;
    }

    /**
     *     县，区     *
     * 参数示例：<pre>滨江区</pre>     
     *
     */
    public void setArea(String area) {
        this.area = area;
    }

    private String areaCode;

    /**
     * @return 省市区编码
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     *     省市区编码     *
     * 参数示例：<pre>330108</pre>     
     *
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    private String city;

    /**
     * @return 城市
     */
    public String getCity() {
        return city;
    }

    /**
     *     城市     *
     * 参数示例：<pre>杭州市</pre>     
     *
     */
    public void setCity(String city) {
        this.city = city;
    }

    private String contactPerson;

    /**
     * @return 联系人姓名
     */
    public String getContactPerson() {
        return contactPerson;
    }

    /**
     *     联系人姓名     *
     * 参数示例：<pre>张三</pre>     
     *
     */
    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    private String fax;

    /**
     * @return 传真
     */
    public String getFax() {
        return fax;
    }

    /**
     *     传真     *
     *        
     *
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    private String mobile;

    /**
     * @return 手机
     */
    public String getMobile() {
        return mobile;
    }

    /**
     *     手机     *
     * 参数示例：<pre>13988888888</pre>     
     *
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    private String province;

    /**
     * @return 省份
     */
    public String getProvince() {
        return province;
    }

    /**
     *     省份     *
     * 参数示例：<pre>浙江省</pre>     
     *
     */
    public void setProvince(String province) {
        this.province = province;
    }

    private String telephone;

    /**
     * @return 电话
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     *     电话     *
     * 参数示例：<pre>0517-88990077</pre>     
     *
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    private String zip;

    /**
     * @return 邮编
     */
    public String getZip() {
        return zip;
    }

    /**
     *     邮编     *
     * 参数示例：<pre>000000</pre>     
     *
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    private AlibabaOpenplatformTradeModelNativeLogisticsItemsInfo[] logisticsItems;

    /**
     * @return 运单明细
     */
    public AlibabaOpenplatformTradeModelNativeLogisticsItemsInfo[] getLogisticsItems() {
        return logisticsItems;
    }

    /**
     *     运单明细     *
     *        
     *
     */
    public void setLogisticsItems(AlibabaOpenplatformTradeModelNativeLogisticsItemsInfo[] logisticsItems) {
        this.logisticsItems = logisticsItems;
    }

    private String townCode;

    /**
     * @return 镇，街道地址码
     */
    public String getTownCode() {
        return townCode;
    }

    /**
     *     镇，街道地址码     *
     *        
     *
     */
    public void setTownCode(String townCode) {
        this.townCode = townCode;
    }

    private String town;

    /**
     * @return 镇，街道
     */
    public String getTown() {
        return town;
    }

    /**
     *     镇，街道     *
     *        
     *
     */
    public void setTown(String town) {
        this.town = town;
    }

    private String caid;

    /**
     * @return 解密地址ID，用于电商平台收货人信息加密场景使用，非订单加密场景请勿使用。
     */
    public String getCaid() {
        return caid;
    }

    /**
     *     解密地址ID，用于电商平台收货人信息加密场景使用，非订单加密场景请勿使用。     *
     *    
     *
     */
    public void setCaid(String caid) {
        this.caid = caid;
    }

    private String realLogisticsCompanyNo;

    /**
     * @return 真实承运物流公司code
     */
    public String getRealLogisticsCompanyNo() {
        return realLogisticsCompanyNo;
    }

    /**
     *     真实承运物流公司code     *
     * 参数示例：<pre>""</pre>     
     *
     */
    public void setRealLogisticsCompanyNo(String realLogisticsCompanyNo) {
        this.realLogisticsCompanyNo = realLogisticsCompanyNo;
    }

}
