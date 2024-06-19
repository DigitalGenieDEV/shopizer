package com.salesmanager.shop.store.clients.sendy;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.salesmanager.shop.application.config.FeignConfig;
import com.salesmanager.shop.model.references.SendyDTO;
import com.salesmanager.shop.model.references.SendyResponseBody;
import com.salesmanager.shop.model.references.SendyFareCalcData;
import com.salesmanager.shop.model.references.SendyOrdersData;
import com.salesmanager.shop.model.references.SendyOrdersRegisterData;

@FeignClient(name="sendyClient", configuration = FeignConfig.class, url = "${sendy.host}")
public interface SendyClient {
	
	@RequestMapping(method = RequestMethod.POST, value = "/v2/external/fare/calc")
	SendyResponseBody<SendyFareCalcData> fareCalc(
			@RequestHeader String apiKey,
			@RequestBody SendyDTO dto
			);
	
	@RequestMapping(method = RequestMethod.POST, value = "/v2/external/orders/register")
	SendyResponseBody<SendyOrdersRegisterData> ordersRegister(
			@RequestHeader String apiKey,
			@RequestBody SendyDTO dto
			);
	
	@RequestMapping(method = RequestMethod.GET, value = "/v2/external/orders/{number}")
	SendyResponseBody<SendyOrdersData> orders(
			@RequestHeader String apiKey,
			@RequestParam String number
			);
	
	@RequestMapping(method = RequestMethod.POST, value = "/v2/external/orders/cancel")
	SendyResponseBody<String> ordersCancel(
			@RequestHeader String apiKey,
			@RequestBody Map<String, String> bookingNumber
			);
}