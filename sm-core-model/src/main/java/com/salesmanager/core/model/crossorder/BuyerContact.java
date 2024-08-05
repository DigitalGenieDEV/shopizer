package com.salesmanager.core.model.crossorder;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BuyerContact {

    @Column(name = "BUYER_PHONE")
    private String phone;

    @Column(name = "BUYER_EMAIL")
    private String email;

    @Column(name = "BUYER_NAME")
    private String name;

    @Column(name = "BUYER_MOBILE")
    private String mobile;

    @Column(name = "BUYER_COMPANY")
    private String company;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
