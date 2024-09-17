package com.salesmanager.core.business.repositories.order.orderproduct;

import com.salesmanager.core.model.order.orderproduct.OrderProductDesign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductDesignRepository extends JpaRepository<OrderProductDesign, Long> {

    OrderProductDesign getByOrderProductId(Long orderProductId);
}
