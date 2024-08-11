package com.salesmanager.shop.mapper.crossorder;

import com.salesmanager.core.model.crossorder.logistics.SupplierCrossOrderLogisticsTrace;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.mapper.Mapper;
import com.salesmanager.shop.model.crossorder.ReadableSupplierCrossOrderLogisticsTrace;
import com.salesmanager.shop.model.crossorder.ReadableSupplierCrossOrderLogisticsTraceStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReadableSupplierCrossOrderLogisticsTraceMapper implements Mapper<SupplierCrossOrderLogisticsTrace, ReadableSupplierCrossOrderLogisticsTrace> {
    @Autowired
    private ReadableSupplierCrossOrderLogisticsTraceStepMapper readableSupplierCrossOrderLogisticsTraceStepMapper;

    @Override
    public ReadableSupplierCrossOrderLogisticsTrace convert(SupplierCrossOrderLogisticsTrace source, MerchantStore store, Language language) {
        if (source == null) {
            return null;
        }

        ReadableSupplierCrossOrderLogisticsTrace destination = new ReadableSupplierCrossOrderLogisticsTrace();
        destination.setLogisticsId(source.getLogisticsId());
        destination.setLogisticsBillNo(source.getLogisticsBillNo());
        destination.setCrossOrderId(source.getOrderId());

        if (source.getLogisticsSteps() != null && !source.getLogisticsSteps().isEmpty()) {
            List<ReadableSupplierCrossOrderLogisticsTraceStep> readableSteps = source.getLogisticsSteps().stream()
                    .map(step -> readableSupplierCrossOrderLogisticsTraceStepMapper.convert(step, store, language))
                    .collect(Collectors.toList());
            destination.setLogisticsSteps(readableSteps);
        }

        return destination;
    }

    @Override
    public ReadableSupplierCrossOrderLogisticsTrace merge(SupplierCrossOrderLogisticsTrace source, ReadableSupplierCrossOrderLogisticsTrace destination, MerchantStore store, Language language) {
        if (source == null || destination == null) {
            return null;
        }

        destination.setLogisticsId(source.getLogisticsId());
        destination.setLogisticsBillNo(source.getLogisticsBillNo());
        destination.setCrossOrderId(source.getOrderId());

        if (source.getLogisticsSteps() != null && !source.getLogisticsSteps().isEmpty()) {
            List<ReadableSupplierCrossOrderLogisticsTraceStep> readableSteps = source.getLogisticsSteps().stream()
                    .map(step -> readableSupplierCrossOrderLogisticsTraceStepMapper.convert(step, store, language))
                    .collect(Collectors.toList());
            destination.setLogisticsSteps(readableSteps);
        }

        return destination;
    }
}
