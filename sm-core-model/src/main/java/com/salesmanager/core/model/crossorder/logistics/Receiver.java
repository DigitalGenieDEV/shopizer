package com.salesmanager.core.model.crossorder.logistics;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Receiver {
    @Column(name = "RECEIVER_NAME")
    private String receiverName;

    @Column(name = "RECEIVER_PHONE")
    private String receiverPhone;

    @Column(name = "RECEIVER_MOBILE")
    private String receiverMobile;

    @Column(name = "RECEIVER_ENCRYPT")
    private String encrypt;

    @Column(name = "RECEIVER_PROVINCE_CODE")
    private String receiverProvinceCode;

    @Column(name = "RECEIVER_CITY_CODE")
    private String receiverCityCode;

    @Column(name = "RECEIVER_COUNTY_CODE")
    private String receiverCountyCode;

    @Column(name = "RECEIVER_ADDRESS")
    private String receiverAddress;

    @Column(name = "RECEIVER_PROVINCE")
    private String receiverProvince;

    @Column(name = "RECEIVER_CITY")
    private String receiverCity;

    @Column(name = "RECEIVER_COUNTY")
    private String receiverCounty;

    // Getters and Setters
    // ...


    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }

    public String getReceiverProvinceCode() {
        return receiverProvinceCode;
    }

    public void setReceiverProvinceCode(String receiverProvinceCode) {
        this.receiverProvinceCode = receiverProvinceCode;
    }

    public String getReceiverCityCode() {
        return receiverCityCode;
    }

    public void setReceiverCityCode(String receiverCityCode) {
        this.receiverCityCode = receiverCityCode;
    }

    public String getReceiverCountyCode() {
        return receiverCountyCode;
    }

    public void setReceiverCountyCode(String receiverCountyCode) {
        this.receiverCountyCode = receiverCountyCode;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverProvince() {
        return receiverProvince;
    }

    public void setReceiverProvince(String receiverProvince) {
        this.receiverProvince = receiverProvince;
    }

    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    public String getReceiverCounty() {
        return receiverCounty;
    }

    public void setReceiverCounty(String receiverCounty) {
        this.receiverCounty = receiverCounty;
    }
}
