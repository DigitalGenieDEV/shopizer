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
        if (source == null) {
            return null;
        }

        ReadableSupplierCrossOrderProduct destination = new ReadableSupplierCrossOrderProduct();
        destination.setId(source.getId());
        destination.setName(source.getName());
        destination.setProductId(source.getProductId());
        destination.setProductSnapshotUrl(source.getProductSnapshotUrl());
        destination.setItemAmount(source.getItemAmount());
        destination.setQuantity(source.getQuantity());
        destination.setRefund(source.getRefund());
        destination.setSkuId(source.getSkuId());
        destination.setStatus(source.getStatus());
        destination.setSubItemId(source.getSubItemId());
        destination.setSkuInfos(source.getSkuInfos());
        destination.setSpecId(source.getSpecId());
        destination.setStatus(source.getStatusStr());
        destination.setRefundStatus(source.getRefundStatus());
        destination.setLogisticsStatus(source.getLogisticsStatus());
        destination.setGmtCreated(source.getGmtCreated());
        destination.setGmtModified(source.getGmtModified());
        destination.setGmtPayExpireTime(source.getGmtPayExpireTime());
        destination.setRefundId(source.getRefundId());
        destination.setSubItemIdStr(source.getSubItemIdStr());

        return destination;
    }

    @Override
    public ReadableSupplierCrossOrderProduct merge(SupplierCrossOrderProduct source, ReadableSupplierCrossOrderProduct destination, MerchantStore store, Language language) {
        return null;
    }
}
