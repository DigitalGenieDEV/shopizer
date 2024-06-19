package com.salesmanager.shop.store.controller.sendy.facade;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.references.SendyDTO;
import com.salesmanager.shop.model.references.SendyFareCalcData;
import com.salesmanager.shop.model.references.SendyOrdersData;
import com.salesmanager.shop.model.references.SendyOrdersRegisterData;
import com.salesmanager.shop.model.references.SendyResponseBody;

public interface SendyFacade {
	SendyResponseBody<SendyFareCalcData> fareCalc(Language language, MerchantStore merchantStore, SendyDTO dto);
	SendyResponseBody<SendyOrdersRegisterData> ordersRegister(Language language, MerchantStore merchantStore, SendyDTO dto);
	SendyResponseBody<SendyOrdersData> orders(Language language, MerchantStore merchantStore, String number);
	SendyResponseBody<String> ordersCancel(Language language, MerchantStore merchantStore, String bookingNumber);
}
