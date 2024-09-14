package com.salesmanager.core.model.order;

import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "ORDER_INVOICE")
public class OrderInvoice extends SalesManagerEntity<Long, OrderInvoice> implements Auditable {

    @Id
    @Column(name = "INVOICE_ID", unique = true, nullable = false)
    @TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "INVOICE_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Long id;

    @OneToOne(mappedBy = "orderInvoice")
    @EqualsAndHashCode.Exclude
    private Order order;

    @Column(name = "INVOICE_TYPE")
    @Enumerated(EnumType.STRING)
    private InvoiceType invoiceType = InvoiceType.NO_INVOICE;

    @Column(name = "INVOICING_METHOD")
    @Enumerated(EnumType.STRING)
    private InvoicingMethod invoicingMethod;

    @Column(name = "TAX_TYPE")
    @Enumerated(EnumType.STRING)
    private TaxType taxType;

    @Column(name = "INVOICING_EMAIL")
    private String invoicingEmail;

    @Embedded
    private AuditSection auditSection = new AuditSection();

}
