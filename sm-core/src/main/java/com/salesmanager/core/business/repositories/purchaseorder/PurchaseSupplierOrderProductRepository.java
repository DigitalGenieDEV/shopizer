package com.salesmanager.core.business.repositories.purchaseorder;

import com.salesmanager.core.model.purchaseorder.PurchaseSupplierOrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseSupplierOrderProductRepository extends JpaRepository<PurchaseSupplierOrderProduct, Long> {

    @Query("SELECT p FROM PurchaseSupplierOrderProduct p WHERE p.orderProduct.id = :orderProductId")
    PurchaseSupplierOrderProduct findByOrderProductId(@Param("orderProductId") Long orderProductId);
}
