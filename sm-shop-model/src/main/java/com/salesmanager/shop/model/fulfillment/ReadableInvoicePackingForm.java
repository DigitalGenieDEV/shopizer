package com.salesmanager.shop.model.fulfillment;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class ReadableInvoicePackingForm extends InvoicePackingForm{


    private List<ReadableInvoicePackingFormDetail> invoicePackingFormDetails = new ArrayList<>();


}
