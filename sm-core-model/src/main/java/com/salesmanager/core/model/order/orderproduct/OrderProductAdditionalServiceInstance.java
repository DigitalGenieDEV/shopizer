package com.salesmanager.core.model.order.orderproduct;


import com.salesmanager.core.enmus.AdditionalServiceInstanceStatusEnums;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@EntityListeners(value = AutoCloseable.class)
@Table(name = "ORDER_PRODUCT_ADDITIONAL_SERVICE_INSTANCE",
    uniqueConstraints = @UniqueConstraint(columnNames = {"ORDER_PRODUCT_ID", "ADDITIONAL_SERVICE_ID"}))
public class OrderProductAdditionalServiceInstance extends SalesManagerEntity<Long, OrderProductAdditionalServiceInstance> implements Auditable {

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "ADDITIONAL_SERVICE_INSTANCE_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Long id;

    @Column(name = "ORDER_PRODUCT_ID", unique = true, nullable = false)
    private Long orderProductId;


    @Column(name = "ADDITIONAL_SERVICE_ID", unique = true, nullable = false)
    private Long additionalServiceId;

    @Column(name = "CONTENT", length = 4096)
    private String content;

    @Column(name = "ADDITIONAL_FILENAME")
    private String additionalFilename;

    @Column(name = "ADDITIONAL_FILE_URL")
    private String additionalFileUrl;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private AdditionalServiceInstanceStatusEnums status;

    @Embedded
    private AuditSection auditSection = new AuditSection();

}
