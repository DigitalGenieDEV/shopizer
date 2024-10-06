package com.salesmanager.core.enmus;

public enum FulfillmentTypeEnums {

    //付款完成
    PAYMENT_COMPLETED,

    //商品准备
    PROCESSED,

    /**
     * 中国本地配送中
     */
    CHINA_LOCAL_DELIVERY,

    ARRIVING_AT_WHW,

    SELLER_QC,

    SELLER_QC_FINISH,

    /**
     * 跨境运输中
     */
    CROSS_BORDER_TRANSPORTATION,

    /**
     * 海关进行中
     */
    CUSTOMS_IN_PROGRESS,

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
