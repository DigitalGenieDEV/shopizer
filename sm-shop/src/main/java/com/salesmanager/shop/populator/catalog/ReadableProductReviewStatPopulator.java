package com.salesmanager.shop.populator.catalog;

import org.springframework.stereotype.Service;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.catalog.product.review.ProductReviewStat;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.product.ReadableProductReviewStat;

public class ReadableProductReviewStatPopulator extends AbstractDataPopulator<ProductReviewStat, ReadableProductReviewStat> {

	@Override
	public ReadableProductReviewStat populate(ProductReviewStat source, ReadableProductReviewStat target,
			MerchantStore store, Language language) throws ConversionException {
		// TODO Auto-generated method stub
		if(target == null) {
			target = new ReadableProductReviewStat();
		}
		try {
			target.setProductId(source.getProductId());
			target.setReviewRatingCount1(source.getReviewRatingCount1());
			target.setReviewRatingPercent1(source.getReviewRatingPercent1());
			target.setReviewRatingCount2(source.getReviewRatingCount2());
			target.setReviewRatingPercent2(source.getReviewRatingPercent2());
			target.setReviewRatingCount3(source.getReviewRatingCount3());
			target.setReviewRatingPercent3(source.getReviewRatingPercent3());
			target.setReviewRatingCount4(source.getReviewRatingCount4());
			target.setReviewRatingPercent4(source.getReviewRatingPercent4());
			target.setReviewRatingCount5(source.getReviewRatingCount5());
			target.setReviewRatingPercent5(source.getReviewRatingPercent5());
			target.setReviewRatingSum(source.getReviewRatingSum());
			target.setTotalCount(source.getTotalCount());
			target.setReviewRatingAvg(source.getReviewRatingAvg());
		} catch (Exception e) {
			// TODO: handle exception
			throw new ConversionException("Cannot populate ProductReviewStat", e);
		}
		return target;
	}

	@Override
	protected ReadableProductReviewStat createTarget() {
		// TODO Auto-generated method stub
		return null;
	}

}
