package com.salesmanager.shop.store.controller.recommend.facade;

import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.common.ReadablePaginationResult;
import com.salesmanager.shop.model.recommend.*;

public interface RecProductFacade {

    ReadableRecProductList getRecGuessULike(RecGuessULikeRequest request, Language language) throws Exception;

    ReadableRecProductList getRecRelateItem(RecRelateItemRequest request, Language language) throws Exception;

    ReadableRecProductList getRecSelectionProduct(RecSelectionProductRequest request, Language language) throws Exception;

    ReadableRecProductList getRecentView(RecFootPrintRequest request, Language language) throws Exception;
}
