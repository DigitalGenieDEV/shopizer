package com.salesmanager.core.business.services.order.orderproduct;

import com.salesmanager.core.model.order.OrderProductSnapshot;

import java.util.List;

public interface OrderProductSnapshotService {


    void saveOrderProduct(OrderProductSnapshot orderProductSnapshot);


    void saveOrderProductList(List<OrderProductSnapshot> orderProductSnapshot);

    OrderProductSnapshot findSnapshotByOrderProductId(Long orderProductId);
}
