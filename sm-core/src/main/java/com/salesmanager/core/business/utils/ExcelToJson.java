package com.salesmanager.core.business.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.salesmanager.core.business.modules.AnnouncementInfo;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class ExcelToJson {

    public static void main(String[] args) {
        String excelFilePath = "/Users/yuguanghui/Desktop/cleandata1688.xlsx"; // Excel 文件路径
        List<Map<String, Object>> keywordList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(new File(excelFilePath));
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // 读取第一个工作表

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // 跳过标题行

                int categoryId = (int) row.getCell(0).getNumericCellValue();
                Integer categoryId1688 = null;

                Cell cell = row.getCell(1);
                if (cell.getCellType() == CellType.NUMERIC.getCode()) {
                    categoryId1688 = (int) cell.getNumericCellValue();
                }

                String keyword = row.getCell(2).getStringCellValue();

                keywordList.add(createKeyword(categoryId, categoryId1688, keyword));
            }

            // 创建最终 JSON 结构
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("beginPage", 0);
            jsonMap.put("country", "ko");
            jsonMap.put("keywordList", keywordList);
            jsonMap.put("pageSize", 100);

            // 转换为 JSON 字符串
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonMap);
            System.out.println(jsonString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Map<String, Object> createKeyword(int categoryId, Integer categoryId1688, String keyword) {
        Map<String, Object> keywordMap = new HashMap<>();
        keywordMap.put("categoryId", categoryId);
        keywordMap.put("categoryId1688", categoryId1688);
        keywordMap.put("keyword", keyword);
        return keywordMap;
    }
}