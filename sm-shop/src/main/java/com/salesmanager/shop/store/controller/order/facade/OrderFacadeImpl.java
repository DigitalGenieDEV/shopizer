package com.salesmanager.shop.store.controller.order.facade;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.alibaba.fastjson.JSON;
import com.google.api.client.util.Lists;
import com.salesmanager.core.business.fulfillment.service.*;
import com.salesmanager.core.business.repositories.fulfillment.ShippingDocumentOrderRepository;
import com.salesmanager.core.business.repositories.order.OrderRelationRepository;
import com.salesmanager.core.business.repositories.order.orderproduct.OrderProductRepository;
import com.salesmanager.core.business.services.catalog.product.variant.ProductVariantService;
import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.business.services.customer.order.CustomerOrderService;
import com.salesmanager.core.business.services.order.OrderAdditionalPaymentService;
import com.salesmanager.core.business.services.order.orderproduct.OrderProductService;
import com.salesmanager.core.business.services.order.orderproduct.OrderProductSnapshotService;
import com.salesmanager.core.business.services.order.ordertotal.OrderTotalService;
import com.salesmanager.core.business.services.payments.combine.CombineTransactionService;
import com.salesmanager.core.business.services.reference.language.LanguageService;
import com.salesmanager.core.business.utils.*;
import com.salesmanager.core.enmus.TruckModelEnums;
import com.salesmanager.core.enmus.TruckTypeEnums;
import com.salesmanager.core.model.catalog.product.PublishWayEnums;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.customer.order.CustomerOrder;
import com.salesmanager.core.model.fulfillment.*;
import com.salesmanager.core.model.fulfillment.AdditionalServices;
import com.salesmanager.core.model.fulfillment.FulfillmentMainOrder;
import com.salesmanager.core.model.fulfillment.GeneralDocument;
import com.salesmanager.core.model.fulfillment.InvoicePackingForm;
import com.salesmanager.core.model.fulfillment.InvoicePackingFormDetail;
import com.salesmanager.core.model.fulfillment.QcInfo;
import com.salesmanager.core.model.fulfillment.ShippingDocumentOrder;
import com.salesmanager.core.model.order.*;
import com.salesmanager.core.model.order.orderproduct.OrderProductList;
import com.salesmanager.core.model.order.orderproduct.OrderProductPrice;
import com.salesmanager.core.model.payments.*;
import com.salesmanager.core.model.shipping.*;
import com.salesmanager.core.utils.LogPermUtil;
import com.salesmanager.shop.mapper.catalog.ReadableCategoryMapper;
import com.salesmanager.shop.mapper.catalog.product.ReadableProductMapper;
import com.salesmanager.shop.mapper.catalog.product.ReadableProductVariantMapper;
import com.salesmanager.shop.model.catalog.category.ReadableCategory;
import com.salesmanager.shop.model.catalog.product.ReadableProductSnapshot;
import com.salesmanager.shop.model.customer.order.transaction.ReadableCombineTransaction;
import com.salesmanager.shop.model.customer.order.transaction.ReadableCombineTransactionList;
import com.salesmanager.shop.model.fulfillment.*;
import com.salesmanager.shop.model.fulfillment.facade.FulfillmentFacade;
import com.salesmanager.shop.model.order.v0.ReadableOrder;
import com.salesmanager.shop.model.order.v0.ReadableOrderList;
import com.salesmanager.shop.model.order.v1.ReadableOrderAdditionalPayment;
import com.salesmanager.shop.populator.customer.ReadableCombineTransactionPopulator;
import com.salesmanager.shop.populator.order.*;
import com.salesmanager.shop.populator.store.ReadableMerchantStorePopulator;
import com.salesmanager.shop.store.controller.fulfillment.faced.convert.AdditionalServicesConvert;
import com.salesmanager.shop.utils.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.salesmanager.core.business.constants.Constants;
import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.catalog.pricing.PricingService;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.business.services.catalog.product.attribute.ProductAttributeService;
import com.salesmanager.core.business.services.catalog.product.file.DigitalProductService;
import com.salesmanager.core.business.services.order.OrderService;
import com.salesmanager.core.business.services.payments.PaymentService;
import com.salesmanager.core.business.services.payments.TransactionService;
import com.salesmanager.core.business.services.reference.country.CountryService;
import com.salesmanager.core.business.services.reference.zone.ZoneService;
import com.salesmanager.core.business.services.shipping.ShippingQuoteService;
import com.salesmanager.core.business.services.shipping.ShippingService;
import com.salesmanager.core.business.services.shoppingcart.ShoppingCartService;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.availability.ProductAvailability;
import com.salesmanager.core.model.common.Billing;
import com.salesmanager.core.model.common.Delivery;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.attributes.OrderAttribute;
import com.salesmanager.core.model.order.orderproduct.OrderProduct;
import com.salesmanager.core.model.order.orderstatus.OrderStatus;
import com.salesmanager.core.model.order.orderstatus.OrderStatusHistory;
import com.salesmanager.core.model.order.payment.CreditCard;
import com.salesmanager.core.model.reference.country.Country;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.shoppingcart.ShoppingCart;
import com.salesmanager.core.model.shoppingcart.ShoppingCartItem;
import com.salesmanager.shop.model.customer.PersistableCustomer;
import com.salesmanager.shop.model.customer.ReadableCustomer;
import com.salesmanager.shop.model.customer.address.Address;
import com.salesmanager.shop.model.order.OrderEntity;
import com.salesmanager.shop.model.order.PersistableOrderProduct;
import com.salesmanager.shop.model.order.ReadableOrderProduct;
import com.salesmanager.shop.model.order.ShopOrder;
import com.salesmanager.shop.model.order.history.PersistableOrderStatusHistory;
import com.salesmanager.shop.model.order.history.ReadableOrderStatusHistory;
import com.salesmanager.shop.model.order.total.OrderTotal;
import com.salesmanager.shop.model.order.transaction.ReadableTransaction;
import com.salesmanager.shop.populator.customer.CustomerPopulator;
import com.salesmanager.shop.populator.customer.PersistableCustomerPopulator;
import com.salesmanager.shop.populator.order.OrderProductPopulator;
import com.salesmanager.shop.populator.order.PersistableOrderApiPopulator;
import com.salesmanager.shop.populator.order.ReadableOrderPopulator;
import com.salesmanager.shop.populator.order.ShoppingCartItemPopulator;
import com.salesmanager.shop.populator.order.transaction.PersistablePaymentPopulator;
import com.salesmanager.shop.populator.order.transaction.ReadableTransactionPopulator;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.customer.facade.CustomerFacade;
import com.salesmanager.shop.store.controller.shoppingCart.facade.ShoppingCartFacade;

