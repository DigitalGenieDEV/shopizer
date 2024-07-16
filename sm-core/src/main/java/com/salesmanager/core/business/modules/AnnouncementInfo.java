package com.salesmanager.core.business.modules;

import com.salesmanager.core.business.utils.ExcelToJson;
import lombok.Data;

import java.util.List;

@Data
public class AnnouncementInfo {
    @Data
    public static class AnnouncementField{
        private String field;

        private String comment;
    }

    private List<AnnouncementField> announcementFields;
}
