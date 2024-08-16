package com.salesmanager.core.business.fulfillment.service;


import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.fulfillment.GeneralDocument;

import java.util.List;

public interface GeneralDocumentService extends SalesManagerEntityService<Long, GeneralDocument> {

    Long saveGeneralDocument(GeneralDocument generalDocument);

    List<GeneralDocument> queryGeneralDocumentByOrderId(Long orderId);

    GeneralDocument queryGeneralDocumentByOrderIdAndType(Long orderId, String documentType);


}


