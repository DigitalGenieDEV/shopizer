package com.salesmanager.core.model.catalog.product.price;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Transient entity used to display
 * different price information in the catalogue
 * @author Carl Samson
 *
 */
@Data
public class FinalPrice implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal discountedPrice = null;//final price if a discount is applied
	private BigDecimal originalPrice = null;//original price
	private BigDecimal finalPrice = null;//final price discount or not
	private boolean discounted = false;
	private int discountPercent = 0;
	private String stringPrice;
	private String stringDiscountedPrice;
	
	private Date discountEndDate = null;

	private List<PriceRange> priceRanges;

	private List<PriceRange> productPriceRangeSpecialAmount;
	
	private boolean defaultPrice;

	private ProductPriceDO productPrice;

	List<FinalPrice> additionalPrices;

}
