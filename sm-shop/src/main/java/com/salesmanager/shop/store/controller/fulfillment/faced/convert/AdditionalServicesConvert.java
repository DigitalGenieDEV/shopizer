package com.salesmanager.shop.store.controller.fulfillment.faced.convert;

import com.salesmanager.core.business.utils.ObjectConvert;
import com.salesmanager.core.model.fulfillment.AdditionalServices;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.fulfillment.AdditionalServicesDescription;
import com.salesmanager.shop.model.fulfillment.ReadableAdditionalServices;

import java.util.List;
import java.util.stream.Collectors;

public class AdditionalServicesConvert {

    public static ReadableAdditionalServices convertToReadableAdditionalServices(AdditionalServices additionalServices, Language language){
        ReadableAdditionalServices readableAdditionalServices = ObjectConvert.convert(additionalServices, ReadableAdditionalServices.class);
        List<AdditionalServicesDescription> additionalServicesDescriptions = additionalServices.getDescriptions().stream().map(descriptions -> {
            AdditionalServicesDescription additionalServicesDescription =  ObjectConvert.convert(descriptions, AdditionalServicesDescription.class);
            if (language != null && language.getCode().equals(descriptions.getLanguage().getCode())){
                readableAdditionalServices.setDescription(additionalServicesDescription);
            }
            return additionalServicesDescription;

        }).collect(Collectors.toList());

        return readableAdditionalServices;
    }
}
