package com.salesmanager.shop.model.fulfillment.facade;

import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.fulfillment.*;

import java.util.List;


public interface FulfillmentFacade {

    ReadableGeneralDocument queryGeneralDocumentByOrderId(Long orderId, String documentType);

    void saveGeneralDocumentByOrderId(PersistableGeneralDocument persistableGeneralDocument);

    ReadableInvoicePackingForm queryInvoicePackingFormByOrderId(Long orderId);

    void saveInvoicePackingFormByOrderId(PersistableInvoicePackingForm persistableInvoicePackingForm);


}

