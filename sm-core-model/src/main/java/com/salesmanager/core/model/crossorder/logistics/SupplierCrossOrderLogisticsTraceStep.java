package com.salesmanager.core.model.crossorder.logistics;

import com.salesmanager.core.model.generic.SalesManagerEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "SUPPLIER_CROSS_ORDER_LOGISTICS_TRACE_STEP")
public class SupplierCrossOrderLogisticsTraceStep extends SalesManagerEntity<Long, SupplierCrossOrderLogisticsTraceStep> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ACCEPT_TIME")
    private LocalDateTime acceptTime;

    @Column(name = "REMARK")
    private String remark;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOGISTICS_TRACE_ID")
    private SupplierCrossOrderLogisticsTrace logisticsTrace;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(LocalDateTime acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public SupplierCrossOrderLogisticsTrace getLogisticsTrace() {
        return logisticsTrace;
    }

    public void setLogisticsTrace(SupplierCrossOrderLogisticsTrace logisticsTrace) {
        this.logisticsTrace = logisticsTrace;
    }
}
