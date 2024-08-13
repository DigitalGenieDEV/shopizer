package com.salesmanager.core.model.fulfillment;

import com.salesmanager.core.model.common.Billing;
import com.salesmanager.core.model.common.Delivery;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;

@Data
@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "FULFILLMENT_MAIN_ORDER")
public class FulfillmentMainOrder extends SalesManagerEntity<Long, FulfillmentMainOrder> implements Auditable {


    @Id
    @Column(name = "FULFILLMENT_MAIN_ORDER_ID", unique=true, nullable=false)
    @TableGenerator(
            name = "TABLE_GEN",
            table = "SM_SEQUENCER",
            pkColumnName = "SEQ_NAME",
            valueColumnName = "SEQ_COUNT",
            pkColumnValue = "FULFILLMENT_MAIN_ORDER_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Long id;


    @Column(name = "MAIN_ORDER_ID")
    private Long mainOrderId;

    /**
     * 部分发货
     */
    @Column(name = "PARTIAL_DELIVERY")
    private Boolean partialDelivery;

    @Embedded
    private Delivery delivery = null;

    @Valid
    @Embedded
    private Billing billing = null;

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