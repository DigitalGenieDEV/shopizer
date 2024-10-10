package com.salesmanager.core.business.fulfillment.service.impl;

import com.salesmanager.core.business.fulfillment.service.FulfillmentHistoryService;
import com.salesmanager.core.business.repositories.fulfillment.FulfillmentHistoryRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.enmus.FulfillmentHistoryTypeEnums;
import com.salesmanager.core.enmus.FulfillmentTypeEnums;
import com.salesmanager.core.model.fulfillment.FulfillmentHistory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service("fulfillmentHistoryService")
public class FulfillmentHistoryServiceImpl extends SalesManagerEntityServiceImpl<Long, FulfillmentHistory>  implements FulfillmentHistoryService {

    private FulfillmentHistoryRepository fulfillmentHistoryRepository;


    @Inject
    public FulfillmentHistoryServiceImpl(FulfillmentHistoryRepository fulfillmentHistoryRepository) {
        super(fulfillmentHistoryRepository);
        this.fulfillmentHistoryRepository = fulfillmentHistoryRepository;
    }


    @Override
    public Long saveFulfillmentHistory(FulfillmentHistory fulfillmentHistory){
        fulfillmentHistoryRepository.save(fulfillmentHistory);
        return fulfillmentHistory.getId();
    }

    @Override
    public Long saveFulfillmentHistory(Long orderId, Long orderProductId, String status, String previousStatus) {
        if (FulfillmentTypeEnums.fromString(status) == null){
            return null;
        }
        FulfillmentHistory fulfillmentHistory = new FulfillmentHistory();
        fulfillmentHistory.setOrderId(orderId);
        fulfillmentHistory.setOrderProductId(orderProductId);
        fulfillmentHistory.setPreviousStatus(FulfillmentHistoryTypeEnums.fromString(previousStatus));
        fulfillmentHistory.setStatus(FulfillmentHistoryTypeEnums.fromString(status));
        fulfillmentHistoryRepository.save(fulfillmentHistory);
        return fulfillmentHistory.getId();
    }

    @Override
    public List<FulfillmentHistory> queryFulfillmentHistoryByOrderId(Long orderId){
       return fulfillmentHistoryRepository.queryFulfillmentHistoryByOrderId(orderId);
    }

    @Override
    public List<FulfillmentHistory> queryFulfillmentHistoryByOrderIdAndProductId(Long orderId, Long productId) {
        return fulfillmentHistoryRepository.queryFulfillmentHistoryByOrderIdAndProductId(orderId, productId);
    }

    @Override
    public void updateOrderIdByOrderProductId(Long orderId, Long orderProductId) {
        fulfillmentHistoryRepository.updateOrderIdByOrderProductId(orderId, orderProductId);
    }

    @Override
    public List<FulfillmentHistory> queryFulfillmentHistoryByOrderProductId(Long orderProductId) {
        return fulfillmentHistoryRepository.queryFulfillmentHistoryByOrderProductId(orderProductId);
    }

}

