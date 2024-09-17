package com.salesmanager.core.business.fulfillment.service;

import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.fulfillment.FulfillmentHistory;
import com.salesmanager.core.model.fulfillment.FulfillmentMainOrder;
import com.salesmanager.core.model.order.Order;

import java.util.List;


public interface FulfillmentHistoryService extends SalesManagerEntityService<Long, FulfillmentHistory> {


    Long saveFulfillmentHistory(FulfillmentHistory fulfillmentHistory);


    Long saveFulfillmentHistory(Long orderId, Long orderProductId, String status, String previousStatus);

    List<FulfillmentHistory> queryFulfillmentHistoryByOrderId(Long orderId);


    List<FulfillmentHistory> queryFulfillmentHistoryByOrderIdAndProductId(Long orderId, Long orderProductId);


    List< FulfillmentHistory> queryFulfillmentHistoryByOrderProductId(Long orderProductId);
}

