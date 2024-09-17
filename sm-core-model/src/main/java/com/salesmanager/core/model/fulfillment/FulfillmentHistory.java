package com.salesmanager.core.model.fulfillment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.enmus.*;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.shipping.ShippingTransportationType;
import com.salesmanager.core.model.shipping.ShippingType;
import com.salesmanager.core.model.shipping.TransportationMethod;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "FULFILLMENT_HISTORY")
public class FulfillmentHistory extends SalesManagerEntity<Long, FulfillmentHistory> implements Auditable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "FULFILLMENT_HISTORY_ID", unique=true, nullable=false)
    @TableGenerator(
            name = "TABLE_GEN",
            table = "SM_SEQUENCER",
            pkColumnName = "SEQ_NAME",
            valueColumnName = "SEQ_COUNT",
            pkColumnValue = "FULFILLMENT_HISTORY_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Long id;

    /**
     * 交易单id
     */
    @Column(name = "ORDER_ID")
    private Long orderId;


    @Column(name = "ORDER_PRODUCT_ID")
    private Long orderProductId;


    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private FulfillmentHistoryTypeEnums status;


    @Enumerated(EnumType.STRING)
    @Column(name = "previous_status")
    private FulfillmentHistoryTypeEnums previousStatus;


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
