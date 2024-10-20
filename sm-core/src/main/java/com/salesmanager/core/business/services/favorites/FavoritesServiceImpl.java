package com.salesmanager.core.business.services.favorites;

import com.salesmanager.core.business.repositories.catalog.product.availability.PageableProductAvailabilityRepository;
import com.salesmanager.core.business.repositories.catalog.product.availability.ProductAvailabilityRepository;
import com.salesmanager.core.business.repositories.catalog.product.favorites.FavoritesRepository;
import com.salesmanager.core.business.repositories.catalog.product.favorites.PageableFavoritesRepository;
import com.salesmanager.core.business.services.catalog.product.availability.ProductAvailabilityService;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.catalog.product.availability.ProductAvailability;
import com.salesmanager.core.model.favorites.Favorites;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class FavoritesServiceImpl  extends SalesManagerEntityServiceImpl<Long, Favorites>
        implements FavoritesService {
    @Inject
    private PageableFavoritesRepository pageableFavoritesRepository;

    private FavoritesRepository favoritesRepository;

    @Inject
    public FavoritesServiceImpl(FavoritesRepository favoritesRepository) {
        super(favoritesRepository);
        this.favoritesRepository = favoritesRepository;
    }

    @Override
    public Page<Favorites> getListFavoriteProducts(Long userId, int page, int count) {
        Pageable pageRequest = PageRequest.of(page, count);
        if (userId == null){
            return pageableFavoritesRepository.findPage(pageRequest);
        }
        return pageableFavoritesRepository.findByUserId(userId, pageRequest);
    }

    @Override
    public void saveFavoriteProduct(Favorites favorite) {
        favoritesRepository.save(favorite);
    }

    @Override
    public void deleteFavoriteProduct(Long productId, Long userId) {
        favoritesRepository.deleteByUserIdAndProductId(userId, productId);
    }

    @Override
    public Integer queryFavoriteCountByProductId(Long productId) {
        return favoritesRepository.countByProductId(productId);
    }

    @Override
    public Integer findCountByUserIdAndProductId(Long userId, Long productId) {
        return favoritesRepository.findCountByUserIdAndProductId(userId, productId);
    }
}
