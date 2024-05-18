package com.salesmanager.core.business.services.search;

import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.recommend.GuessULikeRequest;
import com.salesmanager.core.model.catalog.product.recommend.RelateItemRequest;
import com.salesmanager.core.model.catalog.product.recommend.SelectionItemRequest;

import java.util.List;

public interface RecProductService {

    List<Product> guessULike(GuessULikeRequest request);

    List<Product> relateItem(RelateItemRequest request);

    List<Product> selectionItem(SelectionItemRequest request);
}
