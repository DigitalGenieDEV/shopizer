package com.salesmanager.core.business.repositories.order;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salesmanager.core.model.order.OrderTotal;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

public interface OrderTotalRepository extends JpaRepository<OrderTotal, Long> {


    @Modifying
    @Transactional
    @Query("UPDATE OrderTotal c SET c.value = :value WHERE c.order.id = :orderId and c.module = :module")
    void updateValueByOrderIdAndModule(@Param("value") BigDecimal value, @Param("orderId") Long orderId
            , @Param("module") String module);
}
