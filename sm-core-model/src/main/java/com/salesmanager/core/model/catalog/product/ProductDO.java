package com.salesmanager.core.model.catalog.product;

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
import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class ProductDO {

	private Long id;

	private AuditSection auditSection = new AuditSection();

	private Set<ProductDescription> descriptions = new HashSet<ProductDescription>();

	private Set<ProductAvailability> availabilities = new HashSet<ProductAvailability>();

	private Set<ProductAttribute> attributes = new HashSet<ProductAttribute>();

	private Set<ProductImage> images = new HashSet<ProductImage>();

	private Set<ProductRelationship> relationships = new HashSet<ProductRelationship>();

	private MerchantStore merchantStore;

	private Set<Category> categories = new HashSet<Category>();

	private Set<ProductVariant> variants = new HashSet<ProductVariant>();

	private Date dateAvailable = new Date();

	private boolean available = true;

	private boolean preOrder = false;

	private Manufacturer manufacturer;

	private ProductType type;

	private TaxClass taxClass;

	private boolean productVirtual = false;

	private boolean productShipeable = false;

	private boolean productIsFree;

	private BigDecimal productLength;

	private BigDecimal productWidth;

	private BigDecimal productHeight;

	private BigDecimal productWeight;

	private BigDecimal productReviewAvg;

	private Integer productReviewCount;

	private Integer productOrdered;

	private Integer sortOrder = new Integer(0);

	private String sku;

	private String refSku;

	private Long shippingTemplateId;

	private ProductCondition condition;

	private ProductAuditStatus productAuditStatus;

	private RentalStatus rentalStatus;

	private Integer minOrderQuantity;


	private Integer batchNumber;

	private Boolean generalMixedBatch;

	private Integer mixAmount;

	private Integer mixNumber;

	private Integer quoteType;


	private ProductStatus productStatus;


	private Integer rentalDuration;

	private Integer rentalPeriod;

	private Long outProductId;


}
