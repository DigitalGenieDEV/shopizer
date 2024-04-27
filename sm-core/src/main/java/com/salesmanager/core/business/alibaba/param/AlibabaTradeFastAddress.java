package com.salesmanager.core.business.alibaba.param;

public class AlibabaTradeFastAddress {

    private Long addressId;

    /**
     * @return 收货地址id
     */
    public Long getAddressId() {
        return addressId;
    }

    /**
     *     收货地址id     *
     * 参数示例：<pre>1234</pre>     
     *    
     */
    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    private String fullName;

    /**
     * @return 收货人姓名
     */
    public String getFullName() {
        return fullName;
    }

    /**
     *     收货人姓名     *
     * 参数示例：<pre>张三</pre>     
     *    
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
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
     * 参数示例：<pre>15251667788</pre>     
     *    
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    private String phone;

    /**
     * @return 电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     *     电话     *
     * 参数示例：<pre>0517-88990077</pre>     
     *    
     */
    public void setPhone(String phone) {
        this.phone = phone;
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
     * 参数示例：<pre>000000</pre>     
     *    
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    private String cityText;

    /**
     * @return 市文本
     */
    public String getCityText() {
        return cityText;
    }

    /**
     *     市文本     *
     * 参数示例：<pre>杭州市</pre>     
     *    
     */
    public void setCityText(String cityText) {
        this.cityText = cityText;
    }

    private String provinceText;

    /**
     * @return 省份文本
     */
    public String getProvinceText() {
        return provinceText;
    }

    /**
     *     省份文本     *
     * 参数示例：<pre>浙江省</pre>     
     *    
     */
    public void setProvinceText(String provinceText) {
        this.provinceText = provinceText;
    }

    private String areaText;

    /**
     * @return 区文本
     */
    public String getAreaText() {
        return areaText;
    }

    /**
     *     区文本     *
     * 参数示例：<pre>滨江区</pre>     
     *    
     */
    public void setAreaText(String areaText) {
        this.areaText = areaText;
    }

    private String townText;

    /**
     * @return 镇文本
     */
    public String getTownText() {
        return townText;
    }

    /**
     *     镇文本     *
     * 参数示例：<pre>长河镇</pre>     
     *    
     */
    public void setTownText(String townText) {
        this.townText = townText;
    }

    private String address;

    /**
     * @return 街道地址
     */
    public String getAddress() {
        return address;
    }

    /**
     *     街道地址     *
     * 参数示例：<pre>网商路699号</pre>     
     *    
     */
    public void setAddress(String address) {
        this.address = address;
    }

    private String districtCode;

    /**
     * @return 地址编码
     */
    public String getDistrictCode() {
        return districtCode;
    }

    /**
     *     地址编码     *
     * 参数示例：<pre>310107</pre>     
     *    
     */
    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

}
