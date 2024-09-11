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
                            .originCertificationCharge(additional.getOriginCertificationCharge().subtract(confirmed.getOriginCertificationCharge()))
                            .portTranslationCharge(additional.getPortTranslationCharge().subtract(confirmed.getPortTranslationCharge()))
                            .exportCharge(additional.getExportCharge().subtract(confirmed.getExportCharge()))
                            .oceanFreightCharge(additional.getOceanFreightCharge().subtract(confirmed.getOceanFreightCharge()))
                            .marineInsuranceCharge(additional.getMarineInsuranceCharge().subtract(confirmed.getMarineInsuranceCharge()))
                            .arrivalAccountingCharge(additional.getArrivalAccountingCharge().subtract(confirmed.getArrivalAccountingCharge()))
                            .handlingCharge(additional.getHandlingCharge().subtract(confirmed.getHandlingCharge()))
                            .warehouseCharge(additional.getWarehouseCharge().subtract(confirmed.getWarehouseCharge()))
                            .tariff(additional.getTariff().subtract(confirmed.getTariff()))
                            .tax(additional.getTax().subtract(confirmed.getTax()))
                            .duty(additional.getDuty().subtract(confirmed.getDuty()))
                            .shippingCharge(additional.getShippingCharge().subtract(confirmed.getShippingCharge()))
                            .paletteCharge(additional.getPaletteCharge().subtract(confirmed.getPaletteCharge()))
                            .warehouseTranslationCharge(additional.getWarehouseTranslationCharge().subtract(confirmed.getWarehouseTranslationCharge()))
                            .exportPortCharge(additional.getExportPortCharge().subtract(confirmed.getExportPortCharge()))
                            .freightSurcharge(additional.getFreightSurcharge().subtract(confirmed.getFreightSurcharge()))
                            .portArrivalCharge(additional.getPortArrivalCharge().subtract(confirmed.getPortArrivalCharge()))
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
