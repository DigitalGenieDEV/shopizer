package com.salesmanager.shop.store.controller.review.facade;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.review.ProductReview;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.product.PersistableProductReview;
import com.salesmanager.shop.model.catalog.product.PersistableProductReviewRecommend;
import com.salesmanager.shop.model.catalog.product.ReadableProductReview;
import com.salesmanager.shop.model.catalog.product.ReadableProductReviewList;

public interface ReviewCommonFacade {
	public ReadableProductReviewList getProductReviews(Product product, MerchantStore store, Language language, String keyword, Pageable pageRequest) throws Exception;
	public ReadableProductReview getReviewById(Long reviewId, Product product, MerchantStore merchantStore, Language language) throws Exception;
	public void saveOrUpdateReview(PersistableProductReview review, MerchantStore store, Language language, List<MultipartFile> reviewImages) throws Exception;
	public void deleteReview(ProductReview review, MerchantStore store, Language language) throws Exception;
	public void updateReviewRecommend(Long reviewId, PersistableProductReviewRecommend persistableRecommend) throws Exception;
	public ReadableProductReviewList getReviewsByStore(MerchantStore merchantStore, Language language, String keyword, Pageable pageRequest);
}
