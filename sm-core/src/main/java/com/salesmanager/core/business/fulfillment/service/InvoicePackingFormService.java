package com.salesmanager.core.business.fulfillment.service;

import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.fulfillment.GeneralDocument;
import com.salesmanager.core.model.fulfillment.InvoicePackingForm;

import java.util.List;


public interface InvoicePackingFormService extends SalesManagerEntityService<Long, InvoicePackingForm> {

    Long saveInvoicePackingForm(InvoicePackingForm invoicePackingForm);

    List<InvoicePackingForm> queryInvoicePackingFormByOrderIdAndProductId(Long orderId, Long productId);



    List<InvoicePackingForm> queryInvoicePackingFormByOrderId(Long orderId);


}

