package com.salesmanager.core.business.fulfillment.service.impl;

import com.salesmanager.core.business.fulfillment.service.FulfillmentHistoryService;
import com.salesmanager.core.business.fulfillment.service.FulfillmentMainOrderService;
import com.salesmanager.core.business.fulfillment.service.FulfillmentSubOrderService;
import com.salesmanager.core.business.fulfillment.service.QcInfoService;
import com.salesmanager.core.business.repositories.fulfillment.FulfillmentMainOrderRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.enmus.FulfillmentHistoryTypeEnums;
import com.salesmanager.core.enmus.FulfillmentTypeEnums;
import com.salesmanager.core.enmus.TruckModelEnums;
import com.salesmanager.core.enmus.TruckTypeEnums;
import com.salesmanager.core.model.fulfillment.FulfillmentHistory;
import com.salesmanager.core.model.fulfillment.FulfillmentMainOrder;
import com.salesmanager.core.model.fulfillment.FulfillmentSubOrder;
import com.salesmanager.core.model.fulfillment.QcInfo;
import com.salesmanager.core.model.order.Order;
import com.salesmanager.core.utils.StringUtil;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service("fulfillmentMainOrderService")
public class FulfillmentMainOrderServiceImpl extends SalesManagerEntityServiceImpl<Long, FulfillmentMainOrder>  implements FulfillmentMainOrderService {

    private FulfillmentMainOrderRepository fulfillmentMainOrderRepository;

    @Autowired
    private FulfillmentSubOrderService fulfillmentSubOrderService;

    @Autowired
    private FulfillmentHistoryService fulfillmentHistoryService;

    @Autowired
    private QcInfoService qcInfoService;


    @Inject
    public FulfillmentMainOrderServiceImpl(FulfillmentMainOrderRepository fulfillmentMainOrderRepository) {
        super(fulfillmentMainOrderRepository);
        this.fulfillmentMainOrderRepository = fulfillmentMainOrderRepository;
    }


    @Override
    public Long saveFulfillmentMainOrder(FulfillmentMainOrder fulfillmentMainOrder){
        fulfillmentMainOrderRepository.save(fulfillmentMainOrder);
        return fulfillmentMainOrder.getId();
    }

    @Override
    @Transactional
    public void createFulfillmentOrderByOrder(Order order) {
        FulfillmentMainOrder fulfillmentMainOrder = new FulfillmentMainOrder();
        fulfillmentMainOrder.setOrder(order);
        fulfillmentMainOrder.setPartialDelivery(false);
        fulfillmentMainOrder.setDelivery(order.getDelivery());
        fulfillmentMainOrder.setBilling(order.getBilling());
        fulfillmentMainOrderRepository.save(fulfillmentMainOrder);

        order.getOrderProducts().forEach(orderProduct -> {

            if (StringUtils.isNotEmpty(orderProduct.getAdditionalServicesMap())){
                QcInfo qcInfo = new QcInfo();
                qcInfo.setOrderId(order.getId());
                qcInfo.setAdditionalServicesMap(orderProduct.getAdditionalServicesMap());
                qcInfo.setProductId(orderProduct.getProductId());
                qcInfoService.saveQcInfo(qcInfo);
            }

            FulfillmentSubOrder fulfillmentSubOrder = new FulfillmentSubOrder();
            fulfillmentSubOrder.setAdditionalServicesMap(orderProduct.getAdditionalServicesMap());
            fulfillmentSubOrder.setFulfillmentMainType(FulfillmentTypeEnums.PAYMENT_COMPLETED);
            fulfillmentSubOrder.setTruckModel(TruckModelEnums.valueOf(orderProduct.getTruckModel()));
            fulfillmentSubOrder.setTruckType(TruckTypeEnums.valueOf(orderProduct.getTruckType()));
            fulfillmentSubOrder.setNationalTransportationMethod(orderProduct.getNationalTransportationMethod());
            fulfillmentSubOrder.setShippingType(orderProduct.getShippingType());
            fulfillmentSubOrder.setShippingTransportationType(orderProduct.getShippingTransportationType());
            fulfillmentSubOrder.setInternationalTransportationMethod(orderProduct.getInternationalTransportationMethod());
            fulfillmentSubOrder.setFulfillmentMainOrder(fulfillmentMainOrder);
            fulfillmentSubOrder.setOrderId(order.getId());
            fulfillmentSubOrder.setOrderProductId(orderProduct.getId());
            fulfillmentSubOrderService.saveFulfillmentMainOrder(fulfillmentSubOrder);
        });

        //创建履约历史
        FulfillmentHistory fulfillmentHistory = new FulfillmentHistory();
        fulfillmentHistory.setOrderId(order.getId());
        fulfillmentHistory.setStatus(FulfillmentHistoryTypeEnums.PAYMENT_COMPLETED);
        fulfillmentHistoryService.saveFulfillmentHistory(fulfillmentHistory);

    }

    @Override
    public FulfillmentMainOrder queryFulfillmentMainOrderByOrderId(Long orderId) {
        return fulfillmentMainOrderRepository.queryFulfillmentMainOrderByOrderId(orderId);
    }
    @Override
    public void updatePartialDelivery(Long id, Boolean partialDelivery){
        fulfillmentMainOrderRepository.updatePartialDelivery(id, partialDelivery);
    }

}

