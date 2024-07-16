package com.salesmanager.core.business.utils;


import com.google.gson.Gson;
import com.salesmanager.core.business.modules.AnnouncementInfo;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class ExcelToJson {


    public static void main(String[] args) throws IOException {
        FileInputStream file = new FileInputStream("/Users/yuguanghui/Downloads/2222.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(0);

        Map<String, List<AnnouncementInfo>> announcementInfoMap = new HashMap<>(20);

        for (Row row : sheet) {
            String category = null;
            AnnouncementInfo announcementInfo = new AnnouncementInfo();
            List<AnnouncementInfo.AnnouncementField> announcementFields = new ArrayList<>();

            for (Cell cell : row) {
                String cellValue = getCellValue(cell);
                String cellComment = getCellComment(cell);
                AnnouncementInfo.AnnouncementField announcementField = new AnnouncementInfo.AnnouncementField();

                if (cell.getColumnIndex() == 0 && StringUtils.isNotBlank(cellValue)) {
                    // 第一个 field 作为类目，用于 map 的键
                    category = cellValue;
                    continue;
                }

                if (StringUtils.isNotBlank(cellValue)) {
                    announcementField.setField(cellValue);
                    if (cellComment != null) {
                        announcementField.setComment(cellComment);
                    }
                    announcementFields.add(announcementField);
                }
            }

            if (category != null) {
                announcementInfo.setAnnouncementFields(announcementFields);
                announcementInfoMap.computeIfAbsent(category, k -> new ArrayList<>()).add(announcementInfo);
            }
        }

        Gson gson = new Gson();
        String json = gson.toJson(announcementInfoMap);
        System.out.println(json);

        // 将JSON写入文件
//        try (FileOutputStream fos = new FileOutputStream("/path/to/output/file.json")) {
//            fos.write(json.getBytes());
//        }
//        System.out.println("Excel data converted to JSON successfully.");
//
//        workbook.close();
//        file.close();
    }

    private static String getCellValue(Cell cell) {
        switch (cell.getCellTypeEnum()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return Double.toString(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return Boolean.toString(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "";
        }
    }

    private static String getCellComment(Cell cell) {
        Comment comment = cell.getCellComment();
        if (comment != null) {
            return comment.getString().getString();
        }
        return null;
    }
}