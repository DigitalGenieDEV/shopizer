package com.salesmanager.core.business.services.exchangerate;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.catalog.product.ExchangeRate;

public interface ExchangeRateService {

    Boolean saveOrUpdate(ExchangeRate exchangeRate) throws ServiceException;

    void refreshExchangeRate();

    ExchangeRate getById(Long exchangeRateId);

}
