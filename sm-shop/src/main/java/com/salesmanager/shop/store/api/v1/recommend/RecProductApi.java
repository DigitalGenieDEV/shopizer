package com.salesmanager.shop.store.api.v1.recommend;

import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.common.ReadablePaginationResult;
import com.salesmanager.shop.model.recommend.*;
import com.salesmanager.shop.store.controller.recommend.facade.RecProductFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.inject.Inject;

@RestController
@RequestMapping("/api/v1")
@Api(tags = {"Recommend products  (RecProductApi Api)"})
public class RecProductApi {

    @Inject
    private RecProductFacade recProductFacade;

    @PostMapping("/rec/guess_u_like")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
    })
    public ReadableRecProductList getRecGuessULike(
            @RequestBody RecGuessULikeRequest request,
            @ApiIgnore Language language
    ) throws Exception {
        return recProductFacade.getRecGuessULike(request, language);
    }

    @PostMapping("/rec/relate_item")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
    })
    public ReadableRecProductList getRecRelate(
            @RequestBody RecRelateItemRequest request,
            @ApiIgnore Language language
    ) throws Exception {
        return recProductFacade.getRecRelateItem(request, language);
    }

    @PostMapping("/rec/selection_product")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
    })
    public ReadableRecProductList getRecSelection(
            @RequestBody RecSelectionProductRequest request,
            @ApiIgnore Language language
    ) throws Exception {
        return recProductFacade.getRecSelectionProduct(request, language);
    }

}
