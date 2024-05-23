package com.salesmanager.shop.store.facade.favorites;

import com.salesmanager.core.business.services.favorites.FavoritesService;
import com.salesmanager.core.model.favorites.Favorites;
import com.salesmanager.shop.model.entity.ReadableEntityList;
import com.salesmanager.shop.model.favorites.PersistableFavorites;
import com.salesmanager.shop.model.favorites.ReadableFavorites;
import com.salesmanager.shop.store.controller.favorites.facade.FavoritesFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoritesFacadeImpl implements FavoritesFacade {

    @Autowired
    private FavoritesService favoritesService;



    @Override
    public ReadableEntityList<ReadableFavorites> getListFavoriteProducts(Long userId, int page, int count) {
        Page<Favorites> listFavoriteProducts = favoritesService.getListFavoriteProducts(userId, page, count);

        List<ReadableFavorites> readableFavorites = listFavoriteProducts.stream()
                .map(this::convertToReadableFavorites)
                .collect(Collectors.toList());

        ReadableEntityList<ReadableFavorites> readableEntityList = new ReadableEntityList<>();
        readableEntityList.setItems(readableFavorites);
        readableEntityList.setRecordsTotal(listFavoriteProducts.getTotalElements());

        return readableEntityList;
    }

    @Override
    public void saveFavoriteProduct(PersistableFavorites persistableFavorites) {
        Favorites favorite = new Favorites();
        favorite.setUserId(persistableFavorites.getUserId());
        favorite.setProductId(persistableFavorites.getProductId());
        favoritesService.saveFavoriteProduct(favorite);
    }

    @Override
    public Integer queryFavoriteCountByProductId(Long productId) {
        return favoritesService.queryFavoriteCountByProductId(productId);
    }

    @Override
    public void deleteFavoriteProduct(Long productId, Long userId) {
        favoritesService.deleteFavoriteProduct(productId, userId);
    }

    private ReadableFavorites convertToReadableFavorites(Favorites favorite) {
        ReadableFavorites readableFavorites = new ReadableFavorites();
        readableFavorites.setId(favorite.getId());
        readableFavorites.setUserId(favorite.getUserId());
        readableFavorites.setProductId(favorite.getProductId());
        return readableFavorites;
    }
}
