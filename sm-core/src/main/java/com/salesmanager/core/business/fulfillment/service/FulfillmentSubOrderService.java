package com.salesmanager.core.business.fulfillment.service;


import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.fulfillment.FulfillmentSubOrder;

import java.util.List;

public interface FulfillmentSubOrderService extends SalesManagerEntityService<Long, FulfillmentSubOrder> {



    Long saveFulfillmentMainOrder(FulfillmentSubOrder fulfillmentSubOrder);


    void updateFulfillmentMainOrder(FulfillmentSubOrder fulfillmentSubOrder);


    List<FulfillmentSubOrder> queryFulfillmentSubOrderListByOrderId(Long orderId);

    FulfillmentSubOrder queryFulfillmentSubOrderByOrderProductId(Long productOrderId);




}

