package com.salesmanager.core.business.repositories.crossorder;

import com.salesmanager.core.model.crossorder.SupplierCrossOrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierCrossOrderProductRepository extends JpaRepository<SupplierCrossOrderProduct, Long> {
}
