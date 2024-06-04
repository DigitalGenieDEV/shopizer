package com.salesmanager.shop.store.facade.favorites;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.business.services.favorites.FavoritesService;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.description.ProductDescription;
import com.salesmanager.core.model.catalog.product.image.ProductImage;
import com.salesmanager.core.model.catalog.product.manufacturer.ManufacturerDescription;
import com.salesmanager.core.model.favorites.Favorites;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.product.attribute.ReadableProductProperty;
import com.salesmanager.shop.model.entity.ReadableEntityList;
import com.salesmanager.shop.model.favorites.PersistableFavorites;
import com.salesmanager.shop.model.favorites.ReadableFavorites;
import com.salesmanager.shop.store.controller.favorites.facade.FavoritesFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FavoritesFacadeImpl implements FavoritesFacade {

    @Autowired
    private FavoritesService favoritesService;

    @Autowired
    private ProductService productService;



    @Override
    public ReadableEntityList<ReadableFavorites> getListFavoriteProducts(Long userId, int page, int count, Language language) {
        Page<Favorites> listFavoriteProducts = favoritesService.getListFavoriteProducts(userId, page, count);

        List<ReadableFavorites> readableFavorites = listFavoriteProducts.stream()
                .map(favorite-> convertToReadableFavorites(favorite, language))
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

    private ReadableFavorites convertToReadableFavorites(Favorites favorite, Language language) {
        ReadableFavorites readableFavorites = new ReadableFavorites();
        try {
            Product productById = productService.getProductById(favorite.getProductId(), language);
            if (productById!=null){
                Set<ManufacturerDescription> descriptions = productById.getManufacturer().getDescriptions();
                for (ManufacturerDescription manufacturerDescription :  descriptions){
                    if (manufacturerDescription.getLanguage().getCode().equals(language.getCode())){
                        readableFavorites.setManufacturer(manufacturerDescription.getTitle());
                    }
                }
                readableFavorites.setImage(productById.getImages().stream().filter(ProductImage::isDefaultImage).findFirst().orElse(null).getProductImage());
                ProductDescription productDescription = productById.getProductDescription();
                readableFavorites.setProductTitle(productDescription.getTitle());
            }
        } catch (ServiceException e) {

        }
        readableFavorites.setId(favorite.getId());
        readableFavorites.setUserId(favorite.getUserId());
        readableFavorites.setProductId(favorite.getProductId());
        return readableFavorites;
    }
}
