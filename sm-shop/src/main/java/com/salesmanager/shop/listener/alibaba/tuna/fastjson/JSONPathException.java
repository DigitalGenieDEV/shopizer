package com.salesmanager.shop.listener.alibaba.tuna.fastjson;

public class JSONPathException extends JSONException {

    private static final long serialVersionUID = 1L;

    public JSONPathException(String message){
        super(message);
    }
    
    public JSONPathException(String message, Throwable cause){
        super(message, cause);
    }
}
