package com.salesmanager.core.business.repositories.fulfillment;

import com.salesmanager.core.enmus.DocumentTypeEnums;
import com.salesmanager.core.model.fulfillment.GeneralDocument;
import com.salesmanager.core.model.fulfillment.InvoicePackingForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface InvoicePackingFormRepository extends JpaRepository<InvoicePackingForm, Long> {


    @Query("select c from InvoicePackingForm c "
            + " where c.orderId=?1")
    InvoicePackingForm queryInvoicePackingFormByOrderId(Long orderId);


}
