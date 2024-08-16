package com.salesmanager.core.business.repositories.fulfillment;

import com.salesmanager.core.model.fulfillment.FulfillmentMainOrder;
import com.salesmanager.core.model.fulfillment.FulfillmentSubOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface FulfillmentMainOrderRepository extends JpaRepository<FulfillmentMainOrder, Long> {


    @Query("select distinct f from FulfillmentMainOrder f " +
            " where f.order.id = ?1 order by f.auditSection.dateCreated")
    FulfillmentMainOrder queryFulfillmentMainOrderByOrderId(Long orderId);


    @Modifying
    @Transactional
    @Query("UPDATE FulfillmentMainOrder p SET p.partialDelivery = :partialDelivery WHERE p.id = :id")
    void updatePartialDelivery(@Param("id") Long id, @Param("partialDelivery") Boolean partialDelivery);

}
