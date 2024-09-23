package com.salesmanager.shop.model.fulfillment;

import com.salesmanager.core.enmus.QcStatusEnums;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import lombok.Data;
import org.checkerframework.checker.units.qual.A;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class QcInfo {


    private Long id;

    /**
     * 交易单id
     */
    private Long orderId;

    /**
     * 商品id
     */
    private Long productId;

    private String videoUrl;

    /**
     * 逗号分隔的商品图片
     */
    private String productImages;


    /**
     * 逗号分隔的包装图片
     */
    private String packagePictures;

    /**
     * 备注
     */
    private String buyerComment;

    /**
     * 卖家code
     */
    private Long merchantStoreCode;

    /**
     * QC状态
     * @see QcStatusEnums
     *
     */
    private String status;

    /**
     * 同意时间
     */
    private Long passedDate;


    private Date dateCreated;

    private Date dateModified;
}
