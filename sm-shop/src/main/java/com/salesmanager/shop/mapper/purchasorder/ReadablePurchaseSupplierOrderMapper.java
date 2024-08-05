package com.salesmanager.shop.mapper.purchasorder;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.purchaseorder.PurchaseSupplierOrder;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.mapper.Mapper;
import com.salesmanager.shop.model.purchaseorder.ReadablePurchaseSupplierOrder;
import org.springframework.stereotype.Component;

@Component
public class ReadablePurchaseSupplierOrderMapper implements Mapper<PurchaseSupplierOrder, ReadablePurchaseSupplierOrder> {
    @Override
    public ReadablePurchaseSupplierOrder convert(PurchaseSupplierOrder source, MerchantStore store, Language language) {
        return null;
    }

    @Override
    public ReadablePurchaseSupplierOrder merge(PurchaseSupplierOrder source, ReadablePurchaseSupplierOrder destination, MerchantStore store, Language language) {
        return null;
    }
}
