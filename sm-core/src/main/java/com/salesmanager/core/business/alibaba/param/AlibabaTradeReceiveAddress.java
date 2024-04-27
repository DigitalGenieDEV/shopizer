package com.salesmanager.core.business.alibaba.param;

public class AlibabaTradeReceiveAddress {

    private String address;

    /**
     * @return 街道地址，不包括省市编码
     */
    public String getAddress() {
        return address;
    }

    /**
     *     街道地址，不包括省市编码     *
     *        
     *
     */
    public void setAddress(String address) {
        this.address = address;
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
     *        
     *
     */
    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    private String addressCodeText;

    /**
     * @return * 地址区域编码对应的文本（包括国家，省，城市）
     */
    public String getAddressCodeText() {
        return addressCodeText;
    }

    /**
     *     * 地址区域编码对应的文本（包括国家，省，城市）     *
     *        
     *
     */
    public void setAddressCodeText(String addressCodeText) {
        this.addressCodeText = addressCodeText;
    }

    private Long addressId;

    /**
     * @return 地址ID
     */
    public Long getAddressId() {
        return addressId;
    }

    /**
     *     地址ID     *
     *        
     *
     */
    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    private String bizType;

    /**
     * @return 业务类型
     */
    public String getBizType() {
        return bizType;
    }

    /**
     *     业务类型     *
     *        
     *
     */
    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    private Boolean isDefault;

    /**
     * @return 是否为默认地址
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     *     是否为默认地址     *
     *        
     *
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
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
     *        
     *
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    private Boolean latest;

    /**
     * @return 是否最近使用的地址
     */
    public Boolean getLatest() {
        return latest;
    }

    /**
     *     是否最近使用的地址     *
     *        
     *
     */
    public void setLatest(Boolean latest) {
        this.latest = latest;
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
     *        
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
     *        
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
     *        
     *
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

}
