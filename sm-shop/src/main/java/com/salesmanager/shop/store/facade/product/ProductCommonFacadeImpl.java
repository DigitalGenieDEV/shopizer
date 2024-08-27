package com.salesmanager.shop.store.facade.product;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.validation.Valid;

import com.salesmanager.core.business.repositories.catalog.product.ProductRepository;
import com.salesmanager.core.business.repositories.catalog.product.attribute.ProductAnnouncementAttributeRepository;
import com.salesmanager.core.business.repositories.catalog.product.attribute.ProductAttributeRepository;
import com.salesmanager.core.business.repositories.catalog.product.feature.ProductFeatureRepository;
import com.salesmanager.core.business.services.alibaba.product.AlibabaProductService;
import com.salesmanager.core.business.services.catalog.product.attribute.ProductAnnouncementAttributeService;
import com.salesmanager.core.business.services.catalog.product.availability.ProductAvailabilityService;
import com.salesmanager.core.business.services.catalog.product.erp.ErpService;
import com.salesmanager.core.business.services.catalog.product.erp.ProductMaterialService;
import com.salesmanager.core.business.services.catalog.product.feature.ProductFeatureService;
import com.salesmanager.core.business.services.catalog.product.image.ProductImageService;
import com.salesmanager.core.business.services.catalog.product.qna.ProductQnaService;
import com.salesmanager.core.business.services.catalog.product.type.ProductTypeService;
import com.salesmanager.core.business.services.catalog.product.variant.ProductVariantService;
import com.salesmanager.core.business.services.merchant.MerchantStoreService;
import com.salesmanager.core.model.catalog.product.ProductMaterial;
import com.salesmanager.core.model.catalog.product.attribute.ProductAttribute;
import com.salesmanager.core.model.catalog.product.image.ProductImage;
import com.salesmanager.core.model.catalog.product.type.ProductType;
import com.salesmanager.core.model.feature.ProductFeature;
import com.salesmanager.shop.mapper.catalog.PersistableProductAnnouncementAttributeMapper;
import com.salesmanager.shop.model.catalog.product.attribute.PersistableAnnouncement;
import com.salesmanager.shop.model.catalog.product.product.PersistableSimpleProductUpdateReq;
import com.salesmanager.shop.store.controller.content.facade.ContentFacade;
import com.salesmanager.shop.store.controller.product.facade.AlibabaProductFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.catalog.pricing.PricingService;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.business.services.catalog.product.review.ProductReviewImageService;
import com.salesmanager.core.business.services.catalog.product.review.ProductReviewRecommendService;
import com.salesmanager.core.business.services.catalog.product.review.ProductReviewService;
import com.salesmanager.core.business.services.catalog.product.review.ProductReviewStatService;
import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.business.services.reference.language.LanguageService;
import com.salesmanager.core.constants.QuestionType;
import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.availability.ProductAvailability;
import com.salesmanager.core.model.catalog.product.manufacturer.Manufacturer;
import com.salesmanager.core.model.catalog.product.price.ProductPrice;
import com.salesmanager.core.model.catalog.product.qna.ProductQna;
import com.salesmanager.core.model.catalog.product.qna.ProductQnaReply;
import com.salesmanager.core.model.catalog.product.review.ProductReview;
import com.salesmanager.core.model.catalog.product.review.ProductReviewImage;
import com.salesmanager.core.model.catalog.product.review.ProductReviewRecommend;
import com.salesmanager.core.model.catalog.product.review.ProductReviewStat;
import com.salesmanager.core.model.catalog.product.variant.ProductVariant;
import com.salesmanager.core.model.content.FileContentType;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.mapper.catalog.product.PersistableProductMapper;
import com.salesmanager.shop.model.catalog.product.LightPersistableProduct;
import com.salesmanager.shop.model.catalog.product.PersistableProductQna;
import com.salesmanager.shop.model.catalog.product.PersistableProductQnaReply;
import com.salesmanager.shop.model.catalog.product.PersistableProductReview;
import com.salesmanager.shop.model.catalog.product.PersistableProductReviewRecommend;
import com.salesmanager.shop.model.catalog.product.ProductPriceEntity;
import com.salesmanager.shop.model.catalog.product.ReadableProduct;
import com.salesmanager.shop.model.catalog.product.ReadableProductQna;
import com.salesmanager.shop.model.catalog.product.ReadableProductQnaList;
import com.salesmanager.shop.model.catalog.product.ReadableProductReview;
import com.salesmanager.shop.model.catalog.product.ReadableProductReviewStat;
import com.salesmanager.shop.model.catalog.product.ReadableProductReviews;
import com.salesmanager.shop.model.catalog.product.product.PersistableProduct;
import com.salesmanager.shop.model.catalog.product.product.ProductSpecification;
import com.salesmanager.shop.model.content.ContentFile;
import com.salesmanager.shop.populator.catalog.PersistableProductQnaPopulator;
import com.salesmanager.shop.populator.catalog.PersistableProductQnaReplyPopulator;
import com.salesmanager.shop.populator.catalog.PersistableProductReviewPopulator;
import com.salesmanager.shop.populator.catalog.ReadableProductPopulator;
import com.salesmanager.shop.populator.catalog.ReadableProductQnaPopulator;
import com.salesmanager.shop.populator.catalog.ReadableProductReviewPopulator;
import com.salesmanager.shop.populator.catalog.ReadableProductReviewStatPopulator;
import com.salesmanager.shop.store.api.exception.ConversionRuntimeException;
import com.salesmanager.shop.store.api.exception.OperationNotAllowedException;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.product.facade.ProductCommonFacade;
import com.salesmanager.shop.utils.DateUtil;
import com.salesmanager.shop.utils.ImageFilePath;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


