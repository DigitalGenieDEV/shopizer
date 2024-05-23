package com.salesmanager.shop.utils;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class UniqueIdGenerator {

    private static final String PREFIX = "Qkr";
    private static final int RANDOM_NUMBER_LENGTH = 4;
    private static final Random RANDOM = new SecureRandom();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    public static synchronized String generateUniqueId() {
        // 获取当前时间戳
        String timestamp = DATE_FORMAT.format(new Date());

        // 生成指定长度的随机数字
        StringBuilder randomNumbers = new StringBuilder();
        for (int i = 0; i < RANDOM_NUMBER_LENGTH; i++) {
            randomNumbers.append(RANDOM.nextInt(10));
        }

        // 组合前缀、时间戳和随机数生成唯一ID
        return PREFIX + timestamp + randomNumbers.toString();
    }

    public static void main(String[] args) {
        // 测试生成多个唯一ID
        for (int i = 0; i < 10; i++) {
            System.out.println(generateUniqueId());
        }
    }
}
