package com.salesmanager.core.business.repositories.fulfillment;

import com.salesmanager.core.model.fulfillment.FulfillmentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface FulfillmentHistoryRepository extends JpaRepository<FulfillmentHistory, Long> {


    @Query("select distinct f from FulfillmentHistory f " +
            " where f.orderId = ?1 order by f.auditSection.dateCreated")
    List<FulfillmentHistory> queryFulfillmentHistoryByOrderId(Long orderId);

    @Query("select distinct f from FulfillmentHistory f " +
            " where f.orderProductId = ?1 order by f.auditSection.dateCreated")
    List<FulfillmentHistory> queryFulfillmentHistoryByOrderProductId(Long orderProductId);

    @Query("select distinct f from FulfillmentHistory f " +
            " where f.orderId = ?1 and f.orderProductId = ?2 order by f.auditSection.dateCreated")
    List<FulfillmentHistory> queryFulfillmentHistoryByOrderIdAndProductId(Long orderId, Long orderProductId);
}
