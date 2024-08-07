package com.salesmanager.shop.model.purchaseorder;

import com.salesmanager.core.model.purchaseorder.PurchaseOrderStatus;
import com.salesmanager.core.model.purchaseorder.PurchaseSupplierOrder;
import com.salesmanager.shop.model.entity.Entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

public class ReadablePurchaseOrder extends Entity implements Serializable {


    private String channel;

    private Date dateFinished;

    private String currency;

    private List<ReadablePurchaseSupplierOrder> purchaseSupplierOrders = new ArrayList<>();

    private String status;

    private Long updtId;

    private Date createdTime = new Date();

    private Date updatedTime = new Date();

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

    public List<ReadablePurchaseSupplierOrder> getPurchaseSupplierOrders() {
        return purchaseSupplierOrders;
    }

    public void setPurchaseSupplierOrders(List<ReadablePurchaseSupplierOrder> purchaseSupplierOrders) {
        this.purchaseSupplierOrders = purchaseSupplierOrders;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
