package com.salesmanager.core.business.fulfillment.service.impl;

import com.salesmanager.core.business.fulfillment.service.FulfillmentMainOrderService;
import com.salesmanager.core.business.fulfillment.service.FulfillmentSubOrderService;
import com.salesmanager.core.business.repositories.fulfillment.FulfillmentMainOrderRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.enmus.FulfillmentTypeEnums;
import com.salesmanager.core.enmus.TruckModelEnums;
import com.salesmanager.core.enmus.TruckTypeEnums;
import com.salesmanager.core.model.fulfillment.FulfillmentMainOrder;
import com.salesmanager.core.model.fulfillment.FulfillmentSubOrder;
import com.salesmanager.core.model.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service("fulfillmentMainOrderService")
public class FulfillmentMainOrderServiceImpl extends SalesManagerEntityServiceImpl<Long, FulfillmentMainOrder>  implements FulfillmentMainOrderService {

    private FulfillmentMainOrderRepository fulfillmentMainOrderRepository;

    @Autowired
    private FulfillmentSubOrderService fulfillmentSubOrderService;



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
        fulfillmentMainOrder.setMainOrderId(order.getId());
        fulfillmentMainOrder.setPartialDelivery(false);
        fulfillmentMainOrder.setDelivery(order.getDelivery());
        fulfillmentMainOrder.setBilling(order.getBilling());
        fulfillmentMainOrderRepository.save(fulfillmentMainOrder);

        order.getOrderProducts().forEach(orderProduct -> {
            FulfillmentSubOrder fulfillmentSubOrder = new FulfillmentSubOrder();
            fulfillmentSubOrder.setAdditionalServicesIds(orderProduct.getAdditionalServicesIds());
            fulfillmentSubOrder.setFulfillmentMainType(FulfillmentTypeEnums.PRODUCT_PREPARATION);
            fulfillmentSubOrder.setTruckModel(TruckModelEnums.valueOf(orderProduct.getTruckModel()));
            fulfillmentSubOrder.setTruckType(TruckTypeEnums.valueOf(orderProduct.getTruckType()));
            fulfillmentSubOrder.setNationalTransportationMethod(orderProduct.getNationalTransportationMethod());
            fulfillmentSubOrder.setShippingType(orderProduct.getShippingType());
            fulfillmentSubOrder.setShippingTransportationType(orderProduct.getShippingTransportationType());
            fulfillmentSubOrder.setInternationalTransportationMethod(orderProduct.getInternationalTransportationMethod());
            fulfillmentSubOrder.setFulfillmentMainOrderId(fulfillmentMainOrder.getId());
            fulfillmentSubOrder.setOrderId(orderProduct.getId());
            fulfillmentSubOrderService.saveFulfillmentMainOrder(fulfillmentSubOrder);
        });
    }

}

