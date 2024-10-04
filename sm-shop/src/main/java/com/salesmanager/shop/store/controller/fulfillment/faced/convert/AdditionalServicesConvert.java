package com.salesmanager.shop.store.controller.fulfillment.faced.convert;

import com.alibaba.fastjson.JSON;
import com.salesmanager.core.business.fulfillment.service.AdditionalServicesService;
import com.salesmanager.core.business.services.order.orderproduct.OrderProductAdditionalServiceInstanceService;
import com.salesmanager.core.business.utils.ObjectConvert;
import com.salesmanager.core.enmus.AdditionalServiceInstanceStatusEnums;
import com.salesmanager.core.model.fulfillment.AdditionalServices;
import com.salesmanager.core.model.order.orderproduct.OrderProductAdditionalServiceInstance;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.fulfillment.AdditionalServicesDescription;
import com.salesmanager.shop.model.fulfillment.ReadableAdditionalServices;
import com.salesmanager.shop.model.fulfillment.ReadableProductAdditionalService;
import com.salesmanager.shop.store.api.v1.order.OrderProductAdditionalServiceInstanceApi;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AdditionalServicesConvert {

    @Autowired
    private AdditionalServicesService additionalServicesService;

    @Autowired
    private OrderProductAdditionalServiceInstanceService orderProductAdditionalServiceInstanceService;

    public  ReadableAdditionalServices convertToReadableAdditionalServices(
            AdditionalServices additionalServices,
            Language language) {
        ReadableAdditionalServices readableAdditionalServices = ObjectConvert.convert(additionalServices, ReadableAdditionalServices.class);

        // 根据语言设置描述
        additionalServices.getDescriptions().forEach(descriptions -> {
            if (language != null && language.getCode().equals(descriptions.getLanguage().getCode())) {
                AdditionalServicesDescription additionalServicesDescription = ObjectConvert.convert(descriptions, AdditionalServicesDescription.class);
                readableAdditionalServices.setDescription(additionalServicesDescription);
            }
        });

        return readableAdditionalServices;
    }



    public  List<ReadableProductAdditionalService> convertToReadableAdditionalServicesByShoppingItem(
            String additionalServicesIdMap,
            Language language, Long orderProductId) {

        if (StringUtils.isEmpty(additionalServicesIdMap)){
            return null;
        }
        Map<String,String> additionalServiceMap =  (Map<String,String>)JSON.parse(additionalServicesIdMap);

        Set<String> additionalServiceSet = additionalServiceMap.keySet();

        return additionalServiceSet.stream().map(additionalService ->{
            ReadableProductAdditionalService readableProductAdditionalService = new ReadableProductAdditionalService();

            AdditionalServices additionalServices = additionalServicesService.queryAdditionalServicesById(Long.valueOf(additionalService));

            if (orderProductId !=null){
                OrderProductAdditionalServiceInstance orderProductAdditionalServiceInstance = orderProductAdditionalServiceInstanceService.queryByOrderProductIdAndAdditionalServiceId(orderProductId, Long.valueOf(additionalService));
                if (orderProductAdditionalServiceInstance !=null){
                    readableProductAdditionalService.setStatus(orderProductAdditionalServiceInstance.getStatus() == null? AdditionalServiceInstanceStatusEnums.WAIT_FOR_WORK.name() : orderProductAdditionalServiceInstance.getStatus().name());
                }
            }

            if (readableProductAdditionalService.getStatus() == null){
                readableProductAdditionalService.setStatus(AdditionalServiceInstanceStatusEnums.WAIT_FOR_WORK.name());
            }

            ReadableAdditionalServices readableAdditionalServices = convertToReadableAdditionalServices(additionalServices, language);
            readableProductAdditionalService.setAdditionalServices(readableAdditionalServices);
            readableProductAdditionalService.setQuantity(Integer.valueOf(additionalServiceMap.get(additionalService)));
            return readableProductAdditionalService;
        }).collect(Collectors.toList());

    }



}
