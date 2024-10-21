package com.salesmanager.shop.store.api.v1.favorites;

import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.entity.ReadableEntityList;
import com.salesmanager.shop.model.favorites.PersistableFavorites;
import com.salesmanager.shop.model.favorites.ReadableFavorites;
import com.salesmanager.shop.model.shop.CommonResultDTO;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.v2.product.ProductApiV2;
import com.salesmanager.shop.store.controller.customer.facade.v1.CustomerFacade;
import com.salesmanager.shop.store.controller.favorites.facade.FavoritesFacade;
import com.salesmanager.shop.store.error.ErrorCodeEnums;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(FavoritesApi.class);


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
    @ApiImplicitParams({@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko")})
    public ReadableEntityList<ReadableFavorites> getListFavoriteProducts(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "count", required = false, defaultValue = "10") Integer count,
            @ApiIgnore Language language,
            HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        Customer customer = customerService.getByNick(principal.getName());

        // Fetch favorite products
        ReadableEntityList<ReadableFavorites> listFavoriteProducts;
        try {
            listFavoriteProducts = favoritesFacade.getListFavoriteProducts(customer.getId(), page, count, language);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching favorite products for customer id [" + customer.getId() + "]", e);
        }

        return listFavoriteProducts;
    }


    @GetMapping(value = "/private/favorite/list")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(httpMethod = "GET", value = "get favorite products by userId", notes = "",
            response = ReadableEntityList.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko")})
    public ReadableEntityList<ReadableFavorites> getListFavoriteProductsByAdmin(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "count", required = false, defaultValue = "10") Integer count,
            @RequestParam(value = "nickName", required = false) String nickName,
            @RequestParam(value = "customerId", required = false) Long customerId,
            @ApiIgnore Language language) {

        Customer customer = null;
        if (customerId !=null){
            customer  = customerService.getById(customerId);
        }
        if (StringUtils.isNotEmpty(nickName)){
            customer  = customerService.getByNick(nickName);
        }
        // Fetch favorite products
        ReadableEntityList<ReadableFavorites> listFavoriteProducts;
        try {
            listFavoriteProducts = favoritesFacade.getListFavoriteProducts(customer ==null? null : customer.getId(), page, count, language);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching favorite products", e);
        }
        return listFavoriteProducts;
    }

    @GetMapping(value = "/auth/favorite/user/is_favorited/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(httpMethod = "GET", value = "product favorites count by user and productId", notes = "")
    public CommonResultDTO<Boolean> isFavoriteByUserProduct(@PathVariable Long productId, HttpServletRequest request) {
        try {
            Principal principal = request.getUserPrincipal();
            Customer customer = customerService.getByNick(principal.getName());
            Boolean favoriteByUserProduct = favoritesFacade.isFavoriteByUserProduct(customer.getId(), productId);
            return CommonResultDTO.ofSuccess(favoriteByUserProduct);
        }catch (Exception e){
            LOGGER.error("sortUpdateMainDisplayManagementProduct error", e);
            return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
        }
    }


    @PostMapping(value = "/auth/favorite/product")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(httpMethod = "POST", value = "Add a product to favorites", notes = "")
    public void saveFavoriteProduct(
            @RequestBody PersistableFavorites persistableFavorites,
            HttpServletRequest request) {

        favoritesFacade.saveFavoriteProduct(persistableFavorites);
    }

    @DeleteMapping(value = "/auth/favorite/product/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(httpMethod = "DELETE", value = "Remove a product from favorites", notes = "")
    public void deleteFavoriteProduct(
            @PathVariable Long productId,
            @RequestParam(value = "userId", required = true) Long userId,
            HttpServletRequest request) {
        favoritesFacade.deleteFavoriteProduct(productId, userId);
    }

    @GetMapping(value = "/favorite/count/product/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(httpMethod = "GET", value = "product favorites count", notes = "")
    public Integer favoriteProductCount(
            @PathVariable Long productId,
            HttpServletRequest request) {

        return favoritesFacade.queryFavoriteCountByProductId(productId);
    }


}
