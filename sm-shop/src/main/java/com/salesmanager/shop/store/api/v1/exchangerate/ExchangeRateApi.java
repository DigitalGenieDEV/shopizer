package com.salesmanager.shop.store.api.v1.exchangerate;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.shop.model.exchangerate.PersistableExchangeRate;
import com.salesmanager.shop.store.api.exception.OperationNotAllowedException;
import com.salesmanager.shop.store.api.exception.UnauthorizedException;
import com.salesmanager.shop.store.controller.exchangerate.facade.ExchangeRateFacade;
import com.salesmanager.shop.store.controller.manager.facade.ManagerFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@Api(tags = {"Exchange rate resource"})
@SwaggerDefinition(tags = {
        @Tag(name = "Exchange rate resource", description = "Exchange rate management")})
public class ExchangeRateApi {

    @Inject
    private ExchangeRateFacade exchangeRateFacade;

    @Inject
    private ManagerFacade managerFacade;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/private/exchangeRate", produces = {MediaType.APPLICATION_JSON_VALUE})
    public PersistableExchangeRate create(@Valid @RequestBody PersistableExchangeRate exchangeRate, HttpServletRequest request) throws Exception {
        String authenticatedManager = managerFacade.authenticatedManager();
        if (authenticatedManager == null) {
            throw new UnauthorizedException();
        }

        try {
            managerFacade.isSuperAdmin(authenticatedManager);
        } catch (ServiceException e) {
            throw new OperationNotAllowedException("Operation is not allowed");
        }
        return exchangeRateFacade.saveExchangeRate(exchangeRate);
    }

    @PutMapping(value = "/private/exchangeRate/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public PersistableExchangeRate update(@Valid @RequestBody PersistableExchangeRate exchangeRate, HttpServletRequest request) throws Exception {
        String authenticatedManager = managerFacade.authenticatedManager();
        if (authenticatedManager == null) {
            throw new UnauthorizedException();
        }

        try {
            managerFacade.isSuperAdmin(authenticatedManager);
        } catch (ServiceException e) {
            throw new OperationNotAllowedException("Operation is not allowed");
        }
        return exchangeRateFacade.saveExchangeRate(exchangeRate);
    }

}
