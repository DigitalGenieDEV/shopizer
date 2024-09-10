package com.salesmanager.shop.model.fulfillment;

import com.salesmanager.shop.model.entity.ReadableAudit;
import com.salesmanager.shop.model.order.ReadableOrderProduct;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReadableShippingDocumentOrder extends ShippingDocumentOrder{


    private List<ReadableGeneralDocument> generalDocuments;

    private ReadableInvoicePackingForm invoicePackingForm;


    private ReadableAudit audit;

}
