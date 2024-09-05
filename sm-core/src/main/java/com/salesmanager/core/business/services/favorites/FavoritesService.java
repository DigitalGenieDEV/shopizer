package com.salesmanager.core.business.services.favorites;

import com.salesmanager.core.model.favorites.Favorites;
import org.springframework.data.domain.Page;

public interface FavoritesService {

   Page<Favorites> getListFavoriteProducts(Long userId, int page, int count);

    void saveFavoriteProduct(Favorites favorite);


    void deleteFavoriteProduct(Long productId, Long userId);


    Integer queryFavoriteCountByProductId(Long productId);


    Integer findCountByUserIdAndProductId(Long userId, Long productId);
}
