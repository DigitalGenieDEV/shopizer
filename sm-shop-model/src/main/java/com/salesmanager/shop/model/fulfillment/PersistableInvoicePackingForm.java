package com.salesmanager.shop.model.fulfillment;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class PersistableInvoicePackingForm extends InvoicePackingForm{

    private Long shippingOrderId;


    private Set<PersistableInvoicePackingFormDetail> invoicePackingFormDetails = new HashSet<>();

}
