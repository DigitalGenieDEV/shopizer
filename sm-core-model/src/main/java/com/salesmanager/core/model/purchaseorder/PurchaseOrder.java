package com.salesmanager.core.model.purchaseorder;

import com.salesmanager.core.model.generic.SalesManagerEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PURCHASE_ORDER")
public class PurchaseOrder  extends SalesManagerEntity<Long, PurchaseOrder> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CHANNEL")
    private String channel;

    @Column(name = "DATE_FINISHED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateFinished;

    @Column(name = "CURRENCY")
    private String currency;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "purchaseOrder")
    private Set<PurchaseSupplierOrder> purchaseSupplierOrders = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name="ORDER_STATUS")
    private PurchaseOrderStatus status = PurchaseOrderStatus.NEW;

    @Column(name = "UPDT_ID")
    private Long updtId;

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

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Date getDateFinished() {
        return dateFinished;
    }

    public void setDateFinished(Date dateFinished) {
        this.dateFinished = dateFinished;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Set<PurchaseSupplierOrder> getPurchaseSupplierOrders() {
        return purchaseSupplierOrders;
    }

    public void setPurchaseSupplierOrders(Set<PurchaseSupplierOrder> purchaseSupplierOrders) {
        this.purchaseSupplierOrders = purchaseSupplierOrders;
    }

    public PurchaseOrderStatus getStatus() {
        return status;
    }

    public void setStatus(PurchaseOrderStatus status) {
        this.status = status;
    }

    public Long getUpdtId() {
        return updtId;
    }

    public void setUpdtId(Long updtId) {
        this.updtId = updtId;
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
