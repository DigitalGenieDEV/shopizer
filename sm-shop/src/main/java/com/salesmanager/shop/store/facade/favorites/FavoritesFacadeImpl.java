package com.salesmanager.shop.store.facade.favorites;

import com.google.common.collect.Lists;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.catalog.pricing.PricingService;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.business.services.favorites.FavoritesService;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.attribute.ProductAttribute;
import com.salesmanager.core.model.catalog.product.attribute.ProductOptionValueDescription;
import com.salesmanager.core.model.catalog.product.availability.ProductAvailability;
import com.salesmanager.core.model.catalog.product.description.ProductDescription;
import com.salesmanager.core.model.catalog.product.image.ProductImage;
import com.salesmanager.core.model.catalog.product.manufacturer.ManufacturerDescription;
import com.salesmanager.core.model.catalog.product.price.FinalPrice;
import com.salesmanager.core.model.favorites.Favorites;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.product.attribute.ReadableProductProperty;
import com.salesmanager.shop.model.entity.ReadableEntityList;
import com.salesmanager.shop.model.favorites.PersistableFavorites;
import com.salesmanager.shop.model.favorites.ReadableFavorites;
import com.salesmanager.shop.store.controller.favorites.facade.FavoritesFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FavoritesFacadeImpl implements FavoritesFacade {

    @Autowired
    private FavoritesService favoritesService;

    @Autowired
    private ProductService productService;

    @Autowired
    private PricingService pricingService;



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
            Product productById = productService.getProductWithOnlyMerchantStoreById(favorite.getProductId());
            if (productById!=null){
                Set<ManufacturerDescription> descriptions = productById.getManufacturer().getDescriptions();
                if (!descriptions.isEmpty()) {
                    for (ManufacturerDescription manufacturerDescription : descriptions){
                        if (manufacturerDescription.getLanguage().getCode().equals(language.getCode())){
                            readableFavorites.setManufacturer(manufacturerDescription.getName());
                        }
                    }
                }

                Optional<ProductImage> defaultImage = productById.getImages().stream()
                        .filter(ProductImage::isDefaultImage)
                        .findFirst();
                if (defaultImage.isPresent()) {
                    readableFavorites.setImage(defaultImage.get().getProductImageUrl());
                } else {
                    if (CollectionUtils.isNotEmpty(productById.getImages())){
                        Iterator<ProductImage> iterator = productById.getImages().iterator();
                        if (iterator.hasNext()) {
                            ProductImage next = iterator.next();
                            readableFavorites.setImage(next.getProductImageUrl());
                        }
                    }
                }

                ProductDescription productDescription = productById.getProductDescription();
                readableFavorites.setProductTitle(productDescription.getTitle());
                Set<ProductAttribute> attributes = productById.getAttributes();
                List<String> collect = attributes.stream().map(productAttribute -> {
                    if (productAttribute.getProductOption().getId() == 50L) {
                        Set<ProductOptionValueDescription> optionValues = productAttribute.getProductOptionValue().getDescriptions();
                        if (!optionValues.isEmpty()) {
                            for (ProductOptionValueDescription productOptionValueDescription : optionValues){
                                if (productOptionValueDescription.getLanguage().getCode().equals(language.getCode())){
                                    return productOptionValueDescription.getName();
                                }
                            }
                        }
                    }
                    return null;
                }).filter(Objects::nonNull).collect(Collectors.toList());

                if (!collect.isEmpty()) {
                    readableFavorites.setOrigin(collect.get(0));
                }
                FinalPrice price = pricingService.calculateProductPrice(productById);
                readableFavorites.setPrice(price.getStringPrice());
            }
        } catch (ServiceException e) {

        }
        readableFavorites.setId(favorite.getId());
        readableFavorites.setUserId(favorite.getUserId());
        readableFavorites.setProductId(favorite.getProductId());
        return readableFavorites;
    }
}
