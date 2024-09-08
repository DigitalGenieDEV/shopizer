package com.salesmanager.core.business.services.exchangerate;

import com.salesmanager.core.business.repositories.exchangeRate.ExchangeRateRepository;
import com.salesmanager.core.business.utils.ExchangeRateConfig;
import com.salesmanager.core.model.catalog.product.ExchangeRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeRateServiceImpl.class);

    @Inject
    private ExchangeRateRepository exchangeRateRepository;

    @Inject
    private ExchangeRateConfig exchangeRateConfig;

    @Override
    public Boolean saveOrUpdate(ExchangeRate exchangeRate) {
        try {
            exchangeRateRepository.saveAndFlush(exchangeRate);
        } catch (Exception e) {
            LOGGER.error("Error while saveOrUpdate ExchangeRate", e);
            return false;
        }
        return true;
    }

    @Override
    public void refreshExchangeRate() {
        // trigger refresh config
        exchangeRateConfig.init();
    }

    @Override
    public ExchangeRate getById(Long exchangeRateId) {
        return exchangeRateRepository.getById(exchangeRateId);
    }
}
