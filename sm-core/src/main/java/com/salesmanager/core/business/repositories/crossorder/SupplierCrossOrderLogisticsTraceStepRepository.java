package com.salesmanager.core.business.repositories.crossorder;

import com.salesmanager.core.model.crossorder.logistics.SupplierCrossOrderLogisticsTraceStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierCrossOrderLogisticsTraceStepRepository extends JpaRepository<SupplierCrossOrderLogisticsTraceStep, Long> {
}
