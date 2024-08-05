package com.salesmanager.core.business.fulfillment.service;

import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.fulfillment.GeneralDocument;
import com.salesmanager.core.model.fulfillment.InvoicePackingForm;


public interface InvoicePackingFormService extends SalesManagerEntityService<Long, InvoicePackingForm> {

    Long saveInvoicePackingForm(InvoicePackingForm invoicePackingForm);

    InvoicePackingForm queryInvoicePackingFormByOrderId(Long orderId);


}

