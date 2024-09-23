package com.salesmanager.shop.store.clients.external;

import com.salesmanager.shop.model.references.KakaoAlimRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.salesmanager.shop.application.config.FeignConfig;
import com.salesmanager.shop.model.references.EmailVerificationDTO;

@FeignClient(name="externalClient", configuration = FeignConfig.class, url = "${external.host}")
public interface ExternalClient {
	
	@RequestMapping(method = RequestMethod.POST, value = "/api/v1/external/email/verification")
	void emailVerification(
			@RequestBody EmailVerificationDTO dto
			);

	@RequestMapping(method = RequestMethod.POST, value = "/api/v1/external/kakao/send/alimtalk")
	void sendAlimTalk(
			@RequestBody KakaoAlimRequest request
			);
}