package com.salesmanager.core.model.crossorder.logistics;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Sender {
    @Column(name = "SENDER_NAME")
    private String senderName;

    @Column(name = "SENDER_PHONE")
    private String senderPhone;

    @Column(name = "SENDER_MOBILE")
    private String senderMobile;

    @Column(name = "SENDER_ENCRYPT")
    private String encrypt;

    @Column(name = "SENDER_PROVINCE_CODE")
    private String senderProvinceCode;

    @Column(name = "SENDER_CITY_CODE")
    private String senderCityCode;

    @Column(name = "SENDER_COUNTY_CODE")
    private String senderCountyCode;

    @Column(name = "SENDER_ADDRESS")
    private String senderAddress;

    @Column(name = "SENDER_PROVINCE")
    private String senderProvince;

    @Column(name = "SENDER_CITY")
    private String senderCity;

    @Column(name = "SENDER_COUNTY")
    private String senderCounty;

    // Getters and Setters
    // ...


    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    public String getSenderMobile() {
        return senderMobile;
    }

    public void setSenderMobile(String senderMobile) {
        this.senderMobile = senderMobile;
    }

    public String getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }

    public String getSenderProvinceCode() {
        return senderProvinceCode;
    }

    public void setSenderProvinceCode(String senderProvinceCode) {
        this.senderProvinceCode = senderProvinceCode;
    }

    public String getSenderCityCode() {
        return senderCityCode;
    }

    public void setSenderCityCode(String senderCityCode) {
        this.senderCityCode = senderCityCode;
    }

    public String getSenderCountyCode() {
        return senderCountyCode;
    }

    public void setSenderCountyCode(String senderCountyCode) {
        this.senderCountyCode = senderCountyCode;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getSenderProvince() {
        return senderProvince;
    }

    public void setSenderProvince(String senderProvince) {
        this.senderProvince = senderProvince;
    }

    public String getSenderCity() {
        return senderCity;
    }

    public void setSenderCity(String senderCity) {
        this.senderCity = senderCity;
    }

    public String getSenderCounty() {
        return senderCounty;
    }

    public void setSenderCounty(String senderCounty) {
        this.senderCounty = senderCounty;
    }
}
