package com.salesmanager.core.model.purchaseorder;

import com.salesmanager.core.model.generic.SalesManagerEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PURCHASE_SUPPLIER_PAY_ORDER")
public class PurchaseSupplierPayOrder extends SalesManagerEntity<Long, PurchaseSupplierPayOrder> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "PAY_URL")
    private String payUrl;

    @Column(name = "PSO_ORDER_ID")
    private Long psoOrderId;

    @Column(name = "AMOUNT")
    private Double amount;

    @Column(name = "UPDT_ID")
    private Long updtId;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayUrl() {
        return payUrl;
    }

    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }

    public Long getPsoOrderId() {
        return psoOrderId;
    }

    public void setPsoOrderId(Long psoOrderId) {
        this.psoOrderId = psoOrderId;
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
