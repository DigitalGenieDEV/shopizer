package com.salesmanager.core.model.crossorder;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ReceiverInfo {

    @Column(name = "RECEIVER_FULL_NAME")
    private String fullName;

    @Column(name = "RECEIVER_DIVISION_CODE")
    private String divisionCode;

    @Column(name = "RECEIVER_POST_CODE")
    private String postCode;

    @Column(name = "RECEIVER_TOWN_CODE")
    private String townCode;

    @Column(name = "RECEIVER_AREA")
    private String area;

    @Column(name = "RECEIVER_MOBILE")
    private String mobile;

    @Column(name = "RECEIVER_PHONE")
    private String phone;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDivisionCode() {
        return divisionCode;
    }

    public void setDivisionCode(String divisionCode) {
        this.divisionCode = divisionCode;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getTownCode() {
        return townCode;
    }

    public void setTownCode(String townCode) {
        this.townCode = townCode;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}