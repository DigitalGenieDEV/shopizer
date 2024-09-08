package com.salesmanager.shop.store.controller.exchangerate.facade;

import com.salesmanager.shop.model.exchangerate.PersistableExchangeRate;

import javax.validation.Valid;

public interface ExchangeRateFacade {

    PersistableExchangeRate saveExchangeRate(@Valid PersistableExchangeRate exchangeRate);

}
