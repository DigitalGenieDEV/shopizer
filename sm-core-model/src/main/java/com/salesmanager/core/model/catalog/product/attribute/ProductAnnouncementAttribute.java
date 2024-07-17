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
				"PRODUCT_ID"
			})
	}
)
public class ProductAnnouncementAttribute extends SalesManagerEntity<Long, ProductAnnouncementAttribute>  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PRODUCT_ANNOUNCEMENT_ATTRIBUTE_ID", unique=true, nullable=false)
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "PRODUCT_ANNOUNCEMENT_ATTR_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;


	@Column(columnDefinition = "TEXT")
	private String text;

	@Column(name="PRODUCT_ID")
	private Long productId;

	public ProductAnnouncementAttribute() {
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

}
