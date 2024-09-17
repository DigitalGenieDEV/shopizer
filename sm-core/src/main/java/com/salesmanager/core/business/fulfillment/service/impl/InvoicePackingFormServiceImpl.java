package com.salesmanager.core.business.fulfillment.service.impl;

import com.salesmanager.core.business.fulfillment.service.InvoicePackingFormService;
import com.salesmanager.core.business.repositories.fulfillment.InvoicePackingFormRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.fulfillment.GeneralDocument;
import com.salesmanager.core.model.fulfillment.InvoicePackingForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service("invoicePackingFormService")
public class InvoicePackingFormServiceImpl extends SalesManagerEntityServiceImpl<Long, InvoicePackingForm>  implements InvoicePackingFormService {

    private InvoicePackingFormRepository invoicePackingFormRepository;


    @Inject
    public InvoicePackingFormServiceImpl(InvoicePackingFormRepository invoicePackingFormRepository) {
        super(invoicePackingFormRepository);
        this.invoicePackingFormRepository = invoicePackingFormRepository;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Long saveInvoicePackingForm(InvoicePackingForm invoicePackingForm) {
        invoicePackingFormRepository.save(invoicePackingForm);
        return invoicePackingForm.getId();
    }

    @Override
    public InvoicePackingForm queryInvoicePackingFormByOrderIdAndProductId(Long orderId, Long productId) {
        return invoicePackingFormRepository.queryInvoicePackingFormByOrderIdAndProductId(orderId, productId);
    }

    @Override
    public InvoicePackingForm queryInvoicePackingFormByOrderId(Long orderId) {
        return invoicePackingFormRepository.queryInvoicePackingFormByOrderId(orderId);
    }
}

