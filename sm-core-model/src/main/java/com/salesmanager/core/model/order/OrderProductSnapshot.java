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
@Table (name="ORDER_PRODUCT_SNAPSHOT" )
public class OrderProductSnapshot extends SalesManagerEntity<Long, OrderProductSnapshot> implements Auditable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="ORDER_PRODUCT_SNAPSHOT_ID")
    @TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "ORDER_PRODUCT_SNAPSHOT_ID_NEXT_VALUE")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Long id;

    @Column (name="PRODUCT_SKU")
    private String sku;

    @Column (name="PRODUCT_ID")
    private Long productId;

    @Column (name="ORDER_ID")
    private Long orderId;

    @Column (name="ORDER_PRODUCT_ID")
    private Long orderProductId;

    @Column(name = "SNAPSHOT", columnDefinition = "TEXT")
    private String snapshot;


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
