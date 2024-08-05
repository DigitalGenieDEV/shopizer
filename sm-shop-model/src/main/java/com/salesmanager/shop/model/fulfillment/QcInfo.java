package com.salesmanager.shop.model.fulfillment;

import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
public class QcInfo {


    private Long id;

    private String videoUrl;

    /**
     * 使用逗号分隔，最多4个
     */
    private String productImages;

    private String packagePictures;

    private String buyerComment;

    private Long passedDate;

    private Set<QcInfoHistory> descriptions = new HashSet<>();




    /**
     * 交易单id
     */
    private Long orderId;
}
