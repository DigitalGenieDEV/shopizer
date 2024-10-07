package com.salesmanager.core.model.order;


import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@EntityListeners(value = AuditListener.class)
@Table (name="ORDER_RELATION" )
public class OrderRelation extends SalesManagerEntity<Long, OrderRelation> implements Auditable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="ORDER_RELATION_ID")
    @TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "ORDER_RELATION_ID_NEXT_VALUE")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Long id;

    @Column (name="ROOT_ORDER_ID")
    private Long rootOrderId;

    @Column (name="source_ORDER_ID")
    private Long sourceOrderId;

    @Column (name="ORDER_ID")
    private Long orderId;

    @Embedded
    private AuditSection auditSection = new AuditSection();

    public Long getId() {
        return id;
    }

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
