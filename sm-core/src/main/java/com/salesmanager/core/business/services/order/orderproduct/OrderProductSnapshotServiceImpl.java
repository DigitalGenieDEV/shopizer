package com.salesmanager.core.business.services.order.orderproduct;

import com.salesmanager.core.business.repositories.order.OrderProductSnapshotRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.order.OrderProductSnapshot;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderProductSnapshotServiceImpl extends SalesManagerEntityServiceImpl<Long, OrderProductSnapshot> implements OrderProductSnapshotService{

    private final OrderProductSnapshotRepository orderProductSnapshotRepository;

    public OrderProductSnapshotServiceImpl(OrderProductSnapshotRepository orderProductSnapshotRepository) {
        super(orderProductSnapshotRepository);
        this.orderProductSnapshotRepository = orderProductSnapshotRepository;
    }


    @Override
    public void saveOrderProduct(OrderProductSnapshot orderProductSnapshot) {
        if (orderProductSnapshot.getId() !=null && orderProductSnapshot.getId() == 0L){
            orderProductSnapshot.setId(null);
        }
        orderProductSnapshotRepository.save(orderProductSnapshot);
    }


    @Override
    public void saveOrderProductList(List<OrderProductSnapshot> orderProductSnapshot) {
        orderProductSnapshotRepository.saveAll(orderProductSnapshot);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderProductSnapshot findSnapshotByOrderProductId(Long orderProductId) {
        OrderProductSnapshot snapshotByOrderProductId = orderProductSnapshotRepository.findSnapshotByOrderProductId(orderProductId);
        return snapshotByOrderProductId;
    }



}
