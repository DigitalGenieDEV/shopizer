package com.salesmanager.shop.populator.order;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.OrderInvoice;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.order.v1.ReadableOrderInvoice;
import org.springframework.stereotype.Component;

@Component
public class ReadableOrderInvoicePopulator extends AbstractDataPopulator<OrderInvoice, ReadableOrderInvoice> {
    @Override
    protected ReadableOrderInvoice createTarget() {
        return new ReadableOrderInvoice();
    }


    @Override
    public ReadableOrderInvoice populate(OrderInvoice orderInvoice, ReadableOrderInvoice readableOrderInvoice, MerchantStore store, Language language) throws ConversionException {

        if (readableOrderInvoice == null) {
            readableOrderInvoice = new ReadableOrderInvoice();
        }

        if (orderInvoice == null) {
            return readableOrderInvoice;
        }

        readableOrderInvoice.setId(orderInvoice.getId());
        readableOrderInvoice.setInvoiceType(orderInvoice.getInvoiceType().name());
        readableOrderInvoice.setInvoicingMethod(orderInvoice.getInvoicingMethod().name());
        readableOrderInvoice.setTaxType(orderInvoice.getTaxType().name());
        readableOrderInvoice.setInvoicingEmail(orderInvoice.getInvoicingEmail());

        return readableOrderInvoice;
    }
}
