package com.salesmanager.shop.model.crossorder;

import com.salesmanager.core.model.crossorder.logistics.SupplierCrossOrderLogisticsTraceStep;
import com.salesmanager.shop.model.entity.Entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

public class ReadableSupplierCrossOrderLogisticsTrace extends Entity implements Serializable {

    private String logisticsId;

    private String logisticsBillNo;

    private Long crossOrderId;

    private List<ReadableSupplierCrossOrderLogisticsTraceStep> logisticsSteps;

    public String getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(String logisticsId) {
        this.logisticsId = logisticsId;
    }

    public String getLogisticsBillNo() {
        return logisticsBillNo;
    }

    public void setLogisticsBillNo(String logisticsBillNo) {
        this.logisticsBillNo = logisticsBillNo;
    }

    public Long getCrossOrderId() {
        return crossOrderId;
    }

    public void setCrossOrderId(Long crossOrderId) {
        this.crossOrderId = crossOrderId;
    }

    public List<ReadableSupplierCrossOrderLogisticsTraceStep> getLogisticsSteps() {
        return logisticsSteps;
    }

    public void setLogisticsSteps(List<ReadableSupplierCrossOrderLogisticsTraceStep> logisticsSteps) {
        this.logisticsSteps = logisticsSteps;
    }
}
