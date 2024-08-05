package com.salesmanager.core.business.fulfillment.service.impl;
import com.salesmanager.core.enmus.DocumentTypeEnums;
import com.salesmanager.core.business.fulfillment.service.GeneralDocumentService;
import com.salesmanager.core.business.repositories.fulfillment.GeneralDocumentRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.fulfillment.GeneralDocument;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service("generalDocumentService")
public class GeneralDocumentServiceImpl extends SalesManagerEntityServiceImpl<Long, GeneralDocument> implements GeneralDocumentService {

    private GeneralDocumentRepository generalDocumentRepository;


    @Inject
    public GeneralDocumentServiceImpl(GeneralDocumentRepository generalDocumentRepository) {
        super(generalDocumentRepository);
        this.generalDocumentRepository = generalDocumentRepository;
    }

    @Override
    public Long saveGeneralDocument(GeneralDocument generalDocument) {
        generalDocumentRepository.save(generalDocument);
        return generalDocument.getId();
    }

    @Override
    public GeneralDocument queryGeneralDocumentByOrderId(Long orderId, String documentType) {
        return generalDocumentRepository.queryGeneralDocumentByOrderId(orderId, DocumentTypeEnums.valueOf(documentType));
    }
}

