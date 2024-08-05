package com.salesmanager.shop.mapper.purchasorder;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.purchaseorder.PurchaseOrder;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.mapper.Mapper;
import com.salesmanager.shop.model.purchaseorder.ReadablePurchaseOrder;
import org.springframework.stereotype.Component;

@Component
public class ReadablePurchaseOrderMapper implements Mapper<PurchaseOrder, ReadablePurchaseOrder> {
    @Override
    public ReadablePurchaseOrder convert(PurchaseOrder source, MerchantStore store, Language language) {
        return null;
    }

    @Override
    public ReadablePurchaseOrder merge(PurchaseOrder source, ReadablePurchaseOrder destination, MerchantStore store, Language language) {
        return null;
    }
}
