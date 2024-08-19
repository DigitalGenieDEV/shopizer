package com.salesmanager.shop.model.purchaseorder;

import com.salesmanager.core.model.crossorder.SupplierCrossOrderProduct;
import com.salesmanager.core.model.order.orderproduct.OrderProduct;
import com.salesmanager.core.model.purchaseorder.PurchaseSupplierOrder;
import com.salesmanager.core.model.purchaseorder.PurchaseSupplierOrderProductStatus;
import com.salesmanager.shop.model.entity.Entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

public class ReadablePurchaseSupplierOrderProduct extends Entity implements Serializable {

    private Long productId;

    private String productName;

    private String productImage;

    private Integer quantity;

    private String logisticsStatus;

    private Double price;

    private Boolean isShip;

    private String productImageUrls;

    private String productSnapshotUrl;

    private Double unitPrice;

    private String unit;

    private String sku;

    private String skuInfo;

    private String status;

    private Date createdTime = new Date();

    private Date updatedTime = new Date();

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getLogisticsStatus() {
        return logisticsStatus;
    }

    public void setLogisticsStatus(String logisticsStatus) {
        this.logisticsStatus = logisticsStatus;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getShip() {
        return isShip;
    }

    public void setShip(Boolean ship) {
        isShip = ship;
    }

    public String getProductImageUrls() {
        return productImageUrls;
    }

    public void setProductImageUrls(String productImageUrls) {
        this.productImageUrls = productImageUrls;
    }

    public String getProductSnapshotUrl() {
        return productSnapshotUrl;
    }

    public void setProductSnapshotUrl(String productSnapshotUrl) {
        this.productSnapshotUrl = productSnapshotUrl;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSkuInfo() {
        return skuInfo;
    }

    public void setSkuInfo(String skuInfo) {
        this.skuInfo = skuInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
