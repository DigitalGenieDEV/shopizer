package com.salesmanager.core.business.fulfillment.service;


import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.fulfillment.GeneralDocument;

public interface GeneralDocumentService extends SalesManagerEntityService<Long, GeneralDocument> {

    Long saveGeneralDocument(GeneralDocument generalDocument);

    GeneralDocument queryGeneralDocumentByOrderId(Long orderId, String documentType);

}


