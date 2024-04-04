package com.salesmanager.core.model.catalog.product.feature;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.RentalStatus;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.common.description.Description;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.order.Order;

import javax.persistence.*;

@Entity
@Table(name = "PRODUCT_FEATURE",uniqueConstraints=
@UniqueConstraint(columnNames = {"PRODUCT_ID"}))
public class ProductFeature extends SalesManagerEntity<Long, ProductFeature> implements Auditable {
	private static final long serialVersionUID = 1L;

	@Embedded
	private AuditSection auditSection = new AuditSection();

	@Id
	@Column(name = "PRODUCT_FEATURE_ID", unique=true, nullable=false)
	@TableGenerator(
			name = "TABLE_GEN",
			table = "SM_SEQUENCER",
			pkColumnName = "SEQ_NAME",
			valueColumnName = "SEQ_COUNT",
			pkColumnValue = "PRODUCT_FEATURE_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;

	@JsonIgnore
	@ManyToOne(targetEntity = Product.class)
	@JoinColumn(name = "PRODUCT_ID", nullable = false)
	private Product product;

	@Column(name = "KEY", nullable = false)
	private String key;

	@Column(name = "VALUE", nullable = false)
	private String value;

	@Column(name="STATUS", nullable = true)
	private ProductFeatureStatus productFeatureStatus;

	public ProductFeatureStatus getProductFeatureStatus() {
		return productFeatureStatus;
	}

	public void setProductFeatureStatus(ProductFeatureStatus productFeatureStatus) {
		this.productFeatureStatus = productFeatureStatus;
	}

	public ProductFeature() {
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}


	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {

	}

	@Override
	public AuditSection getAuditSection() {
		return auditSection;
	}

	@Override
	public void setAuditSection(AuditSection audit) {

	}
}
