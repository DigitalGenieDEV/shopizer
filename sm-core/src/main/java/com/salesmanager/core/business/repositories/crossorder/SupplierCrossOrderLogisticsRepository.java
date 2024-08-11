package com.salesmanager.core.business.repositories.crossorder;

import com.salesmanager.core.model.crossorder.logistics.SupplierCrossOrderLogistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierCrossOrderLogisticsRepository extends JpaRepository<SupplierCrossOrderLogistics, Long> {

    SupplierCrossOrderLogistics findByLogisticsId(String logisticsId);

    SupplierCrossOrderLogistics findByLogisticsBillNo(String logisticsBillNo);


    @Query("SELECT scl FROM SupplierCrossOrderLogistics scl " +
            "JOIN scl.supplierCrossOrders sco " +
            "JOIN sco.products scp " +
            "JOIN scp.psoOrderProduct scpo " +
            "WHERE scpo.orderProduct.id = :orderProductId")
    List<SupplierCrossOrderLogistics> findLogisticsByOrderProductId(@Param("orderProductId") Long orderProductId);

//    @Query("SELECT s FROM SupplierCrossOrderLogistics s WHERE :orderId MEMBER OF s.orderEntryIds")
//    List<SupplierCrossOrderLogistics> findByOrderEntryIdContaining(@Param("orderId") String orderId);
}