@Service("orderFacade")
public class OrderFacadeImpl implements OrderFacade {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderFacadeImpl.class);

	@Autowired
	private ReadableOrderAdditionalPaymentPopulator readableOrderAdditionalPaymentPopulator;

	@Autowired
	private OrderProductService orderProductService;

	@Autowired
	private AdditionalServicesService additionalServicesService;

	@Inject
	private OrderService orderService;
	@Inject
	private ProductService productService;

	@Autowired
	private OrderProductRepository orderProductRepository;

	@Autowired
	private ShippingDocumentOrderRepository shippingDocumentOrderRepository;

	@Autowired
	private CombineTransactionService combineTransactionService;
	@Inject
	private ProductAttributeService productAttributeService;
	@Inject
	private ShoppingCartService shoppingCartService;
	@Inject
	private DigitalProductService digitalProductService;
	@Inject
	private ShippingService shippingService;
	@Inject
	private CustomerFacade customerFacade;
	@Inject
	private PricingService pricingService;
	@Inject
	private ShoppingCartFacade shoppingCartFacade;
	@Inject
	private ShippingQuoteService shippingQuoteService;
	@Inject
	private CoreConfiguration coreConfiguration;
	@Inject
	private PaymentService paymentService;
	@Inject
	private CountryService countryService;
	@Inject
	private ZoneService zoneService;

	@Autowired
	private CustomerOrderService customerOrderService;

	@Autowired
	private OrderTotalService orderTotalService;

	@Autowired
	private PersistableOrderApiPopulator persistableOrderApiPopulator;

	@Autowired
	private ReadableProductMapper readableProductMapper;

	@Autowired
	private ProductVariantService productVariantService;

	@Autowired
	private ReadableProductVariantMapper readableProductVariantMapper;

	@Autowired
	private OrderProductSnapshotService orderProductSnapshotService;

	@Autowired
	private ReadableOrderPopulator readableOrderPopulator;

	@Autowired
	private CustomerPopulator customerPopulator;

	@Autowired
	private TransactionService transactionService;

	@Inject
	private EmailTemplatesUtils emailTemplatesUtils;

	@Autowired
	private OrderAdditionalPaymentService orderAdditionalPaymentService;

	@Inject
	private LabelUtils messages;
	
	@Autowired
	private ProductPriceUtils productPriceUtils;

	@Autowired
	private FulfillmentHistoryService fulfillmentHistoryService;

	@Autowired
	private OrderRelationRepository orderRelationRepository;

	@Inject
	@Qualifier("img")
	private ImageFilePath imageUtils;

	@Autowired
	private ShippingOrderService shippingOrderService;

	@Inject
	private InvoicePackingFormService invoicePackingFormService;
	@Autowired
	private FulfillmentFacade fulfillmentFacade;
	@Autowired
	private FulfillmentSubOrderService fulfillmentSubOrderService;
	@Autowired
	private FulfillmentMainOrderService fulfillmentMainOrderService;
	@Autowired
	private AdditionalServicesConvert additionalServicesConvert;
	@Autowired
	private ReadableMerchantStorePopulator readableMerchantStorePopulator;
	@Autowired
	private ReadableCategoryMapper  readableCategoryMapper;
	@Autowired
	private QcInfoService qcInfoService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private LanguageService languageService;
	@Autowired
	private OrderProductPopulatorUtil orderProductPopulatorUtil;

	@Override
	public ShopOrder initializeOrder(MerchantStore store, Customer customer, ShoppingCart shoppingCart,
			Language language) throws Exception {

		// assert not null shopping cart items

		ShopOrder order = new ShopOrder();

		OrderStatus orderStatus = OrderStatus.ORDERED;
		order.setOrderStatus(orderStatus);

		if (customer == null) {
			customer = this.initEmptyCustomer(store);
		}

		PersistableCustomer persistableCustomer = persistableCustomer(customer, store, language);
		order.setCustomer(persistableCustomer);

		// keep list of shopping cart items for core price calculation
		List<ShoppingCartItem> items = new ArrayList<ShoppingCartItem>(shoppingCart.getLineItems());
		order.setShoppingCartItems(items);

		return order;
	}

	@Override
	public OrderTotalSummary calculateOrderTotal(MerchantStore store, ShopOrder order, Language language)
			throws Exception {

		Customer customer = customerFacade.getCustomerModel(order.getCustomer(), store, language);
		OrderTotalSummary summary = calculateOrderTotal(store, customer, order, language);
		this.setOrderTotals(order, summary);
		return summary;
	}

	@Override
	public OrderTotalSummary calculateOrderTotal(MerchantStore store,
			com.salesmanager.shop.model.order.v0.PersistableOrder order, Language language) throws Exception {

		List<PersistableOrderProduct> orderProducts = order.getOrderProductItems();

		ShoppingCartItemPopulator populator = new ShoppingCartItemPopulator();
		populator.setProductAttributeService(productAttributeService);
		populator.setProductService(productService);
		populator.setShoppingCartService(shoppingCartService);

		List<ShoppingCartItem> items = new ArrayList<ShoppingCartItem>();
		for (PersistableOrderProduct orderProduct : orderProducts) {
			ShoppingCartItem item = populator.populate(orderProduct, new ShoppingCartItem(), store, language);
			items.add(item);
		}

		Customer customer = customer(order.getCustomer(), store, language);

		OrderTotalSummary summary = this.calculateOrderTotal(store, customer, order, language);

		return summary;
	}

	private OrderTotalSummary calculateOrderTotal(MerchantStore store, Customer customer,
			com.salesmanager.shop.model.order.v0.PersistableOrder order, Language language) throws Exception {

		OrderTotalSummary orderTotalSummary = null;

		OrderSummary summary = new OrderSummary();

		if (order instanceof ShopOrder) {
			ShopOrder o = (ShopOrder) order;
			summary.setProducts(o.getShoppingCartItems());

			if (o.getShippingSummary() != null) {
				summary.setShippingSummary(o.getShippingSummary());
			}

			if (!StringUtils.isBlank(o.getCartCode())) {

				ShoppingCart shoppingCart = shoppingCartFacade.getShoppingCartModel(o.getCartCode(), store);

				// promo code
				if (!StringUtils.isBlank(shoppingCart.getPromoCode())) {
					Date promoDateAdded = shoppingCart.getPromoAdded();// promo
																		// valid
																		// 1 day
					Instant instant = promoDateAdded.toInstant();
					ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
					LocalDate date = zdt.toLocalDate();
					// date added < date + 1 day
					LocalDate tomorrow = LocalDate.now().plusDays(1);
					if (date.isBefore(tomorrow)) {
						summary.setPromoCode(shoppingCart.getPromoCode());
					} else {
						// clear promo
						shoppingCart.setPromoCode(null);
						shoppingCartService.saveOrUpdate(shoppingCart);
					}
				}

			}

			orderTotalSummary = orderService.caculateOrderTotal(summary, customer, store, language);
		} else {
			// need Set of ShoppingCartItem
			// PersistableOrder not implemented
			throw new Exception("calculateOrderTotal not yet implemented for PersistableOrder");
		}

		return orderTotalSummary;

	}

	private PersistableCustomer persistableCustomer(Customer customer, MerchantStore store, Language language)
			throws Exception {

		PersistableCustomerPopulator customerPopulator = new PersistableCustomerPopulator();
		PersistableCustomer persistableCustomer = customerPopulator.populate(customer, new PersistableCustomer(), store,
				language);
		return persistableCustomer;

	}

	private Customer customer(PersistableCustomer customer, MerchantStore store, Language language) throws Exception {

		Customer cust = customerPopulator.populate(customer, new Customer(), store, language);
		return cust;

	}

	private void setOrderTotals(OrderEntity order, OrderTotalSummary summary) {

		List<OrderTotal> totals = new ArrayList<OrderTotal>();
		List<com.salesmanager.core.model.order.OrderTotal> orderTotals = summary.getTotals();
		for (com.salesmanager.core.model.order.OrderTotal t : orderTotals) {
			OrderTotal total = new OrderTotal();
			total.setCode(t.getOrderTotalCode());
			total.setTitle(t.getTitle());
			total.setValue(t.getValue());
			totals.add(total);
		}

		order.setTotals(totals);

	}

	/**
	 * Submitted object must be valided prior to the invocation of this method
	 */
	@Override
	public Order processOrder(ShopOrder order, Customer customer, MerchantStore store, Language language)
			throws ServiceException {

		return processOrderModel(order, customer, null, store, language);

	}

	@Override
	public Order processOrder(ShopOrder order, Customer customer, Transaction transaction, MerchantStore store,
			Language language) throws ServiceException {

		return processOrderModel(order, customer, transaction, store, language);

	}

	/**
	 * Commit an order
	 * @param order
	 * @param customer
	 * @param transaction
	 * @param store
	 * @param language
	 * @return
	 * @throws ServiceException
	 */
	private Order processOrderModel(ShopOrder order, Customer customer, Transaction transaction, MerchantStore store,
			Language language) throws ServiceException {

		try {

			if (order.isShipToBillingAdress()) {// customer shipping is billing
				PersistableCustomer orderCustomer = order.getCustomer();
				Address billing = orderCustomer.getBilling();
				orderCustomer.setDelivery(billing);
			}

			Order modelOrder = new Order();
			modelOrder.setDatePurchased(new Date());
			modelOrder.setBilling(customer.getBilling());
			modelOrder.setDelivery(customer.getDelivery());
			modelOrder.setPaymentModuleCode(order.getPaymentModule());
			modelOrder.setPaymentType(PaymentType.valueOf(order.getPaymentMethodType()));
			modelOrder.setShippingModuleCode(order.getShippingModule());
			modelOrder.setCustomerAgreement(order.isCustomerAgreed());
			modelOrder.setLocale(LocaleUtils.getLocale(store));// set the store
																// locale based
																// on the
																// country for
																// order $
																// formatting

			List<ShoppingCartItem> shoppingCartItems = order.getShoppingCartItems();
			Set<OrderProduct> orderProducts = new LinkedHashSet<OrderProduct>();

			if (!StringUtils.isBlank(order.getComments())) {
				OrderStatusHistory statusHistory = new OrderStatusHistory();
				statusHistory.setStatus(OrderStatus.ORDERED);
				statusHistory.setOrder(modelOrder);
				statusHistory.setDateAdded(new Date());
				statusHistory.setComments(order.getComments());
				modelOrder.getOrderHistory().add(statusHistory);
			}

			OrderProductPopulator orderProductPopulator = new OrderProductPopulator();
			orderProductPopulator.setDigitalProductService(digitalProductService);
			orderProductPopulator.setProductAttributeService(productAttributeService);
			orderProductPopulator.setProductService(productService);
			String shoppingCartCode = null;

			for (ShoppingCartItem item : shoppingCartItems) {

				if(shoppingCartCode == null && item.getShoppingCart()!=null) {
					shoppingCartCode = item.getShoppingCart().getShoppingCartCode();
				}

				/**
				 * Before processing order quantity of item must be > 0
				 */

				Product product = productService.getBySku(item.getSku(), store, language);
				if (product == null) {
					throw new ServiceException(ServiceException.EXCEPTION_INVENTORY_MISMATCH);
				}

				LOGGER.debug("Validate inventory");
				for (ProductAvailability availability : product.getAvailabilities()) {
					if (availability.getRegion().equals(Constants.ALL_REGIONS)) {
						int qty = availability.getProductQuantity();
						if (qty < item.getQuantity()) {
							throw new ServiceException(ServiceException.EXCEPTION_INVENTORY_MISMATCH);
						}
					}
				}

				OrderProduct orderProduct = new OrderProduct();
				orderProduct = orderProductPopulator.populate(item, orderProduct, store, language, true);
				orderProduct.setOrder(modelOrder);
				orderProducts.add(orderProduct);
			}

			modelOrder.setOrderProducts(orderProducts);

			OrderTotalSummary summary = order.getOrderTotalSummary();
			List<com.salesmanager.core.model.order.OrderTotal> totals = summary.getTotals();

			// re-order totals
			Collections.sort(totals, new Comparator<com.salesmanager.core.model.order.OrderTotal>() {
				public int compare(com.salesmanager.core.model.order.OrderTotal x,
						com.salesmanager.core.model.order.OrderTotal y) {
					if (x.getSortOrder() == y.getSortOrder())
						return 0;
					return x.getSortOrder() < y.getSortOrder() ? -1 : 1;
				}

			});

			Set<com.salesmanager.core.model.order.OrderTotal> modelTotals = new LinkedHashSet<com.salesmanager.core.model.order.OrderTotal>();
			for (com.salesmanager.core.model.order.OrderTotal total : totals) {
				total.setOrder(modelOrder);
				modelTotals.add(total);
			}

			modelOrder.setOrderTotal(modelTotals);
			modelOrder.setTotal(order.getOrderTotalSummary().getTotal());

			// order misc objects
			modelOrder.setCurrency(store.getCurrency());
			modelOrder.setMerchant(store);

			// customer object
			orderCustomer(customer, modelOrder, language);

			// populate shipping information
			if (!StringUtils.isBlank(order.getShippingModule())) {
				modelOrder.setShippingModuleCode(order.getShippingModule());
			}

			String paymentType = order.getPaymentMethodType();
			Payment payment = new Payment();
			payment.setPaymentType(PaymentType.valueOf(paymentType));
			payment.setAmount(order.getOrderTotalSummary().getTotal());
			payment.setModuleName(order.getPaymentModule());
			payment.setCurrency(modelOrder.getCurrency());

			if (order.getPayment() != null && order.getPayment().get("paymentToken") != null) {// set
																				// token
				String paymentToken = order.getPayment().get("paymentToken");
				Map<String, String> paymentMetaData = new HashMap<String, String>();
				payment.setPaymentMetaData(paymentMetaData);
				paymentMetaData.put("paymentToken", paymentToken);
			}

			if (PaymentType.CREDITCARD.name().equals(paymentType)) {

				payment = new CreditCardPayment();
				((CreditCardPayment) payment).setCardOwner(order.getPayment().get("creditcard_card_holder"));
				((CreditCardPayment) payment)
						.setCredidCardValidationNumber(order.getPayment().get("creditcard_card_cvv"));
				((CreditCardPayment) payment).setCreditCardNumber(order.getPayment().get("creditcard_card_number"));
				((CreditCardPayment) payment)
						.setExpirationMonth(order.getPayment().get("creditcard_card_expirationmonth"));
				((CreditCardPayment) payment)
						.setExpirationYear(order.getPayment().get("creditcard_card_expirationyear"));

				Map<String, String> paymentMetaData = order.getPayment();
				payment.setPaymentMetaData(paymentMetaData);
				payment.setPaymentType(PaymentType.valueOf(paymentType));
				payment.setAmount(order.getOrderTotalSummary().getTotal());
				payment.setModuleName(order.getPaymentModule());
				payment.setCurrency(modelOrder.getCurrency());

				CreditCardType creditCardType = null;
				String cardType = order.getPayment().get("creditcard_card_type");

				// supported credit cards
				if (CreditCardType.AMEX.name().equalsIgnoreCase(cardType)) {
					creditCardType = CreditCardType.AMEX;
				} else if (CreditCardType.VISA.name().equalsIgnoreCase(cardType)) {
					creditCardType = CreditCardType.VISA;
				} else if (CreditCardType.MASTERCARD.name().equalsIgnoreCase(cardType)) {
					creditCardType = CreditCardType.MASTERCARD;
				} else if (CreditCardType.DINERS.name().equalsIgnoreCase(cardType)) {
					creditCardType = CreditCardType.DINERS;
				} else if (CreditCardType.DISCOVERY.name().equalsIgnoreCase(cardType)) {
					creditCardType = CreditCardType.DISCOVERY;
				}

				((CreditCardPayment) payment).setCreditCard(creditCardType);

				if (creditCardType != null) {

					CreditCard cc = new CreditCard();
					cc.setCardType(creditCardType);
					cc.setCcCvv(((CreditCardPayment) payment).getCredidCardValidationNumber());
					cc.setCcOwner(((CreditCardPayment) payment).getCardOwner());
					cc.setCcExpires(((CreditCardPayment) payment).getExpirationMonth() + "-"
							+ ((CreditCardPayment) payment).getExpirationYear());

					// hash credit card number
					if (!StringUtils.isBlank(cc.getCcNumber())) {
						String maskedNumber = CreditCardUtils
								.maskCardNumber(order.getPayment().get("creditcard_card_number"));
						cc.setCcNumber(maskedNumber);
						modelOrder.setCreditCard(cc);
					}

				}

			}

			if (PaymentType.PAYPAL.name().equals(paymentType)) {

				// check for previous transaction
				if (transaction == null) {
					throw new ServiceException("payment.error");
				}

				payment = new com.salesmanager.core.model.payments.PaypalPayment();

				((com.salesmanager.core.model.payments.PaypalPayment) payment)
						.setPayerId(transaction.getTransactionDetails().get("PAYERID"));
				((com.salesmanager.core.model.payments.PaypalPayment) payment)
						.setPaymentToken(transaction.getTransactionDetails().get("TOKEN"));

			}

			modelOrder.setShoppingCartCode(shoppingCartCode);
			modelOrder.setPaymentModuleCode(order.getPaymentModule());
			payment.setModuleName(order.getPaymentModule());

			if (transaction != null) {
				orderService.processOrder(modelOrder, customer, order.getShoppingCartItems(), summary, payment, store);
			} else {
				orderService.processOrder(modelOrder, customer, order.getShoppingCartItems(), summary, payment,
						transaction, store);
			}

			return modelOrder;

		} catch (ServiceException se) {// may be invalid credit card
			throw se;
		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}

	private void orderCustomer(Customer customer, Order order, Language language) throws Exception {

		// populate customer
		order.setBilling(customer.getBilling());
		order.setDelivery(customer.getDelivery());
		order.setCustomerEmailAddress(customer.getEmailAddress());
		order.setCustomerId(customer.getId());
		//set username
		if(! customer.isAnonymous() && !StringUtils.isBlank(customer.getPassword())) {
			customer.setNick(customer.getEmailAddress());
		}

	}

	@Override
	public Customer initEmptyCustomer(MerchantStore store) {

		Customer customer = new Customer();
		Billing billing = new Billing();
		billing.setCountry(store.getCountry());
		billing.setZone(store.getZone());
		billing.setState(store.getStorestateprovince());
		/** empty postal code for initial quote **/
		// billing.setPostalCode(store.getStorepostalcode());
		customer.setBilling(billing);

		Delivery delivery = new Delivery();
		delivery.setCountry(store.getCountry());
		delivery.setZone(store.getZone());
		delivery.setState(store.getStorestateprovince());
		/** empty postal code for initial quote **/
		// delivery.setPostalCode(store.getStorepostalcode());
		customer.setDelivery(delivery);

		return customer;
	}

	@Override
	public void refreshOrder(ShopOrder order, MerchantStore store, Customer customer, ShoppingCart shoppingCart,
			Language language) throws Exception {
		if (customer == null && order.getCustomer() != null) {
			order.getCustomer().setId(0L);// reset customer id
		}

		if (customer != null) {
			PersistableCustomer persistableCustomer = persistableCustomer(customer, store, language);
			order.setCustomer(persistableCustomer);
		}

		List<ShoppingCartItem> items = new ArrayList<ShoppingCartItem>(shoppingCart.getLineItems());
		order.setShoppingCartItems(items);

		return;
	}

	@Override
	public ShippingQuote getShippingQuote(PersistableCustomer persistableCustomer, ShoppingCart cart, ShopOrder order,
			MerchantStore store, Language language) throws Exception {

		// create shipping products
		List<ShippingProduct> shippingProducts = shoppingCartService.createShippingProduct(cart);

		if (CollectionUtils.isEmpty(shippingProducts)) {
			return null;// products are virtual
		}

		Customer customer = customerFacade.getCustomerModel(persistableCustomer, store, language);

		Delivery delivery = new Delivery();

		// adjust shipping and billing
		if (order.isShipToBillingAdress() && !order.isShipToDeliveryAddress()) {

			Billing billing = customer.getBilling();

			String postalCode = billing.getPostalCode();
			postalCode = validatePostalCode(postalCode);

			delivery.setAddress(billing.getAddress());
			delivery.setCompany(billing.getCompany());
			delivery.setCity(billing.getCity());
			delivery.setPostalCode(billing.getPostalCode());
			delivery.setState(billing.getState());
			delivery.setCountry(billing.getCountry());
			delivery.setZone(billing.getZone());
		} else {
			delivery = customer.getDelivery();
		}

		ShippingQuote quote = shippingService.getShippingQuote(cart.getId(), store, delivery, shippingProducts,
				language);

		return quote;

	}

	private String validatePostalCode(String postalCode) {

		String patternString = "__";// this one is set in the template
		if (postalCode.contains(patternString)) {
			postalCode = null;
		}
		return postalCode;
	}

	@Override
	public List<Country> getShipToCountry(MerchantStore store, Language language) throws Exception {

		List<Country> shippingCountriesList = shippingService.getShipToCountryList(store, language);
		return shippingCountriesList;

	}

	/**
	 * ShippingSummary contains the subset of information of a ShippingQuote
	 */
	@Override
	public ShippingSummary getShippingSummary(ShippingQuote quote, MerchantStore store, Language language) {

		ShippingSummary summary = new ShippingSummary();
		if (quote.getSelectedShippingOption() != null) {
			summary.setShippingQuote(true);
			summary.setFreeShipping(quote.isFreeShipping());
			summary.setTaxOnShipping(quote.isApplyTaxOnShipping());
			summary.setHandling(quote.getHandlingFees());
			summary.setShipping(quote.getSelectedShippingOption().getOptionPrice());
			summary.setShippingOption(quote.getSelectedShippingOption().getOptionName());
			summary.setShippingModule(quote.getShippingModuleCode());
			summary.setShippingOptionCode(quote.getSelectedShippingOption().getOptionCode());

			if (quote.getDeliveryAddress() != null) {
				summary.setDeliveryAddress(quote.getDeliveryAddress());
			}

		}

		return summary;
	}

	@Override
	public void validateOrder(ShopOrder order, BindingResult bindingResult, Map<String, String> messagesResult,
			MerchantStore store, Locale locale) throws ServiceException {

		Validate.notNull(messagesResult, "messagesResult should not be null");

		try {

			// Language language = (Language)request.getAttribute("LANGUAGE");

			// validate order shipping and billing
			if (StringUtils.isBlank(order.getCustomer().getBilling().getFirstName())) {
				FieldError error = new FieldError("customer.billing.firstName", "customer.billing.firstName",
						messages.getMessage("NotEmpty.customer.firstName", locale));
				bindingResult.addError(error);
				messagesResult.put("customer.billing.firstName",
						messages.getMessage("NotEmpty.customer.firstName", locale));
			}

			if (StringUtils.isBlank(order.getCustomer().getBilling().getLastName())) {
				FieldError error = new FieldError("customer.billing.lastName", "customer.billing.lastName",
						messages.getMessage("NotEmpty.customer.lastName", locale));
				bindingResult.addError(error);
				messagesResult.put("customer.billing.lastName",
						messages.getMessage("NotEmpty.customer.lastName", locale));
			}

			if (StringUtils.isBlank(order.getCustomer().getEmailAddress())) {
				FieldError error = new FieldError("customer.emailAddress", "customer.emailAddress",
						messages.getMessage("NotEmpty.customer.emailAddress", locale));
				bindingResult.addError(error);
				messagesResult.put("customer.emailAddress",
						messages.getMessage("NotEmpty.customer.emailAddress", locale));
			}

			if (StringUtils.isBlank(order.getCustomer().getBilling().getAddress())) {
				FieldError error = new FieldError("customer.billing.address", "customer.billing.address",
						messages.getMessage("NotEmpty.customer.billing.address", locale));
				bindingResult.addError(error);
				messagesResult.put("customer.billing.address",
						messages.getMessage("NotEmpty.customer.billing.address", locale));
			}

			if (StringUtils.isBlank(order.getCustomer().getBilling().getCity())) {
				FieldError error = new FieldError("customer.billing.city", "customer.billing.city",
						messages.getMessage("NotEmpty.customer.billing.city", locale));
				bindingResult.addError(error);
				messagesResult.put("customer.billing.city",
						messages.getMessage("NotEmpty.customer.billing.city", locale));
			}

			if (StringUtils.isBlank(order.getCustomer().getBilling().getCountry())) {
				FieldError error = new FieldError("customer.billing.country", "customer.billing.country",
						messages.getMessage("NotEmpty.customer.billing.country", locale));
				bindingResult.addError(error);
				messagesResult.put("customer.billing.country",
						messages.getMessage("NotEmpty.customer.billing.country", locale));
			}

			if (StringUtils.isBlank(order.getCustomer().getBilling().getZone())
					&& StringUtils.isBlank(order.getCustomer().getBilling().getStateProvince())) {
				FieldError error = new FieldError("customer.billing.stateProvince", "customer.billing.stateProvince",
						messages.getMessage("NotEmpty.customer.billing.stateProvince", locale));
				bindingResult.addError(error);
				messagesResult.put("customer.billing.stateProvince",
						messages.getMessage("NotEmpty.customer.billing.stateProvince", locale));
			}

			if (StringUtils.isBlank(order.getCustomer().getBilling().getPhone())) {
				FieldError error = new FieldError("customer.billing.phone", "customer.billing.phone",
						messages.getMessage("NotEmpty.customer.billing.phone", locale));
				bindingResult.addError(error);
				messagesResult.put("customer.billing.phone",
						messages.getMessage("NotEmpty.customer.billing.phone", locale));
			}

			if (StringUtils.isBlank(order.getCustomer().getBilling().getPostalCode())) {
				FieldError error = new FieldError("customer.billing.postalCode", "customer.billing.postalCode",
						messages.getMessage("NotEmpty.customer.billing.postalCode", locale));
				bindingResult.addError(error);
				messagesResult.put("customer.billing.postalCode",
						messages.getMessage("NotEmpty.customer.billing.postalCode", locale));
			}

			if (!order.isShipToBillingAdress()) {

				if (StringUtils.isBlank(order.getCustomer().getDelivery().getFirstName())) {
					FieldError error = new FieldError("customer.delivery.firstName", "customer.delivery.firstName",
							messages.getMessage("NotEmpty.customer.shipping.firstName", locale));
					bindingResult.addError(error);
					messagesResult.put("customer.delivery.firstName",
							messages.getMessage("NotEmpty.customer.shipping.firstName", locale));
				}

				if (StringUtils.isBlank(order.getCustomer().getDelivery().getLastName())) {
					FieldError error = new FieldError("customer.delivery.lastName", "customer.delivery.lastName",
							messages.getMessage("NotEmpty.customer.shipping.lastName", locale));
					bindingResult.addError(error);
					messagesResult.put("customer.delivery.lastName",
							messages.getMessage("NotEmpty.customer.shipping.lastName", locale));
				}

				if (StringUtils.isBlank(order.getCustomer().getDelivery().getAddress())) {
					FieldError error = new FieldError("customer.delivery.address", "customer.delivery.address",
							messages.getMessage("NotEmpty.customer.shipping.address", locale));
					bindingResult.addError(error);
					messagesResult.put("customer.delivery.address",
							messages.getMessage("NotEmpty.customer.shipping.address", locale));
				}

				if (StringUtils.isBlank(order.getCustomer().getDelivery().getCity())) {
					FieldError error = new FieldError("customer.delivery.city", "customer.delivery.city",
							messages.getMessage("NotEmpty.customer.shipping.city", locale));
					bindingResult.addError(error);
					messagesResult.put("customer.delivery.city",
							messages.getMessage("NotEmpty.customer.shipping.city", locale));
				}

				if (StringUtils.isBlank(order.getCustomer().getDelivery().getCountry())) {
					FieldError error = new FieldError("customer.delivery.country", "customer.delivery.country",
							messages.getMessage("NotEmpty.customer.shipping.country", locale));
					bindingResult.addError(error);
					messagesResult.put("customer.delivery.country",
							messages.getMessage("NotEmpty.customer.shipping.country", locale));
				}

				if (StringUtils.isBlank(order.getCustomer().getDelivery().getZone())
						&& StringUtils.isBlank(order.getCustomer().getDelivery().getStateProvince())) {
					FieldError error = new FieldError("customer.delivery.stateProvince",
							"customer.delivery.stateProvince",
							messages.getMessage("NotEmpty.customer.shipping.stateProvince", locale));
					bindingResult.addError(error);
					messagesResult.put("customer.delivery.stateProvince",
							messages.getMessage("NotEmpty.customer.shipping.stateProvince", locale));
				}

				if (StringUtils.isBlank(order.getCustomer().getDelivery().getPostalCode())) {
					FieldError error = new FieldError("customer.delivery.postalCode", "customer.delivery.postalCode",
							messages.getMessage("NotEmpty.customer.shipping.postalCode", locale));
					bindingResult.addError(error);
					messagesResult.put("customer.delivery.postalCode",
							messages.getMessage("NotEmpty.customer.shipping.postalCode", locale));
				}

			}

			if (bindingResult.hasErrors()) {
				return;

			}

			String paymentType = order.getPaymentMethodType();

			// validate payment
			if (paymentType == null) {
				ServiceException serviceException = new ServiceException(ServiceException.EXCEPTION_VALIDATION,
						"payment.required");
				throw serviceException;
			}

			// validate shipping
			if (shippingService.requiresShipping(order.getShoppingCartItems(), store)
					&& order.getSelectedShippingOption() == null) {
				ServiceException serviceException = new ServiceException(ServiceException.EXCEPTION_VALIDATION,
						"shipping.required");
				throw serviceException;
			}

			// pre-validate credit card
			if (PaymentType.CREDITCARD.name().equals(paymentType)
					&& "true".equals(coreConfiguration.getProperty("VALIDATE_CREDIT_CARD"))) {
				String cco = order.getPayment().get("creditcard_card_holder");
				String cvv = order.getPayment().get("creditcard_card_cvv");
				String ccn = order.getPayment().get("creditcard_card_number");
				String ccm = order.getPayment().get("creditcard_card_expirationmonth");
				String ccd = order.getPayment().get("creditcard_card_expirationyear");

				if (StringUtils.isBlank(cco) || StringUtils.isBlank(cvv) || StringUtils.isBlank(ccn)
						|| StringUtils.isBlank(ccm) || StringUtils.isBlank(ccd)) {
					ObjectError error = new ObjectError("creditcard",
							messages.getMessage("messages.error.creditcard", locale));
					bindingResult.addError(error);
					messagesResult.put("creditcard", messages.getMessage("messages.error.creditcard", locale));
					return;
				}

				CreditCardType creditCardType = null;
				String cardType = order.getPayment().get("creditcard_card_type");

				if (cardType.equalsIgnoreCase(CreditCardType.AMEX.name())) {
					creditCardType = CreditCardType.AMEX;
				} else if (cardType.equalsIgnoreCase(CreditCardType.VISA.name())) {
					creditCardType = CreditCardType.VISA;
				} else if (cardType.equalsIgnoreCase(CreditCardType.MASTERCARD.name())) {
					creditCardType = CreditCardType.MASTERCARD;
				} else if (cardType.equalsIgnoreCase(CreditCardType.DINERS.name())) {
					creditCardType = CreditCardType.DINERS;
				} else if (cardType.equalsIgnoreCase(CreditCardType.DISCOVERY.name())) {
					creditCardType = CreditCardType.DISCOVERY;
				}

				if (creditCardType == null) {
					ServiceException serviceException = new ServiceException(ServiceException.EXCEPTION_VALIDATION,
							"cc.type");
					throw serviceException;
				}

			}

		} catch (ServiceException se) {
			LOGGER.error("Error while commiting order", se);
			throw se;
		}

	}

	@Override
	public com.salesmanager.shop.model.order.v0.ReadableOrderList getReadableOrderList(MerchantStore store,
			Customer customer, int start, int maxCount, Language language) throws Exception {

		OrderCriteria criteria = new OrderCriteria();
		criteria.setStartIndex(start);
		criteria.setMaxCount(maxCount);
		criteria.setCustomerId(customer.getId());

		return this.getReadableOrderList(criteria, store, language);

	}

	@Override
	public ReadableOrderList getCustomerReadableOrderList(MerchantStore store, Customer customer, OrderCustomerCriteria criteria, Language language) throws Exception {
		return this.getCustomerReadableOrderList(customer, criteria, store, language);
	}

	@Override
	public ReadableOrderList getCustomerReadableOrderList(MerchantStore store, Customer customer, int start, int maxCount, Language language) throws Exception {
		OrderCustomerCriteria criteria = new OrderCustomerCriteria();
		criteria.setStartIndex(start);
		criteria.setMaxCount(maxCount);

		return this.getCustomerReadableOrderList(customer, criteria, store, language);
	}

	@Override
	public Map<String, Integer> countCustomerOrderByStatus(MerchantStore merchantStore, Customer customer, OrderCustomerCriteria criteria, Language language) {
		return orderService.countCustomerOrderByStatus(customer, criteria);
	}

	@Override
	public com.salesmanager.shop.model.order.v0.ReadableOrderList getReadableOrderList(OrderCriteria criteria,
			MerchantStore store) {

		try {
			criteria.setLegacyPagination(false);

			OrderList orderList = orderService.getOrders(criteria, store);

			List<Order> orders = orderList.getOrders();
			com.salesmanager.shop.model.order.v0.ReadableOrderList returnList = new com.salesmanager.shop.model.order.v0.ReadableOrderList();

			if (CollectionUtils.isEmpty(orders)) {
				returnList.setRecordsTotal(0);
				return returnList;
			}

			List<com.salesmanager.shop.model.order.v0.ReadableOrder> readableOrders = new ArrayList<com.salesmanager.shop.model.order.v0.ReadableOrder>();
			for (Order order : orders) {
				com.salesmanager.shop.model.order.v0.ReadableOrder readableOrder = new com.salesmanager.shop.model.order.v0.ReadableOrder();
				readableOrderPopulator.populate(order, readableOrder, null, null);
				readableOrders.add(readableOrder);
			}
			returnList.setOrders(readableOrders);
			returnList.setRecordsTotal(orderList.getTotalCount());
			returnList.setTotalPages(orderList.getTotalPages());
			returnList.setNumber(orderList.getOrders().size());
			returnList.setRecordsFiltered(orderList.getOrders().size());

			return returnList;

		} catch (Exception e) {
			throw new ServiceRuntimeException("Error while getting orders", e);
		}

	}

	@Override
	public ShippingQuote getShippingQuote(Customer customer, ShoppingCart cart,
			com.salesmanager.shop.model.order.v0.PersistableOrder order, MerchantStore store, Language language)
			throws Exception {
		// create shipping products
		List<ShippingProduct> shippingProducts = shoppingCartService.createShippingProduct(cart);

		if (CollectionUtils.isEmpty(shippingProducts)) {
			return null;// products are virtual
		}

		Delivery delivery = new Delivery();

		// adjust shipping and billing
		if (order.isShipToBillingAdress()) {
			Billing billing = customer.getBilling();
			delivery.setAddress(billing.getAddress());
			delivery.setCity(billing.getCity());
			delivery.setCompany(billing.getCompany());
			delivery.setPostalCode(billing.getPostalCode());
			delivery.setState(billing.getState());
			delivery.setCountry(billing.getCountry());
			delivery.setZone(billing.getZone());
		} else {
			delivery = customer.getDelivery();
		}

		ShippingQuote quote = shippingService.getShippingQuote(cart.getId(), store, delivery, shippingProducts,
				language);

		return quote;
	}

	private com.salesmanager.shop.model.order.v0.ReadableOrderList populateOrderList(final OrderList orderList,
			final MerchantStore store, final Language language) {
		List<Order> orders = orderList.getOrders();
		com.salesmanager.shop.model.order.v0.ReadableOrderList returnList = new com.salesmanager.shop.model.order.v0.ReadableOrderList();
		if (CollectionUtils.isEmpty(orders)) {
			LOGGER.info("Order list if empty..Returning empty list");
			returnList.setRecordsTotal(0);
			// returnList.setMessage("No results for store code " + store);
			return returnList;
		}

		// ReadableOrderPopulator orderPopulator = new ReadableOrderPopulator();
		Locale locale = LocaleUtils.getLocale(language);
		readableOrderPopulator.setLocale(locale);

		List<com.salesmanager.shop.model.order.v0.ReadableOrder> readableOrders = new ArrayList<com.salesmanager.shop.model.order.v0.ReadableOrder>();
		for (Order order : orders) {
			com.salesmanager.shop.model.order.v0.ReadableOrder readableOrder = new com.salesmanager.shop.model.order.v0.ReadableOrder();
			try {

				readableOrderPopulator.populate(order, readableOrder, store, language);
				setOrderProductList(order, locale, store, language, readableOrder);
			} catch (ConversionException ex) {
				LOGGER.error("Error while converting order to order data", ex);

			}
			readableOrders.add(readableOrder);

		}
		returnList.setTotalPages(orderList.getTotalPages());
		returnList.setNumber(orderList.getOrders().size());
		returnList.setRecordsTotal(orderList.getTotalCount());
		returnList.setRecordsFiltered(orderList.getOrders().size());
		returnList.setOrders(readableOrders);
		return returnList;

	}

	private void setOrderProductList(final Order order, final Locale locale, final MerchantStore store,
			final Language language, final com.salesmanager.shop.model.order.v0.ReadableOrder readableOrder)
			throws ConversionException {
		List<ReadableOrderProduct> orderProducts = new ArrayList<ReadableOrderProduct>();
		for (OrderProduct p : order.getOrderProducts()) {
			ReadableOrderProduct orderProduct = new ReadableOrderProduct();
			orderProductPopulatorUtil.buildReadableOrderProduct(p, orderProduct, store, language);

			orderProducts.add(orderProduct);
		}

		readableOrder.setProducts(orderProducts);
	}

	private com.salesmanager.shop.model.order.v0.ReadableOrderList getReadableOrderList(OrderCriteria criteria,
			MerchantStore store, Language language) throws Exception {

		OrderList orderList = orderService.listByStore(store, criteria);

		// ReadableOrderPopulator orderPopulator = new ReadableOrderPopulator();
		Locale locale = LocaleUtils.getLocale(language);
		// FIXME: not thread safe
		readableOrderPopulator.setLocale(locale);

		List<Order> orders = orderList.getOrders();
		com.salesmanager.shop.model.order.v0.ReadableOrderList returnList = new com.salesmanager.shop.model.order.v0.ReadableOrderList();

		if (CollectionUtils.isEmpty(orders)) {
			returnList.setRecordsTotal(0);
			// returnList.setMessage("No results for store code " + store);
			return null;
		}

		List<com.salesmanager.shop.model.order.v0.ReadableOrder> readableOrders = new ArrayList<com.salesmanager.shop.model.order.v0.ReadableOrder>();
		for (Order order : orders) {
			com.salesmanager.shop.model.order.v0.ReadableOrder readableOrder = new com.salesmanager.shop.model.order.v0.ReadableOrder();
			readableOrderPopulator.populate(order, readableOrder, store, language);
			readableOrders.add(readableOrder);

		}

		returnList.setRecordsTotal(orderList.getTotalCount());
		return this.populateOrderList(orderList, store, language);

	}

	private com.salesmanager.shop.model.order.v0.ReadableOrderList getCustomerReadableOrderList(Customer customer, OrderCustomerCriteria criteria,
																						MerchantStore store, Language language) throws Exception {

		OrderList orderList = orderService.listByCustomer(customer, criteria);

		// ReadableOrderPopulator orderPopulator = new ReadableOrderPopulator();
		Locale locale = LocaleUtils.getLocale(language);
		readableOrderPopulator.setLocale(locale);

		List<Order> orders = orderList.getOrders();
		com.salesmanager.shop.model.order.v0.ReadableOrderList returnList = new com.salesmanager.shop.model.order.v0.ReadableOrderList();

		if (CollectionUtils.isEmpty(orders)) {
			returnList.setRecordsTotal(0);
			// returnList.setMessage("No results for store code " + store);
			return null;
		}

		List<com.salesmanager.shop.model.order.v0.ReadableOrder> readableOrders = new ArrayList<com.salesmanager.shop.model.order.v0.ReadableOrder>();
		for (Order order : orders) {
			com.salesmanager.shop.model.order.v0.ReadableOrder readableOrder = new com.salesmanager.shop.model.order.v0.ReadableOrder();
			readableOrderPopulator.populate(order, readableOrder, store, language);
			readableOrders.add(readableOrder);
		}

		return this.populateOrderList(orderList, store, language);

	}

	@Override
	public com.salesmanager.shop.model.order.v0.ReadableOrderList getReadableOrderList(MerchantStore store, int start,
			int maxCount, Language language) throws Exception {

		OrderCriteria criteria = new OrderCriteria();
		criteria.setStartIndex(start);
		criteria.setMaxCount(maxCount);

		return getReadableOrderList(criteria, store, language);
	}

	@Override
	public com.salesmanager.shop.model.order.v0.ReadableOrder getReadableOrder(Long orderId, MerchantStore store,
			Language language) {
		Order modelOrder = orderService.getOrder(orderId, store);
		if (modelOrder == null) {
			throw new ResourceNotFoundException("Order not found with id " + orderId);
		}

		com.salesmanager.shop.model.order.v0.ReadableOrder readableOrder = new com.salesmanager.shop.model.order.v0.ReadableOrder();
		MerchantStore merchant = modelOrder.getMerchant();
		Long customerId = modelOrder.getCustomerId();
		if (customerId != null) {
			ReadableCustomer readableCustomer = customerFacade.getCustomerById(customerId,
					store == null? merchant : store, language);
			if (readableCustomer == null) {
				LOGGER.warn("Customer id " + customerId + " not found in order " + orderId);
			} else {
				readableOrder.setCustomer(readableCustomer);
			}
		}

		try {
			readableOrderPopulator.populate(modelOrder, readableOrder, store, language);
			store = modelOrder.getMerchant();
			// order products
			List<ReadableOrderProduct> orderProducts = new ArrayList<ReadableOrderProduct>();
			for (OrderProduct p : modelOrder.getOrderProducts()) {
				ReadableOrderProduct orderProduct = new ReadableOrderProduct();
				orderProductPopulatorUtil.buildReadableOrderProduct(p, orderProduct, store, language);
				orderProducts.add(orderProduct);
			}

			readableOrder.setProducts(orderProducts);
		} catch (Exception e) {
			throw new ServiceRuntimeException("Error while getting order [" + orderId + "]");
		}

		return readableOrder;
	}

	@Override
	public ReadableOrder getCustomerReadableOrder(Long orderId, Customer customer, Language language) {
		Order modelOrder = orderService.getOrder(orderId, customer);
		if (modelOrder == null) {
			throw new ResourceNotFoundException("Order not found with id " + orderId);
		}

		com.salesmanager.shop.model.order.v0.ReadableOrder readableOrder = new com.salesmanager.shop.model.order.v0.ReadableOrder();


		try {
			readableOrderPopulator.populate(modelOrder, readableOrder, modelOrder.getMerchant(), language);

			// order products
			List<ReadableOrderProduct> orderProducts = new ArrayList<ReadableOrderProduct>();
			for (OrderProduct p : modelOrder.getOrderProducts()) {
				ReadableOrderProduct orderProduct = new ReadableOrderProduct();
				orderProductPopulatorUtil.buildReadableOrderProduct(p, orderProduct, modelOrder.getMerchant(), language);
				orderProducts.add(orderProduct);
			}

			readableOrder.setProducts(orderProducts);
		} catch (Exception e) {
			throw new ServiceRuntimeException("Error while getting order [" + orderId + "]");
		}

		return readableOrder;
	}

	@Override
	public ShippingQuote getShippingQuote(Customer customer, ShoppingCart cart, MerchantStore store, Language language)
			throws Exception {

		Validate.notNull(customer, "Customer cannot be null");
		Validate.notNull(cart, "cart cannot be null");

		// create shipping products
		List<ShippingProduct> shippingProducts = shoppingCartService.createShippingProduct(cart);

		if (CollectionUtils.isEmpty(shippingProducts)) {
			return null;// products are virtual
		}

		Delivery delivery = new Delivery();
		Billing billing = new Billing();
		//default value
		billing.setCountry(store.getCountry());


		// adjust shipping and billing
		if (customer.getDelivery() == null || StringUtils.isBlank(customer.getDelivery().getPostalCode())) {
			if(customer.getBilling()!=null) {
				billing = customer.getBilling();
			}
			delivery.setAddress(billing.getAddress());
			delivery.setCity(billing.getCity());
			delivery.setCompany(billing.getCompany());
			delivery.setPostalCode(billing.getPostalCode());
			delivery.setState(billing.getState());
			delivery.setCountry(billing.getCountry());
			delivery.setZone(billing.getZone());
		} else {
			delivery = customer.getDelivery();
		}

		ShippingQuote quote = shippingService.getShippingQuote(cart.getId(), store, delivery, shippingProducts,
				language);
		return quote;
	}

	/**
	 * Process order from api
	 */
	@Override
	public Order processOrder(com.salesmanager.shop.model.order.v1.PersistableOrder order, Customer customer,
			MerchantStore store, Language language, Locale locale) throws ServiceException {

		Validate.notNull(order, "Order cannot be null");
		Validate.notNull(customer, "Customer cannot be null");
		Validate.notNull(store, "MerchantStore cannot be null");
		Validate.notNull(language, "Language cannot be null");
		Validate.notNull(locale, "Locale cannot be null");

		long start = LogPermUtil.start("processOrder");
		try {


			Order modelOrder = new Order();
			persistableOrderApiPopulator.populate(order, modelOrder, store, language);

			Long shoppingCartId = order.getShoppingCartId();
			ShoppingCart cart = shoppingCartService.getById(shoppingCartId, store);

			if (cart == null) {
				throw new ServiceException("Shopping cart with id " + shoppingCartId + " does not exist");
			}

			Set<ShoppingCartItem> shoppingCartItems = cart.getLineItems();

			LOGGER.debug("[processOrder] process order shopping cart, [items size:" + shoppingCartItems.size() + "]");
			List<ShoppingCartItem> items = new ArrayList<ShoppingCartItem>(shoppingCartItems);
			Set<OrderProduct> orderProducts = new LinkedHashSet<OrderProduct>();

			OrderProductPopulator orderProductPopulator = new OrderProductPopulator();
			orderProductPopulator.setDigitalProductService(digitalProductService);
			orderProductPopulator.setProductAttributeService(productAttributeService);
			orderProductPopulator.setProductService(productService);
			for (ShoppingCartItem item : shoppingCartItems) {
				OrderProduct orderProduct = new OrderProduct();
				orderProduct = orderProductPopulator.populate(item, orderProduct, store, language, true);
				orderProduct.setOrder(modelOrder);
				orderProducts.add(orderProduct);
			}

			modelOrder.setOrderProducts(orderProducts);
			if (order.getAttributes() != null && order.getAttributes().size() > 0) {
				Set<OrderAttribute> attrs = new HashSet<OrderAttribute>();
				for (com.salesmanager.shop.model.order.OrderAttribute attribute : order.getAttributes()) {
					OrderAttribute attr = new OrderAttribute();
					attr.setKey(attribute.getKey());
					attr.setValue(attribute.getValue());
					attr.setOrder(modelOrder);
					attrs.add(attr);
				}
				modelOrder.setOrderAttributes(attrs);
			}

			// requires Shipping information (need a quote id calculated)
			ShippingSummary shippingSummary = null;

			LOGGER.debug("[processOrder] process order shopping cart shipping quote");
			// get shipping quote if asked for
			if (order.getShippingQuote() != null && order.getShippingQuote().longValue() > 0) {
				shippingSummary = shippingQuoteService.getShippingSummary(order.getShippingQuote(), store);
				if (shippingSummary != null) {
					modelOrder.setShippingModuleCode(shippingSummary.getShippingModule());
				}
			}

			// requires Order Totals, this needs recalculation and then compare
			// total with the amount sent as part
			// of process order request. If totals does not match, an error
			// should be thrown.

			LOGGER.debug("[processOrder] process order shopping cart calculate order total");
			OrderTotalSummary orderTotalSummary = null;

			OrderSummary orderSummary = new OrderSummary();
			orderSummary.setShippingSummary(shippingSummary);
			List<ShoppingCartItem> itemsSet = new ArrayList<ShoppingCartItem>(cart.getLineItems());
			orderSummary.setProducts(itemsSet);

			orderTotalSummary = orderService.caculateOrderTotal(orderSummary, customer, store, language);

			if (order.getPayment().getAmount() == null) {
				throw new ConversionException("Requires Payment.amount");
			}

			String submitedAmount = order.getPayment().getAmount();

			BigDecimal formattedSubmittedAmount = productPriceUtils.getAmount(submitedAmount);

			BigDecimal submitedAmountFormat = productPriceUtils.getAmount(submitedAmount);

			BigDecimal calculatedAmount = orderTotalSummary.getTotal();
			String strCalculatedTotal = calculatedAmount.toPlainString();

			// compare both prices
			if (calculatedAmount.compareTo(formattedSubmittedAmount) != 0) {


				throw new ConversionException("Payment.amount does not match what the system has calculated "
						+ strCalculatedTotal + " (received " + submitedAmount + ") please recalculate the order and submit again");
			}

			modelOrder.setTotal(calculatedAmount);
			List<com.salesmanager.core.model.order.OrderTotal> totals = orderTotalSummary.getTotals();
			Set<com.salesmanager.core.model.order.OrderTotal> set = new HashSet<com.salesmanager.core.model.order.OrderTotal>();

			if (!CollectionUtils.isEmpty(totals)) {
				for (com.salesmanager.core.model.order.OrderTotal total : totals) {
					total.setOrder(modelOrder);
					set.add(total);
				}
			}
			modelOrder.setOrderTotal(set);

			LOGGER.debug("[processOrder] process order shopping cart payment");
			PersistablePaymentPopulator paymentPopulator = new PersistablePaymentPopulator();
			paymentPopulator.setPricingService(pricingService);
			Payment paymentModel = new Payment();
			paymentPopulator.populate(order.getPayment(), paymentModel, store, language);

			modelOrder.setShoppingCartCode(cart.getShoppingCartCode());

			//lookup existing customer
			//if customer exist then do not set authentication for this customer and send an instructions email
			/** **/
			if(!StringUtils.isBlank(customer.getNick()) && !customer.isAnonymous()) {
				if(order.getCustomerId() == null && (customerFacade.checkIfUserExists(customer.getNick(), store))) {
					customer.setAnonymous(true);
					customer.setNick(null);
					//send email instructions
				}
			}


			LOGGER.debug("[processOrder] process order shopping cart process order");
			//order service
			modelOrder = orderService.processOrder(modelOrder, customer, items, orderTotalSummary, paymentModel, store);

			// update cart
			try {
				LOGGER.info("[processOrder] process order shopping cart save update");
				cart.setOrderId(modelOrder.getId());
				shoppingCartFacade.saveOrUpdateShoppingCart(cart);
			} catch (Exception e) {
				LOGGER.error("Cannot delete cart " + cart.getId(), e);
			}

			//email management
			if ("true".equals(coreConfiguration.getProperty("ORDER_EMAIL_API"))) {
				// send email
				try {

					notify(modelOrder, customer, store, language, locale);


				} catch (Exception e) {
					LOGGER.error("Cannot send order confirmation email", e);
				}
			}
			LogPermUtil.end("processOrder", start);
			return modelOrder;

		} catch (Exception e) {
			LOGGER.error("processOrder error", e);
			throw new ServiceException(e);

		}

	}


	/**
	 * 订单拆分
	 * @param oldOrderId  拆单的订单id
	 * @param orderProductIdList 需要拆单的订单商品id
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public Order processOrderSplit(Long oldOrderId, List<Long> orderProductIdList) throws ServiceException {

		long start = LogPermUtil.start("processOrder");
		try {
			Order oldOrder =  orderService.getById(oldOrderId);

			Long customerOrderId = orderService.findCustomerOrderIdByOrderId(oldOrderId);


			Order newOrderFromOld = createNewOrderFromOld(oldOrder);

			if (orderProductIdList.size()>=oldOrder.getOrderProducts().size()){
				throw new ServiceRuntimeException("Need to reserve an order product");
			}

			Set<OrderProduct> orderProducts = new LinkedHashSet<OrderProduct>();

			OrderProductPopulator orderProductPopulator = new OrderProductPopulator();
			orderProductPopulator.setDigitalProductService(digitalProductService);
			orderProductPopulator.setProductAttributeService(productAttributeService);
			orderProductPopulator.setProductService(productService);

			for (Long orderProductId : orderProductIdList) {
				OrderProduct orderProduct = orderProductService.getOrderProduct(orderProductId);
				orderProducts.add(orderProduct);
			}

			newOrderFromOld.setOrderProducts(orderProducts);

			Long customerId = oldOrder.getCustomerId();

			Customer customer = customerService.getById(customerId);

			//开始计算新创建的订单金额；
			LOGGER.debug("[processOrder] process order shopping cart calculate order total");

			String languageString = oldOrder.getLocale().getLanguage();
			Language language = languageService.getByCode(languageString);

			//这个地方是计算订单商品金额
			OrderTotalSummary orderTotalSummary = caculateOrderBySplitOrder(newOrderFromOld, Lists.newArrayList(orderProducts), customer, oldOrder.getMerchant(), language);

			BigDecimal calculatedAmount = orderTotalSummary.getTotal();

			newOrderFromOld.setTotal(calculatedAmount);
			List<com.salesmanager.core.model.order.OrderTotal> totals = orderTotalSummary.getTotals();
			Set<com.salesmanager.core.model.order.OrderTotal> set = new HashSet<com.salesmanager.core.model.order.OrderTotal>();

			if (!CollectionUtils.isEmpty(totals)) {
				for (com.salesmanager.core.model.order.OrderTotal total : totals) {
					total.setOrder(newOrderFromOld);
					set.add(total);
				}
			}
			newOrderFromOld.setOrderTotal(set);

			//查询关联关系
			List<OrderRelation> orderRelationBySourceOrderId = orderRelationRepository.findOrderRelationBySourceOrderId(oldOrderId);

			//根据关联关系创建订单号
			if (CollectionUtils.isEmpty(orderRelationBySourceOrderId)){
				newOrderFromOld.setOrderNo(oldOrder.getOrderNo()+"-1");
			}else {
				newOrderFromOld.setOrderNo(oldOrder.getOrderNo()+"-"+orderRelationBySourceOrderId.size());
			}

			//创建订单
			orderService.create(newOrderFromOld);

			FulfillmentMainOrder fulfillmentMainOrder = fulfillmentMainOrderService.onlyCreateFulfillmentMainOrder(newOrderFromOld, true);

			CustomerOrder customerOrder = customerOrderService.getCustomerOrder(customerOrderId);
			List<Order> orders = customerOrder.getOrders();
			orders.add(newOrderFromOld);
			customerOrderService.save(customerOrder);

			//保存关联关系
			OrderRelation orderRelation = new OrderRelation();
			if (CollectionUtils.isEmpty(orderRelationBySourceOrderId)){
				orderRelation.setRootOrderId(oldOrder.getId());
			}else {
				orderRelation.setRootOrderId(orderRelationBySourceOrderId.get(0).getRootOrderId());
			}

			orderRelation.setSourceOrderId(oldOrder.getId());
			orderRelation.setOrderId(newOrderFromOld.getId());
			orderRelationRepository.save(orderRelation);

			Order finalModelOrder = newOrderFromOld;

			//删除订单商品id
			orderProductIdList.forEach(id->{
				orderProductService.updateOrderIdById(newOrderFromOld.getId(), id);

				QcInfo qcInfo = qcInfoService.queryQcInfoByOrderProductId(id);
				//更新qc 替换订单id
				qcInfoService.updateQcOrderIdById(finalModelOrder.getId(), qcInfo.getId());
				//更新履约子单 orderId 和履约主单id
				fulfillmentSubOrderService.updateFulfillmentSubOrderIdAndFulfillmentMainIdByOrderProductId(finalModelOrder.getId(), fulfillmentMainOrder.getId(), id);
				//更新履约单历史记录id
				fulfillmentHistoryService.updateOrderIdByOrderProductId(finalModelOrder.getId(), id);
			});

			//这个地方是计算订单商品金额
			List<OrderProduct> oldOrderProducts = removeOrderProducts(oldOrder, orderProductIdList);

			OrderTotalSummary oldOrderTotalSummary = caculateOrderBySplitOrder(oldOrder, oldOrderProducts, customer, oldOrder.getMerchant(), language);

			BigDecimal oldCalculatedAmount = oldOrderTotalSummary.getTotal();

			newOrderFromOld.setTotal(oldCalculatedAmount);
			List<com.salesmanager.core.model.order.OrderTotal> oldTotals = oldOrderTotalSummary.getTotals();
			Set<com.salesmanager.core.model.order.OrderTotal> oldSet = new HashSet<com.salesmanager.core.model.order.OrderTotal>();

			if (!CollectionUtils.isEmpty(oldTotals)) {
				for (com.salesmanager.core.model.order.OrderTotal total : oldTotals) {
					orderTotalService.updateValueByOrderIdAndModule(total.getValue(), oldOrder.getId(), total.getModule());

					total.setOrder(newOrderFromOld);
					oldSet.add(total);
				}
			}
			//最后在更新一下order里面的金额
			orderService.updateOrderTotalPriceByOrderId(oldOrder.getId(), oldCalculatedAmount);

			LogPermUtil.end("processOrderSplit", start);
			return newOrderFromOld;
		}catch (ServiceRuntimeException e) {
			LOGGER.error("processOrder error", e);
			throw e;
		} catch (Exception e) {
			LOGGER.error("processOrder error", e);
			throw new ServiceException(e);
		}

	}

	@Async
	protected void notify(Order order, Customer customer, MerchantStore store, Language language, Locale locale) throws Exception {

		// send order confirmation email to customer
		emailTemplatesUtils.sendOrderEmail(customer.getEmailAddress(), customer, order, locale,
				language, store, coreConfiguration.getProperty("CONTEXT_PATH"));

		if (orderService.hasDownloadFiles(order)) {
			emailTemplatesUtils.sendOrderDownloadEmail(customer, order, store, locale,
					coreConfiguration.getProperty("CONTEXT_PATH"));
		}

		// send customer credentials

		// send order confirmation email to merchant
		emailTemplatesUtils.sendOrderEmail(store.getStoreEmailAddress(), customer, order, locale,
				language, store, coreConfiguration.getProperty("CONTEXT_PATH"));


	}

	@Override
	public com.salesmanager.shop.model.order.v0.ReadableOrderList getCapturableOrderList(MerchantStore store,
			Date startDate, Date endDate, Language language) throws Exception {

		// get all transactions for the given date
		List<Order> orders = orderService.getCapturableOrders(store, startDate, endDate);

		// ReadableOrderPopulator orderPopulator = new ReadableOrderPopulator();
		Locale locale = LocaleUtils.getLocale(language);
		readableOrderPopulator.setLocale(locale);

		com.salesmanager.shop.model.order.v0.ReadableOrderList returnList = new com.salesmanager.shop.model.order.v0.ReadableOrderList();

		if (CollectionUtils.isEmpty(orders)) {
			returnList.setRecordsTotal(0);
			// returnList.setMessage("No results for store code " + store);
			return null;
		}

		List<com.salesmanager.shop.model.order.v0.ReadableOrder> readableOrders = new ArrayList<com.salesmanager.shop.model.order.v0.ReadableOrder>();
		for (Order order : orders) {
			com.salesmanager.shop.model.order.v0.ReadableOrder readableOrder = new com.salesmanager.shop.model.order.v0.ReadableOrder();
			readableOrderPopulator.populate(order, readableOrder, store, language);
			readableOrders.add(readableOrder);

		}

		returnList.setRecordsTotal(orders.size());
		returnList.setOrders(readableOrders);

		return returnList;
	}

	@Override
	public ReadableTransaction captureOrder(MerchantStore store, Order order, Customer customer, Language language)
			throws Exception {
		Transaction transactionModel = paymentService.processCapturePayment(order, customer, store);

		ReadableTransaction transaction = new ReadableTransaction();
		ReadableTransactionPopulator trxPopulator = new ReadableTransactionPopulator();
		trxPopulator.setOrderService(orderService);
		trxPopulator.setPricingService(pricingService);

		trxPopulator.populate(transactionModel, transaction, store, language);

		return transaction;

	}

	@Override
	public List<ReadableOrderStatusHistory> getReadableOrderHistory(Long orderId, MerchantStore store,
			Language language) {

		Order order = orderService.getOrder(orderId, store);
		if (order == null) {
			throw new ResourceNotFoundException(
					"Order id [" + orderId + "] not found for merchand [" + store.getId() + "]");
		}

		Set<OrderStatusHistory> historyList = order.getOrderHistory();
		List<ReadableOrderStatusHistory> returnList = historyList.stream().map(f -> mapToReadbleOrderStatusHistory(f))
				.collect(Collectors.toList());
		return returnList;
	}

	ReadableOrderStatusHistory mapToReadbleOrderStatusHistory(OrderStatusHistory source) {
		ReadableOrderStatusHistory readable = new ReadableOrderStatusHistory();
		readable.setComments(source.getComments());
		readable.setDate(DateUtil.formatLongDate(source.getDateAdded()));
		readable.setId(source.getId());
		readable.setOrderId(source.getOrder().getId());
		readable.setOrderStatus(source.getStatus().name());

		return readable;
	}

	@Override
	public void createOrderStatus(PersistableOrderStatusHistory status, Long id, MerchantStore store) {
		Validate.notNull(status, "OrderStatusHistory must not be null");
		Validate.notNull(id, "Order id must not be null");
		Validate.notNull(store, "MerchantStore must not be null");

		// retrieve original order
		Order order = orderService.getOrder(id, store);
		if (order == null) {
			throw new ResourceNotFoundException(
					"Order with id [" + id + "] does not exist for merchant [" + store.getCode() + "]");
		}

		try {
			OrderStatusHistory history = new OrderStatusHistory();
			history.setComments(status.getComments());
			history.setDateAdded(DateUtil.getDate(status.getDate()));
			history.setOrder(order);
			history.setStatus(status.getStatus());

			orderService.addOrderStatusHistory(order, history);

		} catch (Exception e) {
			throw new ServiceRuntimeException("An error occured while converting orderstatushistory", e);
		}

	}

	@Override
	public void updateOrderCustomre(Long orderId, PersistableCustomer customer, MerchantStore store) {
		// TODO Auto-generated method stub

		try {

		//get order by order id
		Order modelOrder = orderService.getOrder(orderId, store);

		if(modelOrder == null) {
			throw new ResourceNotFoundException("Order id [" + orderId + "] not found for store [" + store.getCode() + "]");
		}

		//set customer information
		modelOrder.setCustomerEmailAddress(customer.getEmailAddress());
		modelOrder.setBilling(this.convertBilling(customer.getBilling()));
		modelOrder.setDelivery(this.convertDelivery(customer.getDelivery()));

		orderService.saveOrUpdate(modelOrder);

		} catch(Exception e) {
			throw new ServiceRuntimeException("An error occured while updating order customer", e);
		}

	}

	private Billing convertBilling(Address source) throws ServiceException {
		Billing target = new Billing();
        target.setCity(source.getCity());
        target.setCompany(source.getCompany());
        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        target.setPostalCode(source.getPostalCode());
        target.setTelephone(source.getPhone());
        target.setAddress(source.getAddress());
        if(source.getCountry()!=null) {
        	target.setCountry(countryService.getByCode(source.getCountry()));
        }

        if(source.getZone()!=null) {
            target.setZone(zoneService.getByCode(source.getZone()));
        }
        target.setState(source.getBilstateOther());

        return target;
	}

	private Delivery convertDelivery(Address source) throws ServiceException {
		Delivery target = new Delivery();
        target.setCity(source.getCity());
        target.setCompany(source.getCompany());
        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        target.setPostalCode(source.getPostalCode());
        target.setTelephone(source.getPhone());
        target.setAddress(source.getAddress());
        if(source.getCountry()!=null) {
        	target.setCountry(countryService.getByCode(source.getCountry()));
        }

        if(source.getZone()!=null) {
            target.setZone(zoneService.getByCode(source.getZone()));
        }
        target.setState(source.getBilstateOther());

        return target;
	}

	@Override
	public TransactionType nextTransaction(Long orderId, MerchantStore store) {

		try {

			Order modelOrder = orderService.getOrder(orderId, store);

			if(modelOrder == null) {
				throw new ResourceNotFoundException("Order id [" + orderId + "] not found for store [" + store.getCode() + "]");
			}

			Transaction last = transactionService.lastTransaction(modelOrder, store);

			if(last.getTransactionType().name().equals(TransactionType.AUTHORIZE.name())) {
				return TransactionType.CAPTURE;
			} else if(last.getTransactionType().name().equals(TransactionType.AUTHORIZECAPTURE.name())) {
				return TransactionType.REFUND;
			} else if(last.getTransactionType().name().equals(TransactionType.CAPTURE.name())) {
				return TransactionType.REFUND;
			} else if(last.getTransactionType().name().equals(TransactionType.REFUND.name())) {
				return TransactionType.OK;
			} else {
				return TransactionType.OK;
			}


		} catch(Exception e) {
			throw new ServiceRuntimeException("Error while getting last transaction for order [" + orderId + "]",e);
		}


	}

	@Override
	public List<ReadableTransaction> listTransactions(Long orderId, MerchantStore store) {
		Validate.notNull(orderId, "orderId must not be null");
		Validate.notNull(store, "MerchantStore must not be null");
		List<ReadableTransaction> trx = new ArrayList<ReadableTransaction>();
		try {
			Order modelOrder = orderService.getOrder(orderId, store);

			if(modelOrder == null) {
				throw new ResourceNotFoundException("Order id [" + orderId + "] not found for store [" + store.getCode() + "]");
			}

			List<Transaction> transactions = transactionService.listTransactions(modelOrder);

			ReadableTransaction transaction = null;
			ReadableTransactionPopulator trxPopulator = null;

			for(Transaction tr : transactions) {
				transaction = new ReadableTransaction();
				trxPopulator = new ReadableTransactionPopulator();

				trxPopulator.setOrderService(orderService);
				trxPopulator.setPricingService(pricingService);

				trxPopulator.populate(tr, transaction, store, store.getDefaultLanguage());
				trx.add(transaction);
			}

			return trx;

		} catch(Exception e) {
			LOGGER.error("Error while getting transactions for order [" + orderId + "] and store code [" + store.getCode() + "]");
			throw new ServiceRuntimeException("Error while getting transactions for order [" + orderId + "] and store code [" + store.getCode() + "]");
		}

	}

	@Override
	public void updateOrderStatus(Order order, OrderStatus newStatus, MerchantStore store, String reason) {

		// make sure we are changing to different that current status
		if (order.getStatus().equals(newStatus)) {
			return; // we have the same status, lets just return
		}
		OrderStatus oldStatus = order.getStatus();
		order.setStatus(newStatus);
		OrderStatusHistory history = new OrderStatusHistory();

		String statusChangeComments = messages.getMessage("email.order.status.changed", new String[]{oldStatus.name(),
				newStatus.name()}, LocaleUtils.getLocale(store));
		if (StringUtils.isNotBlank(reason)) {
			statusChangeComments += ", reason:" + reason;
		}
		history.setComments(statusChangeComments);
		history.setCustomerNotified(0);
		history.setStatus(newStatus);
		history.setDateAdded(new Date() );

		try {
			orderService.updateOrderStatus(order.getId(), newStatus);
			orderService.addOrderStatusHistory(order, history);

			Set<OrderProduct> orderProducts = order.getOrderProducts();
			orderProducts.forEach(orderProduct -> {
				fulfillmentHistoryService.saveFulfillmentHistory(order.getId(),
						orderProduct.getId(), newStatus.name(), oldStatus.name() );
			});

		} catch (ServiceException e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<ReadableOrderProduct> queryOrderProductsByOrderId(Long orderId, Language language) {
		List<OrderProduct> orderProducts = orderProductService.getOrderProducts(orderId);


		return orderProducts.stream().map(orderProduct -> {
			ReadableOrderProduct readableOrderProduct = new ReadableOrderProduct();
			try {
				orderProductPopulatorUtil.buildReadableOrderProduct(orderProduct, readableOrderProduct, orderProduct.getOrder().getMerchant(), language);
			} catch (ConversionException e) {
				return null;
			}
			return readableOrderProduct;
		}).filter(Objects::nonNull).collect(Collectors.toList());

	}



	@Override
	public List<ReadableOrderProduct> queryShippingOrderListProducts(Locale locale, Language language, Long shippingOrderId) throws ConversionException {
		ShippingDocumentOrder shippingDocumentOrder = shippingDocumentOrderRepository.getById(shippingOrderId);
		if (shippingDocumentOrder == null){
			return null;
		}
		Set<OrderProduct> orderProducts = shippingDocumentOrder.getOrderProducts();
		if (CollectionUtils.isEmpty(orderProducts)){
			return null;
		}
		List<ReadableOrderProduct> returnList = new ArrayList<>();

		for (OrderProduct product : orderProducts) {
			ReadableOrderProduct orderProduct = new ReadableOrderProduct();
			orderProductPopulatorUtil.buildReadableOrderProduct(product, orderProduct, product.getOrder().getMerchant(), language);
			returnList.add(orderProduct);
		}
		return returnList;
	}

	@Override
	public ReadableOrderProductShippingList queryShippingOrderProductsList(Locale locale, Language language, ShippingOrderProductQuery query) {

		ReadableOrderProductShippingList readableList = new ReadableOrderProductShippingList();
		try {
			/**
			 * Is this a pageable request
			 */
			OrderProductList orderProductList = orderProductRepository.findByProductNameOrOrderId(query);


			readableList.setRecordsTotal(orderProductList.getTotalCount());

			List<OrderProduct> content = orderProductList.getOrderProducts();

			List<ReadableOrderProduct> returnList = new ArrayList<>();

			if (CollectionUtils.isEmpty(content)){
				return readableList;
			}

			for (OrderProduct product : content) {
				ReadableOrderProduct orderProduct = new ReadableOrderProduct();
				orderProductPopulatorUtil.buildReadableOrderProduct(product, orderProduct, product.getOrder().getMerchant(), language);
				returnList.add(orderProduct);
			}

			readableList.setProducts(returnList);
			readableList.setTotalPages(ListUtils.calculateTotalPages(readableList.getRecordsTotal(), query.getMaxCount()));
			return readableList;

		} catch (Exception e) {
			throw new ServiceRuntimeException("Error while get manufacturers",e);
		}
	}

	@Override
	public ReadableShippingDocumentOrderList queryShippingDocumentOrderList(Locale locale, Language language, ShippingOrderProductQuery query){
		ReadableShippingDocumentOrderList readableList = new ReadableShippingDocumentOrderList();
		try {
			/**
			 * Is this a pageable request
			 */
			ShippingDocumentOrderList orderProductList = shippingDocumentOrderRepository.queryShippingDocumentOrderList(query);


			readableList.setRecordsTotal(orderProductList.getTotalCount());

			List<ShippingDocumentOrder> content = orderProductList.getShippingDocumentOrders();

			List<ReadableShippingDocumentOrder> readableShippingDocumentOrders = new ArrayList<>();

			if (CollectionUtils.isEmpty(content)){
				return readableList;
			}

			for (ShippingDocumentOrder shippingDocumentOrder : content) {

				ReadableShippingDocumentOrder readableShippingDocumentOrder = new ReadableShippingDocumentOrder();
				readableShippingDocumentOrder.setShippingNo(shippingDocumentOrder.getShippingNo());
				readableShippingDocumentOrder.setId(shippingDocumentOrder.getId());
				Set<GeneralDocument> generalDocuments = shippingDocumentOrder.getGeneralDocuments();

				if (CollectionUtils.isNotEmpty(generalDocuments)) {
					List<ReadableGeneralDocument> readableGeneralDocuments = generalDocuments.stream()
							.map(generalDocument -> {
								ReadableGeneralDocument readableGeneralDocument = ObjectConvert.convert(generalDocument, ReadableGeneralDocument.class);
								readableGeneralDocument.setDocumentType(generalDocument.getDocumentType()==null? null : generalDocument.getDocumentType().name());
								return readableGeneralDocument;
							})
							.filter(Objects::nonNull)
							.collect(Collectors.toList());

					if (shippingDocumentOrder.getInvoicePackingFormId()!=null){
						InvoicePackingForm invoicePackingForm = invoicePackingFormService.getById(shippingDocumentOrder.getInvoicePackingFormId());
						if (invoicePackingForm != null) {
							readableShippingDocumentOrder.setInvoicePackingForm(convertToReadableInvoicePackingForm(invoicePackingForm));
						}

						readableShippingDocumentOrder.setGeneralDocuments(readableGeneralDocuments);
					}
				}
				readableShippingDocumentOrders.add(readableShippingDocumentOrder);
			}

			readableList.setReadableShippingDocumentOrders(readableShippingDocumentOrders);
			readableList.setTotalPages(ListUtils.calculateTotalPages(readableList.getRecordsTotal(), query.getMaxCount()));
			return readableList;

		} catch (Exception e) {
			throw new ServiceRuntimeException("Error while get manufacturers",e);
		}

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


	@Override
	@Transactional
	public String createShippingDocumentOrder(Long createDate){
		Integer count = shippingDocumentOrderRepository.queryCountByCreateDate(new Date(createDate));
		ShippingDocumentOrder shippingDocumentOrder = new ShippingDocumentOrder();
		AuditSection auditSection = new AuditSection();
		auditSection.setDateCreated(new Date(createDate));
		shippingDocumentOrder.setAuditSection(auditSection);
		shippingDocumentOrder.setShippingNo(UniqueIdGenerator.generateShippingOrderId(createDate, count+1));
		shippingDocumentOrderRepository.save(shippingDocumentOrder);
		return shippingDocumentOrder.getShippingNo();
	}

	@Override
	public void deleteShippingDocumentOrder(Long id) {
		ShippingDocumentOrder byId = shippingDocumentOrderRepository.getById(id);
		List<Long> orderProducts = orderProductRepository.findIdListByShippingDocumentOrderId(id);

		orderProducts.forEach(orderProductId->{
			orderProductRepository.updateShippingDocumentOrderIdById(null, orderProductId);
		});

		shippingDocumentOrderRepository.delete(byId);
	}

	@Override
	@Transactional
	public void addShippingProductByOrderProductId(Long id, Long orderProductId) {
		ShippingDocumentOrder shippingDocumentOrder = shippingDocumentOrderRepository.getById(id);
		if (shippingDocumentOrder == null){
			return;
		}
		OrderProduct orderProduct = orderProductService.getOrderProduct(orderProductId);
		if (orderProduct == null){
			return;
		}

		orderProductRepository.updateShippingDocumentOrderIdById(id, orderProductId);

		orderProductRepository.updateIsInShippingOrderById(true, orderProductId);
	}

	@Override
	@Transactional
	public void removeShippingProductByOrderProductId(Long id, Long orderProductId) {
		ShippingDocumentOrder shippingDocumentOrder = shippingDocumentOrderRepository.getById(id);
		if (shippingDocumentOrder == null){
			return;
		}
		OrderProduct orderProduct = orderProductService.getOrderProduct(orderProductId);
		if (orderProduct == null){
			return;
		}

		orderProductRepository.updateShippingDocumentOrderIdById(null, orderProductId);

		orderProductRepository.updateIsInShippingOrderById(false, orderProductId);
	}


	@Override
	public List<ReadableCombineTransactionList> getCapturableCombineTransactionInfoByOrderId(Long orderId, MerchantStore merchantStore, Language language) throws ServiceException, ConversionException{


		ReadableCombineTransactionList readableCombineTransactionList= new ReadableCombineTransactionList();

		Long customerOrderIdByOrderId = orderService.findCustomerOrderIdByOrderId(orderId);
		CustomerOrder customerOrder = new CustomerOrder();
		customerOrder.setId(customerOrderIdByOrderId);

		List<CombineTransaction> combineTransactions = combineTransactionService.listCombineTransactions(customerOrder);

		List<ReadableCombineTransaction> readableCombineTransactions = new ArrayList<>();

		for(CombineTransaction combineTransaction : combineTransactions){
			try {
				if (combineTransaction.getTransactionDetails().get("orderId") != null &&
						combineTransaction.getTransactionDetails().get("orderId").equals(String.valueOf(orderId))) {
					ReadableCombineTransaction readableCombineTransaction = new ReadableCombineTransaction();
					ReadableCombineTransactionPopulator populator = new ReadableCombineTransactionPopulator();
					populator.setPricingService(pricingService);
					populator.populate(combineTransaction, readableCombineTransaction, merchantStore, language);
					readableCombineTransactions.add(readableCombineTransaction) ;
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		readableCombineTransactionList.setCombineTransactionList(readableCombineTransactions);

		ReadableOrderAdditionalPayment orderAdditionalPayment = readableOrderAdditionalPaymentPopulator.populate(orderAdditionalPaymentService.findById(String.valueOf(orderId)).orElse(null), new ReadableOrderAdditionalPayment(), null, null);
		if (orderAdditionalPayment !=null){
			readableCombineTransactionList.setReadableOrderAdditionalPaymentList(com.google.common.collect.Lists.newArrayList(orderAdditionalPayment));

		}
		return com.google.common.collect.Lists.newArrayList(readableCombineTransactionList);
	}


	@Override
	public List<ReadableCombineTransactionList> getCapturableCombineTransactionInfoByCustomerOrderId(Long customerOrderId,
																									 MerchantStore merchantStore, Language language) throws ConversionException, ServiceException {

		ReadableCombineTransactionList readableCombineTransactionList = new ReadableCombineTransactionList();

		CustomerOrder customerOrder = new CustomerOrder();
		customerOrder.setId(customerOrderId);
		List<CombineTransaction> combineTransactions = combineTransactionService.listCombineTransactions(customerOrder);

		CustomerOrder customerOrderById = customerOrderService.getById(customerOrderId);
		List<Order> orders = customerOrderById.getOrders();
		if (orders != null){
			List<ReadableOrderAdditionalPayment> orderAdditionalPaymentList = orders.stream().map(order -> {
				try {
					OrderAdditionalPayment orderAdditionalPayment = orderAdditionalPaymentService.findById(String.valueOf(order.getId())).orElse(null);
					if (orderAdditionalPayment !=null ){
						ReadableOrderAdditionalPayment populate = readableOrderAdditionalPaymentPopulator.populate(orderAdditionalPayment, new ReadableOrderAdditionalPayment(), null, null);
						return populate;
					}
					return null;
				} catch (ConversionException e) {
					throw new RuntimeException(e);
				}
			}).filter(Objects::nonNull).collect(Collectors.toList());

			readableCombineTransactionList.setReadableOrderAdditionalPaymentList(orderAdditionalPaymentList);
		}

		List<ReadableCombineTransaction> readableCombineTransactions = new ArrayList<>();
		for(CombineTransaction combineTransaction : combineTransactions){
			try {
				ReadableCombineTransaction readableCombineTransaction = new ReadableCombineTransaction();
				ReadableCombineTransactionPopulator populator = new ReadableCombineTransactionPopulator();
				populator.setPricingService(pricingService);
				populator.populate(combineTransaction, readableCombineTransaction, merchantStore, language);
				readableCombineTransactions.add(readableCombineTransaction) ;


			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		readableCombineTransactionList.setCombineTransactionList(readableCombineTransactions);

		return com.google.common.collect.Lists.newArrayList(readableCombineTransactionList);
	}


	/**
	 * 计算拆单后的价格
	 * @param orderProducts
	 * @param customer
	 * @param store
	 * @param language
	 * @return
	 * @throws Exception
	 */
	public OrderTotalSummary caculateOrderBySplitOrder(Order modelOrder, List<OrderProduct> orderProducts, Customer customer, final MerchantStore store, final Language language) throws Exception {
		long start = LogPermUtil.start("OrderService/caculateOrder");
		OrderTotalSummary totalSummary = new OrderTotalSummary();
		List<com.salesmanager.core.model.order.OrderTotal> orderTotals = new ArrayList<com.salesmanager.core.model.order.OrderTotal>();

		BigDecimal grandTotal = new BigDecimal(0);
		grandTotal.setScale(0, RoundingMode.HALF_UP);

		LOGGER.debug("[caculateOrder] calculate order qty price");
		//price by item
		/**
		 * qty * price
		 * subtotal
		 */
		BigDecimal subTotal = new BigDecimal(0);

		//手续费
		BigDecimal totalProductHandlingFeePrice = BigDecimal.ZERO.setScale(0, RoundingMode.UP);

		//运费
		BigDecimal totalShippingPrice = BigDecimal.ZERO.setScale(0, RoundingMode.UP);

		//增值服务费
		BigDecimal totalAdditionalServicesPrice = BigDecimal.ZERO.setScale(0, RoundingMode.UP);

		//erp费用
		BigDecimal erpPrice = BigDecimal.ZERO.setScale(0, RoundingMode.UP);


		subTotal.setScale(0, RoundingMode.UP);
		for(OrderProduct item : orderProducts) {

			//查询快照；
			OrderProductSnapshot snapshotByOrderProduct = orderProductSnapshotService.findSnapshotByOrderProductId(item.getId());

			String snapshot = snapshotByOrderProduct.getSnapshot();

			ReadableProductSnapshot readableProductSnapshot = JSON.parseObject(snapshot, ReadableProductSnapshot.class);

			if (item.getPrices() == null) {
				throw new ServiceException("shopping cart item sku = [" +item.getSku()+"] price is null");
			}
			//创单时候价格
			Set<OrderProductPrice> prices = item.getPrices();
			OrderProductPrice price = prices.iterator().next();

			BigDecimal st = price.getProductPrice().multiply(new BigDecimal(item.getProductQuantity()));
			subTotal = subTotal.add(st);

			List<ReadableCategory> sortedCategories = readableProductSnapshot.getCategories();
			sortedCategories.sort((c1, c2) -> Integer.compare(c2.getDepth(), c1.getDepth()));

			for (ReadableCategory category : sortedCategories) {
				if (category != null ) {
					if (StringUtils.isNotEmpty(category.getHandlingFeeFor1688())
							&& readableProductSnapshot.getPublishWay() != null
							&& PublishWayEnums.IMPORT_BY_1688.name().equals(readableProductSnapshot.getPublishWay())){
						BigDecimal handlingFee = new BigDecimal(category.getHandlingFeeFor1688()).setScale(3, RoundingMode.UP);
						BigDecimal handlingFeeDecimal = handlingFee.divide(new BigDecimal("100"));
						BigDecimal itemPrice = price.getProductPrice();
						BigDecimal finalHandlingFeePrice = itemPrice.multiply(handlingFeeDecimal).setScale(0, RoundingMode.UP);
						finalHandlingFeePrice = finalHandlingFeePrice.multiply(new BigDecimal(item.getProductQuantity()));
						totalProductHandlingFeePrice = totalProductHandlingFeePrice.add(finalHandlingFeePrice);
						break;
					}
					if (StringUtils.isNotEmpty(category.getHandlingFee())){
						BigDecimal handlingFee = new BigDecimal(category.getHandlingFee()).setScale(3, RoundingMode.UP);
						BigDecimal handlingFeeDecimal = handlingFee.divide(new BigDecimal("100"));
						BigDecimal itemPrice = price.getProductPrice();
						BigDecimal finalHandlingFeePrice = itemPrice.multiply(handlingFeeDecimal).setScale(0, RoundingMode.UP);
						finalHandlingFeePrice = finalHandlingFeePrice.multiply(new BigDecimal(item.getProductQuantity()));
						totalProductHandlingFeePrice = totalProductHandlingFeePrice.add(finalHandlingFeePrice);
						break;
					}
				}
			}


			//运费
			ShippingType shippingType = item.getShippingType();
			//跨境运费处理
			if (shippingType != null && shippingType == shippingType.INTERNATIONAL){
				switch(item.getNationalTransportationMethod()){
					case SHIPPING:
						//todo 计算价格
						break;
					case AIR_TRANSPORTATION:
						//todo 计算价格
						break;
				}

			}

			//国内运费处理逻辑
			if (shippingType != null && shippingType == shippingType.NATIONAL){
				//委托配送价格
				if (ShippingTransportationType.COMMISSIONED_DELIVERY == item.getShippingTransportationType()
						&& item.getNationalTransportationMethod() !=null){
					switch(item.getNationalTransportationMethod()){
						case TRUCK:
							TruckModelEnums truckModel = item.getTruckModel();
							TruckTypeEnums truckType = item.getTruckType();
							//todo计算金额
							break;
						case SHIPPING:
							break;

						case LOGISTICS:
							break;

						case URGENT_DELIVERY:
							break;

						case DIRECT_DELIVERY:
							break;
					}
					//todo 计算费用
				}
			}

			//additionalServicePrice
			String additionalServicesMap = item.getAdditionalServicesMap();
			if (StringUtils.isNotEmpty(additionalServicesMap)){
				Map<String, String> additionalServiceMapFromJson = (Map<String, String>) JSON.parse(additionalServicesMap);

				Set<String> keySet = additionalServiceMapFromJson.keySet();

				for(String id : keySet){
					AdditionalServices additionalServices = additionalServicesService.queryAdditionalServicesById(Long.valueOf(id));

					String additionalServicesPrice = AdditionalServicesUtils.getPrice(additionalServices, additionalServiceMapFromJson.get(id) == null ? 0 :Integer.valueOf(additionalServiceMapFromJson.get(id)), item.getProductQuantity());

					totalAdditionalServicesPrice = totalAdditionalServicesPrice.add(new BigDecimal(additionalServicesPrice).setScale(0, RoundingMode.UP));
				}
			}


//            //erp
//            List<ProductMaterial> productMaterials = productMaterialService.queryByProductId(item.getProductId());
//            if (CollectionUtils.isNotEmpty(productMaterials)){
//                for (ProductMaterial productMaterial : productMaterials ){
//                    Long materialId = productMaterial.getMaterialId();
//                    Material material  = erpService.getById(materialId);
//                    BigDecimal price = material.getPrice();
//                    Long weight = productMaterial.getWeight();
//                    BigDecimal productErpPrice = price.multiply(BigDecimal.valueOf(weight)).setScale(2, RoundingMode.HALF_UP);
//                    erpPrice = erpPrice.add(productErpPrice);
//                }
//            }


		}

		LOGGER.debug("[caculateOrder] calculate order total");

		//查询之前的折扣
		Set<com.salesmanager.core.model.order.OrderTotal> oldOrderTotalSet = modelOrder.getOrderTotal();

		com.salesmanager.core.model.order.OrderTotal oldDiscountOrderTotal = oldOrderTotalSet.stream().filter(oldOrderTotal -> {
			return Constants.OT_DISCOUNT_TITLE.equals(oldOrderTotal.getOrderTotalCode());
		}).findFirst().orElse(null);

		if (oldDiscountOrderTotal !=null){
			OrderSummary orderSummary = new OrderSummary();
			orderSummary.setPromoCode(oldDiscountOrderTotal.getText());
			//计算折扣
			OrderTotalVariation orderTotalVariation = orderTotalService.findOrderTotalVariation(orderSummary, customer, store, language);
			int currentCount = 10;
			if(CollectionUtils.isNotEmpty(orderTotalVariation.getVariations())) {
				for(com.salesmanager.core.model.order.OrderTotal variation : orderTotalVariation.getVariations()) {
					variation.setSortOrder(currentCount++);
					orderTotals.add(variation);
					subTotal = subTotal.subtract(variation.getValue());
				}
			}
		}
		totalSummary.setSubTotal(subTotal);


		//商品费用
		com.salesmanager.core.model.order.OrderTotal orderTotalSubTotal = new com.salesmanager.core.model.order.OrderTotal();
		orderTotalSubTotal.setModule(Constants.OT_SUBTOTAL_MODULE_CODE);
		orderTotalSubTotal.setOrderTotalType(OrderTotalType.SUBTOTAL);
		orderTotalSubTotal.setOrderTotalCode("order.total.subtotal");
		orderTotalSubTotal.setTitle(Constants.OT_SUBTOTAL_MODULE_CODE);
		orderTotalSubTotal.setSortOrder(5);
		orderTotalSubTotal.setValue(subTotal);
		orderTotals.add(orderTotalSubTotal);


		//加价费用
		com.salesmanager.core.model.order.OrderTotal handlingubTotal = new com.salesmanager.core.model.order.OrderTotal();
		handlingubTotal.setModule(Constants.OT_HANDLING_MODULE_CODE);
		handlingubTotal.setOrderTotalType(OrderTotalType.HANDLING);
		handlingubTotal.setOrderTotalCode("order.total.handling");
		handlingubTotal.setTitle(Constants.OT_HANDLING_MODULE_CODE);
		handlingubTotal.setText("order.total.handling");
		handlingubTotal.setSortOrder(100);
		handlingubTotal.setValue(totalProductHandlingFeePrice);
		orderTotals.add(handlingubTotal);


		//运费
		com.salesmanager.core.model.order.OrderTotal shippingSubTotal = new com.salesmanager.core.model.order.OrderTotal();
		shippingSubTotal.setModule(Constants.OT_SHIPPING_MODULE_CODE);
		shippingSubTotal.setOrderTotalType(OrderTotalType.SHIPPING);
		shippingSubTotal.setOrderTotalCode("order.total.shipping");
		shippingSubTotal.setTitle(Constants.OT_SHIPPING_MODULE_CODE);
		shippingSubTotal.setSortOrder(103);
		shippingSubTotal.setText("order.total.shipping");
		shippingSubTotal.setValue(totalShippingPrice);
		orderTotals.add(shippingSubTotal);


		//增值服务费用
		com.salesmanager.core.model.order.OrderTotal additionalServicesSubTotal = new com.salesmanager.core.model.order.OrderTotal();
		additionalServicesSubTotal.setModule(Constants.OT_ADDITIONAL_SERVICE_PRICE_MODULE_CODE);
		additionalServicesSubTotal.setOrderTotalType(OrderTotalType.ADDITIONAL_SERVICE);
		additionalServicesSubTotal.setOrderTotalCode("order.total.additionalServices");
		additionalServicesSubTotal.setSortOrder(102);
		additionalServicesSubTotal.setText("order.total.additionalServices");
		additionalServicesSubTotal.setValue(totalAdditionalServicesPrice);
		additionalServicesSubTotal.setTitle(Constants.OT_ADDITIONAL_SERVICE_PRICE_MODULE_CODE);
		orderTotals.add(additionalServicesSubTotal);


		//erp费用
//        OrderTotal erpSubTotal = new OrderTotal();
//        erpSubTotal.setModule(Constants.OT_ERP_MODULE_CODE);
//        erpSubTotal.setOrderTotalType(OrderTotalType.ERP);
//        erpSubTotal.setOrderTotalCode("order.total.erp");
//        erpSubTotal.setSortOrder(102);
//        erpSubTotal.setText("order.total.erp");
//        erpSubTotal.setValue(erpPrice);
//        erpSubTotal.setTitle(Constants.OT_ERP_MODULE_CODE);
//        grandTotal=grandTotal.add(erpPrice);

		LOGGER.debug("[caculateOrder] calculate order shipping");
		//shipping
//        if(summary.getShippingSummary()!=null) {
//            //check handling fees
//            shippingConfiguration = shippingService.getShippingConfiguration(store);
//            if(summary.getShippingSummary().getHandling()!=null && summary.getShippingSummary().getHandling().doubleValue()>0) {
//                if(shippingConfiguration.getHandlingFees()!=null && shippingConfiguration.getHandlingFees().doubleValue()>0) {
//
//                }
//            }
//        }

		LOGGER.info("[caculateOrder] calculate order tax");

//        //tax
//        List<TaxItem> taxes = taxService.calculateTax(summary, customer, store, language);
//        if(taxes!=null && taxes.size()>0) {
//            BigDecimal totalTaxes = new BigDecimal(0);
//            totalTaxes.setScale(0, RoundingMode.UP);
//            int taxCount = 200;
//            for(TaxItem tax : taxes) {
//
//                OrderTotal taxLine = new OrderTotal();
//                taxLine.setModule(Constants.OT_TAX_MODULE_CODE);
//                taxLine.setOrderTotalType(OrderTotalType.TAX);
//                taxLine.setOrderTotalCode(tax.getLabel());
//                taxLine.setSortOrder(taxCount);
//                taxLine.setTitle(Constants.OT_TAX_MODULE_CODE);
//                taxLine.setText(tax.getLabel());
//                taxLine.setValue(tax.getItemPrice());
//
//                totalTaxes = totalTaxes.add(tax.getItemPrice());
//                orderTotals.add(taxLine);
//                //grandTotal=grandTotal.add(tax.getItemPrice());
//
//                taxCount ++;
//
//            }
//            totalSummary.setTaxTotal(totalTaxes);
//        }

		grandTotal = grandTotal.add(totalProductHandlingFeePrice)
				.add(erpPrice).add(subTotal)
				.add(totalShippingPrice)
				.add(totalAdditionalServicesPrice);

		// grand total
		com.salesmanager.core.model.order.OrderTotal orderTotal = new com.salesmanager.core.model.order.OrderTotal();
		orderTotal.setModule(Constants.OT_TOTAL_MODULE_CODE);
		orderTotal.setOrderTotalType(OrderTotalType.TOTAL);
		orderTotal.setOrderTotalCode("order.total.total");
		orderTotal.setTitle(Constants.OT_TOTAL_MODULE_CODE);
		//orderTotal.setText("order.total.total");
		orderTotal.setSortOrder(500);
		orderTotal.setValue(grandTotal);

		orderTotals.add(orderTotal);

		totalSummary.setProductHandlingFeePriceTotal(totalProductHandlingFeePrice);
		totalSummary.setErpPriceTotal(erpPrice);
		totalSummary.setShippingPriceTotal(totalShippingPrice);
		totalSummary.setAdditionalServicesPriceTotal(totalAdditionalServicesPrice);
		totalSummary.setTotal(grandTotal);
		totalSummary.setTotals(orderTotals);

		LogPermUtil.end("OrderService/caculateOrder", start);
		return totalSummary;
	}



	public Order createNewOrderFromOld(Order oldOrder) {
		Order modelOrder = new Order();

		// 复制需要的属性
		modelOrder.setStatus(oldOrder.getStatus());
		modelOrder.setLastModified(oldOrder.getLastModified());
		modelOrder.setCustomerId(oldOrder.getCustomerId());
		modelOrder.setDatePurchased(oldOrder.getDatePurchased());
		modelOrder.setOrderDateFinished(oldOrder.getOrderDateFinished());
		modelOrder.setCurrencyValue(oldOrder.getCurrencyValue());
		modelOrder.setTotal(oldOrder.getTotal());
		modelOrder.setIpAddress(oldOrder.getIpAddress());
		modelOrder.setPaymentModuleCode(oldOrder.getPaymentModuleCode());
		modelOrder.setShippingModuleCode(oldOrder.getShippingModuleCode());
		modelOrder.setCurrency(oldOrder.getCurrency());
		modelOrder.setConfirmedAddress(oldOrder.getConfirmedAddress());
		modelOrder.setCustomerEmailAddress(oldOrder.getCustomerEmailAddress());
		modelOrder.setShoppingCartCode(oldOrder.getShoppingCartCode());
		modelOrder.setChannel(oldOrder.getChannel());
		modelOrder.setOrderType(oldOrder.getOrderType());
		modelOrder.setImportMain(oldOrder.getImportMain());
		modelOrder.setCustomsClearanceNumber(oldOrder.getCustomsClearanceNumber());
//		modelOrder.setOrderNo(oldOrder.getOrderNo());
		modelOrder.setLocale(oldOrder.getLocale());
		modelOrder.setPaymentType(oldOrder.getPaymentType());
		modelOrder.setShippingType(oldOrder.getShippingType());
		modelOrder.setInternationalTransportationMethod(oldOrder.getInternationalTransportationMethod());
		modelOrder.setNationalTransportationMethod(oldOrder.getNationalTransportationMethod());
		modelOrder.setShippingTransportationType(oldOrder.getShippingTransportationType());
		modelOrder.setTruckModel(oldOrder.getTruckModel());
		modelOrder.setPlayThroughOption(oldOrder.getPlayThroughOption());
		modelOrder.setTruckType(oldOrder.getTruckType());
		modelOrder.setTruckTransportationCompany(oldOrder.getTruckTransportationCompany());
		modelOrder.setBilling(oldOrder.getBilling());
		modelOrder.setDelivery(oldOrder.getDelivery());
		modelOrder.setMerchant(oldOrder.getMerchant());
		Set<OrderStatusHistory> oldOrderHistorySet = oldOrder.getOrderHistory();

		Set<OrderStatusHistory> orderStatusHistorySet = oldOrderHistorySet.stream().map(oldOrderHistory -> {
			OrderStatusHistory orderStatusHistory = new OrderStatusHistory();
			orderStatusHistory.setStatus(oldOrderHistory.getStatus());
			orderStatusHistory.setComments(oldOrderHistory.getComments());
			orderStatusHistory.setCustomerNotified(oldOrderHistory.getCustomerNotified());
			orderStatusHistory.setOrder(modelOrder);
			orderStatusHistory.setDateAdded(oldOrderHistory.getDateAdded());
			return orderStatusHistory;
		}).collect(Collectors.toSet());

		modelOrder.setOrderHistory(orderStatusHistorySet);
		// 这里可以设置为 null
		modelOrder.setId(null);

		return modelOrder;
	}



	public List<OrderProduct> removeOrderProducts(Order oldOrder, List<Long> orderProductIdsToRemove) {
		Set<OrderProduct> oldOrderProducts = oldOrder.getOrderProducts();

		// 过滤掉要移除的 OrderProduct
		return oldOrderProducts.stream()
				.filter(orderProduct -> !orderProductIdsToRemove.contains(orderProduct.getId())).map(orderProduct -> {
					return ObjectConvert.convert(orderProduct, OrderProduct.class);
				})
				.collect(Collectors.toList());
	}

	/**
	 * 状态为：等待付款、支付完成、商品准备中 订单可以取消订单
	 *
	 * 订单取消仅在以下情况下显示
	 * 对于 product 类型，显示到支付完成阶段。
	 * 对于 OEM 和 GQ 类型，显示到支付后24小时内。"
	 */
	@Override
	public Boolean cancelOrder(ReadableOrder readableOrder, String reason) throws Exception {
		Order order = orderService.getById(readableOrder.getId());
		if (order == null) {
			throw new ResourceNotFoundException("Order id [" + readableOrder.getId() + "] not found");
		}
		OrderStatus orderStatus = order.getStatus();
		if (orderStatus != OrderStatus.PENDING_REVIEW && orderStatus != OrderStatus.ORDERED && orderStatus != OrderStatus.PAYMENT_COMPLETED) {
			throw new ServiceException("Order can not be canceled, current status:" + orderStatus.name());
		}
		// OEM or GQ
		if (order.getOrderType() == OrderType.OEM && orderStatus == OrderStatus.PAYMENT_COMPLETED) {
			// Orders can only be cancelled within 24 hours of payment completion
			Transaction transaction = transactionService.getRefundableTransaction(order);
			Date transactionDate = transaction.getTransactionDate();

			Date theDayAfterTransaction = DateUtils.addDays(transactionDate, 1);

			// if now is before the day after transaction, it means within 24 hours, otherwise not
            if (new Date().after(theDayAfterTransaction)) {
				throw new ServiceException("Orders can only be cancelled within 24 hours after payment completion");
            }
		}

		if (orderStatus == OrderStatus.PAYMENT_COMPLETED) {
			refund(order, reason);
		} else {
			updateOrderStatus(order, OrderStatus.CANCELED, order.getMerchant(), reason);
		}

		return true;
	}

	private Transaction refund(Order order, String reason) throws ServiceException {
		Customer customer = customerService.getById(order.getCustomerId());
		return paymentService.processRefund(order, customer, order.getMerchant(), order.getTotal(), reason);
	}
}
