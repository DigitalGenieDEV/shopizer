package com.salesmanager.shop.populator.recommend;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.services.catalog.pricing.PricingService;
import com.salesmanager.core.business.services.catalog.product.feature.ProductFeatureService;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.attribute.ProductAttribute;
import com.salesmanager.core.model.catalog.product.image.ProductImage;
import com.salesmanager.core.model.catalog.product.manufacturer.ManufacturerDescription;
import com.salesmanager.core.model.catalog.product.price.FinalPrice;
import com.salesmanager.core.model.catalog.product.type.ProductType;
import com.salesmanager.core.model.catalog.product.variant.ProductVariant;
import com.salesmanager.core.model.feature.ProductFeature;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.mapper.catalog.ReadableProductTypeMapper;
import com.salesmanager.shop.mapper.catalog.product.ReadableProductVariantMapper;
import com.salesmanager.shop.model.catalog.manufacturer.ReadableManufacturer;
import com.salesmanager.shop.model.catalog.product.*;
import com.salesmanager.shop.model.catalog.product.attribute.*;
import com.salesmanager.shop.model.catalog.product.product.ProductSpecification;
import com.salesmanager.shop.model.catalog.product.type.ProductTypeDescription;
import com.salesmanager.shop.model.catalog.product.type.ReadableProductType;
import com.salesmanager.shop.model.recommend.ReadableDisplayProduct;
import com.salesmanager.shop.model.store.ReadableMerchantStore;
import com.salesmanager.shop.populator.manufacturer.ReadableManufacturerPopulator;
import com.salesmanager.shop.populator.store.ReadableMerchantStorePopulator;
import com.salesmanager.shop.utils.ImageFilePath;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class ReadableDisplayProductPopulator extends
        AbstractDataPopulator<Product, ReadableDisplayProduct> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReadableDisplayProductPopulator.class);

    private PricingService pricingService;

    private ReadableMerchantStorePopulator readableMerchantStorePopulator;

    private  ReadableManufacturerPopulator readableManufacturerPopulator = new ReadableManufacturerPopulator();


    private ReadableProductVariantMapper readableProductVariantMapper;

    private ProductFeatureService productFeatureService;

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

    public ProductFeatureService getProductFeatureService() {
        return productFeatureService;
    }

    public void setProductFeatureService(ProductFeatureService productFeatureService) {
        this.productFeatureService = productFeatureService;
    }

    public ReadableMerchantStorePopulator getReadableMerchantStorePopulator() {
        return readableMerchantStorePopulator;
    }

    public void setReadableMerchantStorePopulator(ReadableMerchantStorePopulator readableMerchantStorePopulator) {
        this.readableMerchantStorePopulator = readableMerchantStorePopulator;
    }

    public ReadableManufacturerPopulator getReadableManufacturerPopulator() {
        return readableManufacturerPopulator;
    }

    public void setReadableManufacturerPopulator(ReadableManufacturerPopulator readableManufacturerPopulator) {
        this.readableManufacturerPopulator = readableManufacturerPopulator;
    }

    public ReadableProductVariantMapper getReadableProductVariantMapper() {
        return readableProductVariantMapper;
    }

    public void setReadableProductVariantMapper(ReadableProductVariantMapper readableProductVariantMapper) {
        this.readableProductVariantMapper = readableProductVariantMapper;
    }

    @Override
    protected ReadableDisplayProduct createTarget() {
        return null;
    }

    @Override
    public ReadableDisplayProduct populate(Product source, ReadableDisplayProduct target, MerchantStore store, Language language) throws ConversionException {
        Validate.notNull(pricingService, "Requires to set PricingService");
        Validate.notNull(imageUtils, "Requires to set imageUtils");

        //LOGGER.info("populate display product [" + source.getId() + "]");

        long start = System.currentTimeMillis();
        try {

            List<ProductDescription> fulldescriptions = new ArrayList<ProductDescription>();


            if(target==null) {
                target = new ReadableDisplayProduct();
            }

            com.salesmanager.core.model.catalog.product.description.ProductDescription description = source.getProductDescription();

            if(source.getDescriptions()!=null && source.getDescriptions().size()>0) {
                for(com.salesmanager.core.model.catalog.product.description.ProductDescription desc : source.getDescriptions()) {
                    if(language != null && desc.getLanguage()!=null && desc.getLanguage().getId().intValue() == language.getId().intValue()) {
                        description = desc;
                        break;
                    } else {
                        fulldescriptions.add(populateDescription(desc));
                    }
                }
            }

//            if(target instanceof ReadableProductFull) {
//                ((ReadableProductFull)target).setDescriptions(fulldescriptions);
//            }

            if(language == null) {
                language = store.getDefaultLanguage();
            }

            final Language lang = language;

            target.setId(source.getId());
            target.setAvailable(source.isAvailable());
            target.setProductShipeable(source.isProductShipeable());
//            ProductSpecification specifications = new ProductSpecification();
//            specifications.setHeight(source.getProductHeight());
//            specifications.setLength(source.getProductLength());
//            specifications.setWeight(source.getProductWeight());
//            specifications.setWidth(source.getProductWidth());
//            target.setProductSpecifications(specifications);

//            target.setPreOrder(source.isPreOrder());
//            target.setRefSku(source.getRefSku());
//            target.setSortOrder(source.getSortOrder());
//            target.setIdentifier(source.getSku());
            if(source.getType() != null) {
                target.setType(this.type(source.getType(), language));
            }

            if(source.getOwner() != null) {
                RentalOwner owner = new RentalOwner();
                owner.setId(source.getOwner().getId());
                owner.setEmailAddress(source.getOwner().getEmailAddress());
                owner.setFirstName(source.getOwner().getBilling().getFirstName());
                owner.setLastName(source.getOwner().getBilling().getLastName());
                com.salesmanager.shop.model.customer.address.Address address = new com.salesmanager.shop.model.customer.address.Address();
                address.setAddress(source.getOwner().getBilling().getAddress());
                address.setBillingAddress(true);
                address.setCity(source.getOwner().getBilling().getCity());
                address.setCompany(source.getOwner().getBilling().getCompany());
                address.setCountry(source.getOwner().getBilling().getCountry().getIsoCode());
                address.setZone(source.getOwner().getBilling().getZone().getCode());
                address.setLatitude(source.getOwner().getBilling().getLatitude());
                address.setLongitude(source.getOwner().getBilling().getLongitude());
                address.setPhone(source.getOwner().getBilling().getTelephone());
                address.setPostalCode(source.getOwner().getBilling().getPostalCode());
                owner.setAddress(address);
            }

            if (source.getMerchantStore() != null) {
                target.setMerchantStore(readableMerchantStorePopulator.populate(source.getMerchantStore(), new ReadableMerchantStore(), store, language));
            }

            if (source.getManufacturer() != null) {
                target.setManufacturer(readableManufacturerPopulator.populate(source.getManufacturer(), new ReadableManufacturer(), store, language));
            }

            //LOGGER.info("populate display product [" + source.getId() + "] description");

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

                    imageList.add(prdImage);
                }
                imageList = imageList.stream()
                        .sorted(Comparator.comparingInt(ReadableImage::getOrder))
                        .collect(Collectors.toList());

                target
                        .setImages(imageList);
            }

            if (target.getImage() == null && (target.getImages() != null && target.getImages().size() > 0)) {
                target.setImage(target.getImages().get(0));
            }


