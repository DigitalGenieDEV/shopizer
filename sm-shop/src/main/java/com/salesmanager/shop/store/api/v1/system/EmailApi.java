package com.salesmanager.shop.store.api.v1.system;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.salesmanager.core.business.services.merchant.MerchantStoreService;
import com.salesmanager.core.business.services.system.EmailService;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.shop.store.controller.email.facade.EmailFacade;

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
	private final EmailFacade emailFacade;
	
	
	
	@PostMapping("/mail/authorization")
	@ApiOperation(
			httpMethod = "POST", value = "Mail Authorization", response = ResponseEntity.class
	)
	@ApiImplicitParams({ @ApiImplicitParam(name = "email", dataType = "string") })
	public ResponseEntity<Void> sendVerification(
			@RequestParam("email") @Valid String to
			) throws Exception {
		//MerchantStore merchant = merchantService.getByCode( MerchantStore.DEFAULT_STORE );
		
		emailFacade.emailVerification(to);
	    
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
