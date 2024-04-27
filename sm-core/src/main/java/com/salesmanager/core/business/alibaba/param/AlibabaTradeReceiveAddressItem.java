package com.salesmanager.core.business.alibaba.param;

public class AlibabaTradeReceiveAddressItem {

    private Long id;

    /**
     * @return addressId
     */
    public Long getId() {
        return id;
    }

    /**
     *     addressId     *
     * 参数示例：<pre>322683081</pre>     
     *
     */
    public void setId(Long id) {
        this.id = id;
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

    private String address;

    /**
     * @return 街道地址，不包括省市编码
     */
    public String getAddress() {
        return address;
    }

    /**
     *     街道地址，不包括省市编码     *
     * 参数示例：<pre>网商路699</pre>     
     *
     */
    public void setAddress(String address) {
        this.address = address;
    }

    private String post;

    /**
     * @return 邮编
     */
    public String getPost() {
        return post;
    }

    /**
     *     邮编     *
     * 参数示例：<pre>340000</pre>     
     *
     */
    public void setPost(String post) {
        this.post = post;
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
     * 参数示例：<pre>0517-8888888</pre>     
     *
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String mobilePhone;

    /**
     * @return 手机号
     */
    public String getMobilePhone() {
        return mobilePhone;
    }

    /**
     *     手机号     *
     * 参数示例：<pre>18012345678</pre>     
     *
     */
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    private String addressCode;

    /**
     * @return 地址区域编码
     */
    public String getAddressCode() {
        return addressCode;
    }

    /**
     *     地址区域编码     *
     * 参数示例：<pre>330108</pre>     
     *
     */
    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    private String addressCodeText;

    /**
     * @return 地址区域编码对应的文本（包括国家，省，城市）
     */
    public String getAddressCodeText() {
        return addressCodeText;
    }

    /**
     *     地址区域编码对应的文本（包括国家，省，城市）     *
     * 参数示例：<pre>浙江省 杭州市 滨江区</pre>     
     *
     */
    public void setAddressCodeText(String addressCodeText) {
        this.addressCodeText = addressCodeText;
    }

    private Boolean isDefault;

    /**
     * @return 是否为默认
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     *     是否为默认     *
     * 参数示例：<pre>false</pre>     
     *
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    private String townCode;

    /**
     * @return 镇编码
     */
    public String getTownCode() {
        return townCode;
    }

    /**
     *     镇编码     *
     * 参数示例：<pre>123</pre>     
     *
     */
    public void setTownCode(String townCode) {
        this.townCode = townCode;
    }

    private String townName;

    /**
     * @return 镇地址
     */
    public String getTownName() {
        return townName;
    }

    /**
     *     镇地址     *
     * 参数示例：<pre>长河镇</pre>     
     *
     */
    public void setTownName(String townName) {
        this.townName = townName;
    }

}
