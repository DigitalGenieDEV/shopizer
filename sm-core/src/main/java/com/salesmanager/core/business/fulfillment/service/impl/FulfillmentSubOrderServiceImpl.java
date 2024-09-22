package com.salesmanager.core.business.fulfillment.service.impl;

import com.salesmanager.core.business.fulfillment.service.FulfillmentSubOrderService;
import com.salesmanager.core.business.repositories.fulfillment.FulfillmentSubOrderRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.fulfillment.FulfillmentSubOrder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service
public class FulfillmentSubOrderServiceImpl extends SalesManagerEntityServiceImpl<Long, FulfillmentSubOrder> implements FulfillmentSubOrderService {

    private FulfillmentSubOrderRepository fulfillmentSubOrderRepository;

    @Inject
    public FulfillmentSubOrderServiceImpl(FulfillmentSubOrderRepository fulfillmentSubOrderRepository) {
        super(fulfillmentSubOrderRepository);
        this.fulfillmentSubOrderRepository = fulfillmentSubOrderRepository;
    }


    @Override
    public Long saveFulfillmentMainOrder(FulfillmentSubOrder fulfillmentSubOrder){
        fulfillmentSubOrderRepository.save(fulfillmentSubOrder);
        return fulfillmentSubOrder.getId();
    }

    @Override
    @Transactional
    public void updateFulfillmentMainOrder(FulfillmentSubOrder fulfillmentSubOrder) {
        fulfillmentSubOrderRepository.save(fulfillmentSubOrder);
    }

    @Override
    public List<FulfillmentSubOrder> queryFulfillmentSubOrderListByOrderId(Long orderId) {
        return fulfillmentSubOrderRepository.queryFulfillmentSubOrderListByOrderId(orderId);
    }


    @Override
    public FulfillmentSubOrder queryFulfillmentSubOrderByOrderProductId(Long productOrderId) {
        return fulfillmentSubOrderRepository.queryFulfillmentSubOrderByProductOrderProductId(productOrderId);
    }

}

