package com.salesmanager.shop.store.api.v1.country;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.references.ReadableCountry;
import com.salesmanager.shop.store.api.v1.content.ContentApi;
import com.salesmanager.shop.store.controller.country.facade.CountryFacade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/api/v1")
@Api(tags = { "Country management resource (Country Management Api)" })
@SwaggerDefinition(tags = { @Tag(name = "Country management resource", description = "Country Management") })
@RequiredArgsConstructor
public class CountryApi {
	private static final Logger LOGGER = LoggerFactory.getLogger(ContentApi.class);


	private final CountryFacade countryFacade;

	/**
	 * GET Countries
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/countries")
	@ApiOperation(httpMethod = "GET", value = "Get Countries", notes = "", response = List.class)
	public List<ReadableCountry> countries(
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language,
			HttpServletRequest request
	) throws Exception {
		return countryFacade.listCountryZoneByLanguageAndSupported(language, merchantStore);
	}
}
