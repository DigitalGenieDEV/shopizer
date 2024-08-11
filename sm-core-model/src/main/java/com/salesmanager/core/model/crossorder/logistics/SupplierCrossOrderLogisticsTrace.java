package com.salesmanager.core.model.crossorder.logistics;
import com.salesmanager.core.model.crossorder.SupplierCrossOrder;
import com.salesmanager.core.model.generic.SalesManagerEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "SUPPLIER_CROSS_ORDER_LOGISTICS_TRACE")
public class SupplierCrossOrderLogisticsTrace extends SalesManagerEntity<Long, SupplierCrossOrderLogisticsTrace> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "LOGISTICS_ID")
    private String logisticsId;

    @Column(name = "LOGISTICS_BILL_NO")
    private String logisticsBillNo;

    @Column(name = "ORDER_ID")
    private Long orderId;

    @OneToMany(mappedBy = "logisticsTrace", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SupplierCrossOrderLogisticsTraceStep> logisticsSteps = new HashSet<>();

    @ManyToOne
    @JoinColumn(name="SUPPLIER_CROSS_ORDER_ID")
    private SupplierCrossOrder supplierCrossOrder;

    public SupplierCrossOrder getSupplierCrossOrder() {
        return supplierCrossOrder;
    }

    public void setSupplierCrossOrder(SupplierCrossOrder supplierCrossOrder) {
        this.supplierCrossOrder = supplierCrossOrder;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }


    public Set<SupplierCrossOrderLogisticsTraceStep> getLogisticsSteps() {
        return logisticsSteps;
    }

    public void setLogisticsSteps(Set<SupplierCrossOrderLogisticsTraceStep> logisticsSteps) {
        this.logisticsSteps.clear();
        this.logisticsSteps.addAll(logisticsSteps);
    }
}
