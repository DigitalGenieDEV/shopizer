package com.salesmanager.core.model.fulfillment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "INVOICE_PACKING_FORM")
public class InvoicePackingForm extends SalesManagerEntity<Long, InvoicePackingForm> implements Auditable {

    @Id
    @Column(name = "INVOICE_PACKING_FORM_ID", unique=true, nullable=false)
    @TableGenerator(
            name = "TABLE_GEN",
            table = "SM_SEQUENCER",
            pkColumnName = "SEQ_NAME",
            valueColumnName = "SEQ_COUNT",
            pkColumnValue = "QC_ORDER_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Long id;

    @Column(name = "INVOICE_NO")
    private String invoiceNo;


    @Column(name = "INVOICE_DATE")
    private String invoiceDate;

    @Column(name = "SHIPPER_COMPANY")
    private String shipperCompany;

    @Column(name = "SHIPPER_ADDRESS")
    private String shipperAddress;

    @Column(name = "SHIPPER_TELEPHONE")
    private String shipperTelephone;

    @Column(name = "CONSIGNEE_COMPANY")
    private String consigneeCompany;

    @Column(name = "CONSIGNEE_ADDRESS")
    private String consigneeAddress;

    @Column(name = "CONSIGNEE_TELEPHONE")
    private String consigneeTelephone;

    @Column(name = "LOADING_PORT")
    private String loadingPort;


    @Column(name = "FINAL_DESTINATION")
    private String finalDestination;


    @Column(name = "VESSEL")
    private String vessel;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "invoicePackingForm")
    private Set<InvoicePackingFormDetail> invoicePackingFormDetails = new HashSet<>();


    @Column(name = "ORDER_ID")
    private Long orderId;

    @Column(name = "LOCAL_TRANSPORTATION_CHARGE")
    private String localTransportationCharge;

    @Column(name = "OCEAN_FREIGHT_CHARGES")
    private String oceanFreightCharges;

    @Column(name = "INSURANCE_CHARGES")
    private String insuranceCharges;

    @ElementCollection
    @CollectionTable(name = "INVOICE_PACKING_ORDER_PRODUCT", joinColumns = @JoinColumn(name = "INVOICE_PACKING_FORM_ID"))
    @Column(name = "ORDER_PRODUCT_ID")
    private List<Long> orderProductIds = new ArrayList<>();

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoicePackingForm that = (InvoicePackingForm) o;
        return Objects.equals(id, that.id);
    }

}
