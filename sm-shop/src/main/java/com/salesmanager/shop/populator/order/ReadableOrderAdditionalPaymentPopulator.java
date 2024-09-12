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

import java.math.BigDecimal;

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

            target.setStatus(source.getStatus());

            if (additional != null) {
                target.setAdditionalPayment(additional);
                BigDecimal total = getAdditionalTotal(additional);
                target.setAdditionalPaymentTotal(total);
                target.setSettlementPaymentTotal(total);

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
                BigDecimal total = getConfirmedTotal(confirmed);
                target.setConfirmedPaymentTotal(total);
                target.setSettlementPaymentTotal(target.getSettlementPaymentTotal().subtract(total));
            }
        } catch (Exception e) {
            throw new ConversionException(e);
        }

        return target;
    }

    private BigDecimal getAdditionalTotal(AdditionalPayment additional) {
        BigDecimal total = BigDecimal.ZERO;
        total = total.add(additional.getOriginCharge());
        total = total.add(additional.getOriginCertificationCharge());
        total = total.add(additional.getPortTranslationCharge());
        total = total.add(additional.getExportCharge());
        total = total.add(additional.getOceanFreightCharge());
        total = total.add(additional.getMarineInsuranceCharge());
        total = total.add(additional.getArrivalAccountingCharge());
        total = total.add(additional.getHandlingCharge());
        total = total.add(additional.getWarehouseCharge());
        total = total.add(additional.getTariff());
        total = total.add(additional.getTax());
        total = total.add(additional.getDuty());
        total = total.add(additional.getShippingCharge());
        total = total.add(additional.getPaletteCharge());
        total = total.add(additional.getWarehouseTranslationCharge());
        total = total.add(additional.getExportPortCharge());
        total = total.add(additional.getFreightSurcharge());
        total = total.add(additional.getPortArrivalCharge());
        return total;
    }

    private BigDecimal getConfirmedTotal(ConfirmedAdditionalPayment additional) {
        BigDecimal total = BigDecimal.ZERO;
        total = total.add(additional.getOriginCharge());
        total = total.add(additional.getOriginCertificationCharge());
        total = total.add(additional.getPortTranslationCharge());
        total = total.add(additional.getExportCharge());
        total = total.add(additional.getOceanFreightCharge());
        total = total.add(additional.getMarineInsuranceCharge());
        total = total.add(additional.getArrivalAccountingCharge());
        total = total.add(additional.getHandlingCharge());
        total = total.add(additional.getWarehouseCharge());
        total = total.add(additional.getTariff());
        total = total.add(additional.getTax());
        total = total.add(additional.getDuty());
        total = total.add(additional.getShippingCharge());
        total = total.add(additional.getPaletteCharge());
        total = total.add(additional.getWarehouseTranslationCharge());
        total = total.add(additional.getExportPortCharge());
        total = total.add(additional.getFreightSurcharge());
        total = total.add(additional.getPortArrivalCharge());
        return total;
    }
}
