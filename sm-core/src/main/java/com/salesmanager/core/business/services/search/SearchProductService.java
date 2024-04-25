package com.salesmanager.core.business.services.search;

import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.search.ProductAutocompleteRequest;
import com.salesmanager.core.model.catalog.product.search.ProductAutocompleteResult;
import com.salesmanager.core.model.catalog.product.search.ProductSearchRequest;

import java.util.List;

public interface SearchProductService {

    List<Product> search(ProductSearchRequest request);

    ProductAutocompleteResult autocomplete(ProductAutocompleteRequest request);
}
