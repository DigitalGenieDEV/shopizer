package com.salesmanager.core.model.order.orderproduct;

import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "ORDER_PRODUCT_DESIGN")
public class OrderProductDesign extends SalesManagerEntity<Long, OrderProductDesign> implements Auditable {

    @Id
    @Column(name = "DESIGN_ID", unique = true, nullable = false)
    @TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "DESIGN_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Long id;

    @OneToOne
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "ORDER_PRODUCT_ID", unique = true, nullable = false)
    private OrderProduct orderProduct;

    @Column(name = "STICKER_DESIGN_IMAGE")
    private String stickerDesignImage;

    @Column(name = "PACKAGE_DESIGN_IMAGE")
    private String packageDesignImage;

    @Column(name = "REMARK")
    private String remark;

    @Embedded
    private AuditSection auditSection = new AuditSection();

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        OrderProductDesign that = (OrderProductDesign) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
