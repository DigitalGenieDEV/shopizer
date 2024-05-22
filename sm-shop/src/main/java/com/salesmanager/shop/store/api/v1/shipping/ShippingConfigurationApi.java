package com.salesmanager.shop.store.api.v1.shipping;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.salesmanager.core.model.common.Criteria;
import com.salesmanager.shop.model.shipping.PersistableMerchantShippingConfiguration;
import com.salesmanager.shop.model.shipping.ReadableMerchantShippingConfiguration;
import com.salesmanager.shop.model.shipping.ReadableMerchantShippingConfigurationList;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.shipping.ShippingService;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.shipping.PackageDetails;
import com.salesmanager.core.model.system.IntegrationConfiguration;
import com.salesmanager.core.model.system.IntegrationModule;
import com.salesmanager.shop.constants.Constants;
import com.salesmanager.shop.model.references.PersistableAddress;
import com.salesmanager.shop.model.references.ReadableAddress;
import com.salesmanager.shop.model.system.IntegrationModuleConfiguration;
import com.salesmanager.shop.model.system.IntegrationModuleSummaryEntity;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.shipping.facade.ShippingFacade;
import com.salesmanager.shop.utils.AuthorizationUtils;

import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1")
@Api(tags = { "Shipping configuration resource (Shipping Management Api)" })
@SwaggerDefinition(tags = {
		@Tag(name = "Shipping management resource", description = "Manage shipping configuration") })
public class ShippingConfigurationApi {

	private static final Logger LOGGER = LoggerFactory.getLogger(ShippingConfigurationApi.class);

	@Autowired
	private AuthorizationUtils authorizationUtils;

	@Autowired
	private ShippingFacade shippingFacade;

	@Autowired
	private ShippingService shippingService;

	@ApiOperation(httpMethod = "GET", value = "Get shipping origin for a specific merchant store", notes = "", produces = "application/json", response = ReadableAddress.class)
	@RequestMapping(value = { "/private/shipping/origin" }, method = RequestMethod.GET)
	@ResponseStatus(OK)
	@ResponseBody
	public ReadableAddress shippingOrigin(@ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) {

		String user = authorizationUtils.authenticatedUser();
		authorizationUtils.authorizeUser(user, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN,
				Constants.GROUP_SHIPPING, Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()), merchantStore);

