package com.salesmanager.shop.store.controller.sendy.facade;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.references.SendyDTO;
import com.salesmanager.shop.model.references.SendyFareCalcData;
import com.salesmanager.shop.model.references.SendyOrdersData;
import com.salesmanager.shop.model.references.SendyOrdersRegisterData;
import com.salesmanager.shop.model.references.SendyResponseBody;
import com.salesmanager.shop.store.clients.sendy.SendyClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SendyFacadeImpl implements SendyFacade{
	private final SendyClient sendyClient;
	
	@Value("${sendy.apiKey}")
	private final String apiKey;

	@Override
	public SendyResponseBody<SendyFareCalcData> fareCalc(
			Language language,
			MerchantStore merchantStore,
			SendyDTO dto
	) {
		return sendyClient.fareCalc(apiKey, dto);
	}
	
	@Override
	public SendyResponseBody<SendyOrdersRegisterData> ordersRegister(
			Language language,
			MerchantStore merchantStore,
			SendyDTO dto
	) {
		return sendyClient.ordersRegister(apiKey, dto);
	}
	
	@Override
	public SendyResponseBody<SendyOrdersData> orders(
			Language language,
			MerchantStore merchantStore,
			String number
	) {
		return sendyClient.orders(apiKey, number);
	}
	
	@Override
	public SendyResponseBody<String> ordersCancel(
			Language language,
			MerchantStore merchantStore,
			String bookingNumber
	) {
		Map<String, String> number= new HashMap<String, String>();
		number.put("bookingNumber", bookingNumber);
		return sendyClient.ordersCancel(apiKey, number);
	}
}
