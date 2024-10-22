package com.salesmanager.core.business.utils;

import com.salesmanager.core.business.modules.enmus.ExchangeRateEnums;
import com.salesmanager.core.business.repositories.exchangeRate.ExchangeRateRepository;
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
        // currently only support CNY2KRW, if more exchangeRate config in the future, should be dynamic loading
        ExchangeRatePOJO exchangeRate = exchangeRateRepository.findExchangeRate("CNY", "KRW");
        exchangeRates.put(ExchangeRateEnums.CNY_KRW.name(), exchangeRate);

        ExchangeRatePOJO krw2UsdExchangeRate = exchangeRateRepository.findExchangeRate("KRW", "USD");
        exchangeRates.put(ExchangeRateEnums.KRW_USD.name(), krw2UsdExchangeRate);

        ExchangeRatePOJO cny2UsdExchangeRate = exchangeRateRepository.findExchangeRate("CNY", "USD");
        exchangeRates.put(ExchangeRateEnums.CNY_USD.name(), cny2UsdExchangeRate);

        ExchangeRatePOJO krwToKrwExchangeRate = exchangeRateRepository.findExchangeRate("KRW", "KRW");
        exchangeRates.put(ExchangeRateEnums.KRW_KRW.name(), krwToKrwExchangeRate);

    }

    public ExchangeRatePOJO getExchangeRate(ExchangeRateEnums exchangeRate) {
        return exchangeRates.get(exchangeRate.name());
    }

    public BigDecimal getRate(ExchangeRateEnums exchangeRateEnums) {
        ExchangeRatePOJO exchangeRate = getExchangeRate(exchangeRateEnums);
        return exchangeRate != null ? exchangeRate.getRate() : null;
    }

    public ExchangeRatePOJO getExchangeRate(String from, String to) {
        return exchangeRates.get(from.toUpperCase() + "_" + to.toUpperCase());
    }

    public BigDecimal getRate(String from, String to) {
        ExchangeRatePOJO exchangeRate = getExchangeRate(from, to);
        return exchangeRate != null ? exchangeRate.getRate() : null;
    }
}


