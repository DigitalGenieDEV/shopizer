package com.salesmanager.shop.mapper.purchasorder;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.purchaseorder.PurchaseSupplierOrderProduct;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.mapper.Mapper;
import com.salesmanager.shop.model.purchaseorder.ReadablePurchaseSupplierOrderProduct;
import org.springframework.stereotype.Component;

@Component
public class ReadablePurchaseSupplierOrderProductMapper implements Mapper<PurchaseSupplierOrderProduct, ReadablePurchaseSupplierOrderProduct> {
    @Override
    public ReadablePurchaseSupplierOrderProduct convert(PurchaseSupplierOrderProduct source, MerchantStore store, Language language) {
        return null;
    }

    @Override
    public ReadablePurchaseSupplierOrderProduct merge(PurchaseSupplierOrderProduct source, ReadablePurchaseSupplierOrderProduct destination, MerchantStore store, Language language) {
        return null;
    }
}
