package com.salesmanager.shop.populator.customer;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.services.catalog.pricing.PricingService;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.payments.CombineTransaction;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.customer.order.transaction.ReadableCombineTransaction;
import com.salesmanager.shop.utils.DateUtil;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class ReadableCombineTransactionPopulator extends AbstractDataPopulator<CombineTransaction, ReadableCombineTransaction> {

    @Inject
    private PricingService pricingService;

    @Override
    protected ReadableCombineTransaction createTarget() {
        return null;
    }

    @Override
    public ReadableCombineTransaction populate(CombineTransaction source, ReadableCombineTransaction target, MerchantStore store, Language language) throws ConversionException {

        if (target == null) {
            target = new ReadableCombineTransaction();
        }

        try {
            target.setAmount(pricingService.getDisplayAmount(source.getAmount(), store));
            target.setDetails(source.getDetails());
            target.setPaymentType(source.getPaymentType());
            target.setTransactionType(source.getTransactionType());
            target.setTransactionDate(DateUtil.formatDate(source.getTransactionDate()));
            target.setId(source.getId());
            target.setPayOrderNo(source.getPayOrderNo());
            target.setRelationOrderIdList(source.getRelationOrderIdList());
            if (source.getCustomerOrder() != null) {
                target.setCustomerOrderId(source.getCustomerOrder().getId());
            }

            return target;
        } catch (Exception e) {
            throw new ConversionException(e);
        }

    }

    public PricingService getPricingService() {
        return pricingService;
    }

    public void setPricingService(PricingService pricingService) {
        this.pricingService = pricingService;
    }
}
