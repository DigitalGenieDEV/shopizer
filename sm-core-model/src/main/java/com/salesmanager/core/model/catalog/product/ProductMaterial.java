package com.salesmanager.core.model.catalog.product;

import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "PRODUCT_MATERIAL")
public class ProductMaterial extends SalesManagerEntity<Long, ProductMaterial> implements Auditable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "PRODUCT_MATERIAL_ID", unique = true, nullable = false)
    @TableGenerator(
            name = "TABLE_GEN",
            table = "SM_SEQUENCER",
            pkColumnName = "SEQ_NAME",
            valueColumnName = "SEQ_COUNT",
            pkColumnValue = "MATERIAL_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Long id;

    @Embedded
    private AuditSection auditSection = new AuditSection();

    @Column(name = "product_id",  nullable = false)
    private Long productId;

    @Column(name = "MATERIAL_ID",  nullable = false)
    private Long materialId;

    @Column(name = "WEIGHT",  nullable = false)
    private Long weight;


    @Override
    public AuditSection getAuditSection() {
        return auditSection;
    }

    @Override
    public void setAuditSection(AuditSection audit) {
        this.auditSection = audit;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }


}