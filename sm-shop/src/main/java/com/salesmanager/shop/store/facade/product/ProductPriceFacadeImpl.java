package com.salesmanager.shop.store.facade.product;

import static com.salesmanager.core.business.utils.NumberUtils.isPositive;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.model.catalog.product.price.PriceRange;
import com.salesmanager.shop.model.catalog.product.PersistableProductPriceDiscount;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.catalog.pricing.PricingService;
import com.salesmanager.core.business.services.catalog.product.availability.ProductAvailabilityService;
import com.salesmanager.core.business.services.catalog.product.price.ProductPriceService;
import com.salesmanager.core.model.catalog.product.availability.ProductAvailability;
import com.salesmanager.core.model.catalog.product.price.ProductPrice;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.mapper.inventory.PersistableProductPriceMapper;
import com.salesmanager.shop.model.catalog.product.PersistableProductPrice;
import com.salesmanager.shop.model.catalog.product.ReadableProductPrice;
import com.salesmanager.shop.populator.catalog.ReadableProductPricePopulator;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.product.facade.ProductPriceFacade;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductPriceFacadeImpl implements ProductPriceFacade {
	
	
	@Autowired
	private ProductPriceService productPriceService;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private PricingService pricingService;
	
	@Autowired
	private ProductAvailabilityService productAvailabilityService;
	
	
	@Autowired
	private PersistableProductPriceMapper persistableProductPriceMapper;

	@Override
	public Long save(PersistableProductPrice price, MerchantStore store) {
		
		
		ProductPrice productPrice = persistableProductPriceMapper.convert(price, store, store.getDefaultLanguage());
		try {
			if(!isPositive(productPrice.getId())) {
				//avoid detached entity failed to persist
				productPrice.getProductAvailability().setPrices(null);
				productPrice = productPriceService
						.saveOrUpdate(productPrice);
			} else {
				productPrice = productPriceService
						.saveOrUpdate(productPrice);
			}

		} catch (ServiceException e) {
			throw new ServiceRuntimeException("An exception occured while creating a ProductPrice");
		}
		return productPrice.getId();
	}


	@Override
	public List<ReadableProductPrice> list(String sku, Long inventoryId, MerchantStore store, Language language) {
		Validate.notNull(store, "MerchantStore cannot be null");
		Validate.notNull(sku, "Product sku cannot be null");
		Validate.notNull(inventoryId, "Product inventory cannot be null");
		
		List<ProductPrice> prices = productPriceService.findByInventoryId(inventoryId, sku, store);
		List<ReadableProductPrice> returnPrices = prices.stream().map(p -> {
			try {
				return this.readablePrice(p, store, language);
			} catch (ConversionException e) {
				throw new ServiceRuntimeException("An exception occured while getting product price for sku [" + sku + "] and Store [" + store.getCode() + "]", e);
			}
		}).collect(Collectors.toList());
		
		return returnPrices;
		
		
	}

	@Override
	public List<ReadableProductPrice> list(String sku, MerchantStore store, Language language) {
		Validate.notNull(store, "MerchantStore cannot be null");
		Validate.notNull(sku, "Product sku cannot be null");

			List<ProductPrice> prices = productPriceService.findByProductSku(sku, store);
			List<ReadableProductPrice> returnPrices = prices.stream().map(p -> {
				try {
					return this.readablePrice(p, store, language);
				} catch (ConversionException e) {
					throw new ServiceRuntimeException("An exception occured while getting product price for sku [" + sku + "] and Store [" + store.getCode() + "]", e);
				}
			}).collect(Collectors.toList());
			
			return returnPrices;

	}


	@Override
	public void delete(Long priceId, String sku, MerchantStore store) {
		Validate.notNull(priceId, "Product Price id cannot be null");
		Validate.notNull(store, "MerchantStore cannot be null");
		Validate.notNull(sku, "Product sku cannot be null");
		ProductPrice productPrice = productPriceService.findById(priceId, sku, store);
		if(productPrice == null) {
			throw new ServiceRuntimeException("An exception occured while getting product price [" + priceId + "] for product sku [" + sku + "] and Store [" + store.getCode() + "]");
		}
		
		try {
			productPriceService.delete(productPrice);
		} catch (ServiceException e) {
			throw new ServiceRuntimeException("An exception occured while deleting product price [" + priceId + "] for product sku [" + sku + "] and Store [" + store.getCode() + "]", e);
		}
		
	}
	
	private ReadableProductPrice readablePrice (ProductPrice price, MerchantStore store, Language language) throws ConversionException {
		ReadableProductPricePopulator populator = new ReadableProductPricePopulator();
		populator.setPricingService(pricingService);
		return populator.populate(price, new ReadableProductPrice(),  store, language , false);
	}


	@Override
	public ReadableProductPrice get(String sku, Long productPriceId, MerchantStore store, Language language) {
		Validate.notNull(productPriceId, "Product Price id cannot be null");
		Validate.notNull(store, "MerchantStore cannot be null");
		Validate.notNull(sku, "Product sku cannot be null");
		ProductPrice price = productPriceService.findById(productPriceId, sku, store);
		
		if(price == null) {
			throw new ResourceNotFoundException("ProductPrice with id [" + productPriceId + " not found for product sku [" + sku + "] and Store [" + store.getCode() + "]");
		}
		
		try {
			return readablePrice(price, store, language);
		} catch (ConversionException e) {
			throw new ServiceRuntimeException("An exception occured while deleting product price [" + productPriceId + "] for product sku [" + sku + "] and Store [" + store.getCode() + "]", e);
		}
	}

	@Override
	@Transactional
	public void setProductDiscount(PersistableProductPriceDiscount discount) {
		Validate.notNull(discount.getProductId(), "Product  id cannot be null");
		Validate.notNull(discount.getDiscount(), "discount cannot be null");
		Long productId = discount.getProductId();

		List<ProductAvailability> productAvailabilities = productAvailabilityService.getByProductId(productId);

		AtomicReference<Boolean> isPriceRange = new AtomicReference<>(false);

		AtomicReference<String> productPriceRanges = new AtomicReference<String>();
		AtomicReference<BigDecimal> productPrice = new AtomicReference<BigDecimal>();

		productAvailabilities.forEach(productAvailability -> {
			Set<ProductPrice> prices = productAvailability.getPrices();
			for (ProductPrice price : prices) {
				price.setDiscountPercent(discount.getDiscount());

				if (StringUtils.isNotEmpty(price.getPriceRangeList())){
					List<PriceRange> priceRanges = JSON.parseObject(price.getPriceRangeList(), new TypeReference<List<PriceRange>>() {});
					List<PriceRange> result = priceRanges.stream().map(priceRange -> {
						priceRange.setPromotionPrice(buildDiscountPriceAmount(new BigDecimal(priceRange.getPrice()), discount.getDiscount()).toString());
						return priceRange;
					}).collect(Collectors.toList());
					price.setPriceRangeList(JSON.toJSONString(result));
					isPriceRange.set(true);
					productPriceRanges.set(JSON.toJSONString(result));
				}else {
					BigDecimal productPriceSpecialAmount = buildDiscountPriceAmount(price.getProductPriceAmount(), discount.getDiscount());
					price.setProductPriceSpecialAmount(productPriceSpecialAmount);
					if (productPrice.get() == null || productPriceSpecialAmount.compareTo(productPrice.get()) < 0) {
						productPrice.set(productPriceSpecialAmount);
					}
				}

				price.setDiscountPercent(discount.getDiscount());
				try {
					productPriceService.save(price);
				} catch (ServiceException e) {
					throw new RuntimeException(e);
				}
			}
			productService.updateProductDiscount(discount.getProductId(), discount.getDiscount());
			if (isPriceRange.get()){
				productService.updateProductPriceRange(discount.getProductId(), productPriceRanges.get());
			}else {
				productService.updateProductPrice(discount.getProductId(), productPrice.get());
			}
		});
	}

	BigDecimal buildDiscountPriceAmount(BigDecimal price, int discount){
		BigDecimal productPriceAmount = price;
		BigDecimal discountPercent = new BigDecimal(discount);

		BigDecimal discountAmount = productPriceAmount.multiply(discountPercent).divide(new BigDecimal(100));

		return productPriceAmount.subtract(discountAmount);
	}

}
