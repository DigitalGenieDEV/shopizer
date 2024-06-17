package com.salesmanager.shop.store.api.v1.favorites;

import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.entity.ReadableEntityList;
import com.salesmanager.shop.model.favorites.PersistableFavorites;
import com.salesmanager.shop.model.favorites.ReadableFavorites;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.controller.customer.facade.v1.CustomerFacade;
import com.salesmanager.shop.store.controller.favorites.facade.FavoritesFacade;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1")
@Api(tags = {
        "user favorites" })
@SwaggerDefinition(tags = {
        @Tag(name = "user favorites", description = "add user favorites , user favorites list, user favorites delete") })
public class FavoritesApi {

    @Autowired
    private FavoritesFacade favoritesFacade;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerFacade customerFacadev1;

    @GetMapping(value = "/auth/favorite/list")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(httpMethod = "GET", value = "get favorite products by userId", notes = "",
            response = ReadableEntityList.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")})
    public ReadableEntityList<ReadableFavorites> getListFavoriteProducts(
            @RequestParam(value = "userId", required = true) Long userId,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "count", required = false, defaultValue = "10") Integer count,
            @ApiIgnore Language language,
            HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();

        // Fetch customer
        Customer customer = customerService.getById(userId);
        if (customer == null) {
            throw new ResourceNotFoundException("No Customer found for id [" + userId + "]");
        }

        // Authorize customer
        try {
            customerFacadev1.authorize(customer, principal);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Unauthorized access for customer id [" + userId + "]");
        }

        // Fetch favorite products
        ReadableEntityList<ReadableFavorites> listFavoriteProducts;
        try {
            listFavoriteProducts = favoritesFacade.getListFavoriteProducts(userId, page, count, language);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching favorite products for customer id [" + userId + "]", e);
        }

        return listFavoriteProducts;
    }

    @PostMapping(value = "/auth/favorite/product")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(httpMethod = "POST", value = "Add a product to favorites", notes = "")
    public void saveFavoriteProduct(
            @RequestBody PersistableFavorites persistableFavorites,
            HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();

        Customer customer = customerService.getById(persistableFavorites.getUserId());
        if (customer == null) {
            throw new ResourceNotFoundException("No Customer found for id [" + persistableFavorites.getUserId() + "]");
        }
        customerFacadev1.authorize(customer, principal);
        favoritesFacade.saveFavoriteProduct(persistableFavorites);
    }

    @DeleteMapping(value = "/auth/favorite/product/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(httpMethod = "DELETE", value = "Remove a product from favorites", notes = "")
    public void deleteFavoriteProduct(
            @PathVariable Long productId,
            @RequestParam(value = "userId", required = true) Long userId,
            HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();

        Customer customer = customerService.getById(userId);
        if (customer == null) {
            throw new ResourceNotFoundException("No Customer found for id [" + userId + "]");
        }
        customerFacadev1.authorize(customer, principal);
        favoritesFacade.deleteFavoriteProduct(productId, userId);
    }

    @GetMapping(value = "/favorite/count/product/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(httpMethod = "count", value = "product favorites count", notes = "")
    public Integer favoriteProductCount(
            @PathVariable Long productId,
            HttpServletRequest request) {

        return favoritesFacade.queryFavoriteCountByProductId(productId);
    }


}