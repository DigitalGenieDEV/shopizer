package com.salesmanager.shop.store.facade.exchangerate;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.exchangeRate.ExchangeRateRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.business.services.exchangerate.ExchangeRateService;
import com.salesmanager.core.model.catalog.product.ExchangeRate;
import com.salesmanager.shop.model.exchangerate.PersistableExchangeRate;
import com.salesmanager.shop.populator.exchangerate.PersistableExchangeRatePopulator;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.exchangerate.facade.ExchangeRateFacade;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ExchangeRateFacadeImpl extends SalesManagerEntityServiceImpl<Long, ExchangeRate>
        implements ExchangeRateFacade {

    private ExchangeRateRepository exchangeRateRepository;

    private PersistableExchangeRatePopulator persistableExchangeRatePopulator;

    private ExchangeRateService exchangeRateService;

    @Inject
    public ExchangeRateFacadeImpl(ExchangeRateRepository exchangeRateRepository,
                                  PersistableExchangeRatePopulator persistableExchangeRatePopulator,
                                  ExchangeRateService exchangeRateService) {
        super(exchangeRateRepository);
        this.exchangeRateRepository = exchangeRateRepository;
        this.persistableExchangeRatePopulator = persistableExchangeRatePopulator;
        this.exchangeRateService = exchangeRateService;
    }

    @Override
    public PersistableExchangeRate saveExchangeRate(PersistableExchangeRate exchangeRate) {
        try {
            Long exchangeRateId = exchangeRate.getId();

            ExchangeRate target = new ExchangeRate();
            if (exchangeRateId != null) {
                target = exchangeRateService.getById(exchangeRateId);
            }

            ExchangeRate dbExchangeRate = persistableExchangeRatePopulator.populate(exchangeRate, target);

            exchangeRateService.saveOrUpdate(dbExchangeRate);
            exchangeRateService.refreshExchangeRate();

            exchangeRate.setId(dbExchangeRate.getId());

            return exchangeRate;
        } catch (ServiceException e) {
            throw new ServiceRuntimeException("Error while saving exchangeRate", e);
        }
    }
}
