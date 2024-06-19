package com.salesmanager.shop.store.api.v1.sendy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.references.SendyDTO;
import com.salesmanager.shop.model.references.SendyFareCalcData;
import com.salesmanager.shop.model.references.SendyOrdersData;
import com.salesmanager.shop.model.references.SendyOrdersRegisterData;
import com.salesmanager.shop.model.references.SendyResponseBody;
import com.salesmanager.shop.store.api.v1.customer.CustomerApi;
import com.salesmanager.shop.store.controller.sendy.facade.SendyFacade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/api/v1/sendy")
@Api(tags = { "Sendy API resource (Sendy Api)" })
@SwaggerDefinition(tags = { @Tag(name = "Sendy API resource", description = "Sendy API") })
@RequiredArgsConstructor
public class SendyApi {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerApi.class);
	
	private final SendyFacade sendyFacade;
	
	/**
	 * GET fareCalc
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/fare/calc")
	@ApiOperation(httpMethod = "GET", value = "GET fare calc", notes = "", response = SendyResponseBody.class)
	public SendyResponseBody<SendyFareCalcData> fare(
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language,
			@RequestBody SendyDTO dto
	) throws Exception {
		LOGGER.info("sendy API ::: fareCalc");
		return sendyFacade.fareCalc(language, merchantStore, dto);
	}
	
	/**
	 * POST orders register
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/orders/register")
	@ApiOperation(httpMethod = "POST", value = "POST orders register", notes = "", response = SendyResponseBody.class)
	public SendyResponseBody<SendyOrdersRegisterData> ordersRegister(
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language,
			@RequestBody SendyDTO dto
	) throws Exception {
		LOGGER.info("sendy API ::: ordersRegister");
		return sendyFacade.ordersRegister(language, merchantStore, dto);
	}
	
	/**
	 * GET orders
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/orders/{number}")
	@ApiOperation(httpMethod = "GET", value = "GET orders", notes = "", response = SendyResponseBody.class)
	public SendyResponseBody<SendyOrdersData> orders(
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language,
			@PathVariable String number
	) throws Exception {
		LOGGER.info("sendy API ::: orders");
		return sendyFacade.orders(language, merchantStore, number);
	}
	
	/**
	 * POST orders cancel
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/orders/{number}/cancel")
	@ApiOperation(httpMethod = "POST", value = "POST orders cancel", notes = "", response = SendyResponseBody.class)
	public SendyResponseBody<String> ordersCancel(
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language,
			@PathVariable String number
	) throws Exception {
		LOGGER.info("sendy API ::: ordersCancel");
		return sendyFacade.ordersCancel(language, merchantStore, number);
	}
}
