package com.salesmanager.shop.model.crossorder;

import com.salesmanager.core.model.crossorder.BuyerContact;
import com.salesmanager.core.model.crossorder.ReceiverInfo;
import com.salesmanager.core.model.crossorder.SellerContact;
import com.salesmanager.core.model.crossorder.SupplierCrossOrderProduct;
import com.salesmanager.core.model.crossorder.logistics.SupplierCrossOrderLogistics;
import com.salesmanager.core.model.purchaseorder.PurchaseSupplierOrder;
import com.salesmanager.shop.model.entity.Entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

public class ReadableSupplierCrossOrder extends Entity implements Serializable {


    private String orderIdStr;

    private String status;

    private BigDecimal totalAmount;

    private Long discount;

    private BigDecimal refund;

    private BigDecimal shippingFee;

    private String sellerId;

    private Date payTime;

    private ReadableBuyerContact buyerContact;

    private ReadableSellerContact sellerContact;

    private ReadableReceiverInfo receiverInfo;

    private String refundStatus;

    private Integer refundPayment;

    private String alipayTradeId;

    private Date createdTime;

    private Date updatedTime;

    private Long sellerUserId;

    private String sellerLoginId;

    private Long buyerUserId;

    private String buyerLoginId;

    private String tradeTypeCode;

    private List<ReadableSupplierCrossOrderProduct> products = new ArrayList<>();

//    private Set<SupplierCrossOrderLogistics> logistics = new HashSet<>();


    public String getOrderIdStr() {
        return orderIdStr;
    }

    public void setOrderIdStr(String orderIdStr) {
        this.orderIdStr = orderIdStr;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public BigDecimal getRefund() {
        return refund;
    }

    public void setRefund(BigDecimal refund) {
        this.refund = refund;
    }

    public BigDecimal getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(BigDecimal shippingFee) {
        this.shippingFee = shippingFee;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public ReadableBuyerContact getBuyerContact() {
        return buyerContact;
    }

    public void setBuyerContact(ReadableBuyerContact buyerContact) {
        this.buyerContact = buyerContact;
    }

    public ReadableSellerContact getSellerContact() {
        return sellerContact;
    }

    public void setSellerContact(ReadableSellerContact sellerContact) {
        this.sellerContact = sellerContact;
    }

    public ReadableReceiverInfo getReceiverInfo() {
        return receiverInfo;
    }

    public void setReceiverInfo(ReadableReceiverInfo receiverInfo) {
        this.receiverInfo = receiverInfo;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public Integer getRefundPayment() {
        return refundPayment;
    }

    public void setRefundPayment(Integer refundPayment) {
        this.refundPayment = refundPayment;
    }

    public String getAlipayTradeId() {
        return alipayTradeId;
    }

    public void setAlipayTradeId(String alipayTradeId) {
        this.alipayTradeId = alipayTradeId;
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

    public Long getSellerUserId() {
        return sellerUserId;
    }

    public void setSellerUserId(Long sellerUserId) {
        this.sellerUserId = sellerUserId;
    }

    public String getSellerLoginId() {
        return sellerLoginId;
    }

    public void setSellerLoginId(String sellerLoginId) {
        this.sellerLoginId = sellerLoginId;
    }

    public Long getBuyerUserId() {
        return buyerUserId;
    }

    public void setBuyerUserId(Long buyerUserId) {
        this.buyerUserId = buyerUserId;
    }

    public String getBuyerLoginId() {
        return buyerLoginId;
    }

    public void setBuyerLoginId(String buyerLoginId) {
        this.buyerLoginId = buyerLoginId;
    }

    public String getTradeTypeCode() {
        return tradeTypeCode;
    }

    public void setTradeTypeCode(String tradeTypeCode) {
        this.tradeTypeCode = tradeTypeCode;
    }

    public List<ReadableSupplierCrossOrderProduct> getProducts() {
        return products;
    }

    public void setProducts(List<ReadableSupplierCrossOrderProduct> products) {
        this.products = products;
    }

}
