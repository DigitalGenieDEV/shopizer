package com.salesmanager.shop.populator.order;

import com.alibaba.fastjson.JSON;
import com.google.api.client.util.Lists;
import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.fulfillment.service.InvoicePackingFormService;
import com.salesmanager.core.business.fulfillment.service.ShippingOrderService;
import com.salesmanager.core.business.fulfillment.service.impl.ShippingOrderServiceImpl;
import com.salesmanager.core.business.services.catalog.pricing.PricingService;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.business.services.catalog.product.variant.ProductVariantService;
import com.salesmanager.core.business.services.order.orderproduct.OrderProductSnapshotService;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.business.utils.ObjectConvert;
import com.salesmanager.core.enmus.TruckTransportationCompanyEnums;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.image.ProductImage;
import com.salesmanager.core.model.catalog.product.variant.ProductVariant;
import com.salesmanager.core.model.fulfillment.FulfillmentSubOrder;
import com.salesmanager.core.model.fulfillment.GeneralDocument;
import com.salesmanager.core.model.fulfillment.InvoicePackingForm;
import com.salesmanager.core.model.fulfillment.InvoicePackingFormDetail;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.OrderProductSnapshot;
import com.salesmanager.core.model.order.orderproduct.OrderProduct;
import com.salesmanager.core.model.order.orderproduct.OrderProductAttribute;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.shipping.ShippingOption;
import com.salesmanager.shop.mapper.catalog.ReadableCategoryMapper;
import com.salesmanager.shop.mapper.catalog.product.ReadableProductVariantMapper;
import com.salesmanager.shop.model.catalog.product.ReadableProduct;
import com.salesmanager.shop.model.catalog.product.ReadableProductSnapshot;
import com.salesmanager.shop.model.catalog.product.product.variant.ReadableProductVariant;
import com.salesmanager.shop.model.customer.ReadableBilling;
import com.salesmanager.shop.model.customer.ReadableDelivery;
import com.salesmanager.shop.model.fulfillment.*;
import com.salesmanager.shop.model.fulfillment.facade.FulfillmentFacade;
import com.salesmanager.shop.model.order.ReadableOrderProduct;
import com.salesmanager.shop.model.order.ReadableOrderProductAttribute;
import com.salesmanager.shop.model.order.v0.ReadableOrder;
import com.salesmanager.shop.populator.catalog.ReadableProductPopulator;
import com.salesmanager.shop.populator.catalog.ReadableProductSimplePopulator;
import com.salesmanager.shop.populator.store.ReadableMerchantStorePopulator;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.fulfillment.faced.convert.AdditionalServicesConvert;
import com.salesmanager.shop.utils.ImageFilePath;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Use mappers
 * @author carlsamson
 *
 */
