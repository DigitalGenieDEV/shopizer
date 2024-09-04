package com.salesmanager.shop.model.catalog.product.attribute;

import lombok.Data;

import java.util.List;

@Data
public class PersistableAnnouncement {


    @Data
    public static class AnnouncementField{
        private String field;

        private String comment;

        private String value;
    }

    private List<AnnouncementField> announcementFields;

    private Long productId;
    
    private Long announcementId;
}
