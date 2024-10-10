package com.salesmanager.core.business.repositories.order;

import com.salesmanager.core.model.order.OrderProductSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface OrderProductSnapshotRepository extends JpaRepository<OrderProductSnapshot, Long> {

    @Query("select os from OrderProductSnapshot os  where os.orderProductId = ?1 ")
    OrderProductSnapshot findSnapshotByOrderProductId(Long orderProductId);

}
