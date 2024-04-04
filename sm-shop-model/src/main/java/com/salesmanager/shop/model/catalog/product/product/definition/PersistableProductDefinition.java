package com.salesmanager.shop.model.catalog.product.product.definition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.salesmanager.shop.model.catalog.category.Category;
import com.salesmanager.shop.model.catalog.product.ProductDescription;
import com.salesmanager.shop.model.catalog.product.attribute.PersistableProductAttribute;

public class PersistableProductDefinition extends ProductDefinition {

	/**
	 * type and manufacturer are String type corresponding to the unique code
	 */
	private static final long serialVersionUID = 1L;
	
	private List<ProductDescription> descriptions = new ArrayList<ProductDescription>();
	private List<PersistableProductAttribute> properties = new ArrayList<PersistableProductAttribute>();
	private List<Category> categories = new ArrayList<Category>();
	private String type;
	private String manufacturer;
	private BigDecimal price;


	/**
	 * 0-无sku按商品数量报价，1-按sku规格报价 2-有sku按商品数量报价
	 *
	 * 0-No sku. Quote based on product quantity.
	 * 1-Quotation based on SKU specifications
	 *  2- If there is a SKU, the quotation is based on the quantity of the product.
	 */
	private Integer quoteType;

	private List<PriceRange> priceSupplyRangeList;


	/**
	 * supplyPrice
	 */
	private BigDecimal supplyPrice;


	private Integer minOrderQuantity;

	private Integer batchNumber;

	private int quantity;

	public List<PriceRange> getPriceSupplyRangeList() {
		return priceSupplyRangeList;
	}

	public void setPriceSupplyRangeList(List<PriceRange> priceSupplyRangeList) {
		this.priceSupplyRangeList = priceSupplyRangeList;
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

	public List<ProductDescription> getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(List<ProductDescription> descriptions) {
		this.descriptions = descriptions;
	}
	public List<PersistableProductAttribute> getProperties() {
		return properties;
	}
	public void setProperties(List<PersistableProductAttribute> properties) {
		this.properties = properties;
	}
	public List<Category> getCategories() {
		return categories;
	}
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
