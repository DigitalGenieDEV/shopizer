package com.salesmanager.shop.populator.order;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.AdditionalPayment;
import com.salesmanager.core.model.order.ConfirmedAdditionalPayment;
import com.salesmanager.core.model.order.OrderAdditionalPayment;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.order.v1.ReadableOrderAdditionalPayment;
import org.springframework.stereotype.Component;

@Component
public class ReadableOrderAdditionalPaymentPopulator extends AbstractDataPopulator<OrderAdditionalPayment, ReadableOrderAdditionalPayment> {

    @Override
    protected ReadableOrderAdditionalPayment createTarget() {
        return null;
    }

    @Override
    public ReadableOrderAdditionalPayment populate(OrderAdditionalPayment source, ReadableOrderAdditionalPayment target, MerchantStore store, Language language) throws ConversionException {
        try {
            target.setOrderId(source.getId());
            AdditionalPayment additional = source.getAdditionalPayment();
            ConfirmedAdditionalPayment confirmed = source.getConfirmedAdditionalPayment();

            if (additional != null) {
                target.setAdditionalPayment(additional);
            }

            if (confirmed != null) {
                target.setConfirmedAdditionalPayment(confirmed);
            }
        } catch (Exception e) {
            throw new ConversionException(e);
        }

        return target;
    }
}
