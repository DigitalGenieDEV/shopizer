package com.salesmanager.shop.model.order.v1;

import com.salesmanager.core.model.order.InvoiceType;
import com.salesmanager.shop.model.entity.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderInvoice extends Entity {
    private static final long serialVersionUID = 1L;

    /**
     * @see InvoiceType
     */
    private String invoiceType = InvoiceType.NO_INVOICE.name();

    /**
     * @see com.salesmanager.core.model.order.InvoicingMethod
     */
    private String invoicingMethod;

    /**
     * @see com.salesmanager.core.model.order.TaxType
     */
    private String taxType;


    private String invoicingEmail;
}
