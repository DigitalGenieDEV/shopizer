package com.salesmanager.shop.store.controller.favorites.facade;

import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.entity.ReadableEntityList;
import com.salesmanager.shop.model.favorites.PersistableFavorites;
import com.salesmanager.shop.model.favorites.ReadableFavorites;

import java.util.Optional;

public interface FavoritesFacade {

    ReadableEntityList<ReadableFavorites> getListFavoriteProducts(Long userId, int page, int count, Language language);


    void saveFavoriteProduct(PersistableFavorites persistableFavorites);


    Integer queryFavoriteCountByProductId(Long productId);

    void deleteFavoriteProduct(Long id, Long userId);

}
