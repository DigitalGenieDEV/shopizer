package com.salesmanager.core.model.fulfillment;

import com.salesmanager.core.enmus.AdditionalServiceEnums;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "ADDITIONAL_SERVICES")
public class AdditionalServices extends SalesManagerEntity<Long, AdditionalServices> implements Auditable {


    @Id
    @Column(name = "ADDITIONAL_SERVICES_ID", unique=true, nullable=false)
    @TableGenerator(
            name = "TABLE_GEN",
            table = "SM_SEQUENCER",
            pkColumnName = "SEQ_NAME",
            valueColumnName = "SEQ_COUNT",
            pkColumnValue = "ADDITIONAL_SERVICES_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Long id;

    /**
     * @see AdditionalServiceEnums
     */
    @Column(name = "CODE")
    private String code;

    @Column(name = "SUB_CODE")
    private String subCode;

    @Column(name = "PRICE")
    private String price;


    @Column(name = "MERCHANT_ID")
    private Long merchantId;

    @Column(name = "SORT")
    private Integer sort;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "additionalServices")
    private Set<AddidtionalServicesDescription> descriptions = new HashSet<AddidtionalServicesDescription>();


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
