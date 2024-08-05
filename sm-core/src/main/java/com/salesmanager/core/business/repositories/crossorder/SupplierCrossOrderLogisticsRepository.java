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

    @Query("SELECT s FROM SupplierCrossOrderLogistics s WHERE :orderId MEMBER OF s.orderEntryIds")
    List<SupplierCrossOrderLogistics> findByOrderEntryIdContaining(@Param("orderId") String orderId);
}
