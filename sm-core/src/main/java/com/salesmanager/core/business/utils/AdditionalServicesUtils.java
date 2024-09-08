package com.salesmanager.core.business.utils;

import com.salesmanager.core.enmus.AdditionalServiceEnums;
import com.salesmanager.core.model.fulfillment.AdditionalServices;

public class AdditionalServicesUtils {



    public static String getPrice(AdditionalServices additionalServices, Integer additionalServicesQuantity, Integer itemQuantity){
        String price = "0";
        if (AdditionalServiceEnums.QUANTITY_CONFIRMATION.name().equals(additionalServices.getCode())){
            price = buildAdditionalSpecialPrice(itemQuantity, additionalServices.getPrice());
        }else {
            price = buildAdditionalPrice(additionalServicesQuantity, additionalServices.getPrice());
        }
        return price;
    }



    public static String buildAdditionalSpecialPrice(Integer itemQuantity, String price) {
        // 当数量少于等于100时，价格为0
        if (itemQuantity <= 100) {
            return "0";
        }

        // 基础价格
        int basePriceInt = Integer.parseInt(price);

        // 计算每增加100件的数量块数（超过100的部分）
        int additionalUnits = (int) Math.ceil((itemQuantity - 100) / 100.0);

        // 每增加100件，增加2000韩元
        int priceIncrement = additionalUnits * basePriceInt;

        // 最终价格
        return String.valueOf(priceIncrement);
    }



    public static String buildAdditionalPrice(Integer additionalServicesQuantity, String price) {
        if (additionalServicesQuantity == 0 || "0".equals(price)){
            return "0";
        }
        int basePriceInt = Integer.parseInt(price);
        return String.valueOf(additionalServicesQuantity * basePriceInt);
    }
}
