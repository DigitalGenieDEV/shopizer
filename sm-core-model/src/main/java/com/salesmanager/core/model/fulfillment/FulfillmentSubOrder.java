package com.salesmanager.core.model.fulfillment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.enmus.*;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.shipping.ShippingTransportationType;
import com.salesmanager.core.model.shipping.ShippingType;
import com.salesmanager.core.model.shipping.TransportationMethod;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "FULFILLMENT_SUB_ORDER")
public class FulfillmentSubOrder extends SalesManagerEntity<Long, FulfillmentSubOrder> implements Auditable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "FULFILLMENT_SUB_ORDER_ID", unique=true, nullable=false)
    @TableGenerator(
            name = "TABLE_GEN",
            table = "SM_SEQUENCER",
            pkColumnName = "SEQ_NAME",
            valueColumnName = "SEQ_COUNT",
            pkColumnValue = "FULFILLMENT_SUB_ORDER_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Long id;

    /**
     * 交易单id
     */
    @Column(name = "ORDER_ID")
    private Long orderId;


    /**
     * 交易单商品id
     */
    @Column(name = "ORDER_PRODUCT_ID")
    private Long orderProductId;

    /**
     * 国内运输还是国外运输
     */
    @Column(name = "SHIPPING_TYPE")
    @Enumerated(EnumType.STRING)
    private ShippingType shippingType;

    /**
     * 国际运输方式
     * @see TransportationMethod
     */
    @Column(name = "INTERNATIONAL_TRANSPORTATION_METHOD")
    @Enumerated(EnumType.STRING)
    private TransportationMethod internationalTransportationMethod;

    /**
     * 国内运输方式
     * @see TransportationMethod
     */
    @Column(name = "NATIONAL_TRANSPORTATION_METHOD")
    @Enumerated(EnumType.STRING)
    private TransportationMethod nationalTransportationMethod;


    /**
     * 委托配送还是自提
     */
    @Column(name = "SHIPPING_TRANSPORTATION_TYPE")
    @Enumerated(EnumType.STRING)
    private ShippingTransportationType shippingTransportationType;



    /**
     * 货车型号
     * @see TruckModelEnums
     */
    @Column(name = "TRUCK_MODEL")
    @Enumerated(EnumType.STRING)
    private TruckModelEnums truckModel;


    /**
     * 货车类型
     * @see TruckTypeEnums
     */
    @Column(name = "TRUCK_TYPE")
    @Enumerated(EnumType.STRING)
    private TruckTypeEnums truckType;


    /**
     * 1688物流单号
     */
    @Column(name = "LOGISTICS_NUMBER_1688")
    private String logisticsNumberBy1688;

    /**
     * 物流单号
     */
    @Column(name = "LOGISTICS_NUMBER")
    private String logisticsNumber;


    /**
     * 物流公司
     */
    @Column(name = "NATIONAL_LOGISTICS_COMPANY")
    private String nationalLogisticsCompany;


    /**
     * 发货时间
     */
    @Column(name = "NATIONAL_SHIPPING_TIME")
    private Date nationalShippingTime;

    /**
     * 运输信息
     */
    @Column(name = "TRANSPORT_INFORMATION")
    private String  transportInformation;


    @Column(name = "NATIONAL_DRIVER_NAME")
    private String nationalDriverName;


    @Column(name = "NATIONAL_DRIVER_PHONE")
    private String nationalDriverPhone;

    /**
     * 跨境运输单号
     */
    @Column(name = "CROSS_BORDER_TRANSPORTATION_LOGISTICS_NUMBER")
    private String crossBorderTransportationLogisticsNumber;



    /**
     * 送货单
     */
    @Column(name = "DELIVERY_ORDER")
    private String  deliveryOrder;


    /**
     * 履约子单主状态
     */
    @Column(name = "FULFILLMENT_MAIN_TYPE")
    @Enumerated(EnumType.STRING)
    private FulfillmentTypeEnums fulfillmentMainType;


    /**
     * 履约子单子状态
     */
    @Column(name = "FULFILLMENT_SUB_TYPE")
    @Enumerated(EnumType.STRING)
    private FulfillmentSubTypeEnums fulfillmentSubTypeEnums;


    /**
     * 增值服务Id list列表用逗号分隔
     * @see AdditionalServiceEnums
     */
    @Column(name = "ADDITIONAL_SERVICES_IDS")
    private String additionalServicesMap;



    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ManyToOne(targetEntity = FulfillmentMainOrder.class)
    @JoinColumn(name = "FULFILLMENT_MAIN_ORDER_ID", nullable = false)
    private FulfillmentMainOrder fulfillmentMainOrder;


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
