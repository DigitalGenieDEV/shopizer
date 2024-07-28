package com.salesmanager.core.model.system;

import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.shipping.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Merchant configuration information
 *
 */
@Entity
@EntityListeners(AuditListener.class)
@Table(name = "MERCHANT_SHIPPING_CONFIGURATION")
@Getter
@Setter
@NoArgsConstructor
public class MerchantShippingConfiguration extends SalesManagerEntity<Long, MerchantShippingConfiguration>
        implements Serializable, Auditable {

	private static final long serialVersionUID = 4246917986731953459L;

	@Id
	@Column(name = "MERCHANT_SHIPPING_CONFIG_ID")
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME",
					valueColumnName = "SEQ_COUNT", pkColumnValue = "MERCH_SHIP_CONF_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MERCHANT_ID", nullable = true)
	private MerchantStore merchantStore;

	@Embedded
	private AuditSection auditSection = new AuditSection();

	@Column(name = "NAME")
	private String name;

	@Column(name = "CONFIG_KEY")
	private String key;

	/**
	 * activate and deactivate configuration
	*/
	@Column(name = "ACTIVE")
	private boolean active;
	
	@Column(name = "DEFAULT_SHIPPING")
	private boolean defaultShipping;

	@Column(name = "VALUE")
	@Lob
	private String value;

	@Column(name = "SHIPPING_TYPE")
	private String shippingType;
	
	@Column(name = "SHIPPING_WAY")
	private String shippingWay;

	@Enumerated(EnumType.STRING)
	@Column(name = "SHIPPING_BASIS_TYPE")
	private ShippingBasisType shippingBasisType = ShippingBasisType.SHIPPING;
	
	@Column(name = "RETURN_ENABLED")
	private boolean returnEnabled;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "SHIPPING_PACKAGE_TYPE")
	private ShippingPackageType shippingPackageType;

	@Column(name = "TRANSPORTATION_METHOD")
	private String transportationMethod;

	@Enumerated(EnumType.STRING)
	@Column(name = "SHIPPING_OPTION_PRICE_TYPE")
	private ShippingOptionPriceType shippingOptionPriceType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SHIPPING_ORIGIN_ID")
	private ShippingOrigin shippingOrigin;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RETURN_SHIPPING_ORIGIN_ID")
	private ShippingOrigin returnShippingOrigin;

	@Column(name = "FREE_SHIPPING_ENABLED")
	private boolean freeShippingEnabled;

	@Column(name = "ORDER_TOTAL_FREE_SHIPPING")
	private String orderTotalFreeShipping;

	@Column(name = "RETURN_SHIPPING_PRICE")
	private String returnShippingPrice;

}
