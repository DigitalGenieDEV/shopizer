package com.salesmanager.core.model.catalog.product.information;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.UniqueConstraint;

import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.common.audit2.AuditSection2;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;

@SuppressWarnings("serial")
@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "PRODUCT_INFORMATION", indexes = @Index(columnList = "DIVISION"), uniqueConstraints = @UniqueConstraint(columnNames = {
		"ID" }))
public class ProductInformation extends SalesManagerEntity<Long, ProductInformation> implements Auditable {

	@Id
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "PRODUCT_INFORMATION_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;

	@Column(name = "DIVISION", length = 1, nullable = false, updatable = false, columnDefinition = "varchar(1) not null comment 'A:배송안내, B:교환/반품/환불 안내'")
	private String division;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Language.class)
	@JoinColumn(name = "LANGUAGE_ID", nullable = false, updatable = false, columnDefinition = "Integer(11) not null comment '언어 ID'")
	private Language defaultLanguage;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MERCHANT_ID", nullable = false, updatable = false, columnDefinition = "Integer(11) not null comment '상점 ID'")
	private MerchantStore merchantStore;

	@Column(name = "DESC1", length = 1000, nullable = true, columnDefinition = "varchar(1000) not null comment '배송안내:배송지역, 교환/반품/환불 안내:교환/반품 기간'")
	private String desc1;

	@Column(name = "DESC2", length = 1000, nullable = true, columnDefinition = "varchar(1000) not null comment '배송안내:배송비, 교환/반품/환불안내:교환/반품 방법'")
	private String desc2;

	@Lob
	@Column(name = "DESC3", nullable = false, columnDefinition = "LONGTEXT not null comment '배송안내:배송일, 교환/반품/환불 안내:교환/반품 가능한 경우'")
	private String desc3;

	@Embedded
	private AuditSection auditSection = new AuditSection();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public MerchantStore getMerchantStore() {
		return merchantStore;
	}

	public void setMerchantStore(MerchantStore merchantStore) {
		this.merchantStore = merchantStore;
	}

	public Language getDefaultLanguage() {
		return defaultLanguage;
	}

	public void setDefaultLanguage(Language defaultLanguage) {
		this.defaultLanguage = defaultLanguage;
	}

	public String getDesc1() {
		return desc1;
	}

	public void setDesc1(String desc1) {
		this.desc1 = desc1;
	}

	public String getDesc2() {
		return desc2;
	}

	public void setDesc2(String desc2) {
		this.desc2 = desc2;
	}

	public String getDesc3() {
		return desc3;
	}

	public void setDesc3(String desc3) {
		this.desc3 = desc3;
	}

	public AuditSection getAuditSection() {
		return auditSection;
	}

	public void setAuditSection(AuditSection auditSection) {
		this.auditSection = auditSection;
	}

}
