package com.salesmanager.shop.populator.catalog;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.services.catalog.pricing.PricingService;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.attribute.ProductAttribute;
import com.salesmanager.core.model.catalog.product.attribute.ProductOptionDescription;
import com.salesmanager.core.model.catalog.product.attribute.ProductOptionValue;
import com.salesmanager.core.model.catalog.product.attribute.ProductOptionValueDescription;
import com.salesmanager.core.model.catalog.product.availability.ProductAvailability;
import com.salesmanager.core.model.catalog.product.description.ProductDescription;
import com.salesmanager.core.model.catalog.product.image.ProductImage;
import com.salesmanager.core.model.catalog.product.manufacturer.ManufacturerDescription;
import com.salesmanager.core.model.catalog.product.price.FinalPrice;
import com.salesmanager.core.model.catalog.product.price.ProductPrice;
import com.salesmanager.core.model.catalog.product.price.ProductPriceDescription;
import com.salesmanager.core.model.catalog.product.type.ProductType;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.category.ReadableCategory;
import com.salesmanager.shop.model.catalog.manufacturer.ReadableManufacturer;
import com.salesmanager.shop.model.catalog.product.ReadableImage;
import com.salesmanager.shop.model.catalog.product.ReadableProduct;
import com.salesmanager.shop.model.catalog.product.ReadableProductFull;
import com.salesmanager.shop.model.catalog.product.ReadableProductPrice;
import com.salesmanager.shop.model.catalog.product.attribute.*;
import com.salesmanager.shop.model.catalog.product.attribute.api.ReadableProductOptionValue;
import com.salesmanager.shop.model.catalog.product.product.ProductSpecification;
import com.salesmanager.shop.model.catalog.product.type.ProductTypeDescription;
import com.salesmanager.shop.model.catalog.product.type.ReadableProductType;
import com.salesmanager.shop.utils.DateUtil;
import com.salesmanager.shop.utils.ImageFilePath;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.util.*;
import java.util.stream.Collectors;


