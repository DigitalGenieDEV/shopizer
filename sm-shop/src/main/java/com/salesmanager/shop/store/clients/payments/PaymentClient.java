package com.salesmanager.shop.store.clients.payments;

import com.salesmanager.shop.application.config.FeignConfig;
import com.salesmanager.shop.model.references.EmailVerificationDTO;
import com.salesmanager.shop.model.references.KakaoAlimRequest;
import com.salesmanager.shop.model.references.NicepayPayment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="paymentClient", configuration = FeignConfig.class, url = "${payments.host}")
public interface PaymentClient {
	
	@RequestMapping(method = RequestMethod.GET, value = "/api/v1/payments/{id}")
	NicepayPayment getNicepayPayments(
			@PathVariable String id
	);
}