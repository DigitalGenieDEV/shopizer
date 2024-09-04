package com.salesmanager.shop.model.catalog.product.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.salesmanager.core.model.catalog.product.price.PriceRange;
import com.salesmanager.shop.model.catalog.product.Product;
import lombok.Data;

import javax.persistence.Column;

/**
 * A product entity is used by services API to populate or retrieve a Product
 * entity
 * 
 * @author Carl Samson
 *
 */
@Data
public class ProductEntity extends Product implements Serializable {

	/**
	 * 
	 */

	private String hsCode;

	private String sellerOpenId;

	private static final long serialVersionUID = 1L;

	private String certificationDocument;

	private String intellectualPropertyDocuments;

	private Long shippingTemplateId;


	private BigDecimal price;
	private int quantity = 0;
	private String sku;

	private boolean preOrder = false;

	private Integer batchNumber;

	private Integer minOrderQuantity;

	private Long leftCategoryId;

	private List<PriceRange> priceRangeList;
	/**
	 *
	 * 0-No sku. Quote based on product quantity.
	 * 1-Quotation based on SKU specifications
	 *  2- If there is a SKU, the quotation is based on the quantity of the product.
	 */
	private Integer quoteType = 2;

	private boolean productIsFree;

	private ProductSpecification productSpecifications;
	private Double rating = 0D;
	private int ratingCount;
	private int sortOrder;
	private String refSku;

	private Integer mixAmount;

	private Integer mixNumber;

	private Boolean generalMixedBatch;


	private String orderQuantityType;

}
