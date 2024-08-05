package com.salesmanager.core.model.fulfillment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.enmus.DocumentTypeEnums;
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
@Table(name = "GENERAL_DOCUMENT")
public class GeneralDocument extends SalesManagerEntity<Long, GeneralDocument> implements Auditable {

    @Id
    @Column(name = "GENERAL_DOCUMENT_ID", unique=true, nullable=false)
    @TableGenerator(
            name = "TABLE_GEN",
            table = "SM_SEQUENCER",
            pkColumnName = "SEQ_NAME",
            valueColumnName = "SEQ_COUNT",
            pkColumnValue = "GENERAL_DOCUMENT_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Long id;


    @Column(name = "DOCUMENT_TYPE")
    @Enumerated(EnumType.STRING)
    private DocumentTypeEnums documentType;


    @Column(name = "DOCUMENT_NUMBER")
    private String documentNumber;


    @Column(name = "DOCUMENT_URL")
    private String documentUrl;

    /**
     * 履约子单号
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "generalDocument")
    private Set<FulfillmentSubOrder> fulfillmentSubOrders = new HashSet<>();



    @Column(name = "ORDER_ID")
    private Long orderId;

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
