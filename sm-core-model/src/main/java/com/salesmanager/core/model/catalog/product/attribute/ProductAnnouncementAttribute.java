package com.salesmanager.core.model.catalog.product.attribute;

import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.generic.SalesManagerEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="PRODUCT_ANNOUNCEMENT_ATTRIBUTE",
    indexes = @Index(columnList = "PRODUCT_ID"),
	uniqueConstraints={
		@UniqueConstraint(columnNames={
				"OPTION_ID",
				"OPTION_VALUE_ID",
				"PRODUCT_ID"
			})
	}
)
public class ProductAnnouncementAttribute extends SalesManagerEntity<Long, ProductAnnouncementAttribute> implements Optionable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PRODUCT_ANNOUNCEMENT_ATTRIBUTE_ID", unique=true, nullable=false)
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "PRODUCT_ANNOUNCEMENT_ATTR_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;


	@Column(name="PRODUCT_ATTRIBUTE_REQUIRED")
	private boolean attributeRequired=false;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="OPTION_ID", nullable=false)
	private ProductOption productOption;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="OPTION_VALUE_ID", nullable=false)
	private ProductOptionValue productOptionValue;


	@Column(name="PRODUCT_ID")
	private Long productId;

	public ProductAnnouncementAttribute() {
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public boolean getAttributeRequired() {
		return attributeRequired;
	}

	public void setAttributeRequired(boolean attributeRequired) {
		this.attributeRequired = attributeRequired;
	}

	public ProductOption getProductOption() {
		return productOption;
	}

	public void setProductOption(ProductOption productOption) {
		this.productOption = productOption;
	}

	public ProductOptionValue getProductOptionValue() {
		return productOptionValue;
	}

	public void setProductOptionValue(ProductOptionValue productOptionValue) {
		this.productOptionValue = productOptionValue;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public boolean isAttributeRequired() {
		return attributeRequired;
	}
}
