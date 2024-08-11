package com.salesmanager.core.model.catalog.product;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.salesmanager.core.model.feature.ProductFeature;
import org.hibernate.annotations.Cascade;

import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.catalog.product.attribute.ProductAttribute;
import com.salesmanager.core.model.catalog.product.availability.ProductAvailability;
import com.salesmanager.core.model.catalog.product.description.ProductDescription;
import com.salesmanager.core.model.catalog.product.image.ProductImage;
import com.salesmanager.core.model.catalog.product.manufacturer.Manufacturer;
import com.salesmanager.core.model.catalog.product.relationship.ProductRelationship;
import com.salesmanager.core.model.catalog.product.type.ProductType;
import com.salesmanager.core.model.catalog.product.variant.ProductVariant;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.tax.taxclass.TaxClass;


@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "PRODUCT", uniqueConstraints=
@UniqueConstraint(columnNames = {"MERCHANT_ID", "SKU"}))
public class Product extends SalesManagerEntity<Long, Product> implements Auditable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PRODUCT_ID", unique=true, nullable=false)
	@TableGenerator(
		 name = "TABLE_GEN", 
		 table = "SM_SEQUENCER", 
		 pkColumnName = "SEQ_NAME", 
		 valueColumnName = "SEQ_COUNT", 
		 pkColumnValue = "PRODUCT_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;

	@Embedded
	private AuditSection auditSection = new AuditSection();

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "product")
	private Set<ProductDescription> descriptions = new HashSet<ProductDescription>();


	/**
	 * Inventory
	 */
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="product")
	private Set<ProductAvailability> availabilities = new HashSet<ProductAvailability>();

	/**
	 * Attributes of a product
	 * Decorates the product with additional properties
	 */
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "product")
	private Set<ProductAttribute> attributes = new HashSet<ProductAttribute>();
	
	/**
	 * Default product images
	 */
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "product")//cascade is set to remove because product save requires logic to create physical image first and then save the image id in the database, cannot be done in cascade
	private Set<ProductImage> images = new HashSet<ProductImage>();

	@Column(name = "sellerTextInfoId")
	private Long sellerTextInfoId;

	@Column(name = "HS_CODE")
	private String hsCode;

	@Column(name = "SELLER_OPENID")
	private String sellerOpenId;

	/**
	 * Related items / product groups
	 */
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "product")
	private Set<ProductRelationship> relationships = new HashSet<ProductRelationship>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="MERCHANT_ID", nullable=false)
	private MerchantStore merchantStore;
	
	/**
	 * Product to category
	 */
	@ManyToMany(fetch=FetchType.LAZY, cascade = {CascadeType.REFRESH})
	@JoinTable(name = "PRODUCT_CATEGORY", joinColumns = { 
			@JoinColumn(name = "PRODUCT_ID", nullable = false, updatable = false) }
			, 
			inverseJoinColumns = { @JoinColumn(name = "CATEGORY_ID", 
					nullable = false, updatable = false) }
	)
	@Cascade({
		org.hibernate.annotations.CascadeType.DETACH,
		org.hibernate.annotations.CascadeType.LOCK,
		org.hibernate.annotations.CascadeType.REFRESH,
		org.hibernate.annotations.CascadeType.REPLICATE
		
	})
	private Set<Category> categories = new HashSet<Category>();
	
	/**
	 * Product variants
	 * Decorates the product with variants
	 * 
	 */
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "product")
	private Set<ProductVariant> variants = new HashSet<ProductVariant>();
	
	@Column(name="DATE_AVAILABLE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateAvailable = new Date();
	
	
	@Column(name = "AVAILABLE")
	private boolean available = true;
	

	@Column(name = "PREORDER")
	private boolean preOrder = false;
	

	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	@JoinColumn(name="MANUFACTURER_ID", nullable=true)
	private Manufacturer manufacturer;

	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	@JoinColumn(name="PRODUCT_TYPE_ID", nullable=true)
	private ProductType type;

	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	@JoinColumn(name="TAX_CLASS_ID", nullable=true)
	private TaxClass taxClass;

	@Column(name = "PRODUCT_SHIP")
	private boolean productShipeable = false;

	@Column(name = "PRODUCT_FREE")
	private boolean productIsFree;

	@Column(name = "PRODUCT_LENGTH")
	private BigDecimal productLength;

	@Column(name = "PRODUCT_WIDTH")
	private BigDecimal productWidth;

	@Column(name = "PRODUCT_HEIGHT")
	private BigDecimal productHeight;

	@Column(name = "PRODUCT_WEIGHT")
	private BigDecimal productWeight;

	@Column(name = "REVIEW_AVG")
	private BigDecimal productReviewAvg;

	@Column(name = "REVIEW_COUNT")
	private Integer productReviewCount;

	@Column(name = "QUANTITY_ORDERED")
	private Integer productOrdered;
	
	@Column(name = "SORT_ORDER")
	private Integer sortOrder = new Integer(0);

	@Column(name = "SKU")
	private String sku;
	
	/**
	 * External system reference SKU/ID
	 */
	@Column(name = "REF_SKU")
	private String refSku;


	@Column(name = "LEFT_CATEGORY_ID")
	private Long leftCategoryId;


	@Column(name = "SHIPPING_TEMPLATE_ID")
	private Long shippingTemplateId;
	

	@Column(name="PRODUCT_AUDIT_STATUS", nullable = true)
	@Enumerated(value = EnumType.STRING)
	private ProductAuditStatus productAuditStatus;

	@Column(name="PUBLISH_WAY", nullable = true)
	@Enumerated(value = EnumType.STRING)
	private PublishWayEnums publishWay;

	@Column(name="MIN_ORDER_QUANTITY", nullable = true)
	private Integer minOrderQuantity;


	@Column(name="BATCH_NUMBER", nullable = true)
	private Integer batchNumber;

	@Column(name="GENERAL_MIXED_BATCH", nullable = true)
	private Boolean generalMixedBatch;

	@Column(name="MIX_AMOUNT", nullable = true)
	private Integer mixAmount;

	@Column(name="MIX_NUMBER", nullable = true)
	private Integer mixNumber;

	/**
	 *
	 * 0-No sku. Quote based on product quantity.
	 * 1-Quotation based on SKU specifications
	 *  2- If there is a SKU, the quotation is based on the quantity of the product.
	 */
	@Column(name = "QUOTE_TYPE", nullable = true)
	private Integer quoteType;

	@Column(name = "BIZ_TYPE", nullable = true)
	private String bizType;

	@Column(name = "DISCOUNT", nullable = true)
	private Integer discount;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")
	private Set<ProductFeature> features = new HashSet<>();

	@Column(name="PRODUCT_STATUS", nullable = true)
	@Enumerated(value = EnumType.STRING)
	private ProductStatus productStatus;

	@Column(name="OUT_PRODUCT_ID")
	private Long outProductId;


	@Column(name = "PRODUCT_PRICE_RANGE")
	private String priceRangeList;

	/**
	 * product price
	 */
	@Column(name="price")
	private BigDecimal price;

	private Integer featureSort;

	public Integer getFeatureSort() {
		return featureSort;
	}

	public void setFeatureSort(Integer featureSort) {
		this.featureSort = featureSort;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public Integer getQuoteType() {
		return quoteType;
	}

	public void setQuoteType(Integer quoteType) {
		this.quoteType = quoteType;
	}

	public Long getOutProductId() {
		return outProductId;
	}

	public void setOutProductId(Long outProductId) {
		this.outProductId = outProductId;
	}

	/**
	 * End rental fields
	 */
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="CUSTOMER_ID", nullable=true)
	private Customer owner;

	public Product() {
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public AuditSection getAuditSection() {
		return auditSection;
	}

	@Override
	public void setAuditSection(AuditSection auditSection) {
		this.auditSection = auditSection;
	}

	public Set<ProductFeature> getFeatures() {
		return features;
	}

	public void setFeatures(Set<ProductFeature> features) {
		this.features = features;
	}

	public boolean isProductIsFree() {
		return productIsFree;
	}

	public Boolean getGeneralMixedBatch() {
		return generalMixedBatch;
	}

	public void setGeneralMixedBatch(Boolean generalMixedBatch) {
		this.generalMixedBatch = generalMixedBatch;
	}

	public Integer getMixAmount() {
		return mixAmount;
	}

	public void setMixAmount(Integer mixAmount) {
		this.mixAmount = mixAmount;
	}

	public Integer getMixNumber() {
		return mixNumber;
	}

	public void setMixNumber(Integer mixNumber) {
		this.mixNumber = mixNumber;
	}

	public BigDecimal getProductLength() {
		return productLength;
	}

	public void setProductLength(BigDecimal productLength) {
		this.productLength = productLength;
	}

	public BigDecimal getProductWidth() {
		return productWidth;
	}

	public void setProductWidth(BigDecimal productWidth) {
		this.productWidth = productWidth;
	}

	public BigDecimal getProductHeight() {
		return productHeight;
	}

	public void setProductHeight(BigDecimal productHeight) {
		this.productHeight = productHeight;
	}

	public BigDecimal getProductWeight() {
		return productWeight;
	}

	public void setProductWeight(BigDecimal productWeight) {
		this.productWeight = productWeight;
	}

	public BigDecimal getProductReviewAvg() {
		return productReviewAvg;
	}

	public void setProductReviewAvg(BigDecimal productReviewAvg) {
		this.productReviewAvg = productReviewAvg;
	}

	public Integer getProductReviewCount() {
		return productReviewCount;
	}

	public void setProductReviewCount(Integer productReviewCount) {
		this.productReviewCount = productReviewCount;
	}


	public Integer getProductOrdered() {
		return productOrdered;
	}

	public void setProductOrdered(Integer productOrdered) {
		this.productOrdered = productOrdered;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Set<ProductDescription> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(Set<ProductDescription> descriptions) {
		this.descriptions = descriptions;
	}

	public boolean getProductIsFree() {
		return productIsFree;
	}

	public void setProductIsFree(boolean productIsFree) {
		this.productIsFree = productIsFree;
	}



	public Set<ProductAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<ProductAttribute> attributes) {
		this.attributes = attributes;
	}



	public Manufacturer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}

	public ProductType getType() {
		return type;
	}

	public void setType(ProductType type) {
		this.type = type;
	}



	public Set<ProductAvailability> getAvailabilities() {
		return availabilities;
	}

	public void setAvailabilities(Set<ProductAvailability> availabilities) {
		this.availabilities = availabilities;
	}

	public TaxClass getTaxClass() {
		return taxClass;
	}

	public void setTaxClass(TaxClass taxClass) {
		this.taxClass = taxClass;
	}

	public Set<ProductImage> getImages() {
		return images;
	}

	public void setImages(Set<ProductImage> images) {
		this.images = images;
	}

	public Set<ProductRelationship> getRelationships() {
		return relationships;
	}

	public void setRelationships(Set<ProductRelationship> relationships) {
		this.relationships = relationships;
	}


	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public MerchantStore getMerchantStore() {
		return merchantStore;
	}

	public void setMerchantStore(MerchantStore merchantStore) {
		this.merchantStore = merchantStore;
	}



	public Date getDateAvailable() {
		return dateAvailable;
	}

	public void setDateAvailable(Date dateAvailable) {
		this.dateAvailable = dateAvailable;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public Long getSellerTextInfoId() {
		return sellerTextInfoId;
	}

	public void setSellerTextInfoId(Long sellerTextInfoId) {
		this.sellerTextInfoId = sellerTextInfoId;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public boolean isAvailable() {
		return available;
	}
	
	public boolean isProductShipeable() {
		return productShipeable;
	}

	public void setProductShipeable(Boolean productShipeable) {
		this.productShipeable = productShipeable;
	}

	
	public ProductDescription getProductDescription() {
		if(this.getDescriptions()!=null && this.getDescriptions().size()>0) {
			return this.getDescriptions().iterator().next();
		}
		return null;
	}
	
	public ProductImage getProductImage() {
		ProductImage productImage = null;
		if(this.getImages()!=null && this.getImages().size()>0) {
			for(ProductImage image : this.getImages()) {
				productImage = image;
				if(productImage.isDefaultImage()) {
					break;
				}
			}
		}
		return productImage;
	}
	
	public boolean isPreOrder() {
		return preOrder;
	}

	public void setPreOrder(boolean preOrder) {
		this.preOrder = preOrder;
	}

	public String getRefSku() {
		return refSku;
	}

	public void setRefSku(String refSku) {
		this.refSku = refSku;
	}

	public Customer getOwner() {
		return owner;
	}

	public void setOwner(Customer owner) {
		this.owner = owner;
	}

	public Set<ProductVariant> getVariants() {
		return variants;
	}

	public void setVariants(Set<ProductVariant> variants) {
		this.variants = variants;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public void setProductShipeable(boolean productShipeable) {
		this.productShipeable = productShipeable;
	}

	public ProductAuditStatus getProductAuditStatus() {
		return productAuditStatus;
	}

	public void setProductAuditStatus(ProductAuditStatus productAuditStatus) {
		this.productAuditStatus = productAuditStatus;
	}

	public Integer getMinOrderQuantity() {
		return minOrderQuantity;
	}

	public void setMinOrderQuantity(Integer minOrderQuantity) {
		this.minOrderQuantity = minOrderQuantity;
	}

	public Integer getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(Integer batchNumber) {
		this.batchNumber = batchNumber;
	}

	public Long getShippingTemplateId() {
		return shippingTemplateId;
	}

	public void setShippingTemplateId(Long shippingTemplateId) {
		this.shippingTemplateId = shippingTemplateId;
	}

	public ProductStatus getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(ProductStatus productStatus) {
		this.productStatus = productStatus;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getPriceRangeList() {
		return priceRangeList;
	}

	public void setPriceRangeList(String priceRangeList) {
		this.priceRangeList = priceRangeList;
	}

	public PublishWayEnums getPublishWay() {
		return publishWay;
	}

	public void setPublishWay(PublishWayEnums publishWay) {
		this.publishWay = publishWay;
	}

	public Long getLeftCategoryId() {
		return leftCategoryId;
	}

	public void setLeftCategoryId(Long leftCategoryId) {
		this.leftCategoryId = leftCategoryId;
	}

	public String getHsCode() {
		return hsCode;
	}

	public void setHsCode(String hsCode) {
		this.hsCode = hsCode;
	}

	public String getSellerOpenId() {
		return sellerOpenId;
	}

	public void setSellerOpenId(String sellerOpenId) {
		this.sellerOpenId = sellerOpenId;
	}
}
