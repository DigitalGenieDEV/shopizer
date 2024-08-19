package com.salesmanager.core.business.repositories.fulfillment;


import com.salesmanager.core.model.fulfillment.AdditionalServices;
import com.salesmanager.core.model.fulfillment.FulfillmentSubOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FulfillmentSubOrderRepository extends JpaRepository<FulfillmentSubOrder, Long> {


    @Query("select distinct f from FulfillmentSubOrder f " +
            " where f.orderId = ?1 order by f.auditSection.dateCreated")
    List<FulfillmentSubOrder> queryFulfillmentSubOrderListByOrderId(Long orderId);



    @Query("select distinct f from FulfillmentSubOrder f " +
            " where f.orderProductId = ?1 order by f.auditSection.dateCreated")
    FulfillmentSubOrder queryFulfillmentSubOrderByProductOrderId(Long productOrderId);
}