/**
 * Version 1 Product management
 * Version 2 Recommends using productVariant
 * @author carlsamson
 *
 */
@Service("productCommonFacade")
public class ProductCommonFacadeImpl implements ProductCommonFacade {

	@Autowired
	private ProductAvailabilityService productAvailabilityService;

	@Autowired
	private PersistableProductAnnouncementAttributeMapper persistableProductAnnouncementAttributeMapper;
	@Inject
	private LanguageService languageService;

	@Inject
	private ProductService productService;

	@Autowired
	private ErpService erpService;

	@Autowired
	private ProductMaterialService productMaterialService;

	@Autowired
	private ProductFeatureRepository productFeatureRepository;

	@Autowired
	private ProductAnnouncementAttributeRepository productAnnouncementAttributeRepository;

	@Autowired
	private ProductAttributeRepository productAttributeRepository;

	@Autowired
	private ProductRepository productRepository;

	@Inject
	private PricingService pricingService;

	@Autowired
	private ProductVariantService productVariantService;

	@Inject
	private CustomerService customerService;

	@Inject
	private ProductReviewService productReviewService;
	
	@Inject
	private ProductReviewStatService productReviewStatService;
	
	@Inject
	private ProductReviewImageService productReviewImageService;
	
	@Inject
	private ProductReviewRecommendService productReviewRecommendService;
	
	@Inject
	private ProductQnaService productQnaService;
	
	@Autowired
	private PersistableProductMapper persistableProductMapper;

	@Autowired
	private ProductFeatureService productFeatureService;

	@Autowired
	private ProductImageService productImageService;

	@Autowired
	private ProductAnnouncementAttributeService productAnnouncementAttributeService;

	@Autowired
	private ProductTypeService productTypeService;

	@Inject
	@Qualifier("img")
	private ImageFilePath imageUtils;
	
	@Inject
	private ContentFacade contentFacade;
	
