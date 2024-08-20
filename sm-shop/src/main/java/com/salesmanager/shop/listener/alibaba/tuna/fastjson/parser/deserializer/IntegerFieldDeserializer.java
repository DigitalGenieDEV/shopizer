package com.salesmanager.shop.listener.alibaba.tuna.fastjson.parser.deserializer;

import java.lang.reflect.Type;
import java.util.Map;

import com.salesmanager.shop.listener.alibaba.tuna.fastjson.parser.DefaultJSONParser;
import com.salesmanager.shop.listener.alibaba.tuna.fastjson.parser.JSONLexer;
import com.salesmanager.shop.listener.alibaba.tuna.fastjson.parser.JSONToken;
import com.salesmanager.shop.listener.alibaba.tuna.fastjson.parser.ParserConfig;
import com.salesmanager.shop.listener.alibaba.tuna.fastjson.util.FieldInfo;
import com.salesmanager.shop.listener.alibaba.tuna.fastjson.util.TypeUtils;

public class IntegerFieldDeserializer extends FieldDeserializer {

    public IntegerFieldDeserializer(ParserConfig mapping, Class<?> clazz, FieldInfo fieldInfo){
        super(clazz, fieldInfo);
    }

    @Override
    public void parseField(DefaultJSONParser parser, Object object, Type objectType, Map<String, Object> fieldValues) {
        Integer value;

        final JSONLexer lexer = parser.getLexer();
        if (lexer.token() == JSONToken.LITERAL_INT) {
            int val = lexer.intValue();
            lexer.nextToken(JSONToken.COMMA);
            if (object == null) {
                fieldValues.put(fieldInfo.getName(), val);
            } else {
                setValue(object, val);
            }
            return;
        } else if (lexer.token() == JSONToken.NULL) {
            value = null;
            lexer.nextToken(JSONToken.COMMA);
        } else {
            Object obj = parser.parse();

            value = TypeUtils.castToInt(obj);
        }

        if (value == null && getFieldClass() == int.class) {
            // skip
            return;
        }

        if (object == null) {
            fieldValues.put(fieldInfo.getName(), value);
        } else {
            setValue(object, value);
        }
    }

    public int getFastMatchToken() {
        return JSONToken.LITERAL_INT;
    }
}
