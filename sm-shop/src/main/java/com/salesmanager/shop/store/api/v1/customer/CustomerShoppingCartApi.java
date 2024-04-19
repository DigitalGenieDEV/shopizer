package com.salesmanager.shop.store.api.v1.customer;

import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.business.services.customer.shoppingcart.CustomerShoppingCartService;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCart;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.customer.order.PersistableCustomerOrder;
import com.salesmanager.shop.model.customer.shoppingcart.PersistableCustomerShoppingCartItem;
import com.salesmanager.shop.model.customer.shoppingcart.ReadableCustomerShoppingCart;
import com.salesmanager.shop.model.order.v1.ReadableOrderConfirmation;
import com.salesmanager.shop.model.shoppingcart.ReadableShoppingCart;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.customer.facade.CustomerFacade;
import com.salesmanager.shop.store.controller.customer.facade.CustomerShoppingCartFacade;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping("/api/v1")
@Api(tags = { "Customer shopping cart api" })
@SwaggerDefinition(tags = {
        @Tag(name = "Customer shopping cart resource", description = "Add, remove and retrieve customer shopping carts") })
public class CustomerShoppingCartApi {

    @Inject
    private CustomerShoppingCartFacade customerShoppingCartFacade;

    @Inject
    private CustomerShoppingCartService customerShoppingCartService;

    @Inject
    private CustomerService customerService;

    @Inject
    private CustomerFacade customerFacade;

