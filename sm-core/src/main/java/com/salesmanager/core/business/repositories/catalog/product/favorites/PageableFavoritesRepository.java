package com.salesmanager.core.business.repositories.catalog.product.favorites;

import com.salesmanager.core.business.services.favorites.FavoritesService;
import com.salesmanager.core.model.catalog.product.availability.ProductAvailability;
import com.salesmanager.core.model.favorites.Favorites;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PageableFavoritesRepository extends PagingAndSortingRepository<Favorites, Long> {


	@Query("select f from Favorites f where f.userId = ?1")
	Page<Favorites> findByUserId(Long userId, Pageable pageable);


}
