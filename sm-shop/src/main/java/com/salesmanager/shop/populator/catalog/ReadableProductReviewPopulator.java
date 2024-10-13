package com.salesmanager.shop.populator.catalog;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.catalog.product.review.ProductReview;
import com.salesmanager.core.model.catalog.product.review.ProductReviewDescription;
import com.salesmanager.core.model.catalog.product.review.ProductReviewImage;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.product.ReadableProductReview;
import com.salesmanager.shop.model.customer.ReadableCustomer;
import com.salesmanager.shop.populator.customer.ReadableCustomerPopulator;
import com.salesmanager.shop.utils.DateUtil;
import com.salesmanager.shop.model.catalog.product.ReadableProductReviewImage;

public class ReadableProductReviewPopulator extends
		AbstractDataPopulator<ProductReview, ReadableProductReview> {

	@Override
	public ReadableProductReview populate(ProductReview source,
			ReadableProductReview target, MerchantStore store, Language language)
			throws ConversionException {

		
		try {
			ReadableCustomerPopulator populator = new ReadableCustomerPopulator();
			ReadableCustomer customer = new ReadableCustomer();
			populator.populate(source.getCustomer(), customer, store, language);
			
			target.setProductReviewId(source.getId());
			target.setReviewDate(DateUtil.formatDate(source.getReviewDate()));
			
			target.setCustomerId(source.getCustomer().getId());
			target.setFirstName(source.getCustomer().getBilling().getFirstName());
			target.setLastName(source.getCustomer().getBilling().getLastName());
			
			target.setReviewsRating(source.getReviewRating().intValue());
			target.setProductId(source.getOrderProduct().getProductId());
			target.setRecommendCount(source.getRecommends().size());
			
			Set<ProductReviewDescription> descriptions = source.getDescriptions();
			if(descriptions!=null) {
				for(ProductReviewDescription description : descriptions) {
					target.setReviewDescription(description.getDescription());
					break;
				}
			}
			target.setImages(new ArrayList<>(source.getImages()));
			
			return target;
			
		} catch (Exception e) {
			throw new ConversionException("Cannot populate ProductReview", e);
		}
	}

	@Override
	protected ReadableProductReview createTarget() {
		return null;
	}

}
