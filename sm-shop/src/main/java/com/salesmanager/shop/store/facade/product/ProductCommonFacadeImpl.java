package com.salesmanager.shop.store.facade.product;

import java.util.*;
import java.util.stream.Collectors;

import javax.inject.Inject;
import com.salesmanager.core.business.repositories.catalog.product.ProductRepository;
import com.salesmanager.core.business.repositories.catalog.product.attribute.ProductAnnouncementAttributeRepository;
import com.salesmanager.core.business.repositories.catalog.product.attribute.ProductAttributeRepository;
import com.salesmanager.core.business.repositories.catalog.product.feature.ProductFeatureRepository;
import com.salesmanager.core.business.services.catalog.product.attribute.ProductAnnouncementAttributeService;
import com.salesmanager.core.business.services.catalog.product.availability.ProductAvailabilityService;
import com.salesmanager.core.business.services.catalog.product.erp.ErpService;
import com.salesmanager.core.business.services.catalog.product.erp.ProductMaterialService;
import com.salesmanager.core.business.services.catalog.product.feature.ProductFeatureService;
import com.salesmanager.core.business.services.catalog.product.image.ProductImageService;
import com.salesmanager.core.business.services.catalog.product.type.ProductTypeService;
import com.salesmanager.core.business.services.catalog.product.variant.ProductVariantService;
import com.salesmanager.core.model.catalog.product.ProductMaterial;
import com.salesmanager.core.model.catalog.product.attribute.ProductAttribute;
import com.salesmanager.core.model.catalog.product.image.ProductImage;
import com.salesmanager.core.model.catalog.product.type.ProductType;
import com.salesmanager.core.model.feature.ProductFeature;
import com.salesmanager.shop.mapper.catalog.PersistableProductAnnouncementAttributeMapper;
import com.salesmanager.shop.model.catalog.product.attribute.PersistableAnnouncement;
import com.salesmanager.shop.model.catalog.product.product.PersistableSimpleProductUpdateReq;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.catalog.pricing.PricingService;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.availability.ProductAvailability;
import com.salesmanager.core.model.catalog.product.manufacturer.Manufacturer;
import com.salesmanager.core.model.catalog.product.price.ProductPrice;
import com.salesmanager.core.model.catalog.product.variant.ProductVariant;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.mapper.catalog.product.PersistableProductMapper;
import com.salesmanager.shop.model.catalog.product.LightPersistableProduct;
import com.salesmanager.shop.model.catalog.product.ProductPriceEntity;
import com.salesmanager.shop.model.catalog.product.ReadableProduct;
import com.salesmanager.shop.model.catalog.product.product.PersistableProduct;
import com.salesmanager.shop.model.catalog.product.product.ProductSpecification;
import com.salesmanager.shop.populator.catalog.ReadableProductPopulator;
import com.salesmanager.shop.store.api.exception.ConversionRuntimeException;
import com.salesmanager.shop.store.api.exception.OperationNotAllowedException;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.product.facade.ProductCommonFacade;
import com.salesmanager.shop.utils.DateUtil;
import com.salesmanager.shop.utils.ImageFilePath;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


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

		if (product.getAnnouncement() !=null && CollectionUtils.isNotEmpty(product.getAnnouncement().getAnnouncementFields())) {
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
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void deleteProduct(Long id, MerchantStore store) {

		Validate.notNull(id, "Product id cannot be null");

		Product p = productService.getById(id);

		if (p == null) {
			throw new ResourceNotFoundException("Product with id [" + id + " not found");
		}


		try {
			deleteProductsInParallel(p);
		} catch (ServiceException e) {
			throw new ServiceRuntimeException("Error while deleting ptoduct with id [" + id + "]", e);
		}

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void deleteProductsInParallel(Product product) throws ServiceException {
		productMaterialService.deleteProductMaterialByProductId(product.getId());

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
}
