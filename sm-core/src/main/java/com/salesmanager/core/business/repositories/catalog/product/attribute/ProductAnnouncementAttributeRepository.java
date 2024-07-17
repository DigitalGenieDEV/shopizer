package com.salesmanager.core.business.repositories.catalog.product.attribute;

import com.salesmanager.core.model.catalog.product.attribute.ProductAnnouncementAttribute;
import com.salesmanager.core.model.catalog.product.attribute.ProductAnnouncementAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductAnnouncementAttributeRepository extends JpaRepository<ProductAnnouncementAttribute, Long> {

	@Query("select p from ProductAnnouncementAttribute p  where p.id = ?1")
	ProductAnnouncementAttribute findOne(Long id);

	@Query("select COUNT(p) > 0 from ProductAnnouncementAttribute p " +
			"where p.productId = ?1")
	boolean existsByProductId(Long productId);

	@Query("select p from ProductAnnouncementAttribute p where p.id in ?1")
	List<ProductAnnouncementAttribute> findByIds(List<Long> ids);
	

	@Query("select distinct p from ProductAnnouncementAttribute p  " +
			" where p.productId = ?1")
	List<ProductAnnouncementAttribute> findByProductId(Long productId);

	@Modifying
	@Transactional
	@Query("DELETE FROM ProductAnnouncementAttribute p WHERE p.productId = ?1")
	void deleteByProductId(Long id);



}

