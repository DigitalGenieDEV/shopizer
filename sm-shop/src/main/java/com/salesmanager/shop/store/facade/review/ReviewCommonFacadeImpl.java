package com.salesmanager.shop.store.facade.review;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.business.services.catalog.product.review.ProductReviewImageService;
import com.salesmanager.core.business.services.catalog.product.review.ProductReviewRecommendService;
import com.salesmanager.core.business.services.catalog.product.review.ProductReviewService;
import com.salesmanager.core.business.services.catalog.product.review.ProductReviewStatService;
import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.business.services.reference.language.LanguageService;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.review.ProductReview;
import com.salesmanager.core.model.catalog.product.review.ProductReviewImage;
import com.salesmanager.core.model.catalog.product.review.ProductReviewRecommend;
import com.salesmanager.core.model.catalog.product.review.ProductReviewStat;
import com.salesmanager.core.model.content.FileContentType;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.product.PersistableProductReview;
import com.salesmanager.shop.model.catalog.product.PersistableProductReviewRecommend;
import com.salesmanager.shop.model.catalog.product.ReadableProductReview;
import com.salesmanager.shop.model.catalog.product.ReadableProductReviewStat;
import com.salesmanager.shop.model.catalog.product.ReadableProductReviews;
import com.salesmanager.shop.model.content.ContentFile;
import com.salesmanager.shop.populator.catalog.PersistableProductReviewPopulator;
import com.salesmanager.shop.populator.catalog.ReadableProductReviewPopulator;
import com.salesmanager.shop.populator.catalog.ReadableProductReviewStatPopulator;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.content.facade.ContentFacade;
import com.salesmanager.shop.store.controller.review.facade.ReviewCommonFacade;
import com.salesmanager.shop.utils.ImageFilePath;

@Service("reviewCommonFacade")
public class ReviewCommonFacadeImpl implements ReviewCommonFacade {
	
	@Inject
	private ContentFacade contentFacade;
	
	@Inject
	private CustomerService customerService;
	
	@Inject
	@Qualifier("img")
	private ImageFilePath imageUtils;
	
	@Inject
	private LanguageService languageService;
	
	@Inject
	private ProductService productService;
	
	@Inject
	private ProductReviewService productReviewService;
	
	@Inject
	private ProductReviewImageService productReviewImageService;
	
	@Inject
	private ProductReviewStatService productReviewStatService;
	
	@Inject
	private ProductReviewRecommendService productReviewRecommendService;
	
	@Override
	public ReadableProductReviews getProductReviews(Product product, MerchantStore store, Language language, String keyword, Pageable pageRequest)
			throws Exception {
		
		ReadableProductReviews readableProductReviews = new ReadableProductReviews();
		
		List<ProductReview> reviews = productReviewService.listByKeyword(product, keyword, pageRequest);
		ReadableProductReviewPopulator populator = new ReadableProductReviewPopulator();

		List<ReadableProductReview> productReviews = new ArrayList<ReadableProductReview>();

		for (ProductReview review : reviews) {
			ReadableProductReview readableReview = new ReadableProductReview();
			populator.populate(review, readableReview, store, language);
			productReviews.add(readableReview);
		}
		readableProductReviews.setReviews(productReviews);
		
		// review stat
		ReadableProductReviewStat readableProductReviewStat = null;
		if(reviews.size() > 0) {
//		if(!CollectionUtils.isEmpty(reviews.getContent())) {
			ProductReviewStat reviewStat = productReviewStatService.getByProduct(product);
			ReadableProductReviewStatPopulator statPopulator = new ReadableProductReviewStatPopulator();
			readableProductReviewStat = statPopulator.populate(reviewStat, null, store, language);
		} else {
			readableProductReviewStat = new ReadableProductReviewStat(product.getId());
		}
		readableProductReviews.setReviewStat(readableProductReviewStat);
		
		return readableProductReviews;
	}

	@Override
	public void saveOrUpdateReview(PersistableProductReview review, MerchantStore store, Language language, List<MultipartFile> reviewImages)
			throws Exception {
		PersistableProductReviewPopulator populator = new PersistableProductReviewPopulator();
		populator.setLanguageService(languageService);
		populator.setCustomerService(customerService);
		populator.setProductService(productService);

		com.salesmanager.core.model.catalog.product.review.ProductReview rev = new com.salesmanager.core.model.catalog.product.review.ProductReview();
		populator.populate(review, rev, store, language);

		if (review.getId() == null || review.getId() == 0) {
			productReviewService.create(rev);
		} else {
			productReviewService.update(rev);
		}
		
		review.setId(rev.getId());
		
		if(reviewImages != null) {
			for(MultipartFile file : reviewImages) {
				ProductReviewImage reviewImage = new ProductReviewImage();
				ContentFile f = new ContentFile();
				f.setContentType(file.getContentType());
				f.setName(file.getOriginalFilename());
				try {
					f.setFile(file.getBytes());
				} catch (IOException e) {
					throw new ServiceRuntimeException("Error while getting file bytes");
				}
				String fileName = contentFacade.addLibraryFile(f, store.getCode(), FileContentType.valueOf(review.getFileContentType()));
				reviewImage.setImageUrl(imageUtils.buildLibraryFileUtils(store, fileName, review.getFileContentType()));
				reviewImage.setProductReview(rev);
				productReviewImageService.save(reviewImage);
			}
		}
	}

	@Override
	public void deleteReview(ProductReview review, MerchantStore store, Language language) throws Exception {
		productReviewService.delete(review);

	}

	@Override
	public void updateReviewRecommend(Long reviewId, PersistableProductReviewRecommend persistableRecommend) throws Exception {
		// TODO Auto-generated method stub
		ProductReviewRecommend recommend = productReviewRecommendService.getByCutomerReview(persistableRecommend.getCustomerId(), reviewId);
		ProductReview review = productReviewService.getById(reviewId);
		Customer customer = customerService.getById(persistableRecommend.getCustomerId());
		if(review.getCustomer().getId().longValue() != persistableRecommend.getCustomerId().longValue()) {
			try {
				if(recommend == null) {
					recommend = new ProductReviewRecommend();
					recommend.setProductReview(review);
					recommend.setCustomer(customer);
					recommend.setActive(true);
					productReviewRecommendService.save(recommend);
				} else {
					recommend.setActive(!recommend.isActive());
					productReviewRecommendService.update(recommend);
				}
			} catch (Exception e) {
				throw new Exception("Failed Review Recommend", e);
			}
		} else {
			throw new Exception("Own review cannot recommend");
		}
		
		
	}

}
