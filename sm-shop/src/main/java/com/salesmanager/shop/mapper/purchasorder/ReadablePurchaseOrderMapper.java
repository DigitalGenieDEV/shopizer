package com.salesmanager.shop.mapper.purchasorder;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.purchaseorder.PurchaseOrder;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.mapper.Mapper;
import com.salesmanager.shop.model.purchaseorder.ReadablePurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ReadablePurchaseOrderMapper implements Mapper<PurchaseOrder, ReadablePurchaseOrder> {

    @Autowired
    private ReadablePurchaseSupplierOrderMapper readablePurchaseSupplierOrderMapper;

    @Override
    public ReadablePurchaseOrder convert(PurchaseOrder source, MerchantStore store, Language language) {
        if (source == null) {
            return null;
        }
        ReadablePurchaseOrder destination = new ReadablePurchaseOrder();
        destination.setId(source.getId());
        destination.setChannel(source.getChannel());
        destination.setDateFinished(source.getDateFinished());
        destination.setCurrency(source.getCurrency());
        destination.setStatus(source.getStatus().name());
        destination.setCreatedTime(source.getCreatedTime());
        destination.setUpdatedTime(source.getUpdatedTime());

        destination
                .setPurchaseSupplierOrders(source.getPurchaseSupplierOrders().stream()
                        .map(purchaseSupplierOrder -> readablePurchaseSupplierOrderMapper.convert(purchaseSupplierOrder, store, language)).collect(Collectors.toList()));
        return destination;
    }

    @Override
    public ReadablePurchaseOrder merge(PurchaseOrder source, ReadablePurchaseOrder destination, MerchantStore store, Language language) {
        return null;
    }
}
