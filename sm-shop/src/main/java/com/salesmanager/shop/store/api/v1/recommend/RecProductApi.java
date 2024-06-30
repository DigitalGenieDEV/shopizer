package com.salesmanager.shop.store.api.v1.recommend;

import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.utils.StringUtil;
import com.salesmanager.shop.model.common.ReadablePaginationResult;
import com.salesmanager.shop.model.recommend.*;
import com.salesmanager.shop.store.controller.recommend.facade.RecProductFacade;
import com.salesmanager.shop.store.security.JWTTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1")
@Api(tags = {"Recommend products  (RecProductApi Api)"})
public class RecProductApi {

    @Value("${authToken.header}")
    private String tokenHeader;

    @Inject
    private RecProductFacade recProductFacade;

    @Inject
    private JWTTokenUtil jwtTokenUtil;

    @Inject
    private CustomerService customerService;

    @PostMapping("/rec/guess_u_like")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko")
    })
    public ReadableRecProductList getRecGuessULike(
            @RequestBody RecGuessULikeRequest recGuessULikeRequest,
            @ApiIgnore Language language,
            HttpServletRequest request
    ) throws Exception {
        String token = request.getHeader(tokenHeader);

        if(token != null && token.contains("Bearer")) {
            token = token.substring("Bearer ".length(),token.length());
        }

        if (!StringUtils.isBlank(token)) {
            String username = jwtTokenUtil.getUsernameFromToken(token);
            Customer customer = customerService.getByNick(username);

            if (customer != null) {
                recGuessULikeRequest.setUid(customer.getId().intValue());
            }
        }

        return recProductFacade.getRecGuessULike(recGuessULikeRequest, language);
    }

    @PostMapping("/rec/relate_item")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko")
    })
    public ReadableRecProductList getRecRelate(
            @RequestBody RecRelateItemRequest recRelateItemRequest,
            @ApiIgnore Language language,
            HttpServletRequest request
    ) throws Exception {
//        String username = jwtTokenUtil.getUsernameFromToken(token);
        String token = request.getHeader(tokenHeader);

        if(token != null && token.contains("Bearer")) {
            token = token.substring("Bearer ".length(),token.length());
        }

        if (!StringUtils.isBlank(token)) {
            String username = jwtTokenUtil.getUsernameFromToken(token);
            Customer customer = customerService.getByNick(username);

            if (customer != null) {
                recRelateItemRequest.setUid(customer.getId().intValue());
            }
        }


        return recProductFacade.getRecRelateItem(recRelateItemRequest, language);
    }

    @PostMapping("/rec/selection_product")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko")
    })
    public ReadableRecProductList getRecSelection(
            @RequestBody RecSelectionProductRequest recSelectionProductRequest,
            @ApiIgnore Language language,
            HttpServletRequest request
    ) throws Exception {
        String token = request.getHeader(tokenHeader);

        if(token != null && token.contains("Bearer")) {
            token = token.substring("Bearer ".length(),token.length());
        }

        if (!StringUtils.isBlank(token)) {
            String username = jwtTokenUtil.getUsernameFromToken(token);
            Customer customer = customerService.getByNick(username);

            if (customer != null) {
                recSelectionProductRequest.setUid(customer.getId().intValue());
            }
        }

        return recProductFacade.getRecSelectionProduct(recSelectionProductRequest, language);
    }

    @PostMapping("/rec/foot_print")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko")
    })
    public ReadableRecProductList getRecFootPrint(
            @RequestBody RecFootPrintRequest request,
            @ApiIgnore Language language
    ) throws Exception {
        return recProductFacade.getFootPrint(request, language);
    }

}
