package com.salesmanager.core.model.customer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.model.catalog.product.review.ProductReview;
import com.salesmanager.core.model.common.Billing;
import com.salesmanager.core.model.common.CompanyAddress;
import com.salesmanager.core.model.common.CredentialsReset;
import com.salesmanager.core.model.common.Delivery;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.customer.attribute.CustomerAttribute;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.term.CustomerTerms;
import com.salesmanager.core.model.user.Group;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CUSTOMER", 
	 uniqueConstraints=
			@UniqueConstraint(columnNames = {"MERCHANT_ID", "CUSTOMER_NICK"}))
@Getter
@Setter
public class Customer extends SalesManagerEntity<Long, Customer> implements Auditable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "CUSTOMER_ID", unique=true, nullable=false)
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT",
	pkColumnValue = "CUSTOMER_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;
	
	@JsonIgnore
	@Embedded
	private AuditSection auditSection = new AuditSection();
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "customer")
	private Set<CustomerAttribute> attributes = new HashSet<CustomerAttribute>();
	
	@Column(name="CUSTOMER_GENDER", length=1, nullable=true)
	@Enumerated(value = EnumType.STRING)
	private CustomerGender gender;


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CUSTOMER_DOB")
	private Date dateOfBirth;
	
	@Email
	@NotEmpty
	@Column(name="CUSTOMER_EMAIL_ADDRESS", length=96, nullable=false)
	private String emailAddress;
	
	@Column(name="CUSTOMER_NICK", length=96)
	private String nick;// unique username per store

	@Column(name="CUSTOMER_COMPANY", length=100)
	private String company;
	
	@JsonIgnore
	@Column(name="CUSTOMER_PASSWORD", length=60)
	private String password;

	@Column(name="CUSTOMER_ANONYMOUS")
	private boolean anonymous;
	
	@Column(name = "REVIEW_AVG")
	private BigDecimal customerReviewAvg;

	@Column(name = "REVIEW_COUNT")
	private Integer customerReviewCount;
	
	@Column(name="PROVIDER")
	private String provider;
	
	@Column(name="BUSINESS_NUMBER")
	private String businessNumber;
	
	@Column(name="BUSINESS_REGISTRATION")
	private String businessRegistration;
	
	@Column(name="WITHDRAWAL_REASON", length = 255, nullable=true)
	@ElementCollection(fetch = FetchType.LAZY)
	private Set<String> withdrawalReason;
	
	@Column(name="WITHDRAWAL_REASON_DETAIL", length = 255, nullable=true)
	private String withdrawalResonDetail;
	
	@Column(name="COMPANY_CLEARANCE", length = 255, nullable=true)
	private String companyClearance;
	
	@Column(name="PERSONAL_CLEARANCE", length = 255, nullable=true)
	private String personalClearance;
	
	@Column(name="WTIHDRAWAL_AT")
	private LocalDateTime withdrawalAt = null;
	
	@Embedded
	private CompanyAddress companyAddress;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", targetEntity = CustomerTerms.class, cascade = CascadeType.ALL)
	private Set<CustomerTerms> customerTerms = new HashSet<>();

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Language.class)
	@JoinColumn(name = "LANGUAGE_ID", nullable=false)
	private Language defaultLanguage;
	
	@OneToMany(mappedBy = "customer", targetEntity = ProductReview.class)
	private List<ProductReview> reviews = new ArrayList<ProductReview>();
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="MERCHANT_ID", nullable=false)
	private MerchantStore merchantStore;
	
	@Embedded
	private Delivery delivery = null;
	
	@Valid
	@Embedded
	private Billing billing = null;
	
	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY, cascade = {CascadeType.REFRESH})
	@JoinTable(name = "CUSTOMER_GROUP", joinColumns = { 
			@JoinColumn(name = "CUSTOMER_ID", nullable = false, updatable = false) }
			, 
			inverseJoinColumns = { @JoinColumn(name = "GROUP_ID", 
					nullable = false, updatable = false) }
	)
	@Cascade({
		org.hibernate.annotations.CascadeType.DETACH,
		org.hibernate.annotations.CascadeType.LOCK,
		org.hibernate.annotations.CascadeType.REFRESH,
		org.hibernate.annotations.CascadeType.REPLICATE
		
	})
	private Set<Group> groups = new HashSet<Group>();
	
	@JsonIgnore
	@Transient
	private String showCustomerStateList;
	
	@JsonIgnore
	@Transient
	private String showBillingStateList;
	
	@JsonIgnore
	@Transient
	private String showDeliveryStateList;

	@Embedded
	private CredentialsReset credentialsResetRequest = null;

	public Customer() {
	}
}