public class ReadableOrderProductPopulator extends
		AbstractDataPopulator<OrderProduct, ReadableOrderProduct> {

	private AdditionalServicesConvert additionalServicesConvert;
	private ProductVariantService productVariantService;
	private ReadableProductVariantMapper readableProductVariantMapper;

	private OrderProductSnapshotService orderProductSnapshotService;
	private FulfillmentFacade fulfillmentFacade;
	private static final Logger LOGGER = LoggerFactory.getLogger(ReadableOrderProductPopulator.class);

	private ProductService productService;
	private PricingService pricingService;
	private ImageFilePath imageUtils;

	private ReadableMerchantStorePopulator readableMerchantStorePopulator;

	private ReadableCategoryMapper readableCategoryMapper;

	private InvoicePackingFormService invoicePackingFormService;

	public InvoicePackingFormService getInvoicePackingFormService() {
		return invoicePackingFormService;
	}

	public void setInvoicePackingFormService(InvoicePackingFormService invoicePackingFormService) {
		this.invoicePackingFormService = invoicePackingFormService;
	}

	public ImageFilePath getimageUtils() {
		return imageUtils;
	}

	public void setimageUtils(ImageFilePath imageUtils) {
		this.imageUtils = imageUtils;
	}

	@Override
	public ReadableOrderProduct populate(OrderProduct source,
			ReadableOrderProduct target, MerchantStore store, Language language)
			throws ConversionException {
		
		Validate.notNull(productService,"Requires ProductService");
		Validate.notNull(pricingService,"Requires PricingService");
		Validate.notNull(imageUtils,"Requires imageUtils");
		target.setId(source.getId());
		target.setOrderedQuantity(source.getProductQuantity());

		target.setShippingType(source.getShippingType() == null? null : source.getShippingType().name());
		target.setShippingTransportationType(source.getShippingTransportationType() == null? null : source.getShippingTransportationType().name());
		target.setInternationalTransportationMethod(source.getInternationalTransportationMethod() == null? null : source.getInternationalTransportationMethod().name());
		target.setNationalTransportationMethod(source.getNationalTransportationMethod() == null? null : source.getNationalTransportationMethod().name());
		target.setPlayThroughOption(source.getPlayThroughOption() == null? null : source.getPlayThroughOption().name());
		target.setTruckModel(source.getTruckModel() == null? null : source.getTruckModel().name());
		target.setTruckType(source.getTruckType() == null? null : source.getTruckType().name());

		target.setTruckTransportationCompany(source.getTruckTransportationCompany() == null? null : source.getTruckTransportationCompany().name());
		if(source.getQcInfo() != null){
			target.setQcInfoId(source.getQcInfo().getId());
		}

		target.setReadableFulfillmentSubOrder(fulfillmentFacade.queryFulfillmentSubOrderListByProductOrderId(source.getId()));

		if (source.getShippingDocumentOrder() !=null){
			ReadableShippingDocumentOrder readableShippingDocumentOrder = new ReadableShippingDocumentOrder();
			readableShippingDocumentOrder.setShippingNo(source.getShippingDocumentOrder().getShippingNo());
			readableShippingDocumentOrder.setId(source.getShippingDocumentOrder().getId());
			Set<GeneralDocument> generalDocuments = source.getShippingDocumentOrder().getGeneralDocuments();

			if (CollectionUtils.isNotEmpty(generalDocuments)) {
				List<ReadableGeneralDocument> readableGeneralDocuments = generalDocuments.stream()
						.map(generalDocument -> {
							ReadableGeneralDocument readableGeneralDocument = ObjectConvert.convert(generalDocument, ReadableGeneralDocument.class);
							readableGeneralDocument.setDocumentType(generalDocument.getDocumentType()==null? null : generalDocument.getDocumentType().name());
							return readableGeneralDocument;
						})
						.filter(Objects::nonNull)
						.collect(Collectors.toList());

				if (source.getShippingDocumentOrder().getInvoicePackingFormId()!=null){
					InvoicePackingForm invoicePackingForm = invoicePackingFormService.getById(source.getShippingDocumentOrder().getInvoicePackingFormId());
					if (invoicePackingForm != null) {
						readableShippingDocumentOrder.setInvoicePackingForm(convertToReadableInvoicePackingForm(invoicePackingForm));
					}
				}

				readableShippingDocumentOrder.setGeneralDocuments(readableGeneralDocuments);
			}

			target.setReadableShippingDocumentOrder(readableShippingDocumentOrder);
		}

		if (source.getDesign() != null) {
			ReadableOrderProductDesignPopulator readableOrderProductDesignPopulator = new ReadableOrderProductDesignPopulator();
			target.setReadableOrderProductDesign(readableOrderProductDesignPopulator.populate(source.getDesign(), store, language));
		}



		if (source.getOrder()!=null && source.getOrder().getOrderType()!=null){
			target.setOrderType(source.getOrder().getOrderType().name());
		}

		try {
			target.setPrice(pricingService.getDisplayAmount(source.getOneTimeCharge(), store));
		} catch(Exception e) {
			throw new ConversionException("Cannot convert price",e);
		}
		target.setProductName(source.getProductName());
		target.setSku(source.getSku());
		//subtotal = price * quantity
		BigDecimal subTotal = source.getOneTimeCharge();
		subTotal = subTotal.multiply(new BigDecimal(source.getProductQuantity()));
		
		try {
			String subTotalPrice = pricingService.getDisplayAmount(subTotal, store);
			target.setSubTotal(subTotalPrice);
		} catch(Exception e) {
			throw new ConversionException("Cannot format price",e);
		}
		
		if(source.getOrderAttributes()!=null) {
			List<ReadableOrderProductAttribute> attributes = new ArrayList<ReadableOrderProductAttribute>();
			for(OrderProductAttribute attr : source.getOrderAttributes()) {
				ReadableOrderProductAttribute readableAttribute = new ReadableOrderProductAttribute();
				try {
					String price = pricingService.getDisplayAmount(attr.getProductAttributePrice(), store);
					readableAttribute.setAttributePrice(price);
				} catch (ServiceException e) {
					throw new ConversionException("Cannot format price",e);
				}
				
				readableAttribute.setAttributeName(attr.getProductAttributeName());
				readableAttribute.setAttributeValue(attr.getProductAttributeValueName());
				attributes.add(readableAttribute);
			}
			target.setAttributes(attributes);
		}

		if (source.getOrder() != null) {
			target.setOrderId(source.getOrder().getId());
		}

		if(source.getOrder().getDelivery()!=null) {
			ReadableDelivery address = new ReadableDelivery();
			address.setCity(source.getOrder().getDelivery().getCity());
			address.setAddress(source.getOrder().getDelivery().getAddress());
			address.setCompany(source.getOrder().getDelivery().getCompany());
			address.setMessage(source.getOrder().getDelivery().getMessage());
			address.setFirstName(source.getOrder().getDelivery().getFirstName());
			address.setLastName(source.getOrder().getDelivery().getLastName());
			address.setPostalCode(source.getOrder().getDelivery().getPostalCode());
			address.setPhone(source.getOrder().getDelivery().getTelephone());
			if(source.getOrder().getDelivery().getCountry()!=null) {
				address.setCountry(source.getOrder().getDelivery().getCountry().getIsoCode());
			}
			if(source.getOrder().getDelivery().getZone()!=null) {
				address.setZone(source.getOrder().getDelivery().getZone().getCode());
			}

			target.setDelivery(address);
		}


		if(source.getOrder().getBilling()!=null) {
			ReadableBilling address = new ReadableBilling();
			address.setEmail(source.getOrder().getCustomerEmailAddress());
			address.setCity(source.getOrder().getBilling().getCity());
			address.setAddress(source.getOrder().getBilling().getAddress());
			address.setCompany(source.getOrder().getBilling().getCompany());
			address.setFirstName(source.getOrder().getBilling().getFirstName());
			address.setLastName(source.getOrder().getBilling().getLastName());
			address.setPostalCode(source.getOrder().getBilling().getPostalCode());
			address.setPhone(source.getOrder().getBilling().getTelephone());
			if(source.getOrder().getBilling().getCountry()!=null) {
				address.setCountry(source.getOrder().getBilling().getCountry().getIsoCode());
			}
			if(source.getOrder().getBilling().getZone()!=null) {
				address.setZone(source.getOrder().getBilling().getZone().getCode());
			}

			target.setBilling(address);
		}

		target.setPaymentType(source.getOrder().getPaymentType());
		target.setPaymentModule(source.getOrder().getPaymentModuleCode());

		target.setReadableProductAdditionalServices(
				additionalServicesConvert.convertToReadableAdditionalServicesByShoppingItem(source.getAdditionalServicesMap(), language, null));

		OrderProductSnapshot snapshotByOrderProductId = orderProductSnapshotService.findSnapshotByOrderProductId(source.getId());
		if (snapshotByOrderProductId !=null){
			String snapshot = snapshotByOrderProductId.getSnapshot();
			ReadableProduct readableProductSnapshot = JSON.parseObject(snapshot, ReadableProduct.class);
			target.setProduct(readableProductSnapshot);
		}else {
			String productSku = source.getSku();
			if(!StringUtils.isBlank(productSku)) {
				Product product = null;
				try {
					product = productService.getBySku(productSku);
				} catch (ServiceException e) {
					throw new ServiceRuntimeException(e);
				}
				if(product!=null) {

					ReadableProductSimplePopulator populator = new ReadableProductSimplePopulator();
					populator.setPricingService(pricingService);
					populator.setimageUtils(imageUtils);
					populator.setReadableCategoryMapper(readableCategoryMapper);
					populator.setReadableMerchantStorePopulator(readableMerchantStorePopulator);
					ReadableProduct productProxy = populator.populate(product, new ReadableProduct(), store, language);
					//没用的数据直接返回null
					ProductVariant productVariant = productVariantService.queryBySku(productSku);
					if (productVariant != null) {
						ReadableProductVariant convert = readableProductVariantMapper.convert(productVariant, store, language, false);
						productProxy.setVariants(Collections.singletonList(convert));
					}

					target.setProduct(productProxy);
					Set<ProductImage> images = product.getImages();
					ProductImage defaultImage = null;
					if(images!=null) {
						for(ProductImage image : images) {
							if(defaultImage==null) {
								defaultImage = image;
							}
							if(image.isDefaultImage()) {
								defaultImage = image;
							}
						}
					}
					if(defaultImage!=null) {
						target.setImage(defaultImage.getProductImage());
					}
				}
			}
		}
		return target;
	}

	@Override
	protected ReadableOrderProduct createTarget() {

		return null;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	
	public PricingService getPricingService() {
		return pricingService;
	}

	public void setPricingService(PricingService pricingService) {
		this.pricingService = pricingService;
	}


	private ReadableInvoicePackingForm convertToReadableInvoicePackingForm(InvoicePackingForm invoicePackingForm) {
		if (invoicePackingForm == null) {
			return null;
		}
		try {
			ReadableInvoicePackingForm readableInvoicePackingForm = ObjectConvert.convert(invoicePackingForm, ReadableInvoicePackingForm.class);

			Set<InvoicePackingFormDetail> invoicePackingFormDetails = invoicePackingForm.getInvoicePackingFormDetails();
			if (invoicePackingFormDetails != null) {
				List<ReadableInvoicePackingFormDetail> invoicePackingFormDetailList = invoicePackingFormDetails.stream()
						.map(invoicePackingFormDetail -> ObjectConvert.convert(invoicePackingFormDetail, ReadableInvoicePackingFormDetail.class))
						.filter(Objects::nonNull)
						.collect(Collectors.toList());
				readableInvoicePackingForm.setInvoicePackingFormDetails(invoicePackingFormDetailList);
			}

			return readableInvoicePackingForm;
		} catch (Exception e) {
			LOGGER.error("convertToReadableInvoicePackingForm error", e);
			return null;
		}
	}


	public ProductVariantService getProductVariantService() {
		return productVariantService;
	}

	public void setProductVariantService(ProductVariantService productVariantService) {
		this.productVariantService = productVariantService;
	}

	public ReadableProductVariantMapper getReadableProductVariantMapper() {
		return readableProductVariantMapper;
	}

	public void setReadableProductVariantMapper(ReadableProductVariantMapper readableProductVariantMapper) {
		this.readableProductVariantMapper = readableProductVariantMapper;
	}

	public FulfillmentFacade getFulfillmentFacade() {
		return fulfillmentFacade;
	}

	public void setFulfillmentFacade(FulfillmentFacade fulfillmentFacade) {
		this.fulfillmentFacade = fulfillmentFacade;
	}


	public AdditionalServicesConvert getAdditionalServicesConvert() {
		return additionalServicesConvert;
	}

	public void setAdditionalServicesConvert(AdditionalServicesConvert additionalServicesConvert) {
		this.additionalServicesConvert = additionalServicesConvert;
	}

	public ReadableMerchantStorePopulator getReadableMerchantStorePopulator() {
		return readableMerchantStorePopulator;
	}

	public void setReadableMerchantStorePopulator(ReadableMerchantStorePopulator readableMerchantStorePopulator) {
		this.readableMerchantStorePopulator = readableMerchantStorePopulator;
	}

	public ReadableCategoryMapper getReadableCategoryMapper() {
		return readableCategoryMapper;
	}

	public void setReadableCategoryMapper(ReadableCategoryMapper readableCategoryMapper) {
		this.readableCategoryMapper = readableCategoryMapper;
	}


	public OrderProductSnapshotService getOrderProductSnapshotService() {
		return orderProductSnapshotService;
	}

	public void setOrderProductSnapshotService(OrderProductSnapshotService orderProductSnapshotService) {
		this.orderProductSnapshotService = orderProductSnapshotService;
	}
}
