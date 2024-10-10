package com.salesmanager.core.business.repositories.order;

import com.salesmanager.core.model.order.OrderProductSnapshot;
import com.salesmanager.core.model.order.OrderRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface OrderRelationRepository extends JpaRepository<OrderRelation, Long> {

    @Query("select os from OrderRelation os  where os.sourceOrderId = ?1 ")
    List<OrderRelation> findOrderRelationBySourceOrderId(Long sourceOrderId);




}
