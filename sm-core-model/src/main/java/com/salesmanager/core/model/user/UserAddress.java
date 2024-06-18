package com.salesmanager.core.model.user;

import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.reference.country.Country;
import com.salesmanager.core.model.reference.zone.Zone;

import javax.persistence.*;

@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "USER_ADDRESS",
		indexes = { @Index(name="USR_ID_IDX", columnList = "USER_ID")})
public class UserAddress extends SalesManagerEntity<Long, UserAddress> implements Auditable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "USER_ADDRESS_ID", unique=true, nullable=false)
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "USER_ADDRESS_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;

	@Column (name ="USER_ID")
	private Long userId;

	@Embedded
	private AuditSection auditSection = new AuditSection();

	@Column (name ="LAST_NAME", length=64)
	private String name;

	@Column (name ="COMPANY", length=100)
	private String company;
	
	@Column (name ="ADDRESS", length=256)
	private String address;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Zone.class)
	@JoinColumn(name="CITY_ID", nullable=true)
	private Zone city;
	
	@Column (name ="POSTCODE", length=200)
	private String postalCode;
	
	@Column (name ="STATE", length=100)
	private String state;
	
	@Column(name="TELEPHONE", length=32)
	private String telephone;

	@Column(name="EMAIL", length=300)
	private String email;


	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Country.class)
	@JoinColumn(name="COUNTRY_ID", nullable=true)
	private Country country;
	

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Zone.class)
	@JoinColumn(name="PROVINCE_ID", nullable=true)
	private Zone province;
	

	@Column(name="IS_DEFAULT")
	private boolean isDefault = true;

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}


	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getTelephone() {
		return telephone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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
	public void setAuditSection(AuditSection audit) {
		auditSection = audit;

	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean aDefault) {
		isDefault = aDefault;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Zone getCity() {
		return city;
	}

	public void setCity(Zone city) {
		this.city = city;
	}

	public Zone getProvince() {
		return province;
	}

	public void setProvince(Zone province) {
		this.province = province;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
