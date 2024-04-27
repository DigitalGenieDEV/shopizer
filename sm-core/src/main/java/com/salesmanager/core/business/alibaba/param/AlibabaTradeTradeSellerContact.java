package com.salesmanager.core.business.alibaba.param;

public class AlibabaTradeTradeSellerContact {

    private String phone;

    /**
     * @return 联系电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     *     联系电话     *
     *        
     *
     */
    public void setPhone(String phone) {
        this.phone = phone;
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

    private String email;

    /**
     * @return 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     *     邮箱     *
     *        
     *
     */
    public void setEmail(String email) {
        this.email = email;
    }

    private String imInPlatform;

    /**
     * @return 联系人在平台的IM账号
     */
    public String getImInPlatform() {
        return imInPlatform;
    }

    /**
     *     联系人在平台的IM账号     *
     *        
     *
     */
    public void setImInPlatform(String imInPlatform) {
        this.imInPlatform = imInPlatform;
    }

    private String name;

    /**
     * @return 联系人名称
     */
    public String getName() {
        return name;
    }

    /**
     *     联系人名称     *
     *        
     *
     */
    public void setName(String name) {
        this.name = name;
    }

    private String mobile;

    /**
     * @return 联系人手机号
     */
    public String getMobile() {
        return mobile;
    }

    /**
     *     联系人手机号     *
     *        
     *
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    private String companyName;

    /**
     * @return 公司名称
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     *     公司名称     *
     *        
     *
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    private String wgSenderName;

    /**
     * @return 发件人名称，在微供等分销场景下由分销商
     */
    public String getWgSenderName() {
        return wgSenderName;
    }

    /**
     *     发件人名称，在微供等分销场景下由分销商         *
     * 参数示例：<pre>张**</pre>     
     *
     */
    public void setWgSenderName(String wgSenderName) {
        this.wgSenderName = wgSenderName;
    }

    private String wgSenderPhone;

    /**
     * @return 发件人电话，在微供等分销场景下由分销商
     */
    public String getWgSenderPhone() {
        return wgSenderPhone;
    }

    /**
     *     发件人电话，在微供等分销场景下由分销商         *
     * 参数示例：<pre>13800000000</pre>     
     *
     */
    public void setWgSenderPhone(String wgSenderPhone) {
        this.wgSenderPhone = wgSenderPhone;
    }

}
