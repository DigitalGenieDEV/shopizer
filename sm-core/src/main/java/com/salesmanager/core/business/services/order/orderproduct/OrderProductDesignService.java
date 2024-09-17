package com.salesmanager.core.business.services.order.orderproduct;

import com.salesmanager.core.model.order.orderproduct.OrderProductDesign;

public interface OrderProductDesignService {

    Boolean save(OrderProductDesign orderProductDesign);

    OrderProductDesign getByOrderProductId(Long id);

}
