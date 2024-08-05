package com.salesmanager.core.model.fulfillment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "QC_INFO_HISTORY")
public class QcInfoHistory extends SalesManagerEntity<Long, QcInfoHistory> implements Auditable {


    @Id
    @Column(name = "QC_ORDER_HISTORY_ID", unique=true, nullable=false)
    @TableGenerator(
            name = "TABLE_GEN",
            table = "SM_SEQUENCER",
            pkColumnName = "SEQ_NAME",
            valueColumnName = "SEQ_COUNT",
            pkColumnValue = "QC_ORDER_HISTORY_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Long id;

    @Column(name = "CODE")
    private String code;

    @JsonIgnore
    @ManyToOne(targetEntity = QcInfo.class)
    @JoinColumn(name = "QC_INFO_ID", nullable = false)
    private QcInfo qcInfo;

    @Embedded
    private AuditSection auditSection = new AuditSection();

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public AuditSection getAuditSection() {
        return auditSection;
    }

    @Override
    public void setAuditSection(AuditSection auditSection) {
        this.auditSection = auditSection;
    }

}
