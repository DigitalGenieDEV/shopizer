package com.salesmanager.core.business.repositories.catalog.product.favorites;

import com.salesmanager.core.model.catalog.product.availability.ProductAvailability;
import com.salesmanager.core.model.favorites.Favorites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FavoritesRepository extends JpaRepository<Favorites, Long> {


	@Query("select count(f) from Favorites f where f.productId = ?1")
	Integer countByProductId(Long productId);

	@Query("select f from Favorites f where f.userId = ?1 and f.productId = ?2")
	Favorites findByUserIdAndProductId(Long userId, Long productId);



	@Modifying
	@Transactional
	@Query("delete from Favorites f where f.userId = :userId and f.productId = :productId")
	void deleteByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);

}