	@Override
	@Transactional
	public Long saveProduct(MerchantStore store, PersistableProduct product, Language language) throws ServiceException {

		String manufacturer = Manufacturer.DEFAULT_MANUFACTURER;
		if (product.getProductSpecifications() != null) {
			manufacturer = product.getProductSpecifications().getManufacturer();
		} else {
			ProductSpecification specifications = new ProductSpecification();
			specifications.setManufacturer(manufacturer);
		}

		Product target = null;
		if (product.getId() != null && product.getId().longValue() > 0) {
			target = productService.getById(product.getId());
			Validate.notNull(target,"Product must not be null");

			List<ProductAvailability> byProductId = productAvailabilityService.getByProductId(product.getId());
			productAvailabilityService.deleteAll(byProductId);
			Set<ProductAvailability> productAvailabilities = new HashSet<>();
			List<ProductVariant> productVariants = productVariantService.queryListByProductId(product.getId());
			productVariantService.deleteAll(productVariants);
			target.setAvailabilities(productAvailabilities);

			Set<ProductImage> images = target.getImages();
			for (ProductImage image : images) {
				productImageService.removeProductImage(image);
			}
			target.setImages(new HashSet<>());
			target.setSku(null);
		} else {
			target = new Product();
		}

		target = persistableProductMapper.merge(product, target, store, language);

		target = productService.saveProduct(target);
		Long productId = target.getId();
		if (!CollectionUtils.isEmpty(product.getProductTag())){
			List<ProductFeature> listByProductId = productFeatureService.findListByProductId(target.getId());
			processProductFeatureDiff(listByProductId, product.getProductTag(), target.getId());
		}
		if(!CollectionUtils.isEmpty(product.getProductMaterials())) {
			List<ProductMaterial> productMaterials = productMaterialService.queryByProductId(target.getId());
			if (CollectionUtils.isNotEmpty(productMaterials)){
				productMaterialService.deleteProductMaterialByProductId(target.getId());
			}

			Set<ProductMaterial> collect = new HashSet<>();
			for (com.salesmanager.shop.model.catalog.ProductMaterial productMaterial : product.getProductMaterials()) {
				ProductMaterial pm = new ProductMaterial();
				Long materialId = productMaterial.getMaterialId();
				if (materialId == null) {
					continue;
				}
				pm.setMaterialId(materialId);
				pm.setWeight(productMaterial.getWeight());
				pm.setProductId(target.getId());
				collect.add(pm);
			}
			productMaterialService.saveAll(collect);
		}

		if (product.getAnnouncement() !=null) {
			Boolean result = productAnnouncementAttributeService.existsByProductId(target.getId());
			if (result){
				productAnnouncementAttributeService.deleteByProductId(target.getId());
			}
			PersistableAnnouncement announcement = product.getAnnouncement();
			announcement.setProductId(productId);
			productAnnouncementAttributeService.saveOrUpdate(persistableProductAnnouncementAttributeMapper.convert(announcement, store, language));
		}
		return target.getId();
	}

	@Override
	public Long saveAllProduct(MerchantStore store, List<Product> products, Language language) throws ServiceException {
		return null;
	}


	@Override
	@Transactional
	public Long simpleUpdateProduct(PersistableSimpleProductUpdateReq product) throws ServiceException {
		ProductType productType = productTypeService.getProductType(product.getType());
		productService.updateProductType(product.getProductId(), productType.getId());
		productService.updateProductCategory(product.getProductId(), product.getLeftCategory());
		if (!CollectionUtils.isEmpty(product.getProductTag())){
			List<ProductFeature> listByProductId = productFeatureService.findListByProductId(product.getProductId());
			processProductFeatureDiff(listByProductId, product.getProductTag(), product.getProductId());
		}
		return product.getProductId();
	}

	public void updateProduct(MerchantStore store, PersistableProduct product, Language language) {

		Validate.notNull(product, "Product must not be null");
		Validate.notNull(product.getId(), "Product id must not be null");

		// get original product
		Product productModel = productService.getById(product.getId());


	}

	@Override
	public ReadableProduct getProduct(MerchantStore store, Long id, Language language) {

		Product product = productService.findOne(id, store);
		if (product == null) {
			throw new ResourceNotFoundException("Product [" + id + "] not found");
		}

		if (product.getMerchantStore().getId() != store.getId()) {
			throw new ResourceNotFoundException("Product [" + id + "] not found for store [" + store.getId() + "]");
		}

		ReadableProduct readableProduct = new ReadableProduct();
		ReadableProductPopulator populator = new ReadableProductPopulator();
		populator.setPricingService(pricingService);
		populator.setimageUtils(imageUtils);
		try {
			readableProduct = populator.populate(product, readableProduct, store, language);
		} catch (ConversionException e) {
			throw new ConversionRuntimeException("Error converting product [" + id + "]",e);
		}

		return readableProduct;
	}


	@Override
	public ReadableProduct updateProductPrice(ReadableProduct product, ProductPriceEntity price, Language language)
			throws Exception {

		Product persistable = productService.getById(product.getId());

		if (persistable == null) {
			throw new Exception("product is null for id " + product.getId());
		}

		java.util.Set<ProductAvailability> availabilities = persistable.getAvailabilities();
		for (ProductAvailability availability : availabilities) {
			ProductPrice productPrice = availability.defaultPrice();
			productPrice.setProductPriceAmount(price.getPrice());
			if (price.isDiscounted()) {
				productPrice.setProductPriceSpecialAmount(price.getDiscountedPrice());
				if (!StringUtils.isBlank(price.getDiscountStartDate())) {
					Date startDate = DateUtil.getDate(price.getDiscountStartDate());
					productPrice.setProductPriceSpecialStartDate(startDate);
				}
				if (!StringUtils.isBlank(price.getDiscountEndDate())) {
					Date endDate = DateUtil.getDate(price.getDiscountEndDate());
					productPrice.setProductPriceSpecialEndDate(endDate);
				}
			}

		}

		productService.update(persistable);

		ReadableProduct readableProduct = new ReadableProduct();

		ReadableProductPopulator populator = new ReadableProductPopulator();

		populator.setPricingService(pricingService);
		populator.setimageUtils(imageUtils);
		populator.populate(persistable, readableProduct, persistable.getMerchantStore(), language);

		return readableProduct;
	}

