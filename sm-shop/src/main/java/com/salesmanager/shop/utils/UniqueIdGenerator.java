package com.salesmanager.shop.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
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


    public static String generateShippingOrderId(long timestamp, int sequenceNumber) {
        // 1. 格式化日期为 yyyyMMdd 格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String dateStr = dateFormat.format(new Date(timestamp));

        // 2. 格式化序列号为两位数（如：03）
        String formattedSequenceNumber = String.format("%02d", sequenceNumber);

        // 3. 拼接生成的 ID
        return "CK" + dateStr + "-" + formattedSequenceNumber;
    }


    // 获取机器唯一ID，可以使用IP地址作为机器ID的来源
    private static String getMachineId() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            // 将IP地址的每个字节拼接起来作为机器ID
            byte[] ipBytes = ip.getAddress();
            StringBuilder machineId = new StringBuilder();
            for (byte b : ipBytes) {
                machineId.append(String.format("%02X", b));
            }
            return machineId.toString();
        } catch (UnknownHostException e) {
            // 如果获取失败，可以使用默认的机器ID
            return "DEFAULT";
        }
    }

    // 生成订单号码方法
    public static String generateOrderNumber() {
        // 前缀
        String prefix = "P";

        // 获取当前日期
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String dateStr = dateFormat.format(new Date());

        // 获取机器ID
        String machineId = getMachineId();

        // 随机数生成器
        Random random = new Random();
        int randomNumber = random.nextInt(9000) + 1000; // 生成四位随机数

        // 组合订单号 (前缀 + 日期 + 机器ID + 随机数)
        return prefix + dateStr + machineId + randomNumber;
    }


    public static void main(String[] args) {
        // 测试生成多个唯一ID
        for (int i = 0; i < 10; i++) {
            System.out.println(generateUniqueId());
        }
    }
}
