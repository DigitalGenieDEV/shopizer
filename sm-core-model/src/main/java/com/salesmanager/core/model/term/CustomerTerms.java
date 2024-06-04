package com.salesmanager.core.model.term;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.generic.SalesManagerEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "CUSTOMER_TERMS")
public class CustomerTerms extends SalesManagerEntity<Long, CustomerTerms>{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CUSTOMER_TERMS_ID", unique = true, nullable = false)
	@TableGenerator(
			name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "CUSTOMER_TERMS_SEQ_NEXT_VAL"
	)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;
/*
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Terms.class)
	@JoinColumn(name = "TERMS_ID")
	private Terms term;
*/
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Customer.class)
	@JoinColumn(name = "CUSTOMER_ID")
	private Customer customer;

	@Column(name = "CONSENTED", nullable = false)
	@Builder.Default
	private boolean consented = true;
	
	@Column(name = "MODIFIED_DATE", nullable = false)
	private Date modifiedDate;

	@Column(name = "EXPIRED_DATE")
	private Date expiredDate;
	
	@Column(name = "PRIVACY_CODE")
	private String privacyCode;
	
	@Column(name = "PRIVACY_VALUE")
	private String privacyValue;
	
}
