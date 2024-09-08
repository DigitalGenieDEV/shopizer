package com.salesmanager.shop.populator.exchangerate;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.catalog.product.ExchangeRate;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.exchangerate.PersistableExchangeRate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PersistableExchangeRatePopulator extends AbstractDataPopulator<PersistableExchangeRate, ExchangeRate> {
    @Override
    protected ExchangeRate createTarget() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ExchangeRate populate(PersistableExchangeRate persistableExchangeRate, ExchangeRate exchangeRate, MerchantStore store, Language language) throws ConversionException {
        throw new UnsupportedOperationException();
    }

    public ExchangeRate populate(PersistableExchangeRate persistableExchangeRate, ExchangeRate exchangeRate) {
        exchangeRate.setId(persistableExchangeRate.getId());
        // Currency should not be updated, but only rate can be updated
//        exchangeRate.setBaseCurrency(persistableExchangeRate.getBaseCurrency());
//        exchangeRate.setTargetCurrency(persistableExchangeRate.getTargetCurrency());
        exchangeRate.setRate(new BigDecimal(persistableExchangeRate.getRate()));
        return exchangeRate;
    }
}
