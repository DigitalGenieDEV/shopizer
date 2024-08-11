package com.salesmanager.core.business.repositories.crossorder;

import com.salesmanager.core.model.crossorder.SupplierCrossOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierCrossOrderRepository extends JpaRepository<SupplierCrossOrder, Long> {

    SupplierCrossOrder findByOrderIdStr(String orderIdStr);
}
