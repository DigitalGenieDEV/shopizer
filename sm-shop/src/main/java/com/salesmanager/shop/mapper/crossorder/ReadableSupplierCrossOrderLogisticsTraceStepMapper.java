package com.salesmanager.shop.mapper.crossorder;

import com.salesmanager.core.model.crossorder.logistics.SupplierCrossOrderLogisticsTraceStep;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.mapper.Mapper;
import com.salesmanager.shop.model.crossorder.ReadableSupplierCrossOrderLogisticsTraceStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReadableSupplierCrossOrderLogisticsTraceStepMapper implements Mapper<SupplierCrossOrderLogisticsTraceStep, ReadableSupplierCrossOrderLogisticsTraceStep> {

    @Override
    public ReadableSupplierCrossOrderLogisticsTraceStep convert(SupplierCrossOrderLogisticsTraceStep source, MerchantStore store, Language language) {
        if (source == null) {
            return null;
        }

        ReadableSupplierCrossOrderLogisticsTraceStep destination = new ReadableSupplierCrossOrderLogisticsTraceStep();
        destination.setAcceptTime(source.getAcceptTime());
        destination.setRemark(source.getRemark());
        destination.setId(source.getId());
        return destination;
    }

    @Override
    public ReadableSupplierCrossOrderLogisticsTraceStep merge(SupplierCrossOrderLogisticsTraceStep source, ReadableSupplierCrossOrderLogisticsTraceStep destination, MerchantStore store, Language language) {
        if (source == null || destination == null) {
            return null;
        }

        destination.setAcceptTime(source.getAcceptTime());
        destination.setRemark(source.getRemark());
        destination.setId(source.getId());
        return destination;
    }
}
