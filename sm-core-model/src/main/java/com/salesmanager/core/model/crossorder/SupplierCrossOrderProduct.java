package com.salesmanager.core.model.crossorder;

import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.purchaseorder.PurchaseSupplierOrderProduct;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "SUPPLIER_CROSS_ORDER_PRODUCT")
public class SupplierCrossOrderProduct extends SalesManagerEntity<Long, SupplierCrossOrderProduct> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "PRODUCT_SNAPSHOT_URL")
    private String productSnapshotUrl;

    @Column(name = "ITEM_AMOUNT")
    private BigDecimal itemAmount;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "REFUND")
    private BigDecimal refund;

    @Column(name = "SKU_ID")
    private Long skuId;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "SUB_ITEM_ID")
    private Long subItemId;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "UNIT")
    private String unit;

    @Column(name = "SKU_INFOS")
    private String skuInfos;

    @Column(name = "SPEC_ID")
    private String specId;

    @Column(name = "STATUS_STR")
    private String statusStr;

    @Column(name = "REFUND_STATUS")
    private String refundStatus;

    @Column(name = "LOGISTICS_STATUS")
    private Integer logisticsStatus;

    @Column(name = "GMT_CREATED")
    private Date gmtCreated;

    @Column(name = "GMT_MODIFIED")
    private Date gmtModified;

    @Column(name = "GMT_PAY_EXPIRE_TIME")
    private String gmtPayExpireTime;

    @Column(name = "REFUND_ID")
    private String refundId;

    @Column(name = "SUB_ITEM_ID_STR")
    private String subItemIdStr;

    @ManyToOne
    @JoinColumn(name = "SUPPLIER_CROSS_ORDER_ID", referencedColumnName = "ID")
    private SupplierCrossOrder supplierCrossOrder;

    @OneToOne
    @JoinColumn(name = "PSO_ORDER_PRODUCT_ID")
    private PurchaseSupplierOrderProduct psoOrderProduct;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductSnapshotUrl() {
        return productSnapshotUrl;
    }

    public void setProductSnapshotUrl(String productSnapshotUrl) {
        this.productSnapshotUrl = productSnapshotUrl;
    }

    public BigDecimal getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(BigDecimal itemAmount) {
        this.itemAmount = itemAmount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getRefund() {
        return refund;
    }

    public void setRefund(BigDecimal refund) {
        this.refund = refund;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getSubItemId() {
        return subItemId;
    }

    public void setSubItemId(Long subItemId) {
        this.subItemId = subItemId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSkuInfos() {
        return skuInfos;
    }

    public void setSkuInfos(String skuInfos) {
        this.skuInfos = skuInfos;
    }

    public String getSpecId() {
        return specId;
    }

    public void setSpecId(String specId) {
        this.specId = specId;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public Integer getLogisticsStatus() {
        return logisticsStatus;
    }

    public void setLogisticsStatus(Integer logisticsStatus) {
        this.logisticsStatus = logisticsStatus;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getGmtPayExpireTime() {
        return gmtPayExpireTime;
    }

    public void setGmtPayExpireTime(String gmtPayExpireTime) {
        this.gmtPayExpireTime = gmtPayExpireTime;
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public String getSubItemIdStr() {
        return subItemIdStr;
    }

    public void setSubItemIdStr(String subItemIdStr) {
        this.subItemIdStr = subItemIdStr;
    }

    public SupplierCrossOrder getSupplierCrossOrder() {
        return supplierCrossOrder;
    }

    public void setSupplierCrossOrder(SupplierCrossOrder supplierCrossOrder) {
        this.supplierCrossOrder = supplierCrossOrder;
    }

    public PurchaseSupplierOrderProduct getPsoOrderProduct() {
        return psoOrderProduct;
    }

    public void setPsoOrderProduct(PurchaseSupplierOrderProduct psoOrderProduct) {
        this.psoOrderProduct = psoOrderProduct;
    }
}
