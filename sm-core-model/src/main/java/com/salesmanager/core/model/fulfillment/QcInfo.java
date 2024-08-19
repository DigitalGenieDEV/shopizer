package com.salesmanager.core.model.fulfillment;

import com.salesmanager.core.enmus.AdditionalServiceEnums;
import com.salesmanager.core.enmus.QcStatusEnums;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "QC_INFO")
public class QcInfo extends SalesManagerEntity<Long, QcInfo> implements Auditable {


    @Id
    @Column(name = "QC_INFO_ID", unique=true, nullable=false)
    @TableGenerator(
            name = "TABLE_GEN",
            table = "SM_SEQUENCER",
            pkColumnName = "SEQ_NAME",
            valueColumnName = "SEQ_COUNT",
            pkColumnValue = "QC_INFO_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Long id;

    /**
     * 交易单id
     */
    @Column(name = "ORDER_ID")
    private Long orderId;


    /**
     * 增值服务Id list列表用逗号分隔
     * @see AdditionalServiceEnums
     */
    @Column(name = "ADDITIONAL_SERVICES_IDS")
    private String additionalServicesIds;

    /**
     * 商品id
     */
    @Column(name = "PRODUCT_ID")
    private Long productId;



    @Column(name = "VIDEO_URL")
    private String videoUrl;


    /**
     * 逗号分隔的商品图片
     */
    @Column(name = "PRODUCT_IMAGES", length = 5000 )
    private String productImages;

    /**
     * 逗号分隔的包装图片
     */
    @Column(name = "PACKAGE_PICTURES", length = 5000 )
    private String packagePictures;


    @Column(name = "BUYER_COMMENT", length = 5000)
    private String buyerComment;


    /**
     * 卖家code
     */
    @Column(name = "MERCHANT_STORE_CODE")
    private String merchantStoreCode;

    /**
     * QC状态
     *
     */
    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private QcStatusEnums status;

    /**
     * 同意时间
     */
    @Column(name = "PASSED_DATE")
    private Date passedDate;

    @Embedded
    private AuditSection auditSection = new AuditSection();

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public AuditSection getAuditSection() {
        return auditSection;
    }

    @Override
    public void setAuditSection(AuditSection auditSection) {
        this.auditSection = auditSection;
    }

}
