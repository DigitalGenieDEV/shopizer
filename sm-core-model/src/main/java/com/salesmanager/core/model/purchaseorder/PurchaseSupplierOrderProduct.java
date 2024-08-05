package com.salesmanager.core.model.purchaseorder;

import com.salesmanager.core.model.crossorder.SupplierCrossOrderProduct;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.order.orderproduct.OrderProduct;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "PURCHASE_SUPPLIER_ORDER_PRODUCT")
public class PurchaseSupplierOrderProduct extends SalesManagerEntity<Long, PurchaseSupplierOrderProduct> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "PRODUCT_NAME")
    private String productName;

    @Column(name = "PRODUCT_IMAGE")
    private String productImage;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "LOGISTICS_STATUS")
    private String logisticsStatus;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "IS_SHIP")
    private Boolean isShip;

    @Column(name = "PRODUCT_IMAGE_URLS")
    private String productImageUrls;

    @Column(name = "PRODUCT_SNAPSHOT_URL")
    private String productSnapshotUrl;

    @Column(name = "UNIT_PRICE")
    private Double unitPrice;

    @Column(name = "UNIT")
    private String unit;

    @Column(name = "SKU")
    private String sku;

    @Column(name = "SKU_INFO")
    private String skuInfo;

    @Enumerated(EnumType.STRING)
    @Column(name = "ORDER_PRODUCT_STATUS")
    private PurchaseSupplierOrderProductStatus status = PurchaseSupplierOrderProductStatus.PENDING;

    @ManyToOne
    @JoinColumn(name = "PSO_ORDER_ID")
    private PurchaseSupplierOrder psoOrder;

    @OneToOne
    @JoinColumn(name = "CROSS_ORDER_PRODUCT_ID")
    private SupplierCrossOrderProduct crossOrderProduct;

    @OneToOne
    @JoinColumn(name = "ORDER_PRODUCT_ID")
    private OrderProduct orderProduct;

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

    public OrderProduct getOrderProduct() {
        return orderProduct;
    }

    public void setOrderProduct(OrderProduct orderProduct) {
        this.orderProduct = orderProduct;
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

    public PurchaseSupplierOrderProductStatus getStatus() {
        return status;
    }

    public void setStatus(PurchaseSupplierOrderProductStatus status) {
        this.status = status;
    }

    public PurchaseSupplierOrder getPsoOrder() {
        return psoOrder;
    }

    public void setPsoOrder(PurchaseSupplierOrder psoOrder) {
        this.psoOrder = psoOrder;
    }

    public SupplierCrossOrderProduct getCrossOrderProduct() {
        return crossOrderProduct;
    }

    public void setCrossOrderProduct(SupplierCrossOrderProduct crossOrderProduct) {
        this.crossOrderProduct = crossOrderProduct;
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
