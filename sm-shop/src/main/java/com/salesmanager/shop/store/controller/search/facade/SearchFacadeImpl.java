package com.salesmanager.shop.store.controller.search.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.salesmanager.core.business.services.search.SearchProductService;
import com.salesmanager.core.model.catalog.product.search.AutocompleteRequest;
import com.salesmanager.core.model.catalog.product.search.AutocompleteResult;
import com.salesmanager.core.model.catalog.product.search.SearchProductResult;
import com.salesmanager.core.model.catalog.product.search.SearchRequest;
import com.salesmanager.shop.model.catalog.SearchProductAutocompleteRequestV2;
import com.salesmanager.shop.model.catalog.SearchProductRequestV2;
import com.salesmanager.shop.model.search.ReadableSearchProductV2;
import com.salesmanager.shop.model.search.ReadableSearchResult;
import com.salesmanager.shop.populator.search.ReadableSearchProductV2Populator;
import com.salesmanager.shop.populator.store.ReadableMerchantStorePopulator;
import org.jsoup.helper.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.catalog.category.CategoryService;
import com.salesmanager.core.business.services.catalog.pricing.PricingService;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.business.services.search.SearchService;
import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.SearchProductRequest;
import com.salesmanager.shop.model.catalog.category.ReadableCategory;
import com.salesmanager.shop.model.catalog.product.ReadableProduct;
import com.salesmanager.shop.model.entity.ValueList;
import com.salesmanager.shop.populator.catalog.ReadableCategoryPopulator;
import com.salesmanager.shop.populator.catalog.ReadableProductPopulator;
import com.salesmanager.shop.store.api.exception.ConversionRuntimeException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.utils.ImageFilePath;

import modules.commons.search.request.Aggregation;
import modules.commons.search.request.SearchItem;
import modules.commons.search.request.SearchResponse;

@Service("searchFacade")
public class SearchFacadeImpl implements SearchFacade {

	private static final Logger LOGGER = LoggerFactory.getLogger(SearchFacadeImpl.class);

	@Inject
	private SearchService searchService;

	@Inject
	private ProductService productService;

	@Inject
	private CategoryService categoryService;

	@Inject
	private PricingService pricingService;

	@Autowired
	private ReadableMerchantStorePopulator readableMerchantStorePopulator;

	@Inject
	private SearchProductService searchProductService;

	@Inject
	@Qualifier("img")
	private ImageFilePath imageUtils;

	private final static String CATEGORY_FACET_NAME = "categories";
	private final static String MANUFACTURER_FACET_NAME = "manufacturer";
	private final static int AUTOCOMPLETE_ENTRIES_COUNT = 15;

	/**
	 * Index all products from the catalogue Better stop the system, remove ES
	 * indexex manually restart ES and run this query
	 */
	@Override
	@Async
	public void indexAllData(MerchantStore store) throws Exception {
		List<Product> products = productService.listByStore(store);

		products.stream().forEach(p -> {
			try {
				searchService.index(store, p);
			} catch (ServiceException e) {
				throw new RuntimeException("Exception while indexing products", e);
			}
		});

	}

	@Override
	public List<SearchItem> search(MerchantStore store, Language language, SearchProductRequest searchRequest) {
		SearchResponse response = search(store, language.getCode(), searchRequest.getQuery(), searchRequest.getCount(),
				searchRequest.getStart());
		return response.getItems();
	}

	private SearchResponse search(MerchantStore store, String languageCode, String query, Integer count,
			Integer start) {
		
		Validate.notNull(query,"Search Keyword must not be null");
		Validate.notNull(languageCode, "Language cannot be null");
		Validate.notNull(store,"MerchantStore cannot be null");
		
		
		try {
			LOGGER.debug("Search " + query);
			modules.commons.search.request.SearchRequest searchRequest = new modules.commons.search.request.SearchRequest();
			searchRequest.setLanguage(languageCode);
			searchRequest.setSearchString(query);
			searchRequest.setStore(store.getCode().toLowerCase());
			
			
			//aggregations
			
			//TODO add scroll
			return searchService.search(store, languageCode, searchRequest, count, start);

		} catch (ServiceException e) {
			throw new ServiceRuntimeException(e);
		}
	}

	private List<ReadableCategory> getCategoryFacets(MerchantStore merchantStore, Language language, List<Aggregation> facets) {
		
		
		/**
		List<SearchFacet> categoriesFacets = facets.entrySet().stream()
				.filter(e -> CATEGORY_FACET_NAME.equals(e.getKey())).findFirst().map(Entry::getValue)
				.orElse(Collections.emptyList());

		if (CollectionUtils.isNotEmpty(categoriesFacets)) {

			List<String> categoryCodes = categoriesFacets.stream().map(SearchFacet::getName)
					.collect(Collectors.toList());

			Map<String, Long> productCategoryCount = categoriesFacets.stream()
					.collect(Collectors.toMap(SearchFacet::getKey, SearchFacet::getCount));

			List<Category> categories = categoryService.listByCodes(merchantStore, categoryCodes, language);
			return categories.stream().map(category -> convertCategoryToReadableCategory(merchantStore, language,
					productCategoryCount, category)).collect(Collectors.toList());
		} else {
			return Collections.emptyList();
		}
		**/
		
		return null;
	}

