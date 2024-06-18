package com.salesmanager.shop.model.shop;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommonResultDTO<T> {
    private T data;
    private Boolean success;
    private String errorCode;
    private String errorMessage;
    private String innerErrorMessage;


    public CommonResultDTO() {}

    public static <T> CommonResultDTO<T> ofSuccess(T data) {
        CommonResultDTO<T> resultDto = new CommonResultDTO<>();
        resultDto.data = data;
        resultDto.success = true;
        return resultDto;
    }

    public static <T> CommonResultDTO<T> ofSuccess() {
        CommonResultDTO<T> resultDto = new CommonResultDTO<>();
        resultDto.success = true;
        return resultDto;
    }

    public static <T> CommonResultDTO<T> ofFailed(String errorCode, String errorMessage) {
        return ofFailed(errorCode, errorMessage, null);
    }

    public static <T> CommonResultDTO<T> ofFailed(String errorCode, String errorMessage, String realErrorMessage) {
        CommonResultDTO<T> resultDto = new CommonResultDTO<>();
        resultDto.errorCode = errorCode;
        resultDto.errorMessage = errorMessage;
        resultDto.innerErrorMessage = realErrorMessage;
        resultDto.success = false;
        return resultDto;
    }
}
