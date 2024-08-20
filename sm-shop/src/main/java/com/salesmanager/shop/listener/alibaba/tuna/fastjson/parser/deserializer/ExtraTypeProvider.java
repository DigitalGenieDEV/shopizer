package com.salesmanager.shop.listener.alibaba.tuna.fastjson.parser.deserializer;

import java.lang.reflect.Type;

/**
 *
 * @since 1.1.34
 */
public interface ExtraTypeProvider extends ParseProcess {

    Type getExtraType(Object object, String key);
}
