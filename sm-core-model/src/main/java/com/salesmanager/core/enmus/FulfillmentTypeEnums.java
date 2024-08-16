package com.salesmanager.core.enmus;

public enum FulfillmentTypeEnums {

    //付款完成
    PAYMENT_COMPLETED,

    //商品准备
    PROCESSED,

    ARRIVING_AT_WHW,

    CROSS_BORDER_TRANSPORTATION,

    ARRIVING_AT_ICW,

    //配送中
    DELIVERING,

    //开始配送
    START_DELIVERY,

    //配送完毕
    DELIVERY_COMPLETED,

    /**
     * 确认签收
     */
    CONFIRM_RECEIPT,
    ;


    public static FulfillmentTypeEnums fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        for (FulfillmentTypeEnums type : FulfillmentTypeEnums.values()) {
            if (type.name().equals(value.trim())) {
                return type;
            }
        }
        return null;
    }

}
