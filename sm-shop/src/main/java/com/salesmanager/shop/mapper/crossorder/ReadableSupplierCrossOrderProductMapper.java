package com.salesmanager.shop.mapper.crossorder;

import com.salesmanager.core.model.crossorder.SupplierCrossOrderProduct;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.mapper.Mapper;
import com.salesmanager.shop.model.crossorder.ReadableSupplierCrossOrderProduct;
import org.springframework.stereotype.Component;

@Component
public class ReadableSupplierCrossOrderProductMapper implements Mapper<SupplierCrossOrderProduct, ReadableSupplierCrossOrderProduct> {
    @Override
    public ReadableSupplierCrossOrderProduct convert(SupplierCrossOrderProduct source, MerchantStore store, Language language) {
        return null;
    }

    @Override
    public ReadableSupplierCrossOrderProduct merge(SupplierCrossOrderProduct source, ReadableSupplierCrossOrderProduct destination, MerchantStore store, Language language) {
        return null;
    }
}
