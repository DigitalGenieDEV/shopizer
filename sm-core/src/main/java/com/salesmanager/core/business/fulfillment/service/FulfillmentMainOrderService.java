package com.salesmanager.core.business.fulfillment.service;

import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.fulfillment.FulfillmentMainOrder;
import com.salesmanager.core.model.fulfillment.FulfillmentSubOrder;
import com.salesmanager.core.model.order.Order;


public interface FulfillmentMainOrderService extends SalesManagerEntityService<Long, FulfillmentMainOrder> {


    Long saveFulfillmentMainOrder(FulfillmentMainOrder fulfillmentMainOrder);

    void createFulfillmentOrderByOrder(Order order);

    FulfillmentMainOrder queryFulfillmentMainOrderByOrderId(Long orderId);


    FulfillmentMainOrder onlyCreateFulfillmentMainOrder(Order order, Boolean partialDelivery);

    void updatePartialDelivery(Long id, Boolean partialDelivery);
}

