package com.salesmanager.core.business.services.order.orderproduct;

import com.salesmanager.core.model.order.orderproduct.OrderProductAdditionalServiceInstance;

import java.util.List;

public interface OrderProductAdditionalServiceInstanceService {

    OrderProductAdditionalServiceInstance getById(Long id);

    List<OrderProductAdditionalServiceInstance> listByOrderProductId(Long id);

    Boolean save(OrderProductAdditionalServiceInstance orderProductAdditionalServiceInstance);

    Boolean updateStatus(OrderProductAdditionalServiceInstance orderProductAdditionalServiceInstance);

}
