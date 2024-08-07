package com.salesmanager.shop.mapper.crossorder;

import com.salesmanager.core.model.crossorder.SupplierCrossOrder;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.mapper.Mapper;
import com.salesmanager.shop.model.crossorder.ReadableBuyerContact;
import com.salesmanager.shop.model.crossorder.ReadableReceiverInfo;
import com.salesmanager.shop.model.crossorder.ReadableSellerContact;
import com.salesmanager.shop.model.crossorder.ReadableSupplierCrossOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ReadableSupplierCrossOrderMapper implements Mapper<SupplierCrossOrder, ReadableSupplierCrossOrder> {

    @Autowired
    private ReadableSupplierCrossOrderProductMapper readableSupplierCrossOrderProductMapper;
    @Override
    public ReadableSupplierCrossOrder convert(SupplierCrossOrder source, MerchantStore store, Language language) {
        if (source == null) {
            return null;
        }

        ReadableSupplierCrossOrder destination = new ReadableSupplierCrossOrder();
        destination.setId(source.getId());
        destination.setOrderIdStr(source.getOrderIdStr());
        destination.setStatus(source.getStatus());
        destination.setTotalAmount(source.getTotalAmount());
        destination.setDiscount(source.getDiscount());
        destination.setRefund(source.getRefund());
        destination.setShippingFee(source.getShippingFee());
        destination.setSellerId(source.getSellerId());
        destination.setPayTime(source.getPayTime());

        ReadableBuyerContact readableBuyerContact = new ReadableBuyerContact();
        readableBuyerContact.setName(source.getBuyerContact().getName());
        readableBuyerContact.setCompany(source.getBuyerContact().getCompany());
        readableBuyerContact.setPhone(source.getBuyerContact().getPhone());
        readableBuyerContact.setMobile(source.getBuyerContact().getMobile());
        readableBuyerContact.setEmail(source.getBuyerContact().getEmail());

        destination.setBuyerContact(readableBuyerContact);

        ReadableSellerContact readableSellerContact = new ReadableSellerContact();
        readableSellerContact.setName(source.getSellerContact().getName());
        readableSellerContact.setCompanyName(source.getSellerContact().getCompanyName());
        readableSellerContact.setEmail(source.getSellerContact().getEmail());
        readableSellerContact.setPhone(source.getSellerContact().getPhone());
        readableSellerContact.setMobile(source.getSellerContact().getMobile());

        destination.setSellerContact(readableSellerContact);

        ReadableReceiverInfo readableReceiverInfo = new ReadableReceiverInfo();

        readableReceiverInfo.setFullName(source.getReceiverInfo().getFullName());
        readableReceiverInfo.setArea(source.getReceiverInfo().getArea());
        readableReceiverInfo.setMobile(source.getReceiverInfo().getMobile());
        readableReceiverInfo.setDivisionCode(source.getReceiverInfo().getDivisionCode());
        readableReceiverInfo.setPhone(source.getReceiverInfo().getPhone());
        readableReceiverInfo.setPostCode(source.getReceiverInfo().getPostCode());
        readableReceiverInfo.setTownCode(source.getReceiverInfo().getTownCode());

        destination.setReceiverInfo(readableReceiverInfo);

        destination.setRefundStatus(source.getRefundStatus());
        destination.setRefundPayment(source.getRefundPayment());
        destination.setAlipayTradeId(source.getAlipayTradeId());
        destination.setCreatedTime(source.getCreatedTime());
        destination.setUpdatedTime(source.getUpdatedTime());
        destination.setSellerUserId(source.getSellerUserId());
        destination.setSellerLoginId(source.getSellerLoginId());
        destination.setBuyerUserId(source.getBuyerUserId());
        destination.setBuyerLoginId(source.getBuyerLoginId());
        destination.setTradeTypeCode(source.getTradeTypeCode());

        destination.setProducts(source.getProducts().stream()
                .map(supplierCrossOrderProduct -> readableSupplierCrossOrderProductMapper.convert(supplierCrossOrderProduct, store, language))
                .collect(Collectors.toList()));

        return destination;
    }

    @Override
    public ReadableSupplierCrossOrder merge(SupplierCrossOrder source, ReadableSupplierCrossOrder destination, MerchantStore store, Language language) {
        return null;
    }
}
