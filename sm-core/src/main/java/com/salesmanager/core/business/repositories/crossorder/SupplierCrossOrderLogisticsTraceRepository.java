package com.salesmanager.core.business.repositories.crossorder;

import com.salesmanager.core.model.crossorder.logistics.SupplierCrossOrderLogisticsTrace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierCrossOrderLogisticsTraceRepository extends JpaRepository<SupplierCrossOrderLogisticsTrace, Long> {

    List<SupplierCrossOrderLogisticsTrace> findByLogisticsId(String logisticsId);

    @Query("SELECT sclt FROM SupplierCrossOrderLogisticsTrace sclt " +
            "JOIN sclt.supplierCrossOrder sco " +
            "JOIN sco.products scp " +
            "JOIN scp.psoOrderProduct scpo " +
            "WHERE scpo.orderProduct.id = :orderProductId")
    List<SupplierCrossOrderLogisticsTrace> findLogisticsTraceByOrderProductId(@Param("orderProductId") Long orderProductId);
}
