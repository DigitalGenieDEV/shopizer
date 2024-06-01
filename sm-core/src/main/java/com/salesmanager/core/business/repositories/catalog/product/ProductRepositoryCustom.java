package com.salesmanager.core.business.repositories.catalog.product;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.model.catalog.product.*;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.tax.taxclass.TaxClass;

public interface ProductRepositoryCustom {


	void updateProductAuditStatusById(ProductAuditStatus productAuditStatus, Long id);

	void updateProductStatusById(ProductStatus productStatus, Long id);


	ProductList listByStore(MerchantStore store, Language language,
			ProductCriteria criteria);

	Product getProductWithOnlyMerchantStoreById(Long productId);

	Product getByFriendlyUrl(MerchantStore store,String seUrl, Locale locale);

	List<Product> getProductsListByCategories(@SuppressWarnings("rawtypes") Set categoryIds);

	List<Product> getProductsListByCategories(Set<Long> categoryIds,
											  Language language);


	Product getProductById(Long productId, Language language);

	List<Product> getProductsListByIds(Set<Long> productIds);

	List<Product> listByTaxClass(TaxClass taxClass);

	List<Product> listByStore(MerchantStore store);

	Product getProductForLocale(long productId, Language language,
								Locale locale);

	Product getById(Long productId);
	Product getById(Long productId, MerchantStore merchant);

	/**
	 * Get product by code
	 * @deprecated
	 * This method is no longer acceptable to get product by code.
	 * <p> Use {@link ProductService#getBySku(sku, store)} instead.
	 */
	@Deprecated
	Product getByCode(String productCode, Language language);

	/**
	 * Get product by code
	 * @deprecated
	 * This method is no longer acceptable to get product by code.
	 * <p> Use {@link ProductService#getBySku(sku, store)} instead.
	 */
	@Deprecated
	Product getByCode(String productCode, MerchantStore store);

	Product getById(Long productId, MerchantStore store, Language language);

	ProductList listByStoreForList(MerchantStore store, Language language, ProductCriteria criteria);

	List<Product> getProductsForLocale(MerchantStore store,
									   Set<Long> categoryIds, Language language, Locale locale);
}
