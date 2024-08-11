package com.salesmanager.shop.mapper.crossorder;

import com.salesmanager.core.model.crossorder.logistics.SupplierCrossOrderLogisticsOrderGoods;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.mapper.Mapper;
import com.salesmanager.shop.model.crossorder.ReadableSupplierCrossOrderLogisticsOrderGoods;
import org.springframework.stereotype.Component;

@Component
public class ReadableSupplierCrossOrderLogisticsOrderGoodsMapper implements Mapper<SupplierCrossOrderLogisticsOrderGoods, ReadableSupplierCrossOrderLogisticsOrderGoods> {
    @Override
    public ReadableSupplierCrossOrderLogisticsOrderGoods convert(SupplierCrossOrderLogisticsOrderGoods source, MerchantStore store, Language language) {
        if (source == null) {
            return null;
        }

        ReadableSupplierCrossOrderLogisticsOrderGoods destination = new ReadableSupplierCrossOrderLogisticsOrderGoods();

        destination.setId(source.getId());
        destination.setLogisticsOrderId(source.getLogisticsOrderId());
        destination.setLogisticsId(source.getLogisticsId());
        destination.setTradeOrderId(source.getTradeOrderId());
        destination.setTradeOrderItemId(source.getTradeOrderItemId());
        destination.setDescription(source.getDescription());
        destination.setQuantity(source.getQuantity());
        destination.setUnit(source.getUnit());
        destination.setProductName(source.getProductName());

        return destination;
    }

    @Override
    public ReadableSupplierCrossOrderLogisticsOrderGoods merge(SupplierCrossOrderLogisticsOrderGoods source, ReadableSupplierCrossOrderLogisticsOrderGoods destination, MerchantStore store, Language language) {
        return null;
    }
}
