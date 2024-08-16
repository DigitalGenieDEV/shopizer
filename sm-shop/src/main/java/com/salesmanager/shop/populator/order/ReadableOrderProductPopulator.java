package com.salesmanager.shop.populator.order;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.fulfillment.service.InvoicePackingFormService;
import com.salesmanager.core.business.services.catalog.pricing.PricingService;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.business.utils.ObjectConvert;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.image.ProductImage;
import com.salesmanager.core.model.fulfillment.InvoicePackingForm;
import com.salesmanager.core.model.fulfillment.InvoicePackingFormDetail;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.orderproduct.OrderProduct;
import com.salesmanager.core.model.order.orderproduct.OrderProductAttribute;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.shipping.ShippingOption;
import com.salesmanager.shop.model.catalog.product.ReadableProduct;
import com.salesmanager.shop.model.customer.ReadableBilling;
import com.salesmanager.shop.model.customer.ReadableDelivery;
import com.salesmanager.shop.model.fulfillment.ReadableInvoicePackingForm;
import com.salesmanager.shop.model.fulfillment.ReadableInvoicePackingFormDetail;
import com.salesmanager.shop.model.order.ReadableOrderProduct;
import com.salesmanager.shop.model.order.ReadableOrderProductAttribute;
import com.salesmanager.shop.populator.catalog.ReadableProductPopulator;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.utils.ImageFilePath;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Use mappers
 * @author carlsamson
 *
 */
public class ReadableOrderProductPopulator extends
		AbstractDataPopulator<OrderProduct, ReadableOrderProduct> {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReadableOrderProductPopulator.class);

	private ProductService productService;
	private PricingService pricingService;
	private ImageFilePath imageUtils;


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

		if (invoicePackingFormService != null){
			InvoicePackingForm invoicePackingForm = invoicePackingFormService.queryInvoicePackingFormByOrderIdAndProductId(source.getId(), source.getProductId());
			if (invoicePackingForm != null) {
				target.setInvoicePackingForm(convertToReadableInvoicePackingForm(invoicePackingForm));
			}
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

		if(source.getOrder().getDelivery()!=null) {
			ReadableDelivery address = new ReadableDelivery();
			address.setCity(source.getOrder().getDelivery().getCity());
			address.setAddress(source.getOrder().getDelivery().getAddress());
			address.setCompany(source.getOrder().getDelivery().getCompany());
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


			String productSku = source.getSku();
			if(!StringUtils.isBlank(productSku)) {
				Product product = null;
				try {
					product = productService.getBySku(productSku);
//					product = productService.getBySku(productSku, store, language);
				} catch (ServiceException e) {
					throw new ServiceRuntimeException(e);
				}
				if(product!=null) {
					
					
					
					ReadableProductPopulator populator = new ReadableProductPopulator();
					populator.setPricingService(pricingService);
					populator.setimageUtils(imageUtils);
					
					ReadableProduct productProxy = populator.populate(product, new ReadableProduct(), store, language);
					target.setProduct(productProxy);
					target.setHsCode(product.getHsCode());
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
}
