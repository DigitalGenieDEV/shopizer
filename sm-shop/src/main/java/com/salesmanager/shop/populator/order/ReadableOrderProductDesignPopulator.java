package com.salesmanager.shop.populator.order;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.orderproduct.OrderProductDesign;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.order.ReadableOrderProductDesign;
import org.springframework.stereotype.Component;

@Component
public class ReadableOrderProductDesignPopulator extends AbstractDataPopulator<OrderProductDesign, ReadableOrderProductDesign> {
    @Override
    protected ReadableOrderProductDesign createTarget() {
        return new ReadableOrderProductDesign();
    }

    @Override
    public ReadableOrderProductDesign populate(OrderProductDesign orderProductDesign, ReadableOrderProductDesign readableOrderProductDesign, MerchantStore store, Language language) throws ConversionException {
        if (readableOrderProductDesign == null) {
            readableOrderProductDesign = new ReadableOrderProductDesign();
        }

        readableOrderProductDesign.setId(orderProductDesign.getId());
        readableOrderProductDesign.setOrderProductId(orderProductDesign.getOrderProduct().getId());
        readableOrderProductDesign.setStickerDesignImage(orderProductDesign.getStickerDesignImage());
        readableOrderProductDesign.setPackageDesignImage(orderProductDesign.getPackageDesignImage());
        readableOrderProductDesign.setRemark(orderProductDesign.getRemark());

        return readableOrderProductDesign;
    }
}
