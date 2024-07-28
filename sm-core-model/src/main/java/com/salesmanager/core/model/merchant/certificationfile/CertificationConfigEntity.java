package com.salesmanager.core.model.merchant.certificationfile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.ColumnDefault;

import com.salesmanager.core.model.catalog.product.description.ProductDescription;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor 
@Entity
@Table(name = "CERTIFICATION_CONFIG", indexes = @Index(columnList = "TEMPLETE_ID, MERCHANT_ID"))
public class CertificationConfigEntity extends SalesManagerEntity<Long, CertificationConfigEntity> implements Auditable {
	
	private static final long serialVersionUID = 1L;
	public final static String DEFAULT_STORE = "DEFAULT";
	
	@Id
	@Column(name = "TEMPLETE_ID", unique = true, nullable = false)
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "CERTIFICATION_CONFIG_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;
	
	@Embedded
	private AuditSection auditSection = new AuditSection();
	
	@Column(name = "MERCHANT_ID", length = 50)
	private Integer storeId;
	
	@Column(name = "TITLE", length = 256)
	private String title;
	
	@Column(name = "DELETE_YN", length = 1)
	private String deleteYn;
	
//	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "templeteId")
//	private Set<CertificationFileEntity> papers = new HashSet<CertificationFileEntity>();
	
}
