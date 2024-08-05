package com.salesmanager.core.business.repositories.fulfillment;

import com.salesmanager.core.enmus.DocumentTypeEnums;
import com.salesmanager.core.model.fulfillment.GeneralDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface GeneralDocumentRepository extends JpaRepository<GeneralDocument, Long> {

    @Query("select c from GeneralDocument c "
            + " where c.orderId=?1 and c.documentType = ?2")
    GeneralDocument queryGeneralDocumentByOrderId(Long orderId, DocumentTypeEnums documentType);



}
