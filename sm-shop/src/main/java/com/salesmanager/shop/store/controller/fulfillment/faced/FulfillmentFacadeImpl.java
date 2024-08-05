package com.salesmanager.shop.store.controller.fulfillment.faced;

import com.salesmanager.core.business.fulfillment.service.GeneralDocumentService;
import com.salesmanager.core.business.fulfillment.service.InvoicePackingFormService;
import com.salesmanager.core.business.utils.ObjectConvert;
import com.salesmanager.core.enmus.DocumentTypeEnums;
import com.salesmanager.shop.model.fulfillment.*;
import com.salesmanager.shop.model.fulfillment.facade.FulfillmentFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FulfillmentFacadeImpl implements FulfillmentFacade {

    @Autowired
    private GeneralDocumentService generalDocumentService;

    @Autowired
    private InvoicePackingFormService invoicePackingFormService;

    @Override
    public ReadableGeneralDocument queryGeneralDocumentByOrderId(Long orderId, String documentType) {
        com.salesmanager.core.model.fulfillment.GeneralDocument generalDocument = generalDocumentService.queryGeneralDocumentByOrderId(orderId, documentType);
        if (generalDocument == null){
            return null;
        }
        ReadableGeneralDocument result = ObjectConvert.convert(generalDocument, ReadableGeneralDocument.class);
        Set<com.salesmanager.core.model.fulfillment.FulfillmentSubOrder> favouriteSubOrders = generalDocument.getFulfillmentSubOrders();
        List<FulfillmentSubOrder> fulfillmentSubOrders = new ArrayList<>();
        for(com.salesmanager.core.model.fulfillment.FulfillmentSubOrder fulfillmentSubOrder : favouriteSubOrders){
            FulfillmentSubOrder convert = ObjectConvert.convert(fulfillmentSubOrder, FulfillmentSubOrder.class);
            fulfillmentSubOrders.add(convert);
        }
        result.setDocumentType(generalDocument.getDocumentType().name());
        result.setFulfillSubOrders(fulfillmentSubOrders);
        return result;
    }

    @Override
    public void saveGeneralDocumentByOrderId(PersistableGeneralDocument persistableGeneralDocument) {
        com.salesmanager.core.model.fulfillment.GeneralDocument generalDocument = ObjectConvert.convert(persistableGeneralDocument, com.salesmanager.core.model.fulfillment.GeneralDocument.class);
        generalDocument.setDocumentType(DocumentTypeEnums.valueOf(persistableGeneralDocument.getDocumentType()));
        generalDocumentService.saveGeneralDocument(generalDocument);
    }

    @Override
    public ReadableInvoicePackingForm queryInvoicePackingFormByOrderId(Long orderId) {
        com.salesmanager.core.model.fulfillment.InvoicePackingForm invoicePackingForm = invoicePackingFormService.queryInvoicePackingFormByOrderId(orderId);
        ReadableInvoicePackingForm convert = ObjectConvert.convert(invoicePackingForm, ReadableInvoicePackingForm.class);

        Set<com.salesmanager.core.model.fulfillment.InvoicePackingFormDetail> invoicePackingFormDetails = invoicePackingForm.getInvoicePackingFormDetails();
        Set<InvoicePackingFormDetail> detailFormDetails =  new HashSet<>();
        for(com.salesmanager.core.model.fulfillment.InvoicePackingFormDetail invoicePackingFormDetail : invoicePackingFormDetails){
            InvoicePackingFormDetail detail = ObjectConvert.convert(invoicePackingFormDetail, InvoicePackingFormDetail.class);
            detailFormDetails.add(detail);
        }
        convert.setInvoicePackingFormDetails(detailFormDetails);
        return convert;
    }

    @Override
    public void saveInvoicePackingFormByOrderId(PersistableInvoicePackingForm persistableInvoicePackingForm) {
        com.salesmanager.core.model.fulfillment.InvoicePackingForm invoicePackingForm = ObjectConvert.convert(persistableInvoicePackingForm, com.salesmanager.core.model.fulfillment.InvoicePackingForm.class);
        Set<InvoicePackingFormDetail> invoicePackingFormDetails = persistableInvoicePackingForm.getInvoicePackingFormDetails();

        Set<com.salesmanager.core.model.fulfillment.InvoicePackingFormDetail> invoicePackingFormDetailList = new HashSet<>();
        for(InvoicePackingFormDetail invoicePackingFormDetail : invoicePackingFormDetails){
            com.salesmanager.core.model.fulfillment.InvoicePackingFormDetail detail = ObjectConvert.convert(invoicePackingFormDetail, com.salesmanager.core.model.fulfillment.InvoicePackingFormDetail.class);
            detail.setInvoicePackingForm(invoicePackingForm);
            invoicePackingFormDetailList.add(detail);
        }
        invoicePackingForm.setInvoicePackingFormDetails(invoicePackingFormDetailList);
        invoicePackingFormService.saveInvoicePackingForm(invoicePackingForm);
    }
}
