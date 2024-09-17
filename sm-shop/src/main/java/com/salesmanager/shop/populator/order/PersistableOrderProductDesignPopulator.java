package com.salesmanager.shop.populator.order;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.orderproduct.OrderProduct;
import com.salesmanager.core.model.order.orderproduct.OrderProductDesign;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.order.PersistableOrderProductDesign;
import org.springframework.stereotype.Component;

@Component
public class PersistableOrderProductDesignPopulator extends AbstractDataPopulator<PersistableOrderProductDesign, OrderProductDesign> {


    @Override
    protected OrderProductDesign createTarget() {
        return new OrderProductDesign();
    }

    @Override
    public OrderProductDesign populate(PersistableOrderProductDesign persistableOrderProductDesign, OrderProductDesign orderProductDesign, MerchantStore store, Language language) throws ConversionException {
        if (orderProductDesign == null) {
            orderProductDesign = new OrderProductDesign();
        }

        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setId(persistableOrderProductDesign.getOrderProductId());
        orderProductDesign.setOrderProduct(orderProduct);
        orderProductDesign.setStickerDesignImage(persistableOrderProductDesign.getStickerDesignImage());
        orderProductDesign.setPackageDesignImage(persistableOrderProductDesign.getPackageDesignImage());
        orderProductDesign.setRemark(persistableOrderProductDesign.getRemark());

        return orderProductDesign;
    }
}
