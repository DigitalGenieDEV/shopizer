package com.salesmanager.core.model.favorites;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "FAVORITES",
        indexes = { @Index(name="USER_IDX", columnList = "USER_ID")})
public class Favorites extends SalesManagerEntity<Long, Favorites> implements Auditable {


    private static final long serialVersionUID = 1L;


    @Id
    @Column(name = "FAVORITES_ID", unique=true, nullable=false)
    @TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT",
            pkColumnValue = "FAVORITES_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Long id;

    @NotNull
    @Column(name = "USER_ID",  nullable = false)
    private Long userId;

    @NotNull
    @Column(name = "PRODUCT_ID", nullable = false)
    private Long productId;

    @JsonIgnore
    @Embedded
    private AuditSection auditSection = new AuditSection();
    @Override
    public AuditSection getAuditSection() {
        return auditSection;
    }

    @Override
    public void setAuditSection(AuditSection auditSection) {
        this.auditSection = auditSection;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
