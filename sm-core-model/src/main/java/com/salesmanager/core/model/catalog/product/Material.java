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
@Table(name = "MATERIAL")
public class Material extends SalesManagerEntity<Long, Material> implements Auditable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "MATERIAL_ID", unique = true, nullable = false)
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


    @Column(name = "CODE", nullable = false, length = 100)
    private String code;


    @Column(name = "SUB_CODE", nullable = true, length = 100)
    private String subCode;


    @Column(name = "PRICE")
    private BigDecimal  price;


    @Column(name = "SORT")
    private Integer sort;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "media")
    private Set<MaterialDescription> descriptions = new HashSet<MaterialDescription>();

    /**
     * @see MaterialType
     */
    @Column(name = "TYPE", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MaterialType type;


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