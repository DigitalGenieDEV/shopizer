package com.salesmanager.shop.model.fulfillment.facade;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.fulfillment.FulfillmentSubOrder;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.fulfillment.*;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;


public interface FulfillmentFacade {

    ReadableGeneralDocument queryGeneralDocumentByOrderIdAndType(Long orderId, String documentType);

    void saveGeneralDocument(PersistableGeneralDocument persistableGeneralDocument);

    ReadableInvoicePackingForm queryInvoicePackingFormByOrderProductId(Long orderId, Long orderProductId);

    void saveInvoicePackingForm(PersistableInvoicePackingForm persistableInvoicePackingForm);


    ReadableFulfillmentMainOrder queryFulfillmentMainOrderByOrderId(Long orderId);


    List<ReadableFulfillmentSubOrder> queryFulfillmentSubOrderListByOrderId(Long orderId);

    ReadableFulfillmentSubOrder queryFulfillmentSubOrderListByProductOrderId(Long productOrderId);


    void updateFulfillmentOrderStatusByOrderId(Long orderId, String status);


    void updateFulfillmentOrderStatusByProductOrderId(List<Long> productOrderId, String status);


    List<ReadableFulfillmentShippingInfo> queryShippingInformationByOrderId(Long orderId);

    ReadableFulfillmentShippingInfo queryShippingInformationByOrderProductId(Long orderProductId);


    void updateNationalLogistics(PersistableFulfillmentLogisticsUpdateReqDTO persistableFulfillmentLogisticsUpdateReqDTO) throws ServiceException;


}

