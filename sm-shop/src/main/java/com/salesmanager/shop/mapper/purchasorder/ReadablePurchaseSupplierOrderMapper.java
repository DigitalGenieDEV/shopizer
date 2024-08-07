package com.salesmanager.shop.mapper.purchasorder;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.purchaseorder.PurchaseSupplierOrder;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.mapper.Mapper;
import com.salesmanager.shop.mapper.crossorder.ReadableSupplierCrossOrderMapper;
import com.salesmanager.shop.model.crossorder.ReadableSupplierCrossOrder;
import com.salesmanager.shop.model.purchaseorder.ReadablePurchaseSupplierOrder;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ReadablePurchaseSupplierOrderMapper implements Mapper<PurchaseSupplierOrder, ReadablePurchaseSupplierOrder> {

    @Autowired
    private ReadablePurchaseSupplierOrderProductMapper readablePurchaseSupplierOrderProductMapper;

    @Autowired
    private ReadableSupplierCrossOrderMapper readableSupplierCrossOrderMapper;

    @Override
    public ReadablePurchaseSupplierOrder convert(PurchaseSupplierOrder source, MerchantStore store, Language language) {
        ReadablePurchaseSupplierOrder destination = new ReadablePurchaseSupplierOrder();
        destination.setId(source.getId());
        destination.setReceiverArea(source.getReceiverArea());
        destination.setReceiverCity(source.getReceiverCity());
        destination.setReceiverDivisionCode(source.getReceiverDivisionCode());
        destination.setReceiverFullName(source.getReceiverFullName());
        destination.setReceiverMobile(source.getReceiverMobile());
        destination.setReceiverPhone(source.getReceiverPhone());
        destination.setCurrency(source.getCurrency());
        destination.setStatus(source.getStatus().name());
        destination.setPayStatus(source.getPayStatus().name());
        destination.setCreatedTime(source.getCreatedTime());
        destination.setUpdatedTime(source.getUpdatedTime());
        destination.setOrderTotal(source.getOrderTotal());
        destination.setShippingFee(source.getShippingFee());
        destination.setSupplierEmail(source.getSupplierEmail());
        destination.setSupplierMobile(source.getSupplierMobile());
        destination.setSupplierName(source.getSupplierName());
        destination.setSupplierPhone(source.getSupplierPhone());
        destination.setSupplierCompanyName(source.getSupplierCompanyName());
        destination.setSupplierPhone(source.getSupplierPhone());
        destination.setOrderDateFinished(source.getOrderDateFinished());
        destination.setStatus(source.getStatus().name());

        destination.setOrderProducts(source.getOrderProducts().stream().map(purchaseSupplierOrderProduct -> readablePurchaseSupplierOrderProductMapper.convert(purchaseSupplierOrderProduct, store, language))
                .collect(Collectors.toList()));

        destination.setCrossOrders(source.getCrossOrders().stream().map(supplierCrossOrder -> readableSupplierCrossOrderMapper.convert(supplierCrossOrder, store, language))
                .collect(Collectors.toList()));
        return destination;
    }

    @Override
    public ReadablePurchaseSupplierOrder merge(PurchaseSupplierOrder source, ReadablePurchaseSupplierOrder destination, MerchantStore store, Language language) {
        return null;
    }
}
