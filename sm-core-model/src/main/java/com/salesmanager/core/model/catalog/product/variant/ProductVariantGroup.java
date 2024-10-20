package com.salesmanager.core.model.catalog.product.variant;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.salesmanager.core.model.catalog.product.variation.ProductVariation;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.zone.Zone;

/**
 * Extra properties on a group of variants
 * @author carlsamson
 *
 */
@Entity
@EntityListeners(value = AuditListener.class)
@Table(name="PRODUCT_VARIANT_GROUP")
public class ProductVariantGroup extends SalesManagerEntity<Long, ProductVariantGroup> {

	private static final long serialVersionUID = 1L;


	@Id
	@Column(name = "PRODUCT_VARIANT_GROUP_ID", unique=true, nullable=false)
	@TableGenerator(name = "TABLE_GEN", 
	table = "SM_SEQUENCER", 
	pkColumnName = "SEQ_NAME", 
	valueColumnName = "SEQ_COUNT", pkColumnValue = "PRODUCT_VAR_GROUP_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;


	@Column(name = "IMAGE_URL", length = 1024)
	private String imageUrl;
	
//	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="productVariantGroup")
//	private List<ProductVariantImage> images = new ArrayList<ProductVariantImage>();

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH }, mappedBy = "productVariantGroup")
	private Set<ProductVariant> productVariants = new HashSet<ProductVariant>();

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="PRODUCT_VARIATION_ID", nullable=true, updatable=true)
	private ProductVariation productVariation;

	
	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id=id;
		
	}

//	public List<ProductVariantImage> getImages() {
//		return images;
//	}
//
//	public void setImages(List<ProductVariantImage> images) {
//		this.images = images;
//	}



	public Set<ProductVariant> getProductVariants() {
		return productVariants;
	}

	public void setProductVariants(Set<ProductVariant> productVariants) {
		this.productVariants = productVariants;
	}


	public ProductVariation getProductVariation() {
		return productVariation;
	}

	public void setProductVariation(ProductVariation productVariation) {
		this.productVariation = productVariation;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
