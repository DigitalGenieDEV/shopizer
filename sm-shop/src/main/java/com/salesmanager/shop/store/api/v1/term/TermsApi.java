package com.salesmanager.shop.store.api.v1.term;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.term.PersistableTerms;
import com.salesmanager.shop.model.term.ReadableTerms;
import com.salesmanager.shop.store.api.v1.store.MerchantStoreApi;
import com.salesmanager.shop.store.controller.term.facade.TermsFacade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/v1")
@Api(tags = { "Terms management resource (Terms Management Api)" })
@SwaggerDefinition(tags = { @Tag(name = "Terms management", description = "Edit Terms") })
@RequiredArgsConstructor
public class TermsApi {
	private static final Logger LOGGER = LoggerFactory.getLogger(MerchantStoreApi.class);

	private final TermsFacade termsFacade;

	@PostMapping(value = { "/private/terms"})
	@ApiOperation(httpMethod = "POST", value = "Creates Terms", notes = "", response = ReadableTerms.class)
	public ReadableTerms terms(
			@RequestBody PersistableTerms terms,
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language
	) {
		return termsFacade.createTerms(terms, merchantStore, language);
	}
	
	@GetMapping(value = { "/terms"})
	@ApiOperation(httpMethod = "GET", value = "get Terms", notes = "", response = List.class)
	public List<ReadableTerms> terms(
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language
	) {
		return termsFacade.terms(merchantStore, language);
	}
}
