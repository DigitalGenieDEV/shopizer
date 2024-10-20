package com.salesmanager.core.business.repositories.catalog.product.variant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.salesmanager.core.model.catalog.product.variant.ProductVariantGroup;

public interface PageableProductVariantGroupRepository extends PagingAndSortingRepository<ProductVariantGroup, Long> {

	
	
	
	@Query(value = "select p from ProductVariantGroup p " 
			+ "join fetch p.productVariants pi " 
			+ "join fetch pi.product pip "
			+ "where pip.id = ?1 ",
			countQuery = "select p from ProductVariantGroup p " + 
			"join fetch p.productVariants pi "
					+ "join fetch pi.product pip " +
			"where pip.id = ?1")
	Page<ProductVariantGroup> findByProductId( Long productId, Pageable pageable);
	
}
