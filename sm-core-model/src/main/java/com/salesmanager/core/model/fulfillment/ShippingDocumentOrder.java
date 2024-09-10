package com.salesmanager.core.model.fulfillment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.enmus.FulfillmentSubTypeEnums;
import com.salesmanager.core.enmus.FulfillmentTypeEnums;
import com.salesmanager.core.enmus.TruckModelEnums;
import com.salesmanager.core.enmus.TruckTypeEnums;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.order.orderproduct.OrderProduct;
import com.salesmanager.core.model.shipping.ShippingTransportationType;
import com.salesmanager.core.model.shipping.ShippingType;
import com.salesmanager.core.model.shipping.TransportationMethod;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SHIPPING_DOCUMENT_ORDER")
public class ShippingDocumentOrder extends SalesManagerEntity<Long, ShippingDocumentOrder> implements Auditable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "SHIPPING_DOCUMENT_ORDER_ID", unique=true, nullable=false)
    @TableGenerator(
            name = "TABLE_GEN",
            table = "SM_SEQUENCER",
            pkColumnName = "SEQ_NAME",
            valueColumnName = "SEQ_COUNT",
            pkColumnValue = "SHIPPING_DOCUMENT_ORDER_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Long id;

    @Column(name = "SHIPPING_NO", length = 300)
    private String shippingNo;

    /**
     * 交易单商品
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "shippingDocumentOrder")
    private Set<GeneralDocument> generalDocuments =   new HashSet<>();


    @Column(name = "INVOICE_PACKING_FORM_ID")
    private Long invoicePackingFormId;

    /**
     * 交易单商品
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "shippingDocumentOrder")
    private Set<OrderProduct> orderProducts =  new HashSet<>();


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

    public String getShippingNo() {
        return shippingNo;
    }

    public void setShippingNo(String shippingNo) {
        this.shippingNo = shippingNo;
    }

    public Set<GeneralDocument> getGeneralDocuments() {
        return generalDocuments;
    }

    public void setGeneralDocuments(Set<GeneralDocument> generalDocuments) {
        this.generalDocuments = generalDocuments;
    }

    public Long getInvoicePackingFormId() {
        return invoicePackingFormId;
    }

    public void setInvoicePackingFormId(Long invoicePackingFormId) {
        this.invoicePackingFormId = invoicePackingFormId;
    }

    public Set<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(Set<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }
}
