package com.salesmanager.shop.populator.order;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


import com.salesmanager.core.business.fulfillment.service.FulfillmentMainOrderService;
import com.salesmanager.core.business.fulfillment.service.FulfillmentSubOrderService;
import com.salesmanager.core.business.fulfillment.service.GeneralDocumentService;
import com.salesmanager.core.business.fulfillment.service.InvoicePackingFormService;
import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.business.services.order.orderproduct.OrderProductService;
import com.salesmanager.core.business.utils.ObjectConvert;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.fulfillment.*;
import com.salesmanager.core.model.fulfillment.FulfillmentMainOrder;
import com.salesmanager.core.model.fulfillment.FulfillmentSubOrder;
import com.salesmanager.core.model.fulfillment.GeneralDocument;
import com.salesmanager.core.model.fulfillment.InvoicePackingForm;
import com.salesmanager.core.model.fulfillment.InvoicePackingFormDetail;
import com.salesmanager.core.model.order.orderproduct.OrderProduct;
import com.salesmanager.shop.listener.alibaba.tuna.fastjson.JSON;
import com.salesmanager.shop.model.customer.ReadableCustomer;
import com.salesmanager.shop.model.fulfillment.*;
import com.salesmanager.shop.store.controller.customer.facade.CustomerFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.services.reference.country.CountryService;
import com.salesmanager.core.business.services.reference.zone.ZoneService;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.Order;
import com.salesmanager.core.model.order.OrderTotal;
import com.salesmanager.core.model.order.OrderTotalType;
import com.salesmanager.core.model.order.attributes.OrderAttribute;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.customer.ReadableBilling;
import com.salesmanager.shop.model.customer.ReadableDelivery;
import com.salesmanager.shop.model.customer.address.Address;
import com.salesmanager.shop.model.order.v0.ReadableOrder;
import com.salesmanager.shop.model.store.ReadableMerchantStore;

import org.springframework.beans.factory.annotation.Qualifier;
import com.salesmanager.shop.populator.store.ReadableMerchantStorePopulator;
import com.salesmanager.shop.utils.ImageFilePath;

