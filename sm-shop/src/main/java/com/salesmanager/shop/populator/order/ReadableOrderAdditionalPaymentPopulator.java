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
                if(confirmed != null) {
                    AdditionalPayment settlement = AdditionalPayment.builder()
                            .originCharge(additional.getOriginCharge().subtract(confirmed.getOriginCharge()))
                            .originCertificationCharge(additional.getOriginCertificationCharge().subtract(confirmed.getOriginCharge()))
                            .portTranslationCharge(additional.getPortTranslationCharge().subtract(confirmed.getOriginCharge()))
                            .exportCharge(additional.getExportCharge().subtract(confirmed.getOriginCharge()))
                            .oceanFreightCharge(additional.getOceanFreightCharge().subtract(confirmed.getOriginCharge()))
                            .marineInsuranceCharge(additional.getMarineInsuranceCharge().subtract(confirmed.getOriginCharge()))
                            .arrivalAccountingCharge(additional.getArrivalAccountingCharge().subtract(confirmed.getOriginCharge()))
                            .handlingCharge(additional.getHandlingCharge().subtract(confirmed.getOriginCharge()))
                            .warehouseCharge(additional.getWarehouseCharge().subtract(confirmed.getOriginCharge()))
                            .tariff(additional.getTariff().subtract(confirmed.getOriginCharge()))
                            .tax(additional.getTax().subtract(confirmed.getOriginCharge()))
                            .duty(additional.getDuty().subtract(confirmed.getOriginCharge()))
                            .shippingCharge(additional.getShippingCharge().subtract(confirmed.getOriginCharge()))
                            .paletteCharge(additional.getPaletteCharge().subtract(confirmed.getOriginCharge()))
                            .warehouseTranslationCharge(additional.getWarehouseTranslationCharge().subtract(confirmed.getOriginCharge()))
                            .exportPortCharge(additional.getExportPortCharge().subtract(confirmed.getOriginCharge()))
                            .freightSurcharge(additional.getFreightSurcharge().subtract(confirmed.getOriginCharge()))
                            .portArrivalCharge(additional.getPortArrivalCharge().subtract(confirmed.getOriginCharge()))
                            .build();

                    target.setSettlementRequiredPayment(settlement);
                } else {
                    target.setSettlementRequiredPayment(additional);
                }
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
