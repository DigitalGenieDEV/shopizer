package com.salesmanager.test.shop.common;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author shirukai
 */
public class EnvUtils {
    public static void setEnv(String name, String value) throws Exception {
        getModifiableEnvironment().put(name, value);
    }

    /**
     * 通过反射的方式从Runtime中获取存储环境变量的Map，返回的Map是可变的
     * Copy from https://stackoverflow.com/questions/580085/is-it-possible-to-set-an-environment-variable-at-runtime-from-java
     *
     * @return Map<String, String></String,String>
     * @throws Exception e
     */
    @SuppressWarnings("unchecked")
    private static Map<String, String> getModifiableEnvironment() throws Exception {
        Class<?> pe = Class.forName("java.lang.ProcessEnvironment");
        Method getenv = pe.getDeclaredMethod("getenv");
        getenv.setAccessible(true);
        Object unmodifiableEnvironment = getenv.invoke(null);
        Class<?> map = Class.forName("java.util.Collections$UnmodifiableMap");
        Field m = map.getDeclaredField("m");
        m.setAccessible(true);
        return (Map<String, String>) m.get(unmodifiableEnvironment);
    }

    public static void main(String[] args) throws Exception {
        EnvUtils.setEnv("TEST_SET_ENV","test-set-env");
        System.out.println(System.getenv("TEST_SET_ENV"));
    }
}


