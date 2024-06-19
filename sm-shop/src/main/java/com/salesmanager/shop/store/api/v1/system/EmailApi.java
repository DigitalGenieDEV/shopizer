package com.salesmanager.shop.store.api.v1.system;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.salesmanager.core.business.modules.email.Email;
import com.salesmanager.core.business.services.merchant.MerchantStoreService;
import com.salesmanager.core.business.services.system.EmailService;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.shop.model.customer.ReadableCustomer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@Api(tags = {"Email Api"})
@SwaggerDefinition(tags = {
    @Tag(name = "Email resource", description = "Email Api")
})
@RequiredArgsConstructor
public class EmailApi {
	private final EmailService emailService;
	
	private final MerchantStoreService merchantService;
	
	@PostMapping("/mail/authorization")
	@ApiOperation(
			httpMethod = "POST", value = "Mail Authorization", response = ResponseEntity.class
	)
	@ApiImplicitParams({ @ApiImplicitParam(name = "email", dataType = "string") })
	public ResponseEntity<Void> sendVerification(
			@RequestParam("email") @Valid String to
			) throws Exception {
		MerchantStore merchant = merchantService.getByCode( MerchantStore.DEFAULT_STORE );
		
	    emailService.sendVerificationEmail(to, merchant);
	    
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
