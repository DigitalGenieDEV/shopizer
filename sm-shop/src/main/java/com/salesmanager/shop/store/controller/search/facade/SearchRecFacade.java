package com.salesmanager.shop.store.controller.search.facade;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.SearchRecGuessULikeRequest;
import com.salesmanager.shop.model.catalog.SearchRecRelateItemRequest;
import com.salesmanager.shop.model.catalog.SearchRecSelectionRequest;
import com.salesmanager.shop.model.catalog.product.ReadableProduct;

import java.util.List;

public interface SearchRecFacade {

    List<ReadableProduct> searchRecGuessULike(SearchRecGuessULikeRequest request, Language language) throws ConversionException;

    List<ReadableProduct> searchRecRelateItem(SearchRecRelateItemRequest request, Language language) throws ConversionException;

    List<ReadableProduct> searchRecSelectionItem(SearchRecSelectionRequest request, Language language) throws ConversionException;
}