//            LOGGER.info("populate recommend product [" + source.getId() + "] 3333");
            if(!CollectionUtils.isEmpty(source.getAttributes())) {

//                Set<ProductAttribute> attributes = source.getAttributes();


                //split read only and options
                //Map<Long,ReadableProductAttribute> readOnlyAttributes = null;
//                Map<Long, ReadableProductProperty> properties = null;
//                Map<Long, ReadableProductOption> selectableOptions = null;

//                if(!CollectionUtils.isEmpty(attributes)) {
//
//                    for(ProductAttribute attribute : attributes) {
//                        ReadableProductOption opt = null;
//                        ReadableProductAttribute attr = null;
//                        ReadableProductProperty property = null;
//                        ReadableProductPropertyValue propertyValue = null;
//                        ReadableProductOptionValue optValue = new ReadableProductOptionValue();
//                        ReadableProductAttributeValue attrValue = new ReadableProductAttributeValue();
//
//                        com.salesmanager.core.model.catalog.product.attribute.ProductOptionValue optionValue = attribute.getProductOptionValue();
//
//                        if(attribute.getAttributeDisplayOnly()) {
//                            property = createProperty(attribute, language);
//
//                            ReadableProductOption readableOption = new ReadableProductOption(); //that is the property
//                            ReadableProductPropertyValue readableOptionValue = new ReadableProductPropertyValue();
//
//                            readableOption.setCode(attribute.getProductOption().getCode());
//                            readableOption.setId(attribute.getProductOption().getId());
//
//                            Set<com.salesmanager.core.model.catalog.product.attribute.ProductOptionDescription> podescriptions = attribute.getProductOption().getDescriptions();
//                            if(podescriptions!=null && podescriptions.size()>0) {
//                                for(com.salesmanager.core.model.catalog.product.attribute.ProductOptionDescription optionDescription : podescriptions) {
//                                    if(optionDescription.getLanguage().getCode().equals(language.getCode())) {
//                                        readableOption.setName(optionDescription.getName());
//                                    }
//                                }
//                            }
//
//                            property.setProperty(readableOption);
//
//                            Set<com.salesmanager.core.model.catalog.product.attribute.ProductOptionValueDescription> povdescriptions = attribute.getProductOptionValue().getDescriptions();
//                            readableOptionValue.setId(attribute.getProductOptionValue().getId());
//                            if(povdescriptions!=null && povdescriptions.size()>0) {
//                                for(com.salesmanager.core.model.catalog.product.attribute.ProductOptionValueDescription optionValueDescription : povdescriptions) {
//                                    if(optionValueDescription.getLanguage().getCode().equals(language.getCode())) {
//                                        readableOptionValue.setName(optionValueDescription.getName());
//                                    }
//                                }
//                            }
//
//                            property.setPropertyValue(readableOptionValue);
//
//
//                            //} else{
//                            //	properties.put(attribute.getProductOption().getId(), property);
//                            //}
//
///*								propertyValue.setCode(attribute.getProductOptionValue().getCode());
//								propertyValue.setId(attribute.getProductOptionValue().getId());
//
//
//								propertyValue.setSortOrder(0);
//								if(attribute.getProductOptionSortOrder()!=null) {
//									propertyValue.setSortOrder(attribute.getProductOptionSortOrder().intValue());
//								}
//
//								List<ProductOptionValueDescription> podescriptions = optionValue.getDescriptionsSettoList();
//								if(podescriptions!=null && podescriptions.size()>0) {
//									for(ProductOptionValueDescription optionValueDescription : podescriptions) {
//										com.salesmanager.shop.model.catalog.product.attribute.ProductOptionValueDescription desc = new com.salesmanager.shop.model.catalog.product.attribute.ProductOptionValueDescription();
//										desc.setId(optionValueDescription.getId());
//										desc.setName(optionValueDescription.getName());
//										propertyValue.getValues().add(desc);
//									}
//								}
//
//								property.setPropertyValue(propertyValue);*/
//
//                            //if(attr!=null) {
//                            //	attr.getAttributeValues().add(attrValue);
//                            //}
////                            target.getProperties().add(property);
//
//
//                        } else {//selectable option
//
//                            if(selectableOptions==null) {
//                                selectableOptions = new TreeMap<Long,ReadableProductOption>();
//                            }
//                            opt = selectableOptions.get(attribute.getProductOption().getId());
//                            if(opt==null) {
//                                opt = createOption(attribute, language);
//                            }
//                            if(opt!=null) {
//                                selectableOptions.put(attribute.getProductOption().getId(), opt);
//                            }
//
//                            optValue.setDefaultValue(attribute.getAttributeDefault());
//                            //optValue.setId(attribute.getProductOptionValue().getId());
//                            optValue.setId(attribute.getId());
//                            optValue.setCode(attribute.getProductOptionValue().getCode());
//                            com.salesmanager.shop.model.catalog.product.attribute.ProductOptionValueDescription valueDescription = new com.salesmanager.shop.model.catalog.product.attribute.ProductOptionValueDescription();
//                            valueDescription.setLanguage(language.getCode());
//                            //optValue.setLang(language.getCode());
//                            if(attribute.getProductAttributePrice()!=null && attribute.getProductAttributePrice().doubleValue()>0) {
//                                String formatedPrice = pricingService.getDisplayAmount(attribute.getProductAttributePrice(), store);
//                                optValue.setPrice(formatedPrice);
//                            }
//
//                            if(!StringUtils.isBlank(attribute.getProductOptionValue().getProductOptionValueImage())) {
//                                optValue.setImage(imageUtils.buildProductPropertyImageUtils(store, attribute.getProductOptionValue().getProductOptionValueImage()));
//                            }
//                            optValue.setSortOrder(0);
//                            if(attribute.getProductOptionSortOrder()!=null) {
//                                optValue.setSortOrder(attribute.getProductOptionSortOrder().intValue());
//                            }
//
//                            List<com.salesmanager.core.model.catalog.product.attribute.ProductOptionValueDescription> podescriptions = optionValue.getDescriptionsSettoList();
//                            com.salesmanager.core.model.catalog.product.attribute.ProductOptionValueDescription podescription = null;
//                            if(podescriptions!=null && podescriptions.size()>0) {
//                                podescription = podescriptions.get(0);
//                                if(podescriptions.size()>1) {
//                                    for(com.salesmanager.core.model.catalog.product.attribute.ProductOptionValueDescription optionValueDescription : podescriptions) {
//                                        if(optionValueDescription.getLanguage().getId().intValue()==language.getId().intValue()) {
//                                            podescription = optionValueDescription;
//                                            break;
//                                        }
//                                    }
//                                }
//                            }
//                            if (podescription != null) {
//                                valueDescription.setName(podescription.getName());
//                                valueDescription.setDescription(podescription.getDescription());
//                            }
//                            optValue.setDescription(valueDescription);
//
//                            if(opt!=null) {
//                                opt.getOptionValues().add(optValue);
//                            }
//                        }
//
//                    }
//
//                }
//
//                if(selectableOptions != null) {
//                    List<ReadableProductOption> options = new ArrayList<ReadableProductOption>(selectableOptions.values());
////                    target.setOptions(options);
//                }


            }

            //LOGGER.info("populate display product [" + source.getId() + "] variant sku");
            // variants
            if (!CollectionUtils.isEmpty(source.getVariants()))

            {
//                List<ReadableProductVariant> instances = source
//                        .getVariants().stream()
//                        .map(i -> readableProductVariantMapper.convert(i, store, lang)).collect(Collectors.toList());
//                target.setVariants(instances);
                Optional<ProductVariant> productVariantOptional = source.getVariants().stream().findFirst();
                if (productVariantOptional.isPresent()) {
                    target.setSku(productVariantOptional.get().getSku());
                }
            }


            //LOGGER.info("populate display product [" + source.getId() + "] feature");
            List<ProductFeature> listByProductId = productFeatureService.findListByProductId(source.getId());
            if (!CollectionUtils.isEmpty(listByProductId)){
                List<String> collect = listByProductId.stream().filter(s -> s.getValue().equals("1")).map(ProductFeature::getKey).collect(Collectors.toList());
                target.setProductTags(collect);
            }

