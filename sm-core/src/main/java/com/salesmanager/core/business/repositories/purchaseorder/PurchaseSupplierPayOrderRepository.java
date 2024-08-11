package com.salesmanager.core.business.repositories.purchaseorder;

import com.salesmanager.core.model.purchaseorder.PurchaseSupplierPayOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseSupplierPayOrderRepository extends JpaRepository<PurchaseSupplierPayOrder, Long> {
}
