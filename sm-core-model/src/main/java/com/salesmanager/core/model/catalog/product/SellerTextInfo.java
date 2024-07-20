package com.salesmanager.core.model.catalog.product;

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
@Table(name = "SELLER_TEXT_INFO")
public class SellerTextInfo extends SalesManagerEntity<Long, SellerTextInfo> implements Auditable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "SELLER_TEXT_INFO_ID", unique = true, nullable = false)
    @TableGenerator(
            name = "TABLE_GEN",
            table = "SM_SEQUENCER",
            pkColumnName = "SEQ_NAME",
            valueColumnName = "SEQ_COUNT",
            pkColumnValue = "SELLER_TEXT_INFO_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Long id;

    @Embedded
    private AuditSection auditSection = new AuditSection();

    @Column(name = "SELLER_ID", nullable = false, length = 10)
    private Long sellerId;

    /**
     * @see SellerProductShippingTextInfo
     */
    @Column(name = "TEXT", columnDefinition = "LONGTEXT NOT NULL")
    private String text;

    /**
     * @see SellerTextType
     */
    @Column(name = "TYPE", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private SellerTextType type;


    @Override
    public AuditSection getAuditSection() {
        return auditSection;
    }

    @Override
    public void setAuditSection(AuditSection audit) {
        this.auditSection = audit;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }


}