package com.salesmanager.shop.populator.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.salesmanager.core.business.services.reference.country.CountryService;
import com.salesmanager.core.model.order.OrderType;
import com.salesmanager.core.model.payments.ImportMainEnums;
import com.salesmanager.shop.store.controller.customer.facade.CustomerOrderFacadeImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.business.services.reference.currency.CurrencyService;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.common.Billing;
import com.salesmanager.core.model.common.Delivery;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.Order;
import com.salesmanager.core.model.order.OrderChannel;
import com.salesmanager.core.model.order.attributes.OrderAttribute;
import com.salesmanager.core.model.order.orderstatus.OrderStatus;
import com.salesmanager.core.model.order.orderstatus.OrderStatusHistory;
import com.salesmanager.core.model.payments.PaymentType;
import com.salesmanager.core.model.reference.currency.Currency;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.customer.PersistableCustomer;
import com.salesmanager.shop.model.order.v1.PersistableAnonymousOrder;
import com.salesmanager.shop.model.order.v1.PersistableOrder;
import com.salesmanager.shop.populator.customer.CustomerPopulator;
import com.salesmanager.shop.utils.LocaleUtils;

@Component
public class PersistableOrderApiPopulator extends AbstractDataPopulator<PersistableOrder, Order> {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersistableOrderApiPopulator.class);

	@Autowired
	private CurrencyService currencyService;
	@Autowired
	private CustomerService customerService;

	@Autowired
	private CountryService countryService;
/*	@Autowired
	private ShoppingCartService shoppingCartService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductAttributeService productAttributeService;
	@Autowired
	private DigitalProductService digitalProductService;*/
	@Autowired
	private CustomerPopulator customerPopulator;
	
	

	


	@Override
	public Order populate(PersistableOrder source, Order target, MerchantStore store, Language language)
			throws ConversionException {
		

/*		Validate.notNull(currencyService,"currencyService must be set");
		Validate.notNull(customerService,"customerService must be set");
		Validate.notNull(shoppingCartService,"shoppingCartService must be set");
		Validate.notNull(productService,"productService must be set");
		Validate.notNull(productAttributeService,"productAttributeService must be set");
		Validate.notNull(digitalProductService,"digitalProductService must be set");*/
		Validate.notNull(source.getPayment(),"Payment cannot be null");
		
		try {
			
			if(target == null) {
				target = new Order();
			}
		
			//target.setLocale(LocaleUtils.getLocale(store));

			target.setLocale(LocaleUtils.getLocale(store));
			
			
			Currency currency = null;
			try {
				currency = currencyService.getByCode(source.getCurrency());
			} catch(Exception e) {
				throw new ConversionException("Currency not found for code " + source.getCurrency());
			}
			
			if(currency==null) {
				throw new ConversionException("Currency not found for code " + source.getCurrency());
			}
			
			//Customer
			Customer customer = null;
			if(source.getCustomerId() != null && source.getCustomerId().longValue() >0) {
			  Long customerId = source.getCustomerId();
			  customer = customerService.getById(customerId);

			  if(customer == null) {
				throw new ConversionException("Curstomer with id " + source.getCustomerId() + " does not exist");
			  }
			  target.setCustomerId(customerId);
			
			} else {
			  if(source instanceof PersistableAnonymousOrder) {
			    PersistableCustomer persistableCustomer = ((PersistableAnonymousOrder)source).getCustomer();
			    customer = new Customer();
			    customer = customerPopulator.populate(persistableCustomer, customer, store, language);
			  } else {
			    throw new ConversionException("Curstomer details or id not set in request");
			  } 
			}
			
			
			target.setCustomerEmailAddress(customer.getEmailAddress());
//			Delivery delivery = customer.getDelivery();
//			target.setDelivery(delivery);
//			Delivery delivery = customer.getDelivery();
			LOGGER.info("order populate get address:" + source.getAddress() + ", delivery" + customer.getDelivery());

			Delivery delivery = new Delivery();
			delivery.setAddress(source.getAddress().getAddress());
			delivery.setCity(source.getAddress().getCity());
			delivery.setCompany(source.getAddress().getCompany());
			delivery.setFirstName(source.getAddress().getFirstName());
			delivery.setLastName(source.getAddress().getLastName());
			delivery.setPostalCode(source.getAddress().getPostalCode());
			delivery.setTelephone(source.getAddress().getPhone());
			delivery.setCountry(countryService.getByCode(source.getAddress().getCountryCode()));
			delivery.setMessage(source.getAddress().getMessage());
			target.setDelivery(delivery);


//			Delivery delivery = customer.getDelivery();
//			target.setDelivery(delivery);
			
			Billing billing = customer.getBilling();
			target.setBilling(billing);
			
			if(source.getAttributes() != null && source.getAttributes().size() > 0) {
				Set<OrderAttribute> attrs = new HashSet<OrderAttribute>();
				for(com.salesmanager.shop.model.order.OrderAttribute attribute : source.getAttributes()) {
					OrderAttribute attr = new OrderAttribute();
					attr.setKey(attribute.getKey());
					attr.setValue(attribute.getValue());
					attr.setOrder(target);
					attrs.add(attr);
				}
				target.setOrderAttributes(attrs);
			}

			target.setDatePurchased(new Date());
			target.setCurrency(currency);
			target.setCurrencyValue(new BigDecimal(0));
			target.setMerchant(store);
			target.setChannel(OrderChannel.API);
			//need this
			target.setStatus(StringUtils.isEmpty(source.getStatus())?  OrderStatus.ORDERED : OrderStatus.fromValue(source.getStatus()));
			target.setPaymentModuleCode(source.getPayment().getPaymentModule());
			target.setPaymentType(PaymentType.valueOf(source.getPayment().getPaymentType()));
			
			target.setCustomerAgreement(source.isCustomerAgreement());
			target.setConfirmedAddress(true);//force this to true, cannot perform this activity from the API


			target.setImportMain(source.getImportMain() == null? null : ImportMainEnums.valueOf(source.getImportMain()));
			target.setCustomsClearanceNumber(source.getCustomsClearanceNumber());
			target.setOrderType(StringUtils.isEmpty(source.getOrderType())? null : OrderType.valueOf(source.getOrderType()));
			target.setOrderNo(source.getOrderNo());
			if(!StringUtils.isBlank(source.getComments())) {
				OrderStatusHistory statusHistory = new OrderStatusHistory();
				statusHistory.setStatus(null);
				statusHistory.setOrder(target);
				statusHistory.setComments(source.getComments());
				target.getOrderHistory().add(statusHistory);
			}
			
			return target;
		
		} catch(Exception e) {
			throw new ConversionException(e);
		}
	}

	@Override
	protected Order createTarget() {
		// TODO Auto-generated method stub
		return null;
	}


/*	public CurrencyService getCurrencyService() {
		return currencyService;
	}

	public void setCurrencyService(CurrencyService currencyService) {
		this.currencyService = currencyService;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public ShoppingCartService getShoppingCartService() {
		return shoppingCartService;
	}

	public void setShoppingCartService(ShoppingCartService shoppingCartService) {
		this.shoppingCartService = shoppingCartService;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public ProductAttributeService getProductAttributeService() {
		return productAttributeService;
	}

	public void setProductAttributeService(ProductAttributeService productAttributeService) {
		this.productAttributeService = productAttributeService;
	}

	public DigitalProductService getDigitalProductService() {
		return digitalProductService;
	}

	public void setDigitalProductService(DigitalProductService digitalProductService) {
		this.digitalProductService = digitalProductService;
	}*/



}
