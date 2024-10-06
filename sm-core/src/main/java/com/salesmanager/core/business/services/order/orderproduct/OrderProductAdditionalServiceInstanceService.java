package com.salesmanager.core.business.services.order.orderproduct;

import com.salesmanager.core.model.order.orderproduct.OrderProductAdditionalServiceInstance;

import java.util.List;

public interface OrderProductAdditionalServiceInstanceService {

    OrderProductAdditionalServiceInstance getById(Long id);

    List<OrderProductAdditionalServiceInstance> listByOrderProductId(Long id);

    Boolean save(OrderProductAdditionalServiceInstance orderProductAdditionalServiceInstance);

    OrderProductAdditionalServiceInstance queryByOrderProductIdAndAdditionalServiceId(Long orderProductId, Long additionalServiceId);
    Boolean updateStatus(OrderProductAdditionalServiceInstance orderProductAdditionalServiceInstance);

}
