package com.salesmanager.core.business.alibaba.param;

public class AlibabaTradeFastInvoice {

    private Integer invoiceType;

    /**
     * @return 发票类型
    0：普通发票，1:增值税发票
     */
    public Integer getInvoiceType() {
        return invoiceType;
    }

    /**
     * 设置发票类型
    0：普通发票，1:增值税发票     *
     * 参数示例：<pre>0</pre>     
     * 此参数必填
     */
    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    private String provinceText;

    /**
     * @return 省份文本
     */
    public String getProvinceText() {
        return provinceText;
    }

    /**
     * 设置省份文本     *
     * 参数示例：<pre>浙江省</pre>     
     * 此参数必填
     */
    public void setProvinceText(String provinceText) {
        this.provinceText = provinceText;
    }

    private String cityText;

    /**
     * @return 城市文本
     */
    public String getCityText() {
        return cityText;
    }

    /**
     * 设置城市文本     *
     * 参数示例：<pre>杭州市</pre>     
     * 此参数必填
     */
    public void setCityText(String cityText) {
        this.cityText = cityText;
    }

    private String areaText;

    /**
     * @return 地区文本
     */
    public String getAreaText() {
        return areaText;
    }

    /**
     * 设置地区文本     *
     * 参数示例：<pre>滨江区</pre>     
     * 此参数必填
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
     * 设置镇文本     *
     * 参数示例：<pre>长河镇</pre>     
     * 此参数必填
     */
    public void setTownText(String townText) {
        this.townText = townText;
    }

    private String postCode;

    /**
     * @return 邮编
     */
    public String getPostCode() {
        return postCode;
    }

    /**
     * 设置邮编     *
     * 参数示例：<pre>333333</pre>     
     * 此参数必填
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    private String address;

    /**
     * @return 街道
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置街道     *
     * 参数示例：<pre>网商路699号</pre>     
     * 此参数必填
     */
    public void setAddress(String address) {
        this.address = address;
    }

    private String fullName;

    /**
     * @return 收票人姓名
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * 设置收票人姓名     *
     * 参数示例：<pre>张三</pre>     
     * 此参数必填
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    private String phone;

    /**
     * @return 电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置电话     *
     * 参数示例：<pre>0517-88990077</pre>     
     * 此参数必填
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String mobile;

    /**
     * @return 手机
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置手机     *
     * 参数示例：<pre>15251667788</pre>     
     * 此参数必填
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    private String companyName;

    /**
     * @return 购货公司名（发票抬头）
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 设置购货公司名（发票抬头）     *
     * 参数示例：<pre>测试公司</pre>     
     * 此参数必填
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    private String taxpayerIdentifier;

    /**
     * @return 纳税识别码
     */
    public String getTaxpayerIdentifier() {
        return taxpayerIdentifier;
    }

    /**
     * 设置纳税识别码     *
     * 参数示例：<pre>12345</pre>     
     * 此参数必填
     */
    public void setTaxpayerIdentifier(String taxpayerIdentifier) {
        this.taxpayerIdentifier = taxpayerIdentifier;
    }

    private String bankAndAccount;

    /**
     * @return 开户行及帐号
     */
    public String getBankAndAccount() {
        return bankAndAccount;
    }

    /**
     * 设置开户行及帐号     *
     * 参数示例：<pre>网商银行</pre>     
     * 此参数必填
     */
    public void setBankAndAccount(String bankAndAccount) {
        this.bankAndAccount = bankAndAccount;
    }

    private String localInvoiceId;

    /**
     * @return 增值税本地发票号
     */
    public String getLocalInvoiceId() {
        return localInvoiceId;
    }

    /**
     * 设置增值税本地发票号     *
     * 参数示例：<pre>123123123</pre>     
     * 此参数必填
     */
    public void setLocalInvoiceId(String localInvoiceId) {
        this.localInvoiceId = localInvoiceId;
    }

}
