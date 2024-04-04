package com.salesmanager.core.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class UtilsFor1688 {

    public static Map<String, String> convertObjectToMap(Object obj) throws IllegalAccessException {
        Map<String, String> map = new HashMap<>();
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object fieldValue = field.get(obj);
            String fieldValueAsString = (fieldValue != null) ? fieldValue.toString() : null;
            map.put(fieldName, fieldValueAsString);
        }
        return map;
    }

    static class MyObject {
        private String field1;
        private Long field2;

        public MyObject(String field1, Long field2) {
            this.field1 = field1;
            this.field2 = field2;
        }

        public String getField1() {
            return field1;
        }

        public void setField1(String field1) {
            this.field1 = field1;
        }

        public Long getField2() {
            return field2;
        }

        public void setField2(Long field2) {
            this.field2 = field2;
        }
    }
}