		return shippingFacade.getShippingOrigin(merchantStore);

	}

	@RequestMapping(value = { "/private/shipping/origin" }, method = RequestMethod.POST)
	@ResponseStatus(OK)
	public void saveShippingOrigin(@RequestBody PersistableAddress address, @ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language) {

		String user = authorizationUtils.authenticatedUser();
		authorizationUtils.authorizeUser(user, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN,
				Constants.GROUP_SHIPPING, Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()), merchantStore);

		shippingFacade.saveShippingOrigin(address, merchantStore);

	}

	// list packaging
	@ApiOperation(httpMethod = "GET", value = "Get list of configured packages types for a specific merchant store", notes = "", produces = "application/json", response = List.class)
	@RequestMapping(value = { "/private/shipping/packages" }, method = RequestMethod.GET)
	@ResponseStatus(OK)
	public List<PackageDetails> listPackages(@ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) {

		String user = authorizationUtils.authenticatedUser();
		authorizationUtils.authorizeUser(user, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN,
				Constants.GROUP_SHIPPING, Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()), merchantStore);

		return shippingFacade.listPackages(merchantStore);

	}

	// get packaging
	@ApiOperation(httpMethod = "GET", value = "Get package details", notes = "", produces = "application/json", response = PackageDetails.class)
	@RequestMapping(value = { "/private/shipping/package/{code}" }, method = RequestMethod.GET)
	@ResponseStatus(OK)
	public PackageDetails getPackage(@PathVariable String code, @ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language) {

		String user = authorizationUtils.authenticatedUser();
		authorizationUtils.authorizeUser(user, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN,
				Constants.GROUP_SHIPPING, Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()), merchantStore);

		return shippingFacade.getPackage(code, merchantStore);

	}

	// create packaging
	@ApiOperation(httpMethod = "POST", value = "Create new package specification", notes = "", produces = "application/json", response = Void.class)
	@RequestMapping(value = { "/private/shipping/package" }, method = RequestMethod.POST)
	@ResponseStatus(OK)
	public void createPackage(@RequestBody PackageDetails details, @ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language) {

		String user = authorizationUtils.authenticatedUser();
		authorizationUtils.authorizeUser(user, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN,
				Constants.GROUP_SHIPPING, Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()), merchantStore);

		shippingFacade.createPackage(details, merchantStore);

	}

	// edit packaging
	@ApiOperation(httpMethod = "PUT", value = "Edit package specification", notes = "", produces = "application/json", response = Void.class)
	@RequestMapping(value = { "/private/shipping/package/{code}" }, method = RequestMethod.PUT)
	@ResponseStatus(OK)
	public void updatePackage(@PathVariable String code, @RequestBody PackageDetails details,
			@ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) {

		String user = authorizationUtils.authenticatedUser();
		authorizationUtils.authorizeUser(user, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN,
				Constants.GROUP_SHIPPING, Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()), merchantStore);

		shippingFacade.updatePackage(code, details, merchantStore);

	}

	// delete packaging
	@ApiOperation(httpMethod = "DELETE", value = "Delete a package specification", notes = "", produces = "application/json", response = Void.class)
	@RequestMapping(value = { "/private/shipping/package/{code}" }, method = RequestMethod.DELETE)
	@ResponseStatus(OK)
	public void deletePackage(@PathVariable String code, @ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language) {

		String user = authorizationUtils.authenticatedUser();
		authorizationUtils.authorizeUser(user, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN,
				Constants.GROUP_SHIPPING, Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()), merchantStore);

		shippingFacade.deletePackage(code, merchantStore);

	}

	/**
	 * Get available shipping modules
	 * 
	 * @param merchantStore
	 * @param language
	 * @return
	 */
	@GetMapping("/private/modules/shipping")
	@ApiOperation(httpMethod = "GET", value = "List list of shipping modules", notes = "Requires administration access", produces = "application/json", response = List.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT") })
	public List<IntegrationModuleSummaryEntity> shippingModules(@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language) {

		try {
			List<IntegrationModule> modules = shippingService.getShippingMethods(merchantStore);

			// configured modules
			Map<String, IntegrationConfiguration> configuredModules = shippingService
					.getShippingModulesConfigured(merchantStore);
			return modules.stream().map(m -> integrationModule(m, configuredModules)).collect(Collectors.toList());

		} catch (ServiceException e) {
			LOGGER.error("Error getting shipping modules", e);
			throw new ServiceRuntimeException("Error getting shipping modules", e);
		}

	}

	/**
	 * Get merchant shipping module details
	 * 
	 * @param code
	 * @param merchantStore
	 * @param language
	 * @return
	 */
	@GetMapping("/private/modules/shipping/{code}")
	@ApiOperation(httpMethod = "GET", value = "Shipping module by code", produces = "application/json", response = List.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT") })
	public IntegrationConfiguration shippingModule(@PathVariable String code,
			@ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) {

		try {

			// configured modules
			List<IntegrationModule> modules = 
					shippingService
					.getShippingMethods(merchantStore);
			
			//check if exist
			Optional<IntegrationModule> checkIfExist = modules.stream().filter(m -> m.getCode().equals(code)).findAny();
			
			if(!checkIfExist.isPresent()) {
				throw new ResourceNotFoundException("Shipping module [" + code + "] not found");
			}
			
			IntegrationConfiguration config = shippingService.getShippingConfiguration(code, merchantStore);
			if (config == null) {
				config = new IntegrationConfiguration();
			}

			/**
			 * Build return object for now this is a read copy
			 */

			config.setActive(config.isActive());
			config.setDefaultSelected(config.isDefaultSelected());
			config.setIntegrationKeys(config.getIntegrationKeys());
			config.setIntegrationOptions(config.getIntegrationOptions());

			return config;

		} catch (ServiceException e) {
			LOGGER.error("Error getting shipping module [" + code + "]", e);
			throw new ServiceRuntimeException("Error getting shipping module [" + code + "]", e);
		}

	}

	@PostMapping(value = "/private/modules/shipping")
	public void configure(@RequestBody IntegrationModuleConfiguration configuration,
			@ApiIgnore MerchantStore merchantStore) {

		try {

			List<IntegrationModule> modules = shippingService.getShippingMethods(merchantStore);

			Map<String, IntegrationModule> map = modules.stream()
					.collect(Collectors.toMap(IntegrationModule::getCode, module -> module));

			IntegrationModule config = map.get(configuration.getCode());

			if (config == null) {
				throw new ResourceNotFoundException("Shipping module [" + configuration.getCode() + "] not found");
			}

			Map<String, IntegrationConfiguration> configuredModules = shippingService
					.getShippingModulesConfigured(merchantStore);

			IntegrationConfiguration integrationConfiguration = configuredModules.get(configuration.getCode());

			if (integrationConfiguration == null) {
				integrationConfiguration = new IntegrationConfiguration();
			}

			/**
			 * Build return object for now this is a read copy
			 */

			integrationConfiguration.setActive(configuration.isActive());
			integrationConfiguration.setDefaultSelected(configuration.isDefaultSelected());
			integrationConfiguration.setIntegrationKeys(configuration.getIntegrationKeys());
			integrationConfiguration.setIntegrationOptions(configuration.getIntegrationOptions());

			shippingService.saveShippingQuoteModuleConfiguration(integrationConfiguration, merchantStore);

		} catch (ServiceException e) {
			LOGGER.error("Error saving shipping modules", e);
			throw new ServiceRuntimeException("Error saving shipping module", e);
		}

	}

	private IntegrationModuleSummaryEntity integrationModule(IntegrationModule module,
			Map<String, IntegrationConfiguration> configuredModules) {

		IntegrationModuleSummaryEntity readable = null;
		readable = new IntegrationModuleSummaryEntity();

		readable.setCode(module.getCode());
		readable.setImage(module.getImage());
		if (configuredModules.containsKey(module.getCode())) {
			IntegrationConfiguration conf = configuredModules.get(module.getCode());
			readable.setConfigured(true);
			if(conf.isActive()) {
				readable.setActive(true);
			}
		}
		return readable;

	}

	// Module configuration
	/**
	 * private String moduleCode; private boolean active; private boolean
	 * defaultSelected; private Map<String, String> integrationKeys = new
	 * HashMap<String, String>(); private Map<String, List<String>>
	 * integrationOptions = new HashMap<String, List<String>>(); private String
	 * environment;
	 * 
	 * moduleCode:CODE, active:true, defaultSelected:false, environment: "TEST",
	 * integrationKeys { "key":"value", "anotherkey":"anothervalue"... }
	 */

	@GetMapping(value = "/private/shipping/configuration/{id}", produces = { APPLICATION_JSON_VALUE })
	@ApiOperation(httpMethod = "GET", value = "Get shipping configuration by id", notes = "Get shipping configuration by id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Configuration found", response = ReadableMerchantShippingConfiguration.class) })
	public ReadableMerchantShippingConfiguration get(
			@PathVariable(name = "id") Long id) {
		return shippingFacade.getById(null, id);
	}

	@GetMapping(value = "/private/shipping/configurations", produces = { APPLICATION_JSON_VALUE })
	@ApiOperation(httpMethod = "GET", value = "Get list of shipping configurations", notes = "Get list of shipping configurations with pagination")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT")
	})
	public ReadableMerchantShippingConfigurationList list(
			@RequestParam(value = "startPage", required = false, defaultValue = "0") Integer startPage,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
			@ApiIgnore MerchantStore merchantStore) {
		Criteria criteria = new Criteria();
		criteria.setStartIndex(startPage);
		criteria.setPageSize(pageSize);
		try {
			return shippingFacade.list(merchantStore, criteria);
		} catch (ServiceException e) {
			throw new RuntimeException(e);
		}
	}

	@PostMapping(value = "/private/shipping/configuration", produces = { APPLICATION_JSON_VALUE })
	@ApiOperation(httpMethod = "POST", value = "Create a new shipping configuration", notes = "Create a new shipping configuration")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Configuration created", response = PersistableMerchantShippingConfiguration.class) })
	public ResponseEntity<PersistableMerchantShippingConfiguration> create(
			@Valid @RequestBody PersistableMerchantShippingConfiguration configuration,
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language) {
		try {
			shippingFacade.save(merchantStore, configuration);
		} catch (ServiceException e) {
			throw new RuntimeException(e);
		}
		return new ResponseEntity<>(configuration, HttpStatus.CREATED);
	}

	@PutMapping(value = "/private/shipping/configuration/{id}", produces = { APPLICATION_JSON_VALUE })
	@ApiOperation(httpMethod = "PUT", value = "Update a shipping configuration", notes = "Update a shipping configuration")
	public ResponseEntity<PersistableMerchantShippingConfiguration> update(
			@PathVariable Long id,
			@Valid @RequestBody PersistableMerchantShippingConfiguration configuration,
			@ApiIgnore MerchantStore merchantStore) {
		configuration.setId(id);
		try {
			shippingFacade.save(merchantStore, configuration);
		} catch (ServiceException e) {
			throw new RuntimeException(e);
		}
		return new ResponseEntity<>(configuration, OK);
	}

	@DeleteMapping(value = "/private/shipping/configuration/{id}", produces = { APPLICATION_JSON_VALUE })
	@ResponseStatus(OK)
	@ApiOperation(httpMethod = "DELETE", value = "Delete a shipping configuration", notes = "Delete a shipping configuration")
	public void delete(@PathVariable("id") Long id, @ApiIgnore MerchantStore merchantStore) {
		shippingFacade.delete(merchantStore, id);
	}

}
