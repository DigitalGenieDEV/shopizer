package com.salesmanager.core.business.fulfillment.service;

import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.fulfillment.FulfillmentMainOrder;
import com.salesmanager.core.model.order.Order;


public interface FulfillmentMainOrderService extends SalesManagerEntityService<Long, FulfillmentMainOrder> {


    Long saveFulfillmentMainOrder(FulfillmentMainOrder fulfillmentMainOrder);

    void createFulfillmentOrderByOrder(Order order);
}

