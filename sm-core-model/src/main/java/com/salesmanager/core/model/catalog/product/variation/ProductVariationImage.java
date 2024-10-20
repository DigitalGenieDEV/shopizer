package com.salesmanager.core.model.catalog.product.variation;

import com.salesmanager.core.model.catalog.product.variant.ProductVariantGroup;
import com.salesmanager.core.model.catalog.product.variant.ProductVariantImageDescription;
import com.salesmanager.core.model.generic.SalesManagerEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PRODUCT_VARIATION_IMAGE")
public class ProductVariationImage extends SalesManagerEntity<Long, ProductVariationImage> {


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PRODUCT_VARIATION_IMAGE_ID")
	@TableGenerator(name = "TABLE_GEN",
	table = "SM_SEQUENCER",
	pkColumnName = "SEQ_NAME",
	valueColumnName = "SEQ_COUNT",
	pkColumnValue = "PRD_VARIATION_IMG_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;

	@Column(name = "PRODUCT_IMAGE")
	private String productImage;

	@ManyToOne(targetEntity = ProductVariantGroup.class)
	@JoinColumn(name = "PRODUCT_VARIANT_GROUP_ID", nullable = false)
	private ProductVariantGroup productVariantGroup;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "productVariantImage", cascade = CascadeType.ALL)
	private Set<ProductVariantImageDescription> descriptions = new HashSet<ProductVariantImageDescription>();

	public ProductVariationImage(){
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}


	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Set<ProductVariantImageDescription> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(Set<ProductVariantImageDescription> descriptions) {
		this.descriptions = descriptions;
	}

	public ProductVariantGroup getProductVariantGroup() {
		return productVariantGroup;
	}

	public void setProductVariantGroup(ProductVariantGroup productVariantGroup) {
		this.productVariantGroup = productVariantGroup;
	}


}
