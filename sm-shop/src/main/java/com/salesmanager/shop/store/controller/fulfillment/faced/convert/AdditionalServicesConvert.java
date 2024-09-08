package com.salesmanager.shop.store.controller.fulfillment.faced.convert;

import com.salesmanager.core.business.utils.ObjectConvert;
import com.salesmanager.core.enmus.AdditionalServiceEnums;
import com.salesmanager.core.model.fulfillment.AdditionalServices;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.fulfillment.AdditionalServicesDescription;
import com.salesmanager.shop.model.fulfillment.ReadableAdditionalServices;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdditionalServicesConvert {

    public static ReadableAdditionalServices convertToReadableAdditionalServices(
            AdditionalServices additionalServices,
            Language language,
            Map<Long, Integer> additionalServicesMapFromJson) {

        // 转换基本的 AdditionalServices 到 ReadableAdditionalServices
        ReadableAdditionalServices readableAdditionalServices = ObjectConvert.convert(additionalServices, ReadableAdditionalServices.class);

        // 根据语言设置描述
        additionalServices.getDescriptions().forEach(descriptions -> {
            if (language != null && language.getCode().equals(descriptions.getLanguage().getCode())) {
                AdditionalServicesDescription additionalServicesDescription = ObjectConvert.convert(descriptions, AdditionalServicesDescription.class);
                readableAdditionalServices.setDescription(additionalServicesDescription);
            }
        });

        // 如果 additionalServicesMapFromJson 不为空，则设置数量
        if (additionalServicesMapFromJson != null) {
            readableAdditionalServices.setNum(additionalServicesMapFromJson.get(additionalServices.getId()));
        }

        return readableAdditionalServices;
    }

    // 重载方法，提供不需要 additionalServicesMapFromJson 的情况
    public static ReadableAdditionalServices convertToReadableAdditionalServices(
            AdditionalServices additionalServices,
            Language language) {

        // 调用更全面的方法
        return convertToReadableAdditionalServices(additionalServices, language, null);
    }


}
