package com.salesmanager.shop.mapper.crossorder;

import com.salesmanager.core.model.crossorder.logistics.Receiver;
import com.salesmanager.core.model.crossorder.logistics.Sender;
import com.salesmanager.core.model.crossorder.logistics.SupplierCrossOrderLogistics;
import com.salesmanager.core.model.crossorder.logistics.SupplierCrossOrderLogisticsOrderGoods;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.mapper.Mapper;
import com.salesmanager.shop.model.crossorder.ReadableReceiver;
import com.salesmanager.shop.model.crossorder.ReadableSender;
import com.salesmanager.shop.model.crossorder.ReadableSupplierCrossOrderLogistics;
import com.salesmanager.shop.model.crossorder.ReadableSupplierCrossOrderLogisticsOrderGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReadableSupplierCrossOrderLogisticsMapper implements Mapper<SupplierCrossOrderLogistics, ReadableSupplierCrossOrderLogistics> {

    @Autowired
    private ReadableSupplierCrossOrderLogisticsOrderGoodsMapper readableSupplierCrossOrderLogisticsOrderGoodsMapper;

    @Override
    public ReadableSupplierCrossOrderLogistics convert(SupplierCrossOrderLogistics source, MerchantStore store, Language language) {
        ReadableSupplierCrossOrderLogistics destination = new ReadableSupplierCrossOrderLogistics();

        destination.setLogisticsId(source.getLogisticsId());
        destination.setLogisticsBillNo(source.getLogisticsBillNo());
        destination.setOrderEntryIds(source.getOrderEntryIds());
        destination.setStatus(source.getStatus());
        destination.setLogisticsCompanyId(source.getLogisticsCompanyId());
        destination.setLogisticsCompanyName(source.getLogisticsCompanyName());
        destination.setRemarks(source.getRemarks());
        destination.setServiceFeature(source.getServiceFeature());
        destination.setGmtSystemSend(source.getGmtSystemSend());
        destination.setReceiver(convertReceiver(source.getReceiver()));
        destination.setSender(convertSender(source.getSender()));

        if (source.getLogisticsOrderGoods() != null) {
            List<ReadableSupplierCrossOrderLogisticsOrderGoods> readableGoodsList = new ArrayList<>();
            for (SupplierCrossOrderLogisticsOrderGoods goods : source.getLogisticsOrderGoods()) {
                readableGoodsList.add(readableSupplierCrossOrderLogisticsOrderGoodsMapper.convert(goods, store, language));
            }
            destination.setLogisticsOrderGoods(readableGoodsList);
        }

        return destination;
    }

    private ReadableReceiver convertReceiver(Receiver source) {
        ReadableReceiver receiver = new ReadableReceiver();
        receiver.setReceiverName(source.getReceiverName());
        receiver.setReceiverPhone(source.getReceiverPhone());
        receiver.setReceiverMobile(source.getReceiverMobile());
        receiver.setEncrypt(source.getEncrypt());
        receiver.setReceiverProvinceCode(source.getReceiverProvinceCode());
        receiver.setReceiverCityCode(source.getReceiverCityCode());
        receiver.setReceiverCountyCode(source.getReceiverCountyCode());
        receiver.setReceiverAddress(source.getReceiverAddress());
        receiver.setReceiverProvince(source.getReceiverProvince());
        receiver.setReceiverCity(source.getReceiverCity());
        receiver.setReceiverCounty(source.getReceiverCounty());
        return receiver;
    }

    private ReadableSender convertSender(Sender source) {
        ReadableSender sender = new ReadableSender();
        sender.setSenderName(source.getSenderName());
        sender.setSenderPhone(source.getSenderPhone());
        sender.setSenderMobile(source.getSenderMobile());
        sender.setEncrypt(source.getEncrypt());
        sender.setSenderProvinceCode(source.getSenderProvinceCode());
        sender.setSenderCityCode(source.getSenderCityCode());
        sender.setSenderCountyCode(source.getSenderCountyCode());
        sender.setSenderAddress(source.getSenderAddress());
        sender.setSenderProvince(source.getSenderProvince());
        sender.setSenderCity(source.getSenderCity());
        sender.setSenderCounty(source.getSenderCounty());
        return sender;
    }


    @Override
    public ReadableSupplierCrossOrderLogistics merge(SupplierCrossOrderLogistics source, ReadableSupplierCrossOrderLogistics destination, MerchantStore store, Language language) {
        return null;
    }
}
