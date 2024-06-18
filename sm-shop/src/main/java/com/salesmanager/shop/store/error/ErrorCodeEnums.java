package com.salesmanager.shop.store.error;

import lombok.Getter;

public enum ErrorCodeEnums {

    SYSTEM_ERROR("00001","system error"),

    PARAM_ERROR("00002","param error"),

    ;


    @Getter
    private String errorCode;

    @Getter
    private String errorMessage;

    ErrorCodeEnums(String errorCode, String errorMessage){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }


}