@Component
public class ReadableOrderPopulator extends
		AbstractDataPopulator<Order, ReadableOrder> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReadableOrderPopulator.class);
	
	@Autowired
	private CountryService countryService;
	@Autowired
	private ZoneService zoneService;
	
	@Autowired
	@Qualifier("img")
	private ImageFilePath filePath;
	
	@Autowired
	private ReadableMerchantStorePopulator readableMerchantStorePopulator;

	@Autowired
	private FulfillmentSubOrderService fulfillmentSubOrderService;

	@Autowired
	private GeneralDocumentService generalDocumentService;

	@Autowired
	private InvoicePackingFormService invoicePackingFormService;
	@Autowired
	private CustomerFacade customerFacade;

	@Autowired
	private OrderProductService orderProductService;

    @Autowired
    private ReadableOrderInvoicePopulator readableOrderInvoicePopulator;

	@Override
	public ReadableOrder populate(Order source, ReadableOrder target,
			MerchantStore store, Language language) throws ConversionException {
		
		target.setId(source.getId());
		target.setDatePurchased(source.getDatePurchased());
		target.setOrderStatus(source.getStatus());
		target.setCurrency(source.getCurrency().getCode());
		//target.setCurrencyModel(source.getCurrency());
		target.setOrderType(source.getOrderType() == null? null : source.getOrderType().name());
		target.setImportMain(source.getImportMain() == null? null : source.getImportMain().name());
		target.setCustomsClearanceNumber(source.getCustomsClearanceNumber());
		target.setDateCreated(source.getAuditSection() == null ? null : source.getAuditSection().getDateCreated());
		target.setDateModified(source.getAuditSection() == null ? null :source.getAuditSection().getDateModified());

		Long customerId = source.getCustomerId();
		if (source.getCustomerId() != null) {
			ReadableCustomer readableCustomer = customerFacade.getCustomerById(customerId,
					null, language);
			if (readableCustomer == null) {
				LOGGER.warn("Customer id " + customerId + " not found in order " );
			} else {
				target.setCustomer(readableCustomer);
			}
		}
		if (source.getFulfillmentMainOrder() != null) {
			FulfillmentMainOrder fulfillmentMainOrder = source.getFulfillmentMainOrder();

			ReadableFulfillmentMainOrder readableFulfillmentMainOrder = ObjectConvert.convert(fulfillmentMainOrder, ReadableFulfillmentMainOrder.class);

			Set<FulfillmentSubOrder> fulfillmentSubOrders = fulfillmentMainOrder.getFulfillSubOrders();;
			if (fulfillmentSubOrders != null) {
				Set<ReadableFulfillmentSubOrder> collect = fulfillmentSubOrders.stream()
						.map(this::convertToReadableFulfillmentSubOrder)
						.filter(Objects::nonNull)
						.collect(Collectors.toSet());

				readableFulfillmentMainOrder.setFulfillSubOrders(collect);
			}

			List<GeneralDocument> generalDocuments = generalDocumentService.queryGeneralDocumentByOrderId(source.getId());

			if (CollectionUtils.isNotEmpty(generalDocuments)) {
				List<ReadableGeneralDocument> readableGeneralDocuments = generalDocuments.stream()
						.map(generalDocument -> {
							ReadableGeneralDocument readableGeneralDocument = ObjectConvert.convert(generalDocument, ReadableGeneralDocument.class);
							readableGeneralDocument.setDocumentType(generalDocument.getDocumentType()==null? null : generalDocument.getDocumentType().name());
							return readableGeneralDocument;
						})
						.filter(Objects::nonNull)
						.collect(Collectors.toList());

				InvoicePackingForm invoicePackingForm = invoicePackingFormService.queryInvoicePackingFormByOrderId(source.getId());
				if (invoicePackingForm != null) {
					readableFulfillmentMainOrder.setInvoicePackingForm(convertToReadableInvoicePackingForm(invoicePackingForm));
				}

				readableFulfillmentMainOrder.setGeneralDocuments(readableGeneralDocuments);
			}
			target.setFulfillmentMainOrder(readableFulfillmentMainOrder);
		}


		target.setPaymentType(source.getPaymentType());
		target.setPaymentModule(source.getPaymentModuleCode());
		target.setShippingModule(source.getShippingModuleCode());
		
		if(source.getMerchant()!=null) {
/*			ReadableMerchantStorePopulator merchantPopulator = new ReadableMerchantStorePopulator();
			merchantPopulator.setCountryService(countryService);
			merchantPopulator.setFilePath(filePath);
			merchantPopulator.setZoneService(zoneService);*/
			ReadableMerchantStore readableStore = 
			readableMerchantStorePopulator.populate(source.getMerchant(), null, store, source.getMerchant().getDefaultLanguage());
			target.setStore(readableStore);
		}

		if(source.getCustomerAgreement()!=null) {
			target.setCustomerAgreed(source.getCustomerAgreement());
		}
		if(source.getConfirmedAddress()!=null) {
			target.setConfirmedAddress(source.getConfirmedAddress());
		}
		
		com.salesmanager.shop.model.order.total.OrderTotal taxTotal = null;
		com.salesmanager.shop.model.order.total.OrderTotal shippingTotal = null;
		com.salesmanager.shop.model.order.total.OrderTotal handingTotal = null;
		com.salesmanager.shop.model.order.total.OrderTotal additionalServiceTotal = null;
		com.salesmanager.shop.model.order.total.OrderTotal erpTotal = null;

		if(source.getBilling()!=null) {
			ReadableBilling address = new ReadableBilling();
			address.setEmail(source.getCustomerEmailAddress());
			address.setCity(source.getBilling().getCity());
			address.setAddress(source.getBilling().getAddress());
			address.setCompany(source.getBilling().getCompany());
			address.setFirstName(source.getBilling().getFirstName());
			address.setLastName(source.getBilling().getLastName());
			address.setPostalCode(source.getBilling().getPostalCode());
			address.setPhone(source.getBilling().getTelephone());
			if(source.getBilling().getCountry()!=null) {
				address.setCountry(source.getBilling().getCountry().getIsoCode());
			}
			if(source.getBilling().getZone()!=null) {
				address.setZone(source.getBilling().getZone().getCode());
			}
			
			target.setBilling(address);
		}
		
		if(source.getOrderAttributes()!=null && source.getOrderAttributes().size()>0) {
			for(OrderAttribute attr : source.getOrderAttributes()) {
				com.salesmanager.shop.model.order.OrderAttribute a = new com.salesmanager.shop.model.order.OrderAttribute();
				a.setKey(attr.getKey());
				a.setValue(attr.getValue());
				target.getAttributes().add(a);
			}
		}
		
		if(source.getDelivery()!=null) {
			ReadableDelivery address = new ReadableDelivery();
			address.setCity(source.getDelivery().getCity());
			address.setAddress(source.getDelivery().getAddress());
			address.setCompany(source.getDelivery().getCompany());
			address.setMessage(source.getDelivery().getMessage());
			address.setFirstName(source.getDelivery().getFirstName());
			address.setLastName(source.getDelivery().getLastName());
			address.setPostalCode(source.getDelivery().getPostalCode());
			address.setPhone(source.getDelivery().getTelephone());
			if(source.getDelivery().getCountry()!=null) {
				address.setCountry(source.getDelivery().getCountry().getIsoCode());
			}
			if(source.getDelivery().getZone()!=null) {
				address.setZone(source.getDelivery().getZone().getCode());
			}
			
			target.setDelivery(address);
		}

		if (source.getInvoice() != null) {
			target.setInvoice(readableOrderInvoicePopulator.populate(source.getInvoice(), source.getMerchant(), language));
		}
		
		List<com.salesmanager.shop.model.order.total.OrderTotal> totals = new ArrayList<com.salesmanager.shop.model.order.total.OrderTotal>();
		for(OrderTotal t : source.getOrderTotal()) {
			if(t.getOrderTotalType()==null) {
				continue;
			}
			if(t.getOrderTotalType().name().equals(OrderTotalType.TOTAL.name())) {
				com.salesmanager.shop.model.order.total.OrderTotal totalTotal = createTotal(t);
				target.setTotal(totalTotal);
				totals.add(totalTotal);
			}
			else if(t.getOrderTotalType().name().equals(OrderTotalType.TAX.name())) {
				com.salesmanager.shop.model.order.total.OrderTotal totalTotal = createTotal(t);
				if(taxTotal==null) {
					taxTotal = totalTotal;
				} else {
					BigDecimal v = taxTotal.getValue();
					v = v.add(totalTotal.getValue());
					taxTotal.setValue(v);
				}
				target.setTax(totalTotal);
				totals.add(totalTotal);
			}
			else if(t.getOrderTotalType().name().equals(OrderTotalType.SHIPPING.name())) {
				com.salesmanager.shop.model.order.total.OrderTotal totalTotal = createTotal(t);
				if(shippingTotal==null) {
					shippingTotal = totalTotal;
				} else {
					BigDecimal v = shippingTotal.getValue();
					v = v.add(totalTotal.getValue());
					shippingTotal.setValue(v);
				}
				target.setShipping(shippingTotal);
				totals.add(totalTotal);
			}
			else if(t.getOrderTotalType().name().equals(OrderTotalType.HANDLING.name())) {
				com.salesmanager.shop.model.order.total.OrderTotal totalTotal = createTotal(t);
				if(handingTotal==null) {
					handingTotal = totalTotal;
				} else {
					BigDecimal v = handingTotal.getValue();
					v = v.add(totalTotal.getValue());
					handingTotal.setValue(v);
				}
				target.setHandling(handingTotal);
				totals.add(totalTotal);
			}else if(t.getOrderTotalType().name().equals(OrderTotalType.ERP.name())) {
				com.salesmanager.shop.model.order.total.OrderTotal totalTotal = createTotal(t);
				if(erpTotal==null) {
					erpTotal = totalTotal;
				} else {
					BigDecimal v = erpTotal.getValue();
					v = v.add(totalTotal.getValue());
					erpTotal.setValue(v);
				}
				target.setErp(erpTotal);
				totals.add(totalTotal);
			}else if(t.getOrderTotalType().name().equals(OrderTotalType.ADDITIONAL_SERVICE.name())) {
				com.salesmanager.shop.model.order.total.OrderTotal totalTotal = createTotal(t);
				if(additionalServiceTotal==null) {
					additionalServiceTotal = totalTotal;
				} else {
					BigDecimal v = additionalServiceTotal.getValue();
					v = v.add(totalTotal.getValue());
					additionalServiceTotal.setValue(v);
				}
				target.setAdditionalService(additionalServiceTotal);
				totals.add(totalTotal);
			}
			else if(t.getOrderTotalType().name().equals(OrderTotalType.SUBTOTAL.name())) {
				com.salesmanager.shop.model.order.total.OrderTotal subTotal = createTotal(t);
				totals.add(subTotal);
				
			}
			else {
				com.salesmanager.shop.model.order.total.OrderTotal otherTotal = createTotal(t);
				totals.add(otherTotal);
			}
		}
		
		target.setTotals(totals);
		
		return target;
	}
	
	private com.salesmanager.shop.model.order.total.OrderTotal createTotal(OrderTotal t) {
		com.salesmanager.shop.model.order.total.OrderTotal totalTotal = new com.salesmanager.shop.model.order.total.OrderTotal();
		totalTotal.setCode(t.getOrderTotalCode());
		totalTotal.setId(t.getId());
		totalTotal.setModule(t.getModule());
		totalTotal.setOrder(t.getSortOrder());
		totalTotal.setValue(t.getValue());
		return totalTotal;
	}

	@Override
	protected ReadableOrder createTarget() {

		return null;
	}


	private ReadableFulfillmentSubOrder convertToReadableFulfillmentSubOrder(FulfillmentSubOrder fulfillmentSubOrder) {
		if (fulfillmentSubOrder == null) {
			return null;
		}

		ReadableFulfillmentSubOrder readableFulfillmentSubOrder = new ReadableFulfillmentSubOrder();
		try {
			readableFulfillmentSubOrder.setLogisticsNumberBy1688(fulfillmentSubOrder.getLogisticsNumberBy1688());
			readableFulfillmentSubOrder.setLogisticsNumber(fulfillmentSubOrder.getLogisticsNumber());
			readableFulfillmentSubOrder.setNationalLogisticsCompany(fulfillmentSubOrder.getNationalLogisticsCompany());
			readableFulfillmentSubOrder.setNationalShippingTime(fulfillmentSubOrder.getNationalShippingTime());
			readableFulfillmentSubOrder.setNationalDriverName(fulfillmentSubOrder.getNationalDriverName());
			readableFulfillmentSubOrder.setNationalDriverPhone(fulfillmentSubOrder.getNationalDriverPhone());

			readableFulfillmentSubOrder.setId(fulfillmentSubOrder.getId());

			if (fulfillmentSubOrder.getFulfillmentMainType() != null) {
				readableFulfillmentSubOrder.setFulfillmentMainType(fulfillmentSubOrder.getFulfillmentMainType().name());
			}

			if (fulfillmentSubOrder.getFulfillmentSubTypeEnums() != null) {
				readableFulfillmentSubOrder.setFulfillmentSubTypeEnums(fulfillmentSubOrder.getFulfillmentSubTypeEnums().name());
			}

			if (fulfillmentSubOrder.getInternationalTransportationMethod() != null) {
				readableFulfillmentSubOrder.setInternationalTransportationMethod(fulfillmentSubOrder.getInternationalTransportationMethod().name());
			}

			if (fulfillmentSubOrder.getNationalTransportationMethod() != null) {
				readableFulfillmentSubOrder.setNationalTransportationMethod(fulfillmentSubOrder.getNationalTransportationMethod().name());
			}

			if (fulfillmentSubOrder.getShippingType() != null) {
				readableFulfillmentSubOrder.setShippingType(fulfillmentSubOrder.getShippingType().name());
			}

		} catch (Exception e) {
			LOGGER.error("convertToReadableFulfillmentSubOrder error", e);
			return null;
		}

		return readableFulfillmentSubOrder;
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
