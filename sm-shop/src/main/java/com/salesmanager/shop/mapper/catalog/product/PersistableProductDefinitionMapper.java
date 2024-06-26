package com.salesmanager.shop.mapper.catalog.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.salesmanager.core.business.constants.Constants;
import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.services.catalog.category.CategoryService;
import com.salesmanager.core.business.services.catalog.product.manufacturer.ManufacturerService;
import com.salesmanager.core.business.services.catalog.product.type.ProductTypeService;
import com.salesmanager.core.business.services.reference.language.LanguageService;
import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.attribute.ProductAttribute;
import com.salesmanager.core.model.catalog.product.availability.ProductAvailability;
import com.salesmanager.core.model.catalog.product.description.ProductDescription;
import com.salesmanager.core.model.catalog.product.manufacturer.Manufacturer;
import com.salesmanager.core.model.catalog.product.price.ProductPrice;
import com.salesmanager.core.model.catalog.product.price.ProductPriceDescription;
import com.salesmanager.core.model.catalog.product.type.ProductType;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.mapper.Mapper;
import com.salesmanager.shop.mapper.catalog.PersistableProductAttributeMapper;
import com.salesmanager.shop.model.catalog.product.ProductPriceEntity;
import com.salesmanager.shop.model.catalog.product.product.definition.PersistableProductDefinition;
import com.salesmanager.shop.store.api.exception.ConversionRuntimeException;
import com.salesmanager.shop.utils.DateUtil;

@Component
public class PersistableProductDefinitionMapper implements Mapper<PersistableProductDefinition, Product> {

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private LanguageService languageService;
	@Autowired
	private PersistableProductAttributeMapper persistableProductAttributeMapper;
	
	@Autowired
	private ProductTypeService productTypeService;
	
	@Autowired
	private ManufacturerService manufacturerService;
	
	@Override
	public Product convert(PersistableProductDefinition source, MerchantStore store, Language language) {
		Product product = new Product();
		return this.merge(source, product, store, language);
	}

	@Override
	public Product merge(PersistableProductDefinition source, Product destination, MerchantStore store,
			Language language) {

		
		  
	    Validate.notNull(destination,"Product must not be null");

		try {

			//core properties
			
			if(StringUtils.isBlank(source.getIdentifier())) {
				destination.setSku(source.getSku());
			} else {
				destination.setSku(source.getIdentifier());
			}
			destination.setAvailable(source.isVisible());
			destination.setDateAvailable(new Date());

			destination.setRefSku(source.getIdentifier());
			destination.setOutProductId(source.getOutProductId());
			
			if(source.getId() != null && source.getId().longValue()==0) {
				destination.setId(null);
			} else {
				destination.setId(source.getId());
			}
			
			if(!StringUtils.isBlank(source.getDateAvailable())) {
				destination.setDateAvailable(DateUtil.getDate(source.getDateAvailable()));
			}

			List<Language> languages = new ArrayList<Language>();
			Set<ProductDescription> descriptions = new HashSet<ProductDescription>();
			if(!CollectionUtils.isEmpty(source.getDescriptions())) {
				for(com.salesmanager.shop.model.catalog.product.ProductDescription description : source.getDescriptions()) {

				  ProductDescription productDescription = new ProductDescription();
				  Language lang = languageService.getByCode(description.getLanguage());
	              if(lang==null) {
	                    throw new ConversionException("Language code " + description.getLanguage() + " is invalid, use ISO code (en, fr ...)");
	               }
				   if(!CollectionUtils.isEmpty(destination.getDescriptions())) {
				      for(ProductDescription desc : destination.getDescriptions()) {
				        if(desc.getLanguage().getCode().equals(description.getLanguage())) {
				          productDescription = desc;
				          break;
				        }
				      }
				    }

					productDescription.setProduct(destination);
					productDescription.setDescription(description.getDescription());

					productDescription.setProductHighlight(description.getHighlights());

					productDescription.setName(description.getName());
					productDescription.setSeUrl(description.getFriendlyUrl());
					productDescription.setMetatagKeywords(description.getKeyWords());
					productDescription.setMetatagDescription(description.getMetaDescription());
					productDescription.setTitle(description.getTitle());

					languages.add(lang);
					productDescription.setLanguage(lang);
					descriptions.add(productDescription);
				}
			}

			if(descriptions.size()>0) {
				destination.setDescriptions(descriptions);
			}

			destination.setSortOrder(source.getSortOrder());
			destination.setProductShipeable(source.isShipeable());
			destination.setMerchantStore(store);

			return destination;
		
		} catch (Exception e) {
			throw new ConversionRuntimeException("Error converting product mapper",e);
		}
	}

}
