package com.salesmanager.core.business.repositories.purchaseorder;

import com.salesmanager.core.model.purchaseorder.PurchaseSupplierOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseSupplierOrderRepository extends JpaRepository<PurchaseSupplierOrder, Long> {
}
