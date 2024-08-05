package com.salesmanager.core.model.crossorder;

import com.salesmanager.core.model.crossorder.logistics.SupplierCrossOrderLogistics;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.purchaseorder.PurchaseSupplierOrder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SUPPLIER_CROSS_ORDER")
public class SupplierCrossOrder extends SalesManagerEntity<Long, SupplierCrossOrder> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ORDER_ID_STR")
    private String orderIdStr;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "TOTAL_AMOUNT")
    private BigDecimal totalAmount;

    @Column(name = "DISCOUNT")
    private Long discount;

    @Column(name = "REFUND")
    private BigDecimal refund;

    @Column(name = "SHIPPING_FEE")
    private BigDecimal shippingFee;

    @Column(name = "SELLER_ID")
    private String sellerId;

    @Column(name = "PAY_TIME")
    private Date payTime;

    @Embedded
    private BuyerContact buyerContact;

    @Embedded
    private SellerContact sellerContact;

    @Embedded
    private ReceiverInfo receiverInfo;

    @Column(name = "REFUND_STATUS")
    private String refundStatus;

    @Column(name = "REFUND_PAYMENT")
    private Integer refundPayment;

    @Column(name = "ALIPAY_TRADE_ID")
    private String alipayTradeId;

    @Column(name = "CREATED_TIME")
    private Date createdTime;

    @Column(name = "UPDATED_TIME")
    private Date updatedTime;

    @Column(name = "SELLER_USER_ID")
    private Long sellerUserId;

    @Column(name = "SELLER_LOGIN_ID")
    private String sellerLoginId;

    @Column(name = "BUYER_USER_ID")
    private Long buyerUserId;

    @Column(name = "BUYER_LOGIN_ID")
    private String buyerLoginId;

    @Column(name = "TRADE_TYPE_CODE")
    private String tradeTypeCode;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "supplierCrossOrder")
    private Set<SupplierCrossOrderProduct> products = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "supplierCrossOrder")
    private Set<SupplierCrossOrderLogistics> logistics = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "PSO_ORDER_ID")
    private PurchaseSupplierOrder psoOrder;

    public Set<SupplierCrossOrderLogistics> getLogistics() {
        return logistics;
    }

    public void setLogistics(Set<SupplierCrossOrderLogistics> logistics) {
        this.logistics = logistics;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

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

    public BuyerContact getBuyerContact() {
        return buyerContact;
    }

    public void setBuyerContact(BuyerContact buyerContact) {
        this.buyerContact = buyerContact;
    }

    public SellerContact getSellerContact() {
        return sellerContact;
    }

    public void setSellerContact(SellerContact sellerContact) {
        this.sellerContact = sellerContact;
    }

    public ReceiverInfo getReceiverInfo() {
        return receiverInfo;
    }

    public void setReceiverInfo(ReceiverInfo receiverInfo) {
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

    public Set<SupplierCrossOrderProduct> getProducts() {
        return products;
    }

    public void setProducts(Set<SupplierCrossOrderProduct> products) {
        this.products = products;
    }

    public PurchaseSupplierOrder getPsoOrder() {
        return psoOrder;
    }

    public void setPsoOrder(PurchaseSupplierOrder psoOrder) {
        this.psoOrder = psoOrder;
    }
}
