package com.salesmanager.core.business.utils;

import com.salesmanager.core.business.modules.enmus.ExchangeRateEnums;
import com.salesmanager.core.business.repositories.exchangeRate.ExchangeRateRepository;
import com.salesmanager.core.model.catalog.product.ExchangeRate;
import com.salesmanager.core.model.catalog.product.ExchangeRatePOJO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


@Configuration
public class ExchangeRateConfig {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    private Map<String, ExchangeRatePOJO> exchangeRates = new HashMap<>();

    @PostConstruct
    public void init() {
        ExchangeRatePOJO exchangeRate = exchangeRateRepository.findExchangeRate("CNY", "KRW");
        exchangeRates.put(ExchangeRateEnums.CNY_KRW.name(), exchangeRate);
    }

    public ExchangeRatePOJO getExchangeRate(ExchangeRateEnums exchangeRate) {
        return exchangeRates.get(exchangeRate.name());
    }

    public BigDecimal getRate(ExchangeRateEnums exchangeRateEnums) {
        ExchangeRatePOJO exchangeRate = getExchangeRate(exchangeRateEnums);
        return exchangeRate != null ? exchangeRate.getRate() : null;
    }
}


