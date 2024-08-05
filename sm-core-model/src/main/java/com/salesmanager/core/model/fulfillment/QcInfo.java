package com.salesmanager.core.model.fulfillment;

import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import lombok.Data;

import javax.persistence.*;
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

    @Column(name = "CODE")
    private String videoUrl;

    /**
     * 使用逗号分隔，最多4个
     */
    @Column(name = "PRODUCT_IMAGES", length = 5000 )
    private String productImages;

    /**
     *
     */
    @Column(name = "PACKAGE_PICTURES")
    private String packagePictures;

    @Column(name = "BUYER_COMMENT", length = 5000)
    private String buyerComment;

    @Column(name = "PASSED_DATE")
    private Long passedDate;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "qcInfo")
    private Set<QcInfoHistory> descriptions = new HashSet<>();

    /**
     * 交易单id
     */
    @Column(name = "ORDER_ID")
    private Long orderId;

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
