package com.salesmanager.core.model.fulfillment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "INVOICE_PACKING_FORM_DETAIL")
public class InvoicePackingFormDetail extends SalesManagerEntity<Long, InvoicePackingFormDetail> implements Auditable {


    @Id
    @Column(name = "INVOICE_PACKING_FORM_DETAIL_ID", unique=true, nullable=false)
    @TableGenerator(
            name = "TABLE_GEN",
            table = "SM_SEQUENCER",
            pkColumnName = "SEQ_NAME",
            valueColumnName = "SEQ_COUNT",
            pkColumnValue = "INVOICE_PACKING_FORM_DETAIL_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Long id;

    @Column(name = "INVOICE_DATE")
    private String numbers;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CARTON_QTY")
    private String cartonQty;

    @Column(name = "UNIT_QTY")
    private String unitQty;

    @Column(name = "UNIT")
    private String unit;

    @Column(name = "UNIT_PRICE")
    private String unitPrice;

    @Column(name = "NET_WEIGHT")
    private String netWeight;

    @Column(name = "GROSS_WEIGHT")
    private String grossWeight;

    @Column(name = "CBM")
    private String cbm;

    @JsonIgnore
    @ManyToOne(targetEntity = InvoicePackingForm.class)
    @JoinColumn(name = "INVOICE_PACKING_FORM_ID", nullable = false)
    private InvoicePackingForm invoicePackingForm;

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

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
