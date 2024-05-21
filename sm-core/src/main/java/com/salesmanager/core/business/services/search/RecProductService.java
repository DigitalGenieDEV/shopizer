package com.salesmanager.core.business.services.search;

import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.recommend.*;

import java.util.List;

public interface RecProductService {

    GuessULikeResult guessULike(GuessULikeRequest request);

    RelateItemResult relateItem(RelateItemRequest request);

    SelectionItemResult selectionItem(SelectionItemRequest request);
}
