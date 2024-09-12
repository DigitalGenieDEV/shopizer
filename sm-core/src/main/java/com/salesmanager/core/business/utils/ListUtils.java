package com.salesmanager.core.business.utils;

public class ListUtils {




    public static int calculateTotalPages(long totalRecords, int count) {
        // 如果 count 小于等于 0，返回 0 作为默认值，避免异常
        if (count <= 0) {
            return 0; // 或者选择其他默认值
        }
        return (int) ((totalRecords + count - 1) / count);
    }
}
