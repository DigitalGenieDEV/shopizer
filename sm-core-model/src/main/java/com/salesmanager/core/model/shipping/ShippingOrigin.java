package com.salesmanager.core.model.shipping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import javax.validation.constraints.NotEmpty;

import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.country.Country;
import com.salesmanager.core.model.reference.zone.Zone;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "SHIPING_ORIGIN")
@Getter
@Setter
@NoArgsConstructor
public class ShippingOrigin extends SalesManagerEntity<Long, ShippingOrigin> {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1172536723717691214L;


	@Id
	@Column(name = "SHIP_ORIGIN_ID", unique=true, nullable=false)
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT",
		pkColumnValue = "SHP_ORIG_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;
	
	@Column(name = "ACTIVE")
	private boolean active;
	
	
	@Column(name = "SHIPPING_PACKAGE_TYPE")
	private ShippingPackageType shippingPackageType;
	
	@Enumerated(EnumType.STRING)
	@Column (name ="ORIGIN_TYPE")
	private ShippingOriginType originType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="MERCHANT_ID", nullable=false)
	private MerchantStore merchantStore;
	
	@NotEmpty
	@Column (name ="STREET_ADDRESS", length=256)
	private String address;


	@NotEmpty
	@Column (name ="CITY", length=100)
	private String city;
	
	@NotEmpty
	@Column (name ="POSTCODE", length=20)
	private String postalCode;
	
	@Column (name ="STATE", length=100)
	private String state;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Country.class)
	@JoinColumn(name="COUNTRY_ID", nullable=true)
	private Country country;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Zone.class)
	@JoinColumn(name="ZONE_ID", nullable=true)
	private Zone zone;

	


	
}
