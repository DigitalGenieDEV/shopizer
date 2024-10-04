package com.salesmanager.core.business.repositories.order.orderproduct;

import com.salesmanager.core.enmus.AdditionalServiceInstanceStatusEnums;
import com.salesmanager.core.model.order.orderproduct.OrderProductAdditionalServiceInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrderProductAdditionalServiceInstanceRepository extends JpaRepository<OrderProductAdditionalServiceInstance, Long> {

    @Query("select o from OrderProductAdditionalServiceInstance o where o.orderProductId = ?1")
    List<OrderProductAdditionalServiceInstance> queryByOrderProductId(Long orderProductId);

    @Modifying
    @Transactional
    @Query("UPDATE OrderProductAdditionalServiceInstance o set o.status = :status where o.id = :id")
    Integer updateStatusById(@Param("id") Long id , @Param("status")AdditionalServiceInstanceStatusEnums status);



    @Query("select o from OrderProductAdditionalServiceInstance o where o.orderProductId = ?1 and o.additionalServiceId = ?2 ")
    OrderProductAdditionalServiceInstance queryByOrderProductIdAndAdditionalServiceId(Long orderProductId, Long additionalServiceId);
}