	@Override
	public ReadableProduct updateProductQuantity(ReadableProduct product, int quantity, Language language)
			throws Exception {
		Product persistable = productService.getById(product.getId());

		if (persistable == null) {
			throw new Exception("product is null for id " + product.getId());
		}

		java.util.Set<ProductAvailability> availabilities = persistable.getAvailabilities();
		for (ProductAvailability availability : availabilities) {
			availability.setProductQuantity(quantity);
		}

		productService.saveProduct(persistable);

		ReadableProduct readableProduct = new ReadableProduct();

		ReadableProductPopulator populator = new ReadableProductPopulator();

		populator.setPricingService(pricingService);
		populator.setimageUtils(imageUtils);
		populator.populate(persistable, readableProduct, persistable.getMerchantStore(), language);

		return readableProduct;
	}

	@Override
	public void deleteProduct(Product product) throws Exception {
		productService.delete(product);

	}


	@Override
	public ReadableProduct addProductToCategory(Category category, Product product, Language language) {

		Validate.notNull(category, "Category cannot be null");
		Validate.notNull(product, "Product cannot be null");

		// not alloweed if category already attached
		List<Category> assigned = product.getCategories().stream()
				.filter(cat -> cat.getId().longValue() == category.getId().longValue()).collect(Collectors.toList());

		if (assigned.size() > 0) {
			throw new OperationNotAllowedException("Category with id [" + category.getId()
					+ "] already attached to product [" + product.getId() + "]");
		}

		product.getCategories().add(category);
		ReadableProduct readableProduct = new ReadableProduct();
		
		try {

			productService.saveProduct(product);
	
			ReadableProductPopulator populator = new ReadableProductPopulator();
	
			populator.setPricingService(pricingService);
			populator.setimageUtils(imageUtils);
			populator.populate(product, readableProduct, product.getMerchantStore(), language);
		
		} catch(Exception e) {
			throw new RuntimeException("Exception when adding product [" + product.getId() + "] to category [" + category.getId() + "]",e);
		}

		return readableProduct;

	}

	@Override
	public ReadableProduct removeProductFromCategory(Category category, Product product, Language language)
			throws Exception {

		Validate.notNull(category, "Category cannot be null");
		Validate.notNull(product, "Product cannot be null");

		product.getCategories().remove(category);
		productService.saveProduct(product);

		ReadableProduct readableProduct = new ReadableProduct();

		ReadableProductPopulator populator = new ReadableProductPopulator();

		populator.setPricingService(pricingService);
		populator.setimageUtils(imageUtils);
		populator.populate(product, readableProduct, product.getMerchantStore(), language);

		return readableProduct;
	}

