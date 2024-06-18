package com.salesmanager.shop.store.controller.search.facade;

import java.util.List;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.SearchProductAutocompleteRequestV2;
import com.salesmanager.shop.model.catalog.SearchProductList;
import com.salesmanager.shop.model.catalog.SearchProductRequest;
import com.salesmanager.shop.model.catalog.SearchProductRequestV2;
import com.salesmanager.shop.model.catalog.product.ReadableProduct;
import com.salesmanager.shop.model.entity.ValueList;

import com.salesmanager.shop.model.search.ReadableSearchProduct;
import com.salesmanager.shop.model.search.ReadableSearchResult;
import modules.commons.search.request.SearchItem;
import modules.commons.search.request.SearchResponse;

/**
 * Different services for searching and indexing data
 * @author c.samson
 *
 */
public interface SearchFacade {
	

	/**
	 * This utility method will re-index all products in the catalogue
	 * @param store
	 * @throws Exception
	 */
	public void indexAllData(MerchantStore store) throws Exception;
	
	/**
	 * Produces a search request against elastic search
	 * @param searchRequest
	 * @return
	 * @throws Exception
	 */
	List<SearchItem> search(MerchantStore store, Language language, SearchProductRequest searchRequest);

	/**
	 * Copy sm-core search response to a simple readable format populated with corresponding products
	 * @param searchResponse
	 * @return
	 */
	//public SearchProductList convertToSearchProductList(SearchResponse searchResponse, MerchantStore store, int start, int count, Language language) throws Exception;

	/**
	 * List of keywords / autocompletes for a given word being typed
	 * @param query
	 * @param store
	 * @param language
	 * @return
	 * @throws Exception
	 */
	ValueList autocompleteRequest(String query, MerchantStore store, Language language);



	ReadableSearchResult searchV2(SearchProductRequestV2 searchProductRequestV2, MerchantStore store, Language language) throws ConversionException, ServiceException;

	ValueList autoCompleteRequestV2(SearchProductAutocompleteRequestV2 searchProductAutocompleteRequestV2, Language language);
}