//            target.setSku(source.getSku());

            //LOGGER.info("populate display product [" + source.getId() + "] price");

            //availability
//            ProductAvailability availability = null;
//            for(ProductAvailability a : source.getAvailabilities()) {
//                availability = a;
//                target.setQuantity(availability.getProductQuantity() == null ? 1:availability.getProductQuantity());
//                target.setQuantityOrderMaximum(availability.getProductQuantityOrderMax() == null ? 1:availability.getProductQuantityOrderMax());
//                target.setQuantityOrderMinimum(availability.getProductQuantityOrderMin()==null ? 1:availability.getProductQuantityOrderMin());
//                if(availability.getProductQuantity().intValue() > 0 && target.isAvailable()) {
//                    target.setCanBePurchased(true);
//                }
//            }

            FinalPrice price = pricingService.calculateProductPrice(source, false);

            if(price != null) {
                target.setFinalPrice(pricingService.getDisplayAmount(price.getFinalPrice(), store));
                target.setPrice(price.getFinalPrice());
                target.setOriginalPrice(pricingService.getDisplayAmount(price.getOriginalPrice(), store));

                if(price.isDiscounted()) {
                    target.setDiscounted(true);
                }

                //price appender
//                if(availability != null) {
//                    Set<com.salesmanager.core.model.catalog.product.price.ProductPrice> prices = availability.getPrices();
//                    if(!CollectionUtils.isEmpty(prices)) {
//                        ReadableProductPrice readableProductPrice = new ReadableProductPrice();
//                        readableProductPrice.setDiscounted(target.isDiscounted());
//                        readableProductPrice.setFinalPrice(target.getFinalPrice());
//                        readableProductPrice.setOriginalPrice(target.getOriginalPrice());
//
//                        Optional<com.salesmanager.core.model.catalog.product.price.ProductPrice> pr = prices.stream().filter(p -> p.getCode().equals(com.salesmanager.core.model.catalog.product.price.ProductPrice.DEFAULT_PRICE_CODE))
//                                .findFirst();
//
//                        target.setProductPrice(readableProductPrice);
//
//                        if(pr.isPresent()) {
//                            readableProductPrice.setId(pr.get().getId());
//                            Optional<com.salesmanager.core.model.catalog.product.price.ProductPriceDescription> d = pr.get().getDescriptions().stream().filter(desc -> desc.getLanguage().getCode().equals(lang.getCode())).findFirst();
//                            if(d.isPresent()) {
//                                com.salesmanager.shop.model.catalog.product.ProductPriceDescription priceDescription = new com.salesmanager.shop.model.catalog.product.ProductPriceDescription();
//                                priceDescription.setLanguage(language.getCode());
//                                priceDescription.setId(d.get().getId());
//                                priceDescription.setPriceAppender(d.get().getPriceAppender());
//                                readableProductPrice.setDescription(priceDescription);
//                            }
//                        }
//
//                    }
//                }

            }


            //LOGGER.info("populate display product [" + source.getId() + "] done");

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
        List<com.salesmanager.core.model.catalog.product.attribute.ProductOptionDescription> descriptions = productAttribute.getProductOption().getDescriptionsSettoList();
        com.salesmanager.core.model.catalog.product.attribute.ProductOptionDescription description = null;
        if(descriptions!=null && descriptions.size()>0) {
            description = descriptions.get(0);
            if(descriptions.size()>1) {
                for(com.salesmanager.core.model.catalog.product.attribute.ProductOptionDescription optionDescription : descriptions) {
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
        List<com.salesmanager.core.model.catalog.product.attribute.ProductOptionDescription> descriptions = productAttribute.getProductOption().getDescriptionsSettoList();
        com.salesmanager.core.model.catalog.product.attribute.ProductOptionDescription description = null;
        if(descriptions!=null && descriptions.size()>0) {
            description = descriptions.get(0);
            if(descriptions.size()>1) {
                for(com.salesmanager.core.model.catalog.product.attribute.ProductOptionDescription optionDescription : descriptions) {
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




        List<com.salesmanager.core.model.catalog.product.attribute.ProductOptionDescription> descriptions = productAttribute.getProductOption().getDescriptionsSettoList();

        ReadableProductPropertyValue propertyValue = new ReadableProductPropertyValue();


        if(descriptions!=null && descriptions.size()>0) {
            for(com.salesmanager.core.model.catalog.product.attribute.ProductOptionDescription optionDescription : descriptions) {
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

    com.salesmanager.shop.model.catalog.product.ProductDescription populateDescription(com.salesmanager.core.model.catalog.product.description.ProductDescription description) {
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
