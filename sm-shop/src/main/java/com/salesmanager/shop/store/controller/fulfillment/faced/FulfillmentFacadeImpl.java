package com.salesmanager.shop.store.controller.fulfillment.faced;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.fulfillment.service.*;
import com.salesmanager.core.business.repositories.fulfillment.ShippingDocumentOrderRepository;
import com.salesmanager.core.business.services.order.orderproduct.OrderProductService;
import com.salesmanager.core.business.utils.ObjectConvert;
import com.salesmanager.core.enmus.DocumentTypeEnums;
import com.salesmanager.core.enmus.FulfillmentTypeEnums;
import com.salesmanager.core.model.common.Billing;
import com.salesmanager.core.model.common.Delivery;
import com.salesmanager.core.model.order.orderproduct.OrderProduct;
import com.salesmanager.shop.model.customer.ReadableBilling;
import com.salesmanager.shop.model.customer.ReadableDelivery;
import com.salesmanager.shop.model.fulfillment.*;
import com.salesmanager.shop.model.fulfillment.facade.FulfillmentFacade;
import org.apache.commons.collections.CollectionUtils;
import org.drools.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class FulfillmentFacadeImpl implements FulfillmentFacade {

    @Autowired
    private GeneralDocumentService generalDocumentService;

    @Autowired
    private InvoicePackingFormService invoicePackingFormService;

    @Autowired
    private FulfillmentSubOrderService fulfillmentSubOrderService;

    @Autowired
    private FulfillmentMainOrderService fulfillmentMainOrderService;

    @Autowired
    private FulfillmentHistoryService fulfillmentHistoryService;

    @Autowired
    private ShippingDocumentOrderRepository shippingDocumentOrderRepository;

    @Autowired
    private OrderProductService orderProductService;

    private static final Logger LOGGER = LoggerFactory.getLogger(FulfillmentFacadeImpl.class);

    @Override
    public ReadableGeneralDocument queryGeneralDocumentByOrderIdAndType(Long orderId, String documentType) {
        com.salesmanager.core.model.fulfillment.GeneralDocument generalDocument = generalDocumentService.queryGeneralDocumentByOrderIdAndType(orderId, documentType);
        if (generalDocument == null){
            return null;
        }
        ReadableGeneralDocument result = ObjectConvert.convert(generalDocument, ReadableGeneralDocument.class);
        result.setDocumentType(generalDocument.getDocumentType().name());
        return result;
    }

    @Override
    public void saveGeneralDocument(PersistableGeneralDocument persistableGeneralDocument) {
        com.salesmanager.core.model.fulfillment.GeneralDocument generalDocument = ObjectConvert.convert(persistableGeneralDocument, com.salesmanager.core.model.fulfillment.GeneralDocument.class);
        generalDocument.setDocumentType(DocumentTypeEnums.valueOf(persistableGeneralDocument.getDocumentType()));
        if (persistableGeneralDocument.getShippingOrderId() !=null){
            com.salesmanager.core.model.fulfillment.ShippingDocumentOrder shippingDocumentOrder = shippingDocumentOrderRepository.getById(persistableGeneralDocument.getShippingOrderId());
            if (shippingDocumentOrder == null){
                return;
            }
            generalDocument.setShippingDocumentOrder(shippingDocumentOrder);
        }
        generalDocumentService.saveGeneralDocument(generalDocument);
    }


    @Override
    public ReadableInvoicePackingForm queryInvoicePackingFormByOrderProductId(Long orderId, Long orderProductId) {

        OrderProduct orderProduct = orderProductService.getOrderProduct(orderProductId);
        if (orderProduct == null){
            return null;
        }

        com.salesmanager.core.model.fulfillment.ShippingDocumentOrder shippingDocumentOrder = orderProduct.getShippingDocumentOrder();
        if (shippingDocumentOrder == null || shippingDocumentOrder.getInvoicePackingFormId() == null){
            return null;
        }

        Long invoicePackingFormId = shippingDocumentOrder.getInvoicePackingFormId();

        com.salesmanager.core.model.fulfillment.InvoicePackingForm invoicePackingForm = invoicePackingFormService.getById(invoicePackingFormId);
        if (invoicePackingForm == null){
            return null;
        }
        ReadableInvoicePackingForm convert = ObjectConvert.convert(invoicePackingForm, ReadableInvoicePackingForm.class);

        Set<com.salesmanager.core.model.fulfillment.InvoicePackingFormDetail> invoicePackingFormDetails = invoicePackingForm.getInvoicePackingFormDetails();
        List<ReadableInvoicePackingFormDetail> detailFormDetails =  new ArrayList<>();
        for(com.salesmanager.core.model.fulfillment.InvoicePackingFormDetail invoicePackingFormDetail : invoicePackingFormDetails){
            ReadableInvoicePackingFormDetail detail = ObjectConvert.convert(invoicePackingFormDetail, ReadableInvoicePackingFormDetail.class);
            detailFormDetails.add(detail);
        }
        convert.setInvoicePackingFormDetails(detailFormDetails);
        return convert;
    }

    @Override
    public void saveInvoicePackingForm(PersistableInvoicePackingForm persistableInvoicePackingForm) {
        com.salesmanager.core.model.fulfillment.InvoicePackingForm invoicePackingForm = ObjectConvert.convert(persistableInvoicePackingForm, com.salesmanager.core.model.fulfillment.InvoicePackingForm.class);
        Set<PersistableInvoicePackingFormDetail> invoicePackingFormDetails = persistableInvoicePackingForm.getInvoicePackingFormDetails();

        Set<com.salesmanager.core.model.fulfillment.InvoicePackingFormDetail> invoicePackingFormDetailList = new HashSet<>();
        for(InvoicePackingFormDetail invoicePackingFormDetail : invoicePackingFormDetails){
            com.salesmanager.core.model.fulfillment.InvoicePackingFormDetail detail = ObjectConvert.convert(invoicePackingFormDetail, com.salesmanager.core.model.fulfillment.InvoicePackingFormDetail.class);
            detail.setInvoicePackingForm(invoicePackingForm);
            invoicePackingFormDetailList.add(detail);
        }
        invoicePackingForm.setOrderProductIds(persistableInvoicePackingForm.getOrderProductIds());
        invoicePackingForm.setInvoicePackingFormDetails(invoicePackingFormDetailList);
        invoicePackingFormService.saveInvoicePackingForm(invoicePackingForm);
        if (persistableInvoicePackingForm.getShippingOrderId() !=null){
            com.salesmanager.core.model.fulfillment.ShippingDocumentOrder shippingDocumentOrder = shippingDocumentOrderRepository.getById(persistableInvoicePackingForm.getShippingOrderId());
            if (shippingDocumentOrder == null){
                return;
            }
            shippingDocumentOrder.setInvoicePackingFormId(invoicePackingForm.getId());
            shippingDocumentOrderRepository.save(shippingDocumentOrder);
        }
    }


    @Override
    public ReadableFulfillmentMainOrder queryFulfillmentMainOrderByOrderId(Long orderId) {
        com.salesmanager.core.model.fulfillment.FulfillmentMainOrder fulfillmentMainOrder = fulfillmentMainOrderService.queryFulfillmentMainOrderByOrderId(orderId);
        if (fulfillmentMainOrder == null){
            return null;
        }
        Billing billing = fulfillmentMainOrder.getBilling();
        Delivery delivery = fulfillmentMainOrder.getDelivery();
        ReadableFulfillmentMainOrder result = ObjectConvert.convert(fulfillmentMainOrder, ReadableFulfillmentMainOrder.class);
        if(billing != null) {
            ReadableBilling address = new ReadableBilling();
            address.setCity(billing.getCity());
            address.setAddress(billing.getAddress());
            address.setCompany(billing.getCompany());
            address.setFirstName(billing.getFirstName());
            address.setLastName(billing.getLastName());
            address.setPostalCode(billing.getPostalCode());
            address.setPhone(billing.getTelephone());
            if(billing.getCountry()!=null) {
                address.setCountry(billing.getCountry().getIsoCode());
            }
            if(billing.getZone()!=null) {
                address.setZone(billing.getZone().getCode());
            }
            result.setBilling(address);
        }


        if(delivery!=null) {
            ReadableDelivery address = new ReadableDelivery();
            address.setCity(delivery.getCity());
            address.setAddress(delivery.getAddress());
            address.setCompany(delivery.getCompany());
            address.setMessage(delivery.getMessage());
            address.setFirstName(delivery.getFirstName());
            address.setLastName(delivery.getLastName());
            address.setPostalCode(delivery.getPostalCode());
            address.setPhone(delivery.getTelephone());
            if(delivery.getCountry()!=null) {
                address.setCountry(delivery.getCountry().getIsoCode());
            }
            if(delivery.getZone()!=null) {
                address.setZone(delivery.getZone().getCode());
            }
            result.setDelivery(address);
        }

        return result;
    }

    @Override
    public List<ReadableFulfillmentSubOrder> queryFulfillmentSubOrderListByOrderId(Long orderId) {
        List<com.salesmanager.core.model.fulfillment.FulfillmentSubOrder> fulfillmentSubOrders = fulfillmentSubOrderService.queryFulfillmentSubOrderListByOrderId(orderId);
        if (CollectionUtils.isEmpty(fulfillmentSubOrders)){
            return null;
        }
        List<ReadableFulfillmentSubOrder> readableFulfillmentSubOrders = new ArrayList<>();
        for(com.salesmanager.core.model.fulfillment.FulfillmentSubOrder fulfillmentSubOrder : fulfillmentSubOrders){
            ReadableFulfillmentSubOrder readableFulfillmentSubOrder = convertToReadableFulfillmentSubOrder(fulfillmentSubOrder);
            readableFulfillmentSubOrders.add(readableFulfillmentSubOrder);
        }
        return readableFulfillmentSubOrders;
    }

    @Override
    public ReadableFulfillmentSubOrder queryFulfillmentSubOrderListByProductOrderId(Long productOrderId) {
        com.salesmanager.core.model.fulfillment.FulfillmentSubOrder fulfillmentSubOrder = fulfillmentSubOrderService.queryFulfillmentSubOrderByProductOrderId(productOrderId);
        return   convertToReadableFulfillmentSubOrder(fulfillmentSubOrder);
    }

    @Override
    @Transactional
    public void updateFulfillmentOrderStatusByOrderId(Long orderId, String status){
        //更新所有履约单状态
        com.salesmanager.core.model.fulfillment.FulfillmentMainOrder fulfillmentMainOrder = fulfillmentMainOrderService.queryFulfillmentMainOrderByOrderId(orderId);
        if (fulfillmentMainOrder == null){
            return;
        }
        Set<com.salesmanager.core.model.fulfillment.FulfillmentSubOrder> fulfillSubOrders = fulfillmentMainOrder.getFulfillSubOrders();
        FulfillmentTypeEnums fulfillmentMainType = null;
        for (com.salesmanager.core.model.fulfillment.FulfillmentSubOrder fulfillmentSubOrder : fulfillSubOrders){
            if (fulfillmentMainType == null){
                fulfillmentMainType = fulfillmentSubOrder.getFulfillmentMainType();
            }
            fulfillmentSubOrder.setFulfillmentMainType(FulfillmentTypeEnums.valueOf(status));
            fulfillmentHistoryService.saveFulfillmentHistory(orderId, fulfillmentSubOrder.getOrderProductId(),
                    status, fulfillmentMainType == null? null : fulfillmentMainType.name());
            fulfillmentSubOrderService.updateFulfillmentMainOrder(fulfillmentSubOrder);
        }
        fulfillmentMainOrderService.updatePartialDelivery(fulfillmentMainOrder.getId(), false);

    }

    @Override
    public void updateFulfillmentOrderStatusByProductOrderId(List<Long> productOrderIds, String status) {
        if (CollectionUtils.isEmpty(productOrderIds)){
            return;
        }
        AtomicReference<Long> fulfillmentMainId = null;
        AtomicReference<Long> orderId = null;
        AtomicReference<FulfillmentTypeEnums> fulfillmentMainType = null;
        productOrderIds.forEach(productOrderId->{
            com.salesmanager.core.model.fulfillment.FulfillmentSubOrder fulfillmentSubOrder = fulfillmentSubOrderService.queryFulfillmentSubOrderByProductOrderId(productOrderId);
            if (orderId.get() == null){
                orderId.set(fulfillmentSubOrder.getOrderId());
            }
            if (fulfillmentMainType.get() == null){
                fulfillmentMainType.set(fulfillmentSubOrder.getFulfillmentMainType());
            }
            fulfillmentSubOrder.setFulfillmentMainType(FulfillmentTypeEnums.valueOf(status));
            fulfillmentMainId.set(fulfillmentSubOrder.getFulfillmentMainOrder().getId());
            fulfillmentSubOrderService.updateFulfillmentMainOrder(fulfillmentSubOrder);
            fulfillmentHistoryService.saveFulfillmentHistory(orderId.get(), productOrderId, status, fulfillmentMainType.get() == null ? null : fulfillmentMainType.get().name());
        });
        Long mainId =  fulfillmentMainId.get();
        fulfillmentMainOrderService.updatePartialDelivery(mainId, true);
    }

    @Override
    public List<ReadableFulfillmentShippingInfo> queryShippingInformationByOrderId(Long orderId) {

        List<com.salesmanager.core.model.fulfillment.FulfillmentSubOrder> fulfillmentSubOrders = fulfillmentSubOrderService.queryFulfillmentSubOrderListByOrderId(orderId);
        if (CollectionUtils.isEmpty(fulfillmentSubOrders)){
            return new ArrayList<>();
        }
        return fulfillmentSubOrders.stream().map(fulfillmentSubOrder -> {

            return queryShippingInformationByOrderProductId(fulfillmentSubOrder.getOrderProductId());

        }).collect(Collectors.toList());
    }

    @Override
    public ReadableFulfillmentShippingInfo queryShippingInformationByOrderProductId(Long orderProductId) {
        com.salesmanager.core.model.fulfillment.FulfillmentSubOrder fulfillmentSubOrder = fulfillmentSubOrderService.queryFulfillmentSubOrderByProductOrderId(orderProductId);
        ReadableFulfillmentShippingInfo result = new ReadableFulfillmentShippingInfo();

        List<com.salesmanager.core.model.fulfillment.FulfillmentHistory> fulfillmentHistoryList = fulfillmentHistoryService.queryFulfillmentHistoryByOrderProductId(orderProductId);
        if (CollectionUtils.isNotEmpty(fulfillmentHistoryList)){
            List<FulfillmentHistory> fulfillmentHistories =  fulfillmentHistoryList.stream().map(fulfillmentHistory -> {
                FulfillmentHistory convert = ObjectConvert.convert(fulfillmentHistory, FulfillmentHistory.class);
                convert.setStatus(fulfillmentHistory.getStatus() == null? null : fulfillmentHistory.getStatus().name());
                convert.setDateCreated(fulfillmentHistory.getAuditSection().getDateCreated());
                return convert;
            }).collect(Collectors.toList());
            result.setFulfillmentHistoryList(fulfillmentHistories);
        }
        result.setFulfillmentSubOrder(convertToReadableFulfillmentSubOrder(fulfillmentSubOrder));
        return result;
    }

    @Override
    @Transactional
    public void updateNationalLogistics(PersistableFulfillmentLogisticsUpdateReqDTO persistableFulfillmentSubOrderReqDTO) throws ServiceException {
        if (persistableFulfillmentSubOrderReqDTO == null || StringUtils.isEmpty(persistableFulfillmentSubOrderReqDTO.getType())){
            return;
        }
        if (persistableFulfillmentSubOrderReqDTO.getType().equals("ORDER")){
            //更新所有履约单状态
            List<com.salesmanager.core.model.fulfillment.FulfillmentSubOrder> fulfillSubOrders = fulfillmentSubOrderService.queryFulfillmentSubOrderListByOrderId(persistableFulfillmentSubOrderReqDTO.getId());
            if (CollectionUtils.isEmpty(fulfillSubOrders)){
                return;
            }
            for (com.salesmanager.core.model.fulfillment.FulfillmentSubOrder fulfillmentSubOrder : fulfillSubOrders){
                fulfillmentSubOrder.setLogisticsNumber(persistableFulfillmentSubOrderReqDTO.getLogisticsNumber());
                fulfillmentSubOrder.setNationalLogisticsCompany(persistableFulfillmentSubOrderReqDTO.getNationalLogisticsCompany());
                fulfillmentSubOrder.setNationalDriverName(persistableFulfillmentSubOrderReqDTO.getNationalDriverName());
                fulfillmentSubOrder.setNationalShippingTime(persistableFulfillmentSubOrderReqDTO.getNationalShippingTime());
                fulfillmentSubOrder.setNationalDriverPhone(persistableFulfillmentSubOrderReqDTO.getNationalDriverPhone());
                fulfillmentSubOrder.setTransportInformation(persistableFulfillmentSubOrderReqDTO.getTransportInformation());
                fulfillmentSubOrderService.save(fulfillmentSubOrder);
            }
            return ;
        }
        if (persistableFulfillmentSubOrderReqDTO.getType().equals("PRODUCT")){
            com.salesmanager.core.model.fulfillment.FulfillmentSubOrder fulfillmentSubOrder = fulfillmentSubOrderService.queryFulfillmentSubOrderByProductOrderId(persistableFulfillmentSubOrderReqDTO.getId());
            fulfillmentSubOrder.setLogisticsNumber(persistableFulfillmentSubOrderReqDTO.getLogisticsNumber());
            fulfillmentSubOrder.setNationalLogisticsCompany(persistableFulfillmentSubOrderReqDTO.getNationalLogisticsCompany());
            fulfillmentSubOrder.setNationalDriverName(persistableFulfillmentSubOrderReqDTO.getNationalDriverName());
            fulfillmentSubOrder.setNationalShippingTime(persistableFulfillmentSubOrderReqDTO.getNationalShippingTime());
            fulfillmentSubOrder.setNationalDriverPhone(persistableFulfillmentSubOrderReqDTO.getNationalDriverPhone());
            fulfillmentSubOrder.setTransportInformation(persistableFulfillmentSubOrderReqDTO.getTransportInformation());
            fulfillmentSubOrderService.save(fulfillmentSubOrder);
            return;
        }
        return;
    }


    private ReadableFulfillmentSubOrder convertToReadableFulfillmentSubOrder(com.salesmanager.core.model.fulfillment.FulfillmentSubOrder fulfillmentSubOrder) {
        if (fulfillmentSubOrder == null) {
            return null;
        }

        ReadableFulfillmentSubOrder readableFulfillmentSubOrder = new ReadableFulfillmentSubOrder();
        try {
            // 基本字段
            readableFulfillmentSubOrder.setId(fulfillmentSubOrder.getId());
            readableFulfillmentSubOrder.setLogisticsNumberBy1688(fulfillmentSubOrder.getLogisticsNumberBy1688());
            readableFulfillmentSubOrder.setLogisticsNumber(fulfillmentSubOrder.getLogisticsNumber());
            readableFulfillmentSubOrder.setNationalLogisticsCompany(fulfillmentSubOrder.getNationalLogisticsCompany());
            readableFulfillmentSubOrder.setNationalShippingTime(fulfillmentSubOrder.getNationalShippingTime());
            readableFulfillmentSubOrder.setNationalDriverName(fulfillmentSubOrder.getNationalDriverName());
            readableFulfillmentSubOrder.setNationalDriverPhone(fulfillmentSubOrder.getNationalDriverPhone());

            // 枚举字段处理（需要考虑枚举的转换）
            if (fulfillmentSubOrder.getFulfillmentMainType() != null) {
                readableFulfillmentSubOrder.setFulfillmentMainType(fulfillmentSubOrder.getFulfillmentMainType().name());
            }
            if (fulfillmentSubOrder.getFulfillmentSubTypeEnums() != null) {
                readableFulfillmentSubOrder.setFulfillmentSubTypeEnums(fulfillmentSubOrder.getFulfillmentSubTypeEnums().name());
            }
            if (fulfillmentSubOrder.getInternationalTransportationMethod() != null) {
                readableFulfillmentSubOrder.setInternationalTransportationMethod(fulfillmentSubOrder.getInternationalTransportationMethod().name());
            }
            if (fulfillmentSubOrder.getNationalTransportationMethod() != null) {
                readableFulfillmentSubOrder.setNationalTransportationMethod(fulfillmentSubOrder.getNationalTransportationMethod().name());
            }
            if (fulfillmentSubOrder.getShippingType() != null) {
                readableFulfillmentSubOrder.setShippingType(fulfillmentSubOrder.getShippingType().name());
            }
            if (fulfillmentSubOrder.getTruckType() != null) {
                readableFulfillmentSubOrder.setTruckType(fulfillmentSubOrder.getTruckType().name());
            }
            if (fulfillmentSubOrder.getTruckModel() != null) {
                readableFulfillmentSubOrder.setTruckModel(fulfillmentSubOrder.getTruckModel().name());
            }

            readableFulfillmentSubOrder.setCrossBorderTransportationLogisticsNumber(fulfillmentSubOrder.getCrossBorderTransportationLogisticsNumber());
            readableFulfillmentSubOrder.setTransportInformation(fulfillmentSubOrder.getTransportInformation());

        } catch (Exception e) {
            LOGGER.error("convertToReadableFulfillmentSubOrder error", e);
            return null;
        }

        return readableFulfillmentSubOrder;
    }
}
