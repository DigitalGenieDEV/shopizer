package com.salesmanager.core.business.services.search;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.search.*;

import java.util.List;

public interface SearchProductService {

    SearchProductResult search(SearchRequest request) throws ServiceException;

    AutocompleteResult autocomplete(AutocompleteRequest request);
}
