package com.salesmanager.shop.listener.alibaba.tuna.fastjson.parser.deserializer;

import java.lang.reflect.Type;

import com.salesmanager.shop.listener.alibaba.tuna.fastjson.JSONArray;
import com.salesmanager.shop.listener.alibaba.tuna.fastjson.parser.DefaultJSONParser;
import com.salesmanager.shop.listener.alibaba.tuna.fastjson.parser.JSONToken;

public class JSONArrayDeserializer implements ObjectDeserializer {
    public final static JSONArrayDeserializer instance = new JSONArrayDeserializer();

    @SuppressWarnings("unchecked")
    public <T> T deserialze(DefaultJSONParser parser, Type clazz, Object fieldName) {
        JSONArray array = new JSONArray();
        parser.parseArray(array);
        return (T) array;
    }

    public int getFastMatchToken() {
        return JSONToken.LBRACKET;
    }
}
