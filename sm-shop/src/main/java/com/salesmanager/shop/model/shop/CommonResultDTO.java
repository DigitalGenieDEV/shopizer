package com.salesmanager.shop.model.shop;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("通用resutlDTO")
public class CommonResultDTO<T> {
    @ApiModelProperty(value = "返回值",required = false,hidden = false)
    private T data;
    @ApiModelProperty(value = "是否成功",required = false,hidden = false)
    private Boolean success;
    @ApiModelProperty(value = "错误吗",required = false,hidden = false)
    private String errorCode;
    @ApiModelProperty(value = "错误信息",required = false,hidden = false)
    private String errorMessage;
    /** 前端不处理这个字段 */
    @ApiModelProperty(value = "内部错误信息",required = false,hidden = false)
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