public class ReadableProductSimplePopulator extends
		AbstractDataPopulator<Product, ReadableProduct> {

	private PricingService pricingService;

	private ImageFilePath imageUtils;

	public ImageFilePath getimageUtils() {
		return imageUtils;
	}

	public void setimageUtils(ImageFilePath imageUtils) {
		this.imageUtils = imageUtils;
	}

	public PricingService getPricingService() {
		return pricingService;
	}

	public void setPricingService(PricingService pricingService) {
		this.pricingService = pricingService;
	}

	@Override
	public ReadableProduct populate(Product source,
			ReadableProduct target, MerchantStore store, Language language)
			throws ConversionException {
		Validate.notNull(pricingService, "Requires to set PricingService");
		Validate.notNull(imageUtils, "Requires to set imageUtils");


		try {

	        List<com.salesmanager.shop.model.catalog.product.ProductDescription> fulldescriptions = new ArrayList<com.salesmanager.shop.model.catalog.product.ProductDescription>();
	        if(language == null) {
	          target = new ReadableProductFull();
	        }

	        if(target==null) {
	        	target = new ReadableProduct();
	        }

	        ProductDescription description = source.getProductDescription();

			if (source.getDescriptions() != null && source.getDescriptions().size() > 0) {
				for (ProductDescription desc : source.getDescriptions()) {
					if (language != null && desc.getLanguage() != null
							&& desc.getLanguage().getId().intValue() == language.getId().intValue()) {
						description = desc;
						break;
					}
				}
			}


			if(language == null) {
				language = store.getDefaultLanguage();
			}


		    final Language lang = language;

			target.setId(source.getId());
			target.setAvailable(source.isAvailable());
			target.setProductShipeable(source.isProductShipeable());
			ProductSpecification specifications = new ProductSpecification();
			specifications.setHeight(source.getProductHeight());
			specifications.setLength(source.getProductLength());
			specifications.setWeight(source.getProductWeight());
			specifications.setWidth(source.getProductWidth());
			target.setProductSpecifications(specifications);
			target.setProductAuditStatus(source.getProductAuditStatus() == null? null : source.getProductAuditStatus().name());
			target.setPreOrder(source.isPreOrder());
			target.setHsCode(source.getHsCode());
			target.setCertificationDocument(source.getCertificationDocument());
			target.setIntellectualPropertyDocuments(source.getIntellectualPropertyDocuments());
			target.setQuoteType(source.getQuoteType());
			target.setRefSku(source.getRefSku());
			target.setSortOrder(source.getSortOrder());
			target.setIdentifier(source.getSku());
			target.setOrderQuantityType(source.getOrderQuantityType());
			if(source.getType() != null) {
				target.setType(this.type(source.getType(), language));
			}



/*			if(source.getProductReviewAvg()!=null) {
				double avg = source.getProductReviewAvg().doubleValue();
				double rating = Math.round(avg * 2) / 2.0f;
				target.setRating(rating);
			}*/
/*			if(source.getProductReviewCount()!=null) {
				target.setRatingCount(source.getProductReviewCount().intValue());
			}*/
			if(description!=null) {
			    com.salesmanager.shop.model.catalog.product.ProductDescription tragetDescription = populateDescription(description);
				target.setDescription(tragetDescription);

			}

			if(source.getManufacturer()!=null) {
				ManufacturerDescription manufacturer = source.getManufacturer().getDescriptions().iterator().next();
				ReadableManufacturer manufacturerEntity = new ReadableManufacturer();
				com.salesmanager.shop.model.catalog.manufacturer.ManufacturerDescription d = new com.salesmanager.shop.model.catalog.manufacturer.ManufacturerDescription();
				d.setName(manufacturer.getName());
				manufacturerEntity.setDescription(d);
				manufacturerEntity.setId(source.getManufacturer().getId());
				manufacturerEntity.setOrder(source.getManufacturer().getOrder());
				manufacturerEntity.setCode(source.getManufacturer().getCode());
				target.setManufacturer(manufacturerEntity);
			}

/*			if(source.getType() != null) {
			  ReadableProductType type = new ReadableProductType();
			  type.setId(source.getType().getId());
			  type.setCode(source.getType().getCode());
			  type.setName(source.getType().getCode());//need name
			  target.setType(type);
			}*/

			/**
			 * TODO use ProductImageMapper
			 */
			Set<ProductImage> images = source.getImages();
			if(images!=null && images.size()>0) {
				List<ReadableImage> imageList = new ArrayList<ReadableImage>();

				String contextPath = imageUtils.getContextPath();

				for(ProductImage img : images) {
					ReadableImage prdImage = new ReadableImage();
					prdImage.setImageName(img.getProductImage());
					prdImage.setDefaultImage(img.isDefaultImage());
					prdImage.setOrder(img.getSortOrder() != null ? img.getSortOrder().intValue() : 0);

					if (img.getImageType() == 1 && img.getProductImageUrl()!=null) {
						prdImage.setImageUrl(img.getProductImageUrl());
					} else {
						StringBuilder imgPath = new StringBuilder();
						imgPath.append(contextPath).append(imageUtils.buildProductImageUtils(store, source.getSku(), img.getProductImage()));

						prdImage.setImageUrl(imgPath.toString());
					}
					prdImage.setId(img.getId());
					prdImage.setImageType(img.getImageType());
					if(img.getProductImageUrl()!=null){
						prdImage.setExternalUrl(img.getProductImageUrl());
					}
					if(img.getImageType()==1 && img.getProductImageUrl()!=null) {//video
						prdImage.setVideoUrl(img.getProductImageUrl());
					}

					if(prdImage.isDefaultImage()) {
						target.setImage(prdImage);
					}
				}

			}

			return target;

		} catch (Exception e) {
			throw new ConversionException(e);
		}
	}



	private ReadableProductOption createOption(ProductAttribute productAttribute, Language language) {


		ReadableProductOption option = new ReadableProductOption();
		option.setId(productAttribute.getProductOption().getId());//attribute of the option
		option.setType(productAttribute.getProductOption().getProductOptionType());
		option.setCode(productAttribute.getProductOption().getCode());
		List<ProductOptionDescription> descriptions = productAttribute.getProductOption().getDescriptionsSettoList();
		ProductOptionDescription description = null;
		if(descriptions!=null && descriptions.size()>0) {
			description = descriptions.get(0);
			if(descriptions.size()>1) {
				for(ProductOptionDescription optionDescription : descriptions) {
					if(optionDescription.getLanguage().getCode().equals(language.getCode())) {
						description = optionDescription;
						break;
					}
				}
			}
		}

		if(description==null) {
			return null;
		}

		option.setLang(language.getCode());
		option.setName(description.getName());
		option.setCode(productAttribute.getProductOption().getCode());


		return option;

	}

	private ReadableProductType type (ProductType type, Language language) {
		ReadableProductType readableType = new ReadableProductType();
		readableType.setCode(type.getCode());
		readableType.setId(type.getId());

		if(!CollectionUtils.isEmpty(type.getDescriptions())) {
			Optional<ProductTypeDescription> desc = type.getDescriptions().stream().filter(t -> t.getLanguage().getCode().equals(language.getCode()))
			.map(d -> typeDescription(d)).findFirst();
			if(desc.isPresent()) {
				readableType.setDescription(desc.get());
			}
		}

		return readableType;
	}

	private ProductTypeDescription typeDescription(com.salesmanager.core.model.catalog.product.type.ProductTypeDescription description) {
		ProductTypeDescription desc = new ProductTypeDescription();
		desc.setId(description.getId());
		desc.setName(description.getName());
		desc.setDescription(description.getDescription());
		desc.setLanguage(description.getLanguage().getCode());
		return desc;
	}

	private ReadableProductAttribute createAttribute(ProductAttribute productAttribute, Language language) {


		ReadableProductAttribute attr = new ReadableProductAttribute();
		attr.setId(productAttribute.getProductOption().getId());//attribute of the option
		attr.setType(productAttribute.getProductOption().getProductOptionType());
		List<ProductOptionDescription> descriptions = productAttribute.getProductOption().getDescriptionsSettoList();
		ProductOptionDescription description = null;
		if(descriptions!=null && descriptions.size()>0) {
			description = descriptions.get(0);
			if(descriptions.size()>1) {
				for(ProductOptionDescription optionDescription : descriptions) {
					if(optionDescription.getLanguage().getId().intValue()==language.getId().intValue()) {
						description = optionDescription;
						break;
					}
				}
			}
		}

		if(description==null) {
			return null;
		}

		attr.setLang(language.getCode());
		attr.setName(description.getName());
		attr.setCode(productAttribute.getProductOption().getCode());


		return attr;

	}

	private ReadableProductProperty createProperty(ProductAttribute productAttribute, Language language) {


		ReadableProductProperty attr = new ReadableProductProperty();
		attr.setId(productAttribute.getProductOption().getId());//attribute of the option
		attr.setType(productAttribute.getProductOption().getProductOptionType());




		List<ProductOptionDescription> descriptions = productAttribute.getProductOption().getDescriptionsSettoList();

		ReadableProductPropertyValue propertyValue = new ReadableProductPropertyValue();


		if(descriptions!=null && descriptions.size()>0) {
			for(ProductOptionDescription optionDescription : descriptions) {
				com.salesmanager.shop.model.catalog.product.attribute.ProductOptionValueDescription productOptionValueDescription = new com.salesmanager.shop.model.catalog.product.attribute.ProductOptionValueDescription();
				productOptionValueDescription.setId(optionDescription.getId());
				productOptionValueDescription.setLanguage(optionDescription.getLanguage().getCode());
				productOptionValueDescription.setName(optionDescription.getName());
				propertyValue.getValues().add(productOptionValueDescription);

			}
		}

		attr.setCode(productAttribute.getProductOption().getCode());
		return attr;

	}




	@Override
	protected ReadableProduct createTarget() {
		// TODO Auto-generated mepopulateDescriptionthod stub
		return null;
	}

    com.salesmanager.shop.model.catalog.product.ProductDescription populateDescription(ProductDescription description) {
      if(description == null) {
        return null;
      }

      com.salesmanager.shop.model.catalog.product.ProductDescription tragetDescription = new com.salesmanager.shop.model.catalog.product.ProductDescription();
      tragetDescription.setFriendlyUrl(description.getSeUrl());
      tragetDescription.setName(description.getName());
      tragetDescription.setId(description.getId());
      if(!StringUtils.isBlank(description.getMetatagTitle())) {
          tragetDescription.setTitle(description.getMetatagTitle());
      } else {
          tragetDescription.setTitle(description.getName());
      }
      tragetDescription.setMetaDescription(description.getMetatagDescription());
      tragetDescription.setDescription(description.getDescription());
      tragetDescription.setHighlights(description.getProductHighlight());
      tragetDescription.setLanguage(description.getLanguage().getCode());
      tragetDescription.setKeyWords(description.getMetatagKeywords());

      if(description.getLanguage() != null) {
        tragetDescription.setLanguage(description.getLanguage().getCode());
      }
      return tragetDescription;
    }

}
