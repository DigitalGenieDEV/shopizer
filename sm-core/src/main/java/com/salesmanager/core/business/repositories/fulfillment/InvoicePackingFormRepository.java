package com.salesmanager.core.business.repositories.fulfillment;

import com.salesmanager.core.enmus.DocumentTypeEnums;
import com.salesmanager.core.model.fulfillment.GeneralDocument;
import com.salesmanager.core.model.fulfillment.InvoicePackingForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface InvoicePackingFormRepository extends JpaRepository<InvoicePackingForm, Long> {


    @Query("select c from InvoicePackingForm c "
            + " where c.orderId = ?1 and ?2 member of c.orderProductIds")
    List<InvoicePackingForm> queryInvoicePackingFormByOrderIdAndProductId(Long orderId, Long productId);


    @Query("select c from InvoicePackingForm c "
            + " where c.orderId=?1")
    List<InvoicePackingForm> queryInvoicePackingFormByOrderId(Long orderId);


}
