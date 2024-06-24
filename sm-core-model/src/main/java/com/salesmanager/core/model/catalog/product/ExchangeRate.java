package com.salesmanager.core.model.catalog.product;

import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;


@Data
@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "EXCHANGE_RATE", uniqueConstraints=
@UniqueConstraint(columnNames = {"BASE_CURRENCY", "TARGET_CURRENCY"}))
public class ExchangeRate extends SalesManagerEntity<Long, ExchangeRate> implements Auditable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "EXCHANGE_RATE_ID", unique = true, nullable = false)
    @TableGenerator(
            name = "TABLE_GEN",
            table = "SM_SEQUENCER",
            pkColumnName = "SEQ_NAME",
            valueColumnName = "SEQ_COUNT",
            pkColumnValue = "EXCHANGE_RATE_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Long id;

    @Embedded
    private AuditSection auditSection = new AuditSection();

    @Column(name = "BASE_CURRENCY", nullable = false, length = 10)
    private String baseCurrency;

    @Column(name = "TARGET_CURRENCY", nullable = false, length = 10)
    private String targetCurrency;

    @Column(name = "RATE", nullable = false, precision = 15, scale = 6)
    private BigDecimal rate;


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