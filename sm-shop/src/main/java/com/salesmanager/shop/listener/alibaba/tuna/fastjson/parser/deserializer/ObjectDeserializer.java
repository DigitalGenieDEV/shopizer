package com.salesmanager.shop.listener.alibaba.tuna.fastjson.parser.deserializer;

import java.lang.reflect.Type;

import com.salesmanager.shop.listener.alibaba.tuna.fastjson.parser.DefaultJSONParser;

public interface ObjectDeserializer {
    <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName);
    
    int getFastMatchToken();
}
