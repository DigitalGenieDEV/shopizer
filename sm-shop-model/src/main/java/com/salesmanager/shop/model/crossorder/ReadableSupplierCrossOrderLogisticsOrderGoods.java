package com.salesmanager.shop.model.crossorder;

import com.salesmanager.shop.model.entity.Entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

public class ReadableSupplierCrossOrderLogisticsOrderGoods extends Entity implements Serializable {

    private String logisticsOrderId;

    private String logisticsId;

    private Long tradeOrderId;

    private Long tradeOrderItemId;

    private String description;

    private Double quantity;

    private String unit;

    private String productName;

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
}
