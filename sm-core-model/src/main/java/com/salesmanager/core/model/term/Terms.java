package com.salesmanager.core.model.term;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

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
@Table(name = "TERMS")
public class Terms extends SalesManagerEntity<Long, Terms>{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "TERMS_ID", unique = true, nullable = false)
	@TableGenerator(
			name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "TERMS_SEQ_NEXT_VAL"
	)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "REQUIRED")
	private boolean required;

	@Column(name = "USED")
	private boolean used;
	
	/*
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "term", targetEntity = CustomerTerms.class)
	private List<CustomerTerms> customerTerms;
	*/
}
