package com.salesmanager.core.business.utils;

import org.springframework.beans.BeanUtils;

public class ObjectConvert {

    public static <T,R> R convert(T source, Class<R> targetClass, String... ignoreProperties){
        if (source == null){
            return null;
        }
        R targetR = BeanUtils.instantiateClass(targetClass);
        BeanUtils.copyProperties(source, targetR);
        return targetR;
    }
}