    @Autowired
    private com.salesmanager.shop.store.controller.customer.facade.v1.CustomerFacade customerFacadev1;


    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerShoppingCartApi.class);

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/customer_cart")
    @ApiOperation(httpMethod = "POST", value = "Add product to shopping customer cart when no cart exists, this will create a new customer cart id", notes = "No customer ID in scope. Add to cart for non authenticated users, as simple as {\"product\":1232,\"quantity\":1}", produces = "application/json", response = ReadableShoppingCart.class)
    @ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
    public ReadableCustomerShoppingCart addToCart(
            PersistableCustomerShoppingCartItem customerShoppingCartItem,
            Language language
    ) {
        return customerShoppingCartFacade.addToCart(customerShoppingCartItem, language);
    }

    @PutMapping(value = "/customer_cart/{code}")
    @ApiOperation(httpMethod = "PUT", value = "Add to an existing customer shopping cart or modify an item quantity", notes = "No customer ID in scope. Modify cart for non authenticated users, as simple as {\"product\":1232,\"quantity\":0} for instance will remove item 1234 from cart", produces = "application/json", response = ReadableShoppingCart.class)
    @ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
    public ResponseEntity<ReadableCustomerShoppingCart> modifyCart(
            String code,
            PersistableCustomerShoppingCartItem customerShoppingCartItem,
            Language language
    ) {
        try {
            ReadableCustomerShoppingCart cart = customerShoppingCartFacade.modifyCart(code, customerShoppingCartItem, language);

            if (cart == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(cart, HttpStatus.CREATED);

        } catch (Exception e) {
            if(e instanceof ResourceNotFoundException) {
                throw (ResourceNotFoundException)e;
            } else {
                throw new ServiceRuntimeException(e);
            }

        }
    }

    @PostMapping(value = "/customer_cart/{code}/promo/{promo}")
    @ApiOperation(httpMethod = "POST", value = "Add promo / coupon to an existing customer cart", produces = "application/json", response = ReadableShoppingCart.class)
    @ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
    public ResponseEntity<ReadableCustomerShoppingCart> modifyCart(
            @PathVariable String code,
            @PathVariable String promo,
            @ApiIgnore Language language
    ) {
        try {
            ReadableCustomerShoppingCart cart = customerShoppingCartFacade.modifyCart(code, promo, language);

            if (cart == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(cart, HttpStatus.CREATED);

        } catch (Exception e) {
            if(e instanceof ResourceNotFoundException) {
                throw (ResourceNotFoundException)e;
            } else {
                throw new ServiceRuntimeException(e);
            }

        }
    }

    @PostMapping(value = "/customer_cart/{code}/multi", consumes = { "application/json" }, produces = { "application/json" })
    @ApiOperation(httpMethod = "POST", value = "Add to an existing customer shopping cart or modify an item quantity", notes = "No customer ID in scope. Modify cart for non authenticated users, as simple as {\"product\":1232,\"quantity\":0} for instance will remove item 1234 from cart", produces = "application/json", response = ReadableShoppingCart.class)
    @ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
    public ResponseEntity<ReadableCustomerShoppingCart> modifyCart(
            @PathVariable String code,
            @Valid @RequestBody PersistableCustomerShoppingCartItem[] customerShoppingCartItems,
            @ApiIgnore Language language
            ) {
        try {
            ReadableCustomerShoppingCart cart = customerShoppingCartFacade.modifyCartMulti(code, Arrays.asList(customerShoppingCartItems), language);

            return new ResponseEntity<>(cart, HttpStatus.CREATED);

        } catch (Exception e) {
            if(e instanceof ResourceNotFoundException) {
                throw (ResourceNotFoundException)e;
            } else {
                throw new ServiceRuntimeException(e);
            }

        }
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/customer_cart/{code}", method = RequestMethod.GET)
    @ApiOperation(httpMethod = "GET", value = "Get a customer shopping cart by code", notes = "", produces = "application/json", response = ReadableShoppingCart.class)
    @ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
    public ReadableCustomerShoppingCart getByCode(
            @PathVariable String code,
            @ApiIgnore Language language,
            HttpServletResponse response
    ) {
        try {

            ReadableCustomerShoppingCart cart = customerShoppingCartFacade.getByCode(code, language);

            if (cart == null) {
                response.sendError(404, "No ShoppingCart found for customer code : " + code);
                return null;
            }

            return cart;

        } catch (Exception e) {
            if(e instanceof ResourceNotFoundException) {
                throw (ResourceNotFoundException)e;
            } else {
                throw new ServiceRuntimeException(e);
            }

        }
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/auth/customer/customer_cart", method = RequestMethod.GET)
    @ApiOperation(httpMethod = "GET", value = "Get a shopping cart by authenticated customer", notes = "", produces = "application/json", response = ReadableShoppingCart.class)
    @ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
    public ReadableCustomerShoppingCart getByCustomer(
            @RequestParam Optional<String> cart, // cart code
            @ApiIgnore Language language,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        return null;
//        Principal principal = request.getUserPrincipal();
//        Customer customer = null;
//        try {
//            customer = customerFacade.getCustomerByUserName(principal.getName(), merchantStore);
//        } catch (Exception e) {
//            throw new ServiceRuntimeException("Exception while getting customer [ " + principal.getName() + "]");
//        }
//
//        if (customer == null) {
//            throw new ResourceNotFoundException("No Customer found for principal[" + principal.getName() + "]");
//        }
//
//        customerFacadev1.authorize(customer, principal);
//        ReadableShoppingCart readableCart = cus.get(cart, customer.getId(), merchantStore, language);
//
//        if (readableCart == null) {
//            throw new ResourceNotFoundException("No cart found for customer [" + principal.getName() + "]");
//        }
//
//        return readableCart;
    }

    @DeleteMapping(value = "/customer_cart/{code}/{store}/product/{sku}", produces = { APPLICATION_JSON_VALUE })
    @ApiOperation(httpMethod = "DELETE", value = "Remove a product from a specific cart", notes = "If body set to true returns remaining cart in body, empty cart gives empty body. If body set to false no body ", produces = "application/json", response = ReadableShoppingCart.class)
    @ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en"),
            @ApiImplicitParam(name = "body", dataType = "boolean", defaultValue = "false"), })
    public ResponseEntity<ReadableCustomerShoppingCart> deleteCartItem(
            @PathVariable("code") String cartCode,
            @PathVariable("sku")  String sku,
            @PathVariable("store")  Integer store,
            @ApiIgnore Language language,
            @RequestParam(defaultValue = "false") boolean body
    ) throws Exception {
        ReadableCustomerShoppingCart updatedCart = customerShoppingCartFacade.removeCustomerShoppingCartItem(cartCode, sku, store,
                language, body);
        if (body) {
            return new ResponseEntity<>(updatedCart, HttpStatus.OK);
        }
        return new ResponseEntity<>(updatedCart, HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = { "/auth/customer_cart/{code}/checkout" }, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en") })
    public ReadableOrderConfirmation checkout(
            @PathVariable final String code, //shopping cart
            @Valid @RequestBody PersistableCustomerOrder order, // order
            @ApiIgnore Language language,
            HttpServletRequest request,
            HttpServletResponse response, Locale locale
    ) {

        try {
            Principal principal = request.getUserPrincipal();
            String userName = principal.getName();

            Customer customer = customerService.getByNick(userName);

            if (customer == null) {
                response.sendError(401, "Error while performing checkout customer not authorized");
                return null;
            }

            CustomerShoppingCart cart = customerShoppingCartService.getByCode(code);
            if (cart == null) {
                throw new ResourceNotFoundException("Cart code " + code + " does not exist");
            }


            return null;
        } catch (Exception e) {
            LOGGER.error("Error while processing checkout", e);
            try {
                response.sendError(503, "Error while processing checkout " + e.getMessage());
            } catch (Exception ignore) {
            }
            return null;
        }

    }

}
