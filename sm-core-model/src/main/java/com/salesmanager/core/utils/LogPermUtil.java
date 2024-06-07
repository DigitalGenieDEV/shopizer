package com.salesmanager.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogPermUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogPermUtil.class);

    public static long start(String methodName) {
        long start = System.currentTimeMillis();

        LOGGER.debug("[" + methodName + "] start");

        return start;
    }

    public static void end(String method, long start) {

        LOGGER.debug("[" + method + "] end, cost " + (System.currentTimeMillis() - start) + "ms");
    }
}
