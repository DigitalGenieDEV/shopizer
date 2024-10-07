package com.salesmanager.shop.store.api.v1.order;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.salesmanager.core.business.services.order.OrderService;
import com.salesmanager.core.enmus.FulfillmentTypeEnums;
import com.salesmanager.core.model.order.OrderType;
import com.salesmanager.core.model.order.orderstatus.OrderStatus;
import com.salesmanager.core.model.payments.PaymentType;
import com.salesmanager.shop.model.customer.order.transaction.ReadableCombineTransaction;
import com.salesmanager.shop.model.order.ReadableOrderProduct;
import com.salesmanager.shop.model.shop.CommonResultDTO;
import com.salesmanager.shop.store.error.ErrorCodeEnums;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.business.services.shoppingcart.ShoppingCartService;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.Order;
import com.salesmanager.core.model.order.OrderCriteria;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.shoppingcart.ShoppingCart;
import com.salesmanager.shop.constants.Constants;
import com.salesmanager.shop.model.customer.PersistableCustomer;
import com.salesmanager.shop.model.customer.ReadableCustomer;
import com.salesmanager.shop.model.order.v0.ReadableOrder;
import com.salesmanager.shop.model.order.v0.ReadableOrderList;
import com.salesmanager.shop.model.order.v1.PersistableAnonymousOrder;
import com.salesmanager.shop.model.order.v1.PersistableOrder;
import com.salesmanager.shop.model.order.v1.ReadableOrderConfirmation;
import com.salesmanager.shop.populator.customer.ReadableCustomerPopulator;
import com.salesmanager.shop.store.api.exception.GenericRuntimeException;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.customer.facade.CustomerFacade;
import com.salesmanager.shop.store.controller.order.facade.OrderFacade;
import com.salesmanager.shop.store.security.services.CredentialsException;
import com.salesmanager.shop.store.security.services.CredentialsService;
import com.salesmanager.shop.utils.AuthorizationUtils;
import com.salesmanager.shop.utils.LocaleUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/v1")
@Api(tags = { "Ordering api (Order Flow Api)" })
@SwaggerDefinition(tags = { @Tag(name = "Order flow resource", description = "Manage orders (create, list, get)") })
public class OrderApi {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderApi.class);

	@Inject
	private CustomerService customerService;

	@Inject
	private OrderFacade orderFacade;

	@Inject
	private OrderService orderService;

	@Inject
	private com.salesmanager.shop.store.controller.order.facade.v1.OrderFacade orderFacadeV1;

	@Inject
	private ShoppingCartService shoppingCartService;

	@Autowired
	private CustomerFacade customerFacade;

	@Autowired
	private CustomerFacade customerFacadev1; //v1 version

	@Inject
	private AuthorizationUtils authorizationUtils;

	@Inject
	private CredentialsService credentialsService;

	private static final String DEFAULT_ORDER_LIST_COUNT = "10";

	/**
	 * Get a list of orders for a given customer accept request parameter
	 * 'start' start index for count accept request parameter 'max' maximum
	 * number count, otherwise returns all Used for administrators
	 *
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/private/orders/customers/{id}" }, method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko") })
	public ReadableOrderList list(@PathVariable final Long id,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "count", required = false) Integer count, @ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language, HttpServletResponse response) throws Exception {

		Customer customer = customerService.getById(id);

		if (customer == null) {
			LOGGER.error("Customer is null for id " + id);
			response.sendError(404, "Customer is null for id " + id);
			return null;
		}

		if (start == null) {
			start = new Integer(0);
		}
		if (count == null) {
			count = new Integer(100);
		}

		ReadableCustomer readableCustomer = new ReadableCustomer();
		ReadableCustomerPopulator customerPopulator = new ReadableCustomerPopulator();
		customerPopulator.populate(customer, readableCustomer, merchantStore, language);

		ReadableOrderList returnList = orderFacade.getReadableOrderList(merchantStore, customer, start, count,
				language);

		List<ReadableOrder> orders = returnList.getOrders();

		if (!CollectionUtils.isEmpty(orders)) {
			for (ReadableOrder order : orders) {
				order.setCustomer(readableCustomer);
			}
		}

		return returnList;
	}

	/**
	 * List orders for authenticated customers
	 *
	 * @param start
	 * @param count
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/auth/orders" }, method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko") })
	public ReadableOrderList list(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "count", required = false) Integer count, @ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Principal principal = request.getUserPrincipal();
		String userName = principal.getName();

		Customer customer = customerService.getByNick(userName);

		if (customer == null) {
			response.sendError(401, "Error while listing orders, customer not authorized");
			return null;
		}

		if (page == null) {
			page = new Integer(0);
		}
		if (count == null) {
			count = new Integer(100);
		}

		ReadableCustomer readableCustomer = new ReadableCustomer();
		ReadableCustomerPopulator customerPopulator = new ReadableCustomerPopulator();
		customerPopulator.populate(customer, readableCustomer, merchantStore, language);

		ReadableOrderList returnList = orderFacade.getReadableOrderList(merchantStore, customer, page, count, language);

		if (returnList == null) {
			returnList = new ReadableOrderList();
		}

		List<ReadableOrder> orders = returnList.getOrders();
		if (!CollectionUtils.isEmpty(orders)) {
			for (ReadableOrder order : orders) {
				order.setCustomer(readableCustomer);
			}
		}
		return returnList;
	}

	/**
	 * List orders for authenticated customers
	 *
	 * @param start
	 * @param count
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = {"/auth/store/orders"}, method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@ApiImplicitParams({@ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko")})
	public CommonResultDTO<ReadableOrderList> list(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
								  @RequestParam(value = "count", required = false, defaultValue = DEFAULT_ORDER_LIST_COUNT) Integer count,
								  @RequestParam(value = "orderId", required = false) Long orderId,
								  @RequestParam(value = "deliveryName", required = false) String deliveryName,
								  @RequestParam(value = "buyerName", required = false) String buyerName,
								  @RequestParam(value = "startTime", required = false) Long startTime,
								  @RequestParam(value = "endTime", required = false) Long endTime,
								  @RequestParam(value = "orderStatus", required = false) List<String> orderStatus,
								  @RequestParam(value = "shippingStatus", required = false) List<String> shippingStatus,
								  @RequestParam(value = "orderType", required = false) String orderType,
								  @RequestParam(value = "shippingType", required = false) String shippingType,
								  @RequestParam(value = "orderNo", required = false) String orderNo,
								  @RequestParam(value = "transportationMethod", required = false) String transportationMethod,
								  @RequestParam(value = "paymentType", required = false) String paymentType,
								  // TODO(yuxunhui):주문플랫폼 不知道是什么意思 订购平台，没找到对应的概念
								  @ApiIgnore MerchantStore merchantStore,
								  @ApiIgnore Language language,
								  HttpServletRequest request, HttpServletResponse response) throws Exception {

		OrderCriteria orderCriteria = new OrderCriteria();
		orderCriteria.setPageSize(count);
		orderCriteria.setStartPage(page);
		orderCriteria.setOrderType(orderType);
		orderCriteria.setId(orderId);
		orderCriteria.setCustomerName(buyerName);
		orderCriteria.setDeliveryName(deliveryName);
		orderCriteria.setPaymentMethod(paymentType);
		orderCriteria.setShippingType(shippingType);
		orderCriteria.setTransportationMethod(transportationMethod);
		orderCriteria.setStartTime(startTime);
		orderCriteria.setEndTime(endTime);
		if (CollectionUtils.isNotEmpty(orderStatus)) {
			try {
				List<OrderStatus> orderStatuses = new ArrayList<>();
				for (String status : orderStatus) {
					OrderStatus statusEnum = OrderStatus.fromValue(status.toUpperCase());
					orderStatuses.add(statusEnum);
				}
				orderCriteria.setStatus(orderStatuses);
			} catch (Exception e) {
				return CommonResultDTO.ofFailed(ErrorCodeEnums.PARAM_ERROR.getErrorCode(), ErrorCodeEnums.PARAM_ERROR.getErrorMessage(), e.getMessage());
			}
		}
		if (CollectionUtils.isNotEmpty(shippingStatus)) {
			try {
				List<FulfillmentTypeEnums> shippingStatuses = new ArrayList<>();
				for (String status : shippingStatus) {
					FulfillmentTypeEnums statusEnum = FulfillmentTypeEnums.fromString(status.toUpperCase());
					if (statusEnum != null) {
						shippingStatuses.add(statusEnum);
					}
				}
				orderCriteria.setShippingStatus(shippingStatuses);
			} catch (Exception e) {
				return CommonResultDTO.ofFailed(ErrorCodeEnums.PARAM_ERROR.getErrorCode(), ErrorCodeEnums.PARAM_ERROR.getErrorMessage(), e.getMessage());
			}
		}
		orderCriteria.setLanguage(language.getCode());
		orderCriteria.setOrderNo(orderNo);
		try {
			ReadableOrderList orders = orderFacade.getReadableOrderList(orderCriteria, merchantStore);
			return CommonResultDTO.ofSuccess(orders);
		} catch (Exception e) {
			LOGGER.error("admin query order list error", e);
			return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
		}
	}


	/**
	 * This method returns list of all the orders for a store.This is not
	 * bound to any specific stores and will get list of all the orders
	 * available for this instance
	 *
	 * @param start
	 * @param count
	 * @return List of orders
	 * @throws Exception
	 */
	@RequestMapping(value = { "/private/orders" }, method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public CommonResultDTO<ReadableOrderList> list(
			@RequestParam(value = "count", required = false, defaultValue = DEFAULT_ORDER_LIST_COUNT) Integer count,
			@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "orderStatus", required = false) List<String> orderStatus,
			@RequestParam(value = "shippingStatus", required = false) List<String> shippingStatus,
			@RequestParam(value = "startTime", required = false) Long startTime,
			@RequestParam(value = "endTime", required = false) Long endTime,
			@RequestParam(value = "queryType", required = false) String queryType,
			@RequestParam(value = "queryValue", required = false) String queryValue,
			@ApiIgnore Language language) {

		OrderCriteria orderCriteria = new OrderCriteria();
		orderCriteria.setPageSize(count);
		orderCriteria.setStartPage(page);

		if (StringUtils.isNotEmpty(queryType) && queryType.equals("ORDER_TYPE")) {
			try {
				OrderType.valueOf(queryValue.toUpperCase());
			} catch (Exception e) {
				return CommonResultDTO.ofFailed(ErrorCodeEnums.PARAM_ERROR.getErrorCode(), ErrorCodeEnums.PARAM_ERROR.getErrorMessage(), "Invalid order type: " + queryValue);
			}
			orderCriteria.setOrderType(queryValue);
		}
		if (StringUtils.isNotEmpty(queryType) && queryType.equals("ID")) {
			orderCriteria.setId(Long.valueOf(queryValue));
		}
		if (StringUtils.isNotEmpty(queryType) && queryType.equals("ORDER_NO")) {
			orderCriteria.setOrderNo(queryValue);
		}
		if (StringUtils.isNotEmpty(queryType) && queryType.equals("NAME")) {
			orderCriteria.setDeliveryName(queryValue);
		}
		if (StringUtils.isNotEmpty(queryType) && queryType.equals("PHONE")) {
			orderCriteria.setCustomerPhone(queryValue);
		}
		if (StringUtils.isNotEmpty(queryType) && queryType.equals("COUNTRY_NAME")) {
			orderCriteria.setCustomerCountryName(queryValue);
		}
		if (StringUtils.isNotEmpty(queryType) && queryType.equals("CLEARANCE_NUMBER")) {
			orderCriteria.setCustomsClearanceNumber(queryValue);
		}
		if (StringUtils.isNotEmpty(queryType) && queryType.equals("PAYMENT_METHOD")) {
			try {
				PaymentType.valueOf(queryValue.toUpperCase());
			} catch (Exception e) {
				return CommonResultDTO.ofFailed(ErrorCodeEnums.PARAM_ERROR.getErrorCode(), ErrorCodeEnums.PARAM_ERROR.getErrorMessage(), "Invalid payment type: " + queryValue);
			}
			orderCriteria.setPaymentMethod(queryValue);
		}
		if (StringUtils.isNotEmpty(queryType) && queryType.equals("transportationMethod")) {
			orderCriteria.setTransportationMethod(queryValue);
		}
		if (StringUtils.isNotEmpty(queryType) && queryType.equals("PRODUCT_NAME")) {
			orderCriteria.setProductName(queryValue);
		}
		if (StringUtils.isNotEmpty(queryType) && queryType.equals("EMAIL")) {
			orderCriteria.setEmail(queryValue);
		}
		if (StringUtils.isNotEmpty(queryType) && queryType.equals("HS_CODE")) {
			orderCriteria.setHsCode(queryValue);
		}
		if (StringUtils.isNotEmpty(queryType) && queryType.equals("STORE_CODE")) {
			orderCriteria.setStoreCode(queryValue);
		}
		if (CollectionUtils.isNotEmpty(orderStatus)) {
			try {
				List<OrderStatus> orderStatuses = new ArrayList<>();
				for (String status : orderStatus) {
					OrderStatus statusEnum = OrderStatus.fromValue(status.toUpperCase());
					orderStatuses.add(statusEnum);
				}
				orderCriteria.setStatus(orderStatuses);
			} catch (Exception e) {
				return CommonResultDTO.ofFailed(ErrorCodeEnums.PARAM_ERROR.getErrorCode(), ErrorCodeEnums.PARAM_ERROR.getErrorMessage(), e.getMessage());
			}
		}
		orderCriteria.setStartTime(startTime);
		orderCriteria.setEndTime(endTime);

		if (CollectionUtils.isNotEmpty(shippingStatus)) {
			try {
				List<FulfillmentTypeEnums> shippingStatuses = new ArrayList<>();
				for (String status : shippingStatus) {
					FulfillmentTypeEnums statusEnum = FulfillmentTypeEnums.fromString(status.toUpperCase());
					if (statusEnum != null) {
						shippingStatuses.add(statusEnum);
					}
				}
				orderCriteria.setShippingStatus(shippingStatuses);
			} catch (Exception e) {
				return CommonResultDTO.ofFailed(ErrorCodeEnums.PARAM_ERROR.getErrorCode(), ErrorCodeEnums.PARAM_ERROR.getErrorMessage(), e.getMessage());
			}
		}
		orderCriteria.setLanguage(language.getCode());

		try {
			ReadableOrderList orders = orderFacade.getReadableOrderList(orderCriteria, null);
			return CommonResultDTO.ofSuccess(orders);
		} catch (Exception e) {
			LOGGER.error("admin query order list error", e);
			return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
		}
	}

	/**
	 * Order details
	 * @param id
	 * @param merchantStore
	 * @param language
	 * @return
	 */
	@RequestMapping(value = { "/private/orders/{id}" }, method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@ApiImplicitParams({
			@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko") })
	public CommonResultDTO<ReadableOrder> get(
			@PathVariable final Long id,
			@ApiIgnore Language language) {
		try {
			ReadableOrder order = orderFacade.getReadableOrder(id, null, language);
			return CommonResultDTO.ofSuccess(order);
		}catch (Exception e){
			LOGGER.error("updateAdminOrderStatus error", e);
			return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());

		}
	}

	/**
	 * Get a given order by id
	 *
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/auth/orders/{id}" }, method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko") })
	public ReadableOrder getOrder(@PathVariable final Long id, @ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Principal principal = request.getUserPrincipal();
		String userName = principal.getName();

		Customer customer = customerService.getByNick(userName);

		if (customer == null) {
			response.sendError(401, "Error while performing checkout customer not authorized");
			return null;
		}

		ReadableOrder order = orderFacade.getReadableOrder(id, merchantStore, language);

		if (order == null) {
			LOGGER.error("Order is null for id " + id);
			response.sendError(404, "Order is null for id " + id);
			return null;
		}

		if (order.getCustomer() == null) {
			LOGGER.error("Order is null for customer " + principal);
			response.sendError(404, "Order is null for customer " + principal);
			return null;
		}

		if (order.getCustomer().getId() != null
				&& order.getCustomer().getId().longValue() != customer.getId().longValue()) {
			LOGGER.error("Order is null for customer " + principal);
			response.sendError(404, "Order is null for customer " + principal);
			return null;
		}

		return order;
	}

	/**
	 * Action for performing a checkout on a given shopping cart
	 *
	 * @param id
	 * @param order
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/auth/cart/{code}/checkout" }, method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko") })
	public ReadableOrderConfirmation checkout(
			@PathVariable final String code, //shopping cart
			@Valid @RequestBody PersistableOrder order, // order
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language,
			HttpServletRequest request,
			HttpServletResponse response, Locale locale) throws Exception {

		try {
			Principal principal = request.getUserPrincipal();
			String userName = principal.getName();

			Customer customer = customerService.getByNick(userName);

			if (customer == null) {
				response.sendError(401, "Error while performing checkout customer not authorized");
				return null;
			}

			ShoppingCart cart = shoppingCartService.getByCode(code, merchantStore);
			if (cart == null) {
				throw new ResourceNotFoundException("Cart code " + code + " does not exist");
			}

			order.setShoppingCartId(cart.getId());
			order.setCustomerId(customer.getId());//That is an existing customer purchasing

			Order modelOrder = orderFacade.processOrder(order, customer, merchantStore, language, locale);
			Long orderId = modelOrder.getId();
			modelOrder.setId(orderId);


			return orderFacadeV1.orderConfirmation(modelOrder, customer, merchantStore, language);



		} catch (Exception e) {
			LOGGER.error("Error while processing checkout", e);
			try {
				response.sendError(503, "Error while processing checkout " + e.getMessage());
			} catch (Exception ignore) {
			}
			return null;
		}
	}

	/**
	 * Main checkout resource that will complete the order flow
	 * @param code
	 * @param order
	 * @param merchantStore
	 * @param language
	 * @return
	 */
	@RequestMapping(value = { "/cart/{code}/checkout" }, method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko") })
	public ReadableOrderConfirmation checkout(
			@PathVariable final String code,//shopping cart
			@Valid @RequestBody PersistableAnonymousOrder order,//order
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language) {

		Validate.notNull(order.getCustomer(), "Customer must not be null");


		ShoppingCart cart;
		try {
			cart = shoppingCartService.getByCode(code, merchantStore);

			if (cart == null) {
				throw new ResourceNotFoundException("Cart code " + code + " does not exist");
			}

			//security password validation
			PersistableCustomer presistableCustomer = order.getCustomer();
			if(!StringUtils.isBlank(presistableCustomer.getPassword())) { //validate customer password
				credentialsService.validateCredentials(presistableCustomer.getPassword(), presistableCustomer.getRepeatPassword(), merchantStore, language);
			}

			Customer customer = new Customer();
			customer = customerFacade.populateCustomerModel(customer, order.getCustomer(), merchantStore, language);

			if(!StringUtils.isBlank(presistableCustomer.getPassword())) {
				//check if customer already exist
				customer.setAnonymous(false);
				customer.setNick(customer.getEmailAddress()); //username
				if(customerFacadev1.checkIfUserExists(customer.getNick(),  merchantStore)) {
					//409 Conflict
					throw new GenericRuntimeException("409", "Customer with email [" + customer.getEmailAddress() + "] is already registered");
				}
			}


			order.setShoppingCartId(cart.getId());

			Order modelOrder = orderFacade.processOrder(order, customer, merchantStore, language,
					LocaleUtils.getLocale(language));
			Long orderId = modelOrder.getId();
			//populate order confirmation
			order.setId(orderId);
			// set customer id
			order.getCustomer().setId(modelOrder.getCustomerId());

			return orderFacadeV1.orderConfirmation(modelOrder, customer, merchantStore, language);


		} catch (Exception e) {
			if(e instanceof CredentialsException) {
				throw new GenericRuntimeException("412","Credentials creation Failed [" + e.getMessage() + "]");
			}
			String message = e.getMessage();
			if(StringUtils.isBlank(message)) {//exception type
				message = "APP-BACKEND";
				if(e.getCause() instanceof com.salesmanager.core.modules.integration.IntegrationException) {
					message = "Integration problen occured to complete order";
				}
			}
			throw new ServiceRuntimeException("Error during checkout [" + message + "]", e);
		}

	}

	@RequestMapping(value = { "/private/orders/{id}/customer" }, method = RequestMethod.PATCH)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@ApiImplicitParams({
			@ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko") })
	public void updateOrderCustomer(
			@PathVariable final Long id,
			@Valid @RequestBody PersistableCustomer orderCustomer,
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language) {

		String user = authorizationUtils.authenticatedUser();
		authorizationUtils.authorizeUser(user, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN,
				Constants.GROUP_ADMIN_ORDER, Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()), merchantStore);


		orderFacade.updateOrderCustomre(id, orderCustomer, merchantStore);
		return;
	}


	@RequestMapping(value = { "/private/orders/{id}/status" }, method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@ApiImplicitParams({
			@ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT") })
	public CommonResultDTO<Void> updateOrderStatus(
			@PathVariable final Long id,
			@Valid @RequestParam String status,
			@ApiIgnore MerchantStore merchantStore) {
		try {
			Order order = orderService.getById(id);
			if (order == null) {
				return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), "Order not found [" + id + "]");
			}
			OrderStatus statusEnum = OrderStatus.fromValue(status);
			orderFacade.updateOrderStatus(order, statusEnum, merchantStore);
			return CommonResultDTO.ofSuccess();
		}catch (Exception e){
			LOGGER.error("updateAdminOrderStatus error", e);
			return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
		}
	}

	@RequestMapping(value = { "/private/orders/status" }, method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@ApiImplicitParams({
			@ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT") })
	public CommonResultDTO<Integer> batchUpdateOrderStatus(
			@Valid @RequestParam List<Long> ids,
			@Valid @RequestParam String status,
			@ApiIgnore MerchantStore merchantStore) {
		try {
			// check status is valid
			OrderStatus statusEnum = OrderStatus.fromValue(status);

			int affectRows = 0;
			for (Long id : ids) {
				CommonResultDTO<Void> voidCommonResultDTO = updateOrderStatus(id, status, merchantStore);
				if (voidCommonResultDTO.getSuccess()) {
					affectRows++;
				}
			}
			return CommonResultDTO.ofSuccess(affectRows);
		}catch (Exception e){
			LOGGER.error("batchUpdateAdminOrderStatus error", e);
			return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
		}
	}


	@RequestMapping(value = { "/auth/orders/{id}/status" }, method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public CommonResultDTO<Void> updateSellerOrderStatus(
			@PathVariable final Long id,
			@Valid @RequestBody String status, HttpServletRequest request) {
		try {
			Principal principal = request.getUserPrincipal();
			String userName = principal.getName();
			Customer customer = customerService.getByNick(userName);
			Order order = orderService.getOrder(id, customer.getMerchantStore());
			if (order == null) {
				return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), "Order not found [" + id + "]");
			}
			OrderStatus statusEnum = OrderStatus.fromValue(status);
			orderFacade.updateOrderStatus(order, statusEnum, customer.getMerchantStore());
			return CommonResultDTO.ofSuccess();
		}catch (Exception e){
			LOGGER.error("updateSellerOrderStatus error", e);
			return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
		}
	}



	@RequestMapping(value = { "/private/order/products/{id}", "/auth/order/products/{id}" }, method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public CommonResultDTO<List<ReadableOrderProduct>> queryOrderProductsByOrderId(
			@PathVariable final Long id, HttpServletRequest request,
			@ApiIgnore Language language) {
		try {
			Order order = orderService.getOrder(id, new MerchantStore());
			if (order == null) {
				return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), "Order not found [" + id + "]");
			}
			return CommonResultDTO.ofSuccess(orderFacade.queryOrderProductsByOrderId(id, language));
		}catch (Exception e){
			LOGGER.error("updateSellerOrderStatus error", e);
			return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
		}
	}




	@RequestMapping(value = { "/auth/order/details/{id}" }, method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@ApiImplicitParams({@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko") })
	public CommonResultDTO<ReadableOrder> getSellerOrderDetails(
			@PathVariable final Long id,
			@ApiIgnore Language language,
			HttpServletRequest request,
			HttpServletResponse response) {

		try {
			Principal principal = request.getUserPrincipal();
			String userName = principal.getName();

			Customer customer = customerService.getByNick(userName);

			if (customer == null) {
				response.sendError(401, "Error while performing checkout customer not authorized");
				return null;
			}
			ReadableOrder readableOrder = orderFacade.getReadableOrder(id, customer.getMerchantStore(), language);
			return CommonResultDTO.ofSuccess(readableOrder);
		}catch (Exception e){
			LOGGER.error("updateSellerOrderStatus error", e);
			return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
		}
	}



	@RequestMapping(value = { "/private/order/details/{id}" }, method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@ApiImplicitParams({@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko") })
	public CommonResultDTO<ReadableOrder> getAdminOrderDetails(
			@PathVariable final Long id,
			@ApiIgnore Language language,
			HttpServletRequest request,
			HttpServletResponse response) {
		try {
			ReadableOrder readableOrder = orderFacade.getReadableOrder(id, null, language);
			return CommonResultDTO.ofSuccess(readableOrder);
		}catch (Exception e){
			LOGGER.error("updateSellerOrderStatus error", e);
			return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
		}
	}


	@RequestMapping(value = { "/private/order/capturable_combine_transaction_info/{customerOrderId}",
			"/auth/order/capturable_combine_transaction_info/{customerOrderId}" }, method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@ApiImplicitParams({@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko") ,
			@ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT")})
	public CommonResultDTO<ReadableCombineTransaction> getCapturableCombineTransactionInfo(
			@PathVariable final Long customerOrderId,
			@ApiIgnore Language language,
			@ApiIgnore MerchantStore store,
			HttpServletResponse response) {
		try {
			ReadableCombineTransaction readableCombineTransaction = orderFacade.getCapturableCombineTransactionInfoByCustomerOrderId(customerOrderId, store, language);
			return CommonResultDTO.ofSuccess(readableCombineTransaction);
		}catch (Exception e){
			LOGGER.error("updateSellerOrderStatus error", e);
			return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
		}
	}


	/**
	 * 拆单
	 * @param oldOrderId
	 * @param orderProductIdList
	 * @return
	 */
	@RequestMapping(value = { "/private/order/{oldOrderId}/split" }, method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public CommonResultDTO<Void> orderSplit(
			@PathVariable final Long oldOrderId,
			@Valid @RequestBody  List<Long> orderProductIdList) {

		try {
			orderFacade.processOrderSplit(oldOrderId, orderProductIdList);
			return CommonResultDTO.ofSuccess();
		}catch (Exception e){
			LOGGER.error("updateSellerOrderStatus error", e);
			return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
		}
	}


}
