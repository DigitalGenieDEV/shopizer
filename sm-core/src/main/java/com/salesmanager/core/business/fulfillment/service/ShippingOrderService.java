package com.salesmanager.core.business.fulfillment.service;

import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.fulfillment.ShippingDocumentOrder;
import com.salesmanager.core.model.order.orderproduct.OrderProduct;
import org.springframework.data.domain.Page;

public interface ShippingOrderService extends SalesManagerEntityService<Long, ShippingDocumentOrder> {



    Page<OrderProduct> queryShippingOrderProductList(String productName, Long orderId, int page, int count);
}
