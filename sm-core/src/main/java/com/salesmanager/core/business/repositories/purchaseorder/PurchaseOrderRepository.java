package com.salesmanager.core.business.repositories.purchaseorder;

import com.salesmanager.core.model.purchaseorder.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
}
