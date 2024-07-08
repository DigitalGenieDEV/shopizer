package com.salesmanager.shop.model.catalog.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.salesmanager.core.model.catalog.product.price.PriceRange;
import org.apache.commons.lang3.StringUtils;
/**
 * A product entity is used by services API
 * to populate or retrieve a Product price entity
 * @author Carl Samson
 *
 */
public class ProductPriceEntity extends ProductPrice implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private boolean discounted = false;
	private String discountStartDate;
	private String discountEndDate;
	private boolean defaultPrice = true;
	private BigDecimal price;
	private BigDecimal discountedPrice;

	private BigDecimal supplyPrice;

	private BigDecimal consignSupplyPrice;

	/**
	 * 阶梯价
	 */
	private List<PriceRange> priceRangeList;


	public List<PriceRange> getPriceRangeList() {
		return priceRangeList;
	}

	public void setPriceRangeList(List<PriceRange> priceRangeList) {
		this.priceRangeList = priceRangeList;
	}

	public BigDecimal getSupplyPrice() {
		return supplyPrice;
	}

	public void setSupplyPrice(BigDecimal supplyPrice) {
		this.supplyPrice = supplyPrice;
	}

	public BigDecimal getConsignSupplyPrice() {
		return consignSupplyPrice;
	}

	public void setConsignSupplyPrice(BigDecimal consignSupplyPrice) {
		this.consignSupplyPrice = consignSupplyPrice;
	}

	public boolean isDiscounted() {
		return discounted;
	}
	public void setDiscounted(boolean discounted) {
		this.discounted = discounted;
	}
	public String getDiscountStartDate() {
		return discountStartDate;
	}
	public void setDiscountStartDate(String discountStartDate) {
		this.discountStartDate = discountStartDate;
	}
	public String getDiscountEndDate() {
		return discountEndDate;
	}
	public void setDiscountEndDate(String discountEndDate) {
		this.discountEndDate = discountEndDate;
	}
	public boolean isDefaultPrice() {
		return defaultPrice;
	}
	public void setDefaultPrice(boolean defaultPrice) {
		this.defaultPrice = defaultPrice;
	}

	public BigDecimal getDiscountedPrice() {
		return discountedPrice;
	}
	public void setDiscountedPrice(BigDecimal discountedPrice) {
		this.discountedPrice = discountedPrice;
	}
	public String getCode() {
		if(StringUtils.isBlank(this.code)) {
			code = DEFAULT_PRICE_CODE;
		}
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	
	
	


}
