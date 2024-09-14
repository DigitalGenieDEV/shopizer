package com.salesmanager.shop.populator.order;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.InvoiceType;
import com.salesmanager.core.model.order.InvoicingMethod;
import com.salesmanager.core.model.order.OrderInvoice;
import com.salesmanager.core.model.order.TaxType;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.order.v1.PersistableOrderInvoice;
import org.springframework.stereotype.Component;

@Component
public class PersistableOrderInvoicePopulator extends AbstractDataPopulator<PersistableOrderInvoice, OrderInvoice> {
    @Override
    protected OrderInvoice createTarget() {
        return new OrderInvoice();
    }

    @Override
    public OrderInvoice populate(PersistableOrderInvoice persistableOrderInvoice, OrderInvoice orderInvoice, MerchantStore store, Language language) throws ConversionException {
        if (orderInvoice == null) {
            orderInvoice = new OrderInvoice();
        }

        orderInvoice.setInvoiceType(InvoiceType.valueOf(persistableOrderInvoice.getInvoiceType()));
        orderInvoice.setInvoicingMethod(InvoicingMethod.valueOf(persistableOrderInvoice.getInvoicingMethod()));
        orderInvoice.setTaxType(TaxType.valueOf(persistableOrderInvoice.getTaxType()));
        orderInvoice.setInvoicingEmail(persistableOrderInvoice.getInvoicingEmail());

        return orderInvoice;
    }
}