	private ReadableCategory convertCategoryToReadableCategory(MerchantStore merchantStore, Language language,
			Map<String, Long> productCategoryCount, Category category) {
		ReadableCategoryPopulator populator = new ReadableCategoryPopulator();
		try {
			ReadableCategory categoryProxy = populator.populate(category, new ReadableCategory(), merchantStore,
					language);
			Long total = productCategoryCount.get(categoryProxy.getCode());
			if (total != null) {
				categoryProxy.setProductCount(total.intValue());
			}
			return categoryProxy;
		} catch (ConversionException e) {
			throw new ConversionRuntimeException(e);
		}
	}

	private ReadableProduct convertProductToReadableProduct(Product product, MerchantStore merchantStore,
			Language language) {

		ReadableProductPopulator populator = new ReadableProductPopulator();
		populator.setPricingService(pricingService);
		populator.setimageUtils(imageUtils);

		try {
			return populator.populate(product, new ReadableProduct(), merchantStore, language);
		} catch (ConversionException e) {
			throw new ConversionRuntimeException(e);
		}
	}

	@Override
	public ValueList autocompleteRequest(String word, MerchantStore store, Language language) {
		Validate.notNull(word,"Search Keyword must not be null");
		Validate.notNull(language, "Language cannot be null");
		Validate.notNull(store,"MerchantStore cannot be null");
		
		modules.commons.search.request.SearchRequest req = new modules.commons.search.request.SearchRequest();
		req.setLanguage(language.getCode());
		req.setStore(store.getCode().toLowerCase());
		req.setSearchString(word);
		req.setLanguage(language.getCode());
		
		SearchResponse response;
		try {
			response = searchService.searchKeywords(store, language.getCode(), req, AUTOCOMPLETE_ENTRIES_COUNT);
		} catch (ServiceException e) {
			throw new RuntimeException(e);
		}
	
		
		List<String> keywords = response.getItems().stream().map(i -> i.getSuggestions()).collect(Collectors.toList());
		
		ValueList valueList = new ValueList();
		valueList.setValues(keywords);
		
		return valueList;
		

	}

	@Override
	public ReadableSearchResult searchV2(SearchProductRequestV2 searchProductRequestV2, Language language) throws ConversionException {
		SearchRequest searchProductRequest = new SearchRequest();
		searchProductRequest.setLang(searchProductRequestV2.getLang());
		searchProductRequest.setSize(searchProductRequestV2.getSize());
		searchProductRequest.setQ(searchProductRequestV2.getQ());
		searchProductRequest.setPageIdx(searchProductRequestV2.getPageIdx());
		searchProductRequest.setAttrFilt(searchProductRequestV2.getAttrFilt());
		searchProductRequest.setUid(searchProductRequestV2.getUid());
		searchProductRequest.setDeviceid(searchProductRequestV2.getDeviceid());
		SearchProductResult searchProductResult = searchProductService.search(searchProductRequest);

		ReadableSearchProductV2Populator populator = new ReadableSearchProductV2Populator();
		populator.setPricingService(pricingService);
		populator.setimageUtils(imageUtils);
		populator.setReadableMerchantStorePopulator(readableMerchantStorePopulator);

//		ReadableProductList productList = new ReadableProductList();
		ReadableSearchResult result = new ReadableSearchResult();
		for(Product product : searchProductResult.getProductList()) {
			//create new proxy product
			ReadableSearchProductV2 readProduct = populator.populate(product, new ReadableSearchProductV2(), product.getMerchantStore(), language);
			result.getProducts().add(readProduct);
		}

		result.setNumber(searchProductRequestV2.getPageIdx());
		result.setAttrForFilt(searchProductResult.getAttrForFilt());

		return result;
	}

	@Override
	public ValueList autoCompleteRequestV2(SearchProductAutocompleteRequestV2 searchProductAutocompleteRequestV2, Language language) {
		AutocompleteRequest request = new AutocompleteRequest();
		request.setQ(searchProductAutocompleteRequestV2.getQ());
		request.setLang(searchProductAutocompleteRequestV2.getLang());
		request.setDeviceid(searchProductAutocompleteRequestV2.getDeviceid());
		request.setUid(searchProductAutocompleteRequestV2.getUid());

		AutocompleteResult result = searchProductService.autocomplete(request);

		ValueList valueList = new ValueList();
		valueList.setValues(new ArrayList<>(result.getSuggest().keySet()));
		return valueList;
	}


}
