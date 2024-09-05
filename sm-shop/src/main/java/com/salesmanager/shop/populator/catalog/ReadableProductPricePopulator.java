package com.salesmanager.shop.populator.catalog;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.salesmanager.core.business.modules.enmus.ExchangeRateEnums;
import com.salesmanager.core.business.utils.ExchangeRateConfig;
import org.apache.commons.lang3.Validate;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.services.catalog.pricing.PricingService;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.catalog.product.price.FinalPrice;
import com.salesmanager.core.model.catalog.product.price.ProductPrice;
import com.salesmanager.core.model.catalog.product.price.ProductPriceDescription;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.product.ReadableProductPrice;
import com.salesmanager.shop.model.catalog.product.ReadableProductPriceFull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ReadableProductPricePopulator  {

	@Autowired
	private ExchangeRateConfig examRateConfig;
	
	private PricingService pricingService;

	public PricingService getPricingService() {
		return pricingService;
	}

	public void setPricingService(PricingService pricingService) {
		this.pricingService = pricingService;
	}

	public ReadableProductPrice populate(ProductPrice source,
			ReadableProductPrice target, MerchantStore store, Language language, Boolean isShowProductPriceCurrencyCode)
			throws ConversionException {
		Validate.notNull(pricingService,"pricingService must be set");
		Validate.notNull(source.getProductAvailability(),"productPrice.availability cannot be null");
		Validate.notNull(source.getProductAvailability().getProduct(),"productPrice.availability.product cannot be null");
		
		try {
		  
		    if(language == null) {
		      target = new ReadableProductPriceFull();
		    }
		    
		    if(source.getId() != null && source.getId() > 0) {
		      target.setId(source.getId());
		    }
		    
		    target.setDefaultPrice(source.isDefaultPrice());

			FinalPrice finalPrice = null;
			if (source.getProductAvailability() != null && source.getProductAvailability().getProductVariant() !=null){
				finalPrice = pricingService.calculateProductPrice(source.getProductAvailability(), isShowProductPriceCurrencyCode);
			}else {
				finalPrice = pricingService.calculateProductPrice(source.getProductAvailability().getProduct(), isShowProductPriceCurrencyCode);
			}
			if (source.getCurrency().equals("CNY") && source.getProductPriceAmount() != null
					&& (isShowProductPriceCurrencyCode == null || !isShowProductPriceCurrencyCode)){
				BigDecimal productPriceAmount = examRateConfig.getRate(ExchangeRateEnums.CNY_KRW).multiply(source.getProductPriceAmount()).setScale(0, RoundingMode.HALF_UP);
				target.setOriginalPrice(pricingService.getDisplayAmount(productPriceAmount, store));
			}else {
				target.setOriginalPrice(pricingService.getDisplayAmount(source.getProductPriceAmount(), store));
			}

			if(finalPrice.isDiscounted()) {
				target.setDiscounted(true);
				target.setFinalPrice(pricingService.getDisplayAmount(source.getProductPriceSpecialAmount(), store));
			} else {
				target.setFinalPrice(pricingService.getDisplayAmount(finalPrice.getOriginalPrice(), store));
			}
			
//		    if(source.getDescriptions()!=null && source.getDescriptions().size()>0) {
//		       List<com.salesmanager.shop.model.catalog.product.ProductPriceDescription> fulldescriptions = new ArrayList<com.salesmanager.shop.model.catalog.product.ProductPriceDescription>();
//
//               Set<ProductPriceDescription> descriptions = source.getDescriptions();
//               ProductPriceDescription description = null;
//               for(ProductPriceDescription desc : descriptions) {
//                   if(language != null && desc.getLanguage().getCode().equals(language.getCode())) {
//                       description = desc;
//                       break;
//                   } else {
//                     fulldescriptions.add(populateDescription(desc));
//                   }
//               }
//
//
//               if (description != null) {
//                   com.salesmanager.shop.model.catalog.product.ProductPriceDescription d = populateDescription(description);
//                   target.setDescription(d);
//               }
//
//               if(target instanceof ReadableProductPriceFull) {
//                 ((ReadableProductPriceFull)target).setDescriptions(fulldescriptions);
//               }
//		    }


		} catch(Exception e) {
			throw new ConversionException("Exception while converting to ReadableProductPrice",e);
		}
		
		
		
		return target;
	}

	protected ReadableProductPrice createTarget() {
		// TODO Auto-generated method stub
		return null;
	}
	
	com.salesmanager.shop.model.catalog.product.ProductPriceDescription populateDescription(
	      ProductPriceDescription description) {
	    if (description == null) {
	      return null;
	    }
	    com.salesmanager.shop.model.catalog.product.ProductPriceDescription d =
	        new com.salesmanager.shop.model.catalog.product.ProductPriceDescription();
	    d.setName(description.getName());
	    d.setDescription(description.getDescription());
	    d.setId(description.getId());
	    d.setTitle(description.getTitle());
	    if (description.getLanguage() != null) {
	      d.setLanguage(description.getLanguage().getCode());
	    }
	    return d;
	 }

}
