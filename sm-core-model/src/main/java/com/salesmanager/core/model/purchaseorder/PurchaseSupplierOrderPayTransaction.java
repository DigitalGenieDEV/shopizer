package com.salesmanager.core.model.purchaseorder;

import com.salesmanager.core.model.generic.SalesManagerEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PURCHASE_SUPPLIER_ORDER_PAY_TRANSACTION")
public class PurchaseSupplierOrderPayTransaction extends SalesManagerEntity<Long, PurchaseSupplierOrderPayTransaction> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "AMOUNT")
    private Double amount;

    @Column(name = "UPDT_ID")
    private Long updtId;

    @Column(name = "DETAILS")
    private String details;

    @Column(name = "EXTERNAL_DETAILS")
    private String externalDetails;

    @Column(name = "PAY_TYPE")
    private String payType;

    @Column(name = "TRANSACTION_TYPE")
    private String transactionType;

    @ManyToOne
    private PurchaseSupplierOrder psoOrder;

    @Column(name = "CURRENCY")
    private String currency;

    @Column(name = "CREATED_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @Column(name = "UPDATED_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedTime;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getUpdtId() {
        return updtId;
    }

    public void setUpdtId(Long updtId) {
        this.updtId = updtId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getExternalDetails() {
        return externalDetails;
    }

    public void setExternalDetails(String externalDetails) {
        this.externalDetails = externalDetails;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public PurchaseSupplierOrder getPsoOrder() {
        return psoOrder;
    }

    public void setPsoOrder(PurchaseSupplierOrder psoOrder) {
        this.psoOrder = psoOrder;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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