	@Override
	public ReadableProduct getProductByCode(MerchantStore store, String uniqueCode, Language language)
			throws Exception {

		Product product = productService.getBySku(uniqueCode, store, language);

		ReadableProduct readableProduct = new ReadableProduct();

		ReadableProductPopulator populator = new ReadableProductPopulator();

		populator.setPricingService(pricingService);
		populator.setimageUtils(imageUtils);
		populator.populate(product, readableProduct, product.getMerchantStore(), language);

		return readableProduct;
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

		if (review.getId() == null) {
			productReviewService.create(rev);
		} else {
			productReviewService.update(rev);
		}
		
		review.setId(rev.getId());
		
		// ÷�� ������ ������ ó��.
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
	public ReadableProductReviews getProductReviews(Product product, MerchantStore store, Language language, String keyword, Pageable pageRequest)
			throws Exception {
		
		ReadableProductReviews readableProductReviews = new ReadableProductReviews();
		
		// review ���
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
	public void update(Long productId, LightPersistableProduct product, MerchantStore merchant, Language language) {
		// Get product
		Product modified = productService.findOne(productId, merchant);

		// Update product with minimal set
		modified.setAvailable(product.isAvailable());

		for (ProductAvailability availability : modified.getAvailabilities()) {
			availability.setProductQuantity(product.getQuantity());
			if (!StringUtils.isBlank(product.getPrice())) {
				// set default price
				for (ProductPrice price : availability.getPrices()) {
					if (price.isDefaultPrice()) {
						try {
							price.setProductPriceAmount(pricingService.getAmount(product.getPrice()));
						} catch (ServiceException e) {
							throw new ServiceRuntimeException("Invalid product price format");
						}
					}
				}
			}
		}

		try {
			productService.save(modified);
		} catch (ServiceException e) {
			throw new ServiceRuntimeException("Cannot update product ", e);
		}

	}

	@Override
	public boolean exists(String sku, MerchantStore store) {

		return productService.exists(sku, store);
	}


	@Override
	public void deleteProduct(Long id, MerchantStore store) {

		Validate.notNull(id, "Product id cannot be null");
		Validate.notNull(store, "store cannot be null");

		Product p = productService.getById(id);

		if (p == null) {
			throw new ResourceNotFoundException("Product with id [" + id + " not found");
		}

		if (p.getMerchantStore().getId().intValue() != store.getId().intValue()) {
			throw new ResourceNotFoundException(
					"Product with id [" + id + " not found for store [" + store.getCode() + "]");
		}

		try {
			deleteProductsInParallel(p);
		} catch (ServiceException e) {
			throw new ServiceRuntimeException("Error while deleting ptoduct with id [" + id + "]", e);
		}

	}

	@Transactional
	public void deleteProductsInParallel(Product product) throws ServiceException {
		productAnnouncementAttributeRepository.deleteByProductId(product.getId());

		List<ProductAttribute> attributes = productAttributeRepository.findByProductId(product.getId());

		List<Long> collect = attributes.stream().map(ProductAttribute::getId).collect(Collectors.toList());

		productAttributeRepository.deleteProductAttributesByIds(collect);

		productFeatureRepository.deleteByProductId(product.getId());

		productService.delete(product);
	}


	@Override
	public Product getProduct(Long id, MerchantStore store) {
		return productService.findOne(id, store);
	}

	@Override
	public void update(String sku, LightPersistableProduct product, MerchantStore merchant, Language language) {
		// Get product
		Product modified = null;
		try {
			modified = productService.getBySku(sku, merchant, language);
		} catch (ServiceException e) {
			throw new ServiceRuntimeException(e);
		}
		
		ProductVariant instance = modified.getVariants().stream()
				  .filter(inst -> sku.equals(inst.getSku()))
				  .findAny()
				  .orElse(null);
		
		if(instance!=null) {
			instance.setAvailable(product.isAvailable());
			
			for (ProductAvailability availability : instance.getAvailabilities()) {
				this.setAvailability(availability, product);
			}
		} else {
			// Update product with minimal set
			modified.setAvailable(product.isAvailable());
			
			for (ProductAvailability availability : modified.getAvailabilities()) {
				this.setAvailability(availability, product);
			}
		}

		try {
			productService.saveProduct(modified);
		} catch (ServiceException e) {
			throw new ServiceRuntimeException("Cannot update product ", e);
		}
		
	}
	
	/**
	 * edit availability
	 */
	private void setAvailability(ProductAvailability availability, LightPersistableProduct product) {
		availability.setProductQuantity(product.getQuantity());
		if (!StringUtils.isBlank(product.getPrice())) {
			// set default price
			for (ProductPrice price : availability.getPrices()) {
				if (price.isDefaultPrice()) {
					try {
						price.setProductPriceAmount(pricingService.getAmount(product.getPrice()));
					} catch (ServiceException e) {
						throw new ServiceRuntimeException("Invalid product price format");
					}
				}
			}
		}
	}


	public void processProductFeatureDiff(List<ProductFeature> listByProductId, List<String> productTags, Long productId) throws ServiceException {
		if(CollectionUtils.isNotEmpty(listByProductId)){
			Set<String> existingKeys = listByProductId.stream()
					.map(ProductFeature::getKey)
					.collect(Collectors.toSet());

			Set<String> newKeys = new HashSet<>(productTags);

			Set<String> addedKeys = new HashSet<>(newKeys);
			addedKeys.removeAll(existingKeys);

			Set<String> removedKeys = new HashSet<>(existingKeys);
			removedKeys.removeAll(newKeys);

			if (addedKeys.isEmpty() && removedKeys.isEmpty()) {
				return;
			}

			for (String key : addedKeys) {
				ProductFeature newFeature = new ProductFeature();
				newFeature.setProductId(productId);
				newFeature.setKey(key);
				newFeature.setValue("1");
				productFeatureService.save(newFeature);
			}

			for (String key : removedKeys) {
				ProductFeature featureToRemove = listByProductId.stream()
						.filter(feature -> feature.getKey().equals(key))
						.findFirst()
						.orElse(null);

				if (featureToRemove != null) {
					productFeatureService.delete(featureToRemove);
				}
			}
		}else {
			for (String tag : productTags){
				ProductFeature  feature = new ProductFeature();
				feature.setProductId(productId);
				feature.setKey(tag);
				feature.setValue("1");
				productFeatureService.save(feature);
			}
		}
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



	@Override
	public void saveOrUpdateQna(@Valid PersistableProductQna persistableQna, MerchantStore store, Language language) throws Exception {
		// TODO Auto-generated method stub
		PersistableProductQnaPopulator populator = new PersistableProductQnaPopulator();
		populator.setLanguageService(languageService);
		populator.setCustomerService(customerService);
		populator.setProductService(productService);
		
		ProductQna qna = new ProductQna();
		populator.populate(persistableQna, qna, store, language);

		if (persistableQna.getId() == null) {
			productQnaService.create(qna);
		} else {
			productQnaService.update(qna);
		}
	}

	@Override
	public void saveReply(Long qnaId, @Valid PersistableProductQnaReply persistableReply, MerchantStore merchantStore, Language language) throws ConversionException {
		// TODO Auto-generated method stub
		ProductQna qna = productQnaService.getById(qnaId);
		
		ProductQnaReply reply = new ProductQnaReply();
		PersistableProductQnaReplyPopulator populator = new PersistableProductQnaReplyPopulator();
		populator.setProductQnaService(productQnaService);
		persistableReply.setId(qnaId);
		populator.populate(persistableReply, reply, merchantStore, language);
		
		qna.setReply(reply);
		productQnaService.update(qna);
	}

	@Override
	public ReadableProductQna getProductQna(Long qnaId, MerchantStore store, Language lang) throws ConversionException {
		// TODO Auto-generated method stub
		ProductQna qna = productQnaService.getById(qnaId);
		ReadableProductQna readableQna = new ReadableProductQna();
		if(qna != null) {
			ReadableProductQnaPopulator populator = new ReadableProductQnaPopulator();
			populator.setPricingService(pricingService);
			populator.setImageUtils(imageUtils);
			populator.populate(qna, readableQna, store, lang);
		}
		
		return readableQna;
	}

	@Override
	public ReadableProductQnaList getProductQnaList(Long productId, boolean checkSecret, boolean checkSelf, Integer customerId,
			String questingType, Pageable pageRequest, MerchantStore store, Language lang) {
		// TODO Auto-generated method stub
		QuestionType qt = null;
		try {
			qt = QuestionType.valueOf(questingType);
		} catch (Exception e) {
			// TODO: handle exception
			qt = null;
		}
		Page<ProductQna> productQnaList = productQnaService.getProductQnaList(productId, customerId, checkSelf, checkSecret, qt, pageRequest);
		ReadableProductQnaList readableProductQnaList = new ReadableProductQnaList();
		List<ReadableProductQna> readableQnaList = new ArrayList<ReadableProductQna>();
		
		if(!CollectionUtils.isEmpty(productQnaList.getContent())) {
			for(ProductQna qna : productQnaList) {
				ReadableProductQnaPopulator populator = new ReadableProductQnaPopulator();
				populator.setPricingService(pricingService);
				populator.setImageUtils(imageUtils);
				ReadableProductQna readableQna = new ReadableProductQna();
				try {
					populator.populate(qna, readableQna, store, lang);
				} catch (ConversionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				readableQnaList.add(readableQna);
			}
		}
		
		readableProductQnaList.setData(readableQnaList);
		readableProductQnaList.setRecordsTotal(productQnaList.getTotalElements());
		readableProductQnaList.setTotalPages(productQnaList.getTotalPages());
		readableProductQnaList.setNumber(productQnaList.getSize());
		readableProductQnaList.setRecordsFiltered(productQnaList.getSize());
		
		return readableProductQnaList;
	}
}
