package com.salesmanager.core.model.catalog.product.variant;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.availability.ProductAvailability;
import com.salesmanager.core.model.catalog.product.variation.ProductVariation;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;


@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "PRODUCT_VARIANT",
		indexes = {
				@Index(columnList = "PRODUCT_ID"),
				@Index(columnList = "SKU")
		},
uniqueConstraints = 
        @UniqueConstraint(columnNames = { 
        "PRODUCT_ID",
		"SKU" }))
public class ProductVariant extends SalesManagerEntity<Long, ProductVariant> implements Auditable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PRODUCT_VARIANT_ID", unique = true, nullable = false)
	@TableGenerator(name = "TABLE_GEN", 
	table = "SM_SEQUENCER", 
	pkColumnName = "SEQ_NAME", 
	valueColumnName = "SEQ_COUNT", 
	pkColumnValue = "PRODUCT_VAR_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;

	@Column(name = "IMAGE_URL", length = 1024)
	private String imageUrl;

	@Embedded
	private AuditSection auditSection = new AuditSection();

	@Column(name = "DATE_AVAILABLE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateAvailable = new Date();
	
	@Column(name = "AVAILABLE")
	private boolean available = true;
	
	@Column(name = "DEFAULT_SELECTION")
	private boolean defaultSelection = true;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "PRODUCT_VARIANT_VARIATION",
			joinColumns = @JoinColumn(name = "PRODUCT_VARIANT_ID"),
			inverseJoinColumns = @JoinColumn(name = "PRODUCT_VARIATION_ID")
	)
	private Set<ProductVariation> variations = new HashSet<>();

	@ManyToOne(targetEntity = Product.class)
	@JoinColumn(name = "PRODUCT_ID", nullable = false)
	private Product product;
	
	@Column(name = "CODE", nullable = true)
	private String code;
	
	@Column(name="SORT_ORDER")
	private Integer sortOrder = 0;

	@Column(name="SPEC_ID")
	private String specId;

	@Column(name = "ALIAS")
	private String alias;

	@NotEmpty
	@Column(name = "SKU")
	private String sku;
	
	@ManyToOne(targetEntity = ProductVariantGroup.class)
	@JoinColumn(name = "PRODUCT_VARIANT_GROUP_ID", nullable = true)
	private ProductVariantGroup productVariantGroup;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="productVariant")
	private Set<ProductAvailability> availabilities = new HashSet<ProductAvailability>();


	@Override
	public AuditSection getAuditSection() {
		return auditSection;
	}

	@Override
	public void setAuditSection(AuditSection audit) {
		this.auditSection = audit;

	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;

	}

	public Date getDateAvailable() {
		return dateAvailable;
	}

	public void setDateAvailable(Date dateAvailable) {
		this.dateAvailable = dateAvailable;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Set<ProductVariation> getVariations() {
		return variations;
	}

	public void setVariations(Set<ProductVariation> variations) {
		this.variations = variations;
	}

	public boolean isDefaultSelection() {
		return defaultSelection;
	}

	public void setDefaultSelection(boolean defaultSelection) {
		this.defaultSelection = defaultSelection;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}


	public String getCode() { return code; }
	 
	public void setCode(String code) { this.code = code; }

	public ProductVariantGroup getProductVariantGroup() {
		return productVariantGroup;
	}

	public void setProductVariantGroup(ProductVariantGroup productVariantGroup) {
		this.productVariantGroup = productVariantGroup;
	}

	public Set<ProductAvailability> getAvailabilities() {
		return availabilities;
	}

	public void setAvailabilities(Set<ProductAvailability> availabilities) {
		this.availabilities = availabilities;
	}

	public String getSpecId() {
		return specId;
	}

	public void setSpecId(String specId) {
		this.specId = specId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
}
