package com.salesmanager.core.model.crossorder.logistics;

import com.salesmanager.core.model.generic.SalesManagerEntity;

import javax.persistence.*;

@Entity
@Table(name = "SUPPLIER_CROSS_ORDER_LOGISTICS_ORDER_GOODS")
public class SupplierCrossOrderLogisticsOrderGoods extends SalesManagerEntity<Long, SupplierCrossOrderLogisticsOrderGoods> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "LOGISTICS_ORDER_ID")
    private String logisticsOrderId;

    @Column(name = "LOGISTICS_ID")
    private String logisticsId;

    @Column(name = "TRADE_ORDER_ID")
    private Long tradeOrderId;

    @Column(name = "TRADE_ORDER_ITEM_ID")
    private Long tradeOrderItemId;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "QUANTITY")
    private Double quantity;

    @Column(name = "UNIT")
    private String unit;

    @Column(name = "PRODUCT_NAME")
    private String productName;

    @ManyToOne
    @JoinColumn(name = "LOGISTICS_ID", insertable = false, updatable = false)
    private SupplierCrossOrderLogistics supplierCrossOrderLogistics;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogisticsOrderId() {
        return logisticsOrderId;
    }

    public void setLogisticsOrderId(String logisticsOrderId) {
        this.logisticsOrderId = logisticsOrderId;
    }

    public String getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(String logisticsId) {
        this.logisticsId = logisticsId;
    }

    public Long getTradeOrderId() {
        return tradeOrderId;
    }

    public void setTradeOrderId(Long tradeOrderId) {
        this.tradeOrderId = tradeOrderId;
    }

    public Long getTradeOrderItemId() {
        return tradeOrderItemId;
    }

    public void setTradeOrderItemId(Long tradeOrderItemId) {
        this.tradeOrderItemId = tradeOrderItemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public SupplierCrossOrderLogistics getSupplierCrossOrderLogistics() {
        return supplierCrossOrderLogistics;
    }

    public void setSupplierCrossOrderLogistics(SupplierCrossOrderLogistics supplierCrossOrderLogistics) {
        this.supplierCrossOrderLogistics = supplierCrossOrderLogistics;
    }

    // Getters and Setters
    // ...
}
