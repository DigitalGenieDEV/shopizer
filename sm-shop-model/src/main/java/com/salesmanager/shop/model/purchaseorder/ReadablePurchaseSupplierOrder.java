package com.salesmanager.shop.model.purchaseorder;

import com.salesmanager.core.model.crossorder.SupplierCrossOrder;
import com.salesmanager.core.model.purchaseorder.*;
import com.salesmanager.shop.model.crossorder.ReadableSupplierCrossOrder;
import com.salesmanager.shop.model.entity.Entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

public class ReadablePurchaseSupplierOrder extends Entity implements Serializable {


    private String receiverArea;

    private String receiverCity;

    private String receiverProvince;

    private String receiverDivisionCode;

    private String receiverFullName;

    private String receiverMobile;

    private String receiverPhone;

    private String receiverPostcode;

    private String remark;

    private String orderType;

    private Date orderDateFinished;

    private String supplierCompanyName;

    private String supplierEmail;

    private String supplierMobile;

    private String supplierName;

    private String supplierPhone;

    private String  supplierNo;

    private Long supplierSellerId;

    private String supplierSellerNo;

    private Double shippingFee;

    private String tradeType;

    private String tradeTypeCode;

    private String tradeTypeDesc;

    private Double orderTotal;

    private String payStatus;

    private String payUrl;

    private String currency;

    private String status;

    private List<ReadablePurchaseSupplierOrderProduct> orderProducts = new ArrayList<>();

    private List<ReadableSupplierCrossOrder> crossOrders = new ArrayList<>();

    private Date createdTime;

    private Date updatedTime;

    public String getReceiverArea() {
        return receiverArea;
    }

    public void setReceiverArea(String receiverArea) {
        this.receiverArea = receiverArea;
    }

    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    public String getReceiverProvince() {
        return receiverProvince;
    }

    public void setReceiverProvince(String receiverProvince) {
        this.receiverProvince = receiverProvince;
    }

    public String getReceiverDivisionCode() {
        return receiverDivisionCode;
    }

    public void setReceiverDivisionCode(String receiverDivisionCode) {
        this.receiverDivisionCode = receiverDivisionCode;
    }

    public String getReceiverFullName() {
        return receiverFullName;
    }

    public void setReceiverFullName(String receiverFullName) {
        this.receiverFullName = receiverFullName;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getReceiverPostcode() {
        return receiverPostcode;
    }

    public void setReceiverPostcode(String receiverPostcode) {
        this.receiverPostcode = receiverPostcode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Date getOrderDateFinished() {
        return orderDateFinished;
    }

    public void setOrderDateFinished(Date orderDateFinished) {
        this.orderDateFinished = orderDateFinished;
    }

    public String getSupplierCompanyName() {
        return supplierCompanyName;
    }

    public void setSupplierCompanyName(String supplierCompanyName) {
        this.supplierCompanyName = supplierCompanyName;
    }

    public String getSupplierEmail() {
        return supplierEmail;
    }

    public void setSupplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail;
    }

    public String getSupplierMobile() {
        return supplierMobile;
    }

    public void setSupplierMobile(String supplierMobile) {
        this.supplierMobile = supplierMobile;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierPhone() {
        return supplierPhone;
    }

    public void setSupplierPhone(String supplierPhone) {
        this.supplierPhone = supplierPhone;
    }

    public String getSupplierNo() {
        return supplierNo;
    }

    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo;
    }

    public Long getSupplierSellerId() {
        return supplierSellerId;
    }

    public void setSupplierSellerId(Long supplierSellerId) {
        this.supplierSellerId = supplierSellerId;
    }

    public String getSupplierSellerNo() {
        return supplierSellerNo;
    }

    public void setSupplierSellerNo(String supplierSellerNo) {
        this.supplierSellerNo = supplierSellerNo;
    }

    public Double getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(Double shippingFee) {
        this.shippingFee = shippingFee;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradeTypeCode() {
        return tradeTypeCode;
    }

    public void setTradeTypeCode(String tradeTypeCode) {
        this.tradeTypeCode = tradeTypeCode;
    }

    public String getTradeTypeDesc() {
        return tradeTypeDesc;
    }

    public void setTradeTypeDesc(String tradeTypeDesc) {
        this.tradeTypeDesc = tradeTypeDesc;
    }

    public Double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayUrl() {
        return payUrl;
    }

    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ReadablePurchaseSupplierOrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<ReadablePurchaseSupplierOrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public List<ReadableSupplierCrossOrder> getCrossOrders() {
        return crossOrders;
    }

    public void setCrossOrders(List<ReadableSupplierCrossOrder> crossOrders) {
        this.crossOrders = crossOrders;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
}
