package com.salesmanager.core.model.purchaseorder;


import com.salesmanager.core.model.crossorder.SupplierCrossOrder;
import com.salesmanager.core.model.generic.SalesManagerEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PURCHASE_SUPPLIER_ORDER")
public class PurchaseSupplierOrder extends SalesManagerEntity<Long, PurchaseSupplierOrder> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "RECEIVER_AREA")
    private String receiverArea;

    @Column(name = "RECEIVER_CITY")
    private String receiverCity;

    @Column(name = "RECEIVER_PROVINCE")
    private String receiverProvince;

    @Column(name = "RECEIVER_DIVISION_CODE")
    private String receiverDivisionCode;

    @Column(name = "RECEIVER_FULL_NAME")
    private String receiverFullName;

    @Column(name = "RECEIVER_MOBILE")
    private String receiverMobile;

    @Column(name = "RECEIVER_PHONE")
    private String receiverPhone;

    @Column(name = "RECEIVER_POSTCODE")
    private String receiverPostcode;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "ORDER_TYPE")
    private String orderType;

    @Column(name = "ORDER_DATE_FINISHED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDateFinished;

    @Column(name = "SUPPLIER_COMPANY_NAME")
    private String supplierCompanyName;

    @Column(name = "SUPPLIER_EMAIL")
    private String supplierEmail;

    @Column(name = "SUPPLIER_MOBILE")
    private String supplierMobile;

    @Column(name = "SUPPLIER_NAME")
    private String supplierName;

    @Column(name = "SUPPLIER_PHONE")
    private String supplierPhone;

    @Column(name = "SUPPLIER_NO")
    private String  supplierNo;

    @Column(name = "SUPPLIER_SELLER_ID")
    private Long supplierSellerId;

    @Column(name = "SUPPLIER_SELLER_NO")
    private String supplierSellerNo;

    @Column(name = "SHIPPING_FEE")
    private Double shippingFee;

    @Column(name = "TRADE_TYPE")
    private String tradeType;

    @Column(name = "TRADE_TYPE_CODE")
    private String tradeTypeCode;

    @Column(name = "TRADE_TYPE_DESC")
    private String tradeTypeDesc;

    @Column(name = "ORDER_TOTAL")
    private Double orderTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "PAY_STATUS")
    private PurchaseSupplierOrderPayStatus payStatus = PurchaseSupplierOrderPayStatus.PENDING_PAY;

    @Column(name = "PAY_URL")
    private String payUrl;

    @Column(name = "CURRENCY")
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "ORDER_STATUS")
    private PurchaseSupplierOrderStatus status = PurchaseSupplierOrderStatus.PENDING_PAY;

    @ManyToOne
    @JoinColumn(name = "PO_ORDER_ID")
    private PurchaseOrder purchaseOrder;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "psoOrder")
    private Set<PurchaseSupplierOrderProduct> orderProducts = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "psoOrder")
    private Set<SupplierCrossOrder> crossOrders = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "psoOrder")
    private Set<PurchaseSupplierOrderPayTransaction> transactions = new HashSet<>();

    @Column(name = "CREATED_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime = new Date();

    @Column(name = "UPDATED_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedTime = new Date();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

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

    public PurchaseSupplierOrderPayStatus getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(PurchaseSupplierOrderPayStatus payStatus) {
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

    public PurchaseSupplierOrderStatus getStatus() {
        return status;
    }

    public void setStatus(PurchaseSupplierOrderStatus status) {
        this.status = status;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public Set<PurchaseSupplierOrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(Set<PurchaseSupplierOrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public Set<SupplierCrossOrder> getCrossOrders() {
        return crossOrders;
    }

    public void setCrossOrders(Set<SupplierCrossOrder> crossOrders) {
        this.crossOrders = crossOrders;
    }

    public Set<PurchaseSupplierOrderPayTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<PurchaseSupplierOrderPayTransaction> transactions) {
        this.transactions = transactions;
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

    public boolean isStatusGte(PurchaseSupplierOrderStatus purchaseSupplierOrderStatus) {
        return this.status.ordinal() >= purchaseSupplierOrderStatus.ordinal();
    }

    public boolean isStatusGt(PurchaseSupplierOrderStatus purchaseSupplierOrderStatus) {
        return this.status.ordinal() > purchaseSupplierOrderStatus.ordinal();
    }

    public boolean isStatusLte(PurchaseSupplierOrderStatus purchaseSupplierOrderStatus) {
        return this.status.ordinal() <= purchaseSupplierOrderStatus.ordinal();
    }

    public boolean isStatusLt(PurchaseSupplierOrderStatus purchaseSupplierOrderStatus) {
        return this.status.ordinal() < purchaseSupplierOrderStatus.ordinal();
    }

    public boolean isPayStatusGte(PurchaseSupplierOrderPayStatus payStatus) {
        return this.status.ordinal() >= payStatus.ordinal();
    }

    public boolean isPayStatusLte(PurchaseSupplierOrderPayStatus payStatus) {
        return this.status.ordinal() <= payStatus.ordinal();
    }

    public boolean isPayStatusGt(PurchaseSupplierOrderPayStatus payStatus) {
        return this.status.ordinal() > payStatus.ordinal();
    }

    public boolean isPayStatusLt(PurchaseSupplierOrderPayStatus payStatus) {
        return this.status.ordinal() < payStatus.ordinal();
    }
}
