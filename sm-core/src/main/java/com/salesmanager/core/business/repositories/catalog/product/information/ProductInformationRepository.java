package com.salesmanager.core.business.repositories.catalog.product.information;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salesmanager.core.model.catalog.product.information.ProductInformation;

public interface ProductInformationRepository extends JpaRepository<ProductInformation, Long>{

}
