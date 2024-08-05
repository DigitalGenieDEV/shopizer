package com.salesmanager.shop.mapper.crossorder;

import com.salesmanager.core.model.crossorder.SupplierCrossOrder;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.mapper.Mapper;
import com.salesmanager.shop.model.crossorder.ReadableSupplierCrossOrder;
import org.springframework.stereotype.Component;

@Component
public class ReadableSupplierCrossOrderMapper implements Mapper<SupplierCrossOrder, ReadableSupplierCrossOrder> {
    @Override
    public ReadableSupplierCrossOrder convert(SupplierCrossOrder source, MerchantStore store, Language language) {
        return null;
    }

    @Override
    public ReadableSupplierCrossOrder merge(SupplierCrossOrder source, ReadableSupplierCrossOrder destination, MerchantStore store, Language language) {
        return null;
    }
}
