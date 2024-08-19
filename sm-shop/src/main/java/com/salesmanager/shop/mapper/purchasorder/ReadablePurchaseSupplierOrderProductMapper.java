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
        ReadablePurchaseSupplierOrderProduct destination = new ReadablePurchaseSupplierOrderProduct();
        destination.setId(source.getId());
        destination.setProductId(source.getProductId());
        destination.setProductName(source.getProductName());
        destination.setProductImage(source.getProductImage());
        destination.setProductSnapshotUrl(source.getProductSnapshotUrl());
        destination.setQuantity(source.getQuantity());
        destination.setPrice(source.getPrice());
        destination.setUnitPrice(source.getUnitPrice());
        destination.setUnit(source.getUnit());
        destination.setStatus(source.getStatus().name());
        destination.setCreatedTime(source.getCreatedTime());
        destination.setUpdatedTime(source.getUpdatedTime());
        return destination;
    }

    @Override
    public ReadablePurchaseSupplierOrderProduct merge(PurchaseSupplierOrderProduct source, ReadablePurchaseSupplierOrderProduct destination, MerchantStore store, Language language) {
        return null;
    }
}
