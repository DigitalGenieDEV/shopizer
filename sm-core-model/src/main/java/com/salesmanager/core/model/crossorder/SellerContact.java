package com.salesmanager.core.model.crossorder;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SellerContact {

    @Column(name = "SELLER_PHONE")
    private String phone;

    @Column(name = "SELLER_EMAIL")
    private String email;

    @Column(name = "SELLER_NAME")
    private String name;

    @Column(name = "SELLER_MOBILE")
    private String mobile;

    @Column(name = "SELLER_COMPANY_NAME")
    private String companyName;

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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
