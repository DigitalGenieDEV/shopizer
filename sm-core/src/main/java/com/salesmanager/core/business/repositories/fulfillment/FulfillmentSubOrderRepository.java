package com.salesmanager.core.business.repositories.fulfillment;


import com.salesmanager.core.model.fulfillment.AdditionalServices;
import com.salesmanager.core.model.fulfillment.FulfillmentSubOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FulfillmentSubOrderRepository extends JpaRepository<FulfillmentSubOrder, Long> {


    @Query("select distinct f from FulfillmentSubOrder f " +
            " where f.orderId = ?1 order by f.auditSection.dateCreated")
    List<FulfillmentSubOrder> queryFulfillmentSubOrderListByOrderId(Long orderId);


    @Modifying
    @Query("update FulfillmentSubOrder fl set fl.orderId = ?1 , fl.fulfillmentMainOrder.id = ?2 where fl.orderProductId = ?3")
    void updateFulfillmentSubOrderIdAndFulfillmentMainIdByOrderProductId(Long orderId, Long fulfillmentMainOrderId, Long orderProductId);

    @Query("select distinct f from FulfillmentSubOrder f " +
            " where f.orderProductId = ?1 order by f.auditSection.dateCreated")
    FulfillmentSubOrder queryFulfillmentSubOrderByProductOrderProductId(Long productOrderId);
}
