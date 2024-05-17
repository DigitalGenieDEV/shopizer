package com.salesmanager.shop.model.catalog.product.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.salesmanager.shop.model.catalog.product.Product;
import com.salesmanager.shop.model.catalog.product.product.definition.PriceRange;

import javax.persistence.Column;

/**
 * A product entity is used by services API to populate or retrieve a Product
 * entity
 * 
 * @author Carl Samson
 *
 */
public class ProductEntity extends Product implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String certificationDocument;

	private String intellectualPropertyDocuments;

	private String exportDeclarationDocuments;
	
	private BigDecimal price;
	private int quantity = 0;
	private String sku;

	private boolean preOrder = false;
	private boolean productVirtual = false;
	private int quantityOrderMaximum = -1;// default unlimited
	private int quantityOrderMinimum = 1;// default 1

	private Integer batchNumber;

	private Integer minOrderQuantity;
	
	private List<PriceRange> priceSupplyRangeList;


	/**
	 * 0-无sku按商品数量报价，1-按sku规格报价 2-有sku按商品数量报价
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

	/**
	 * End RENTAL fields
	 *
	 * @return
	 */



	public Integer getMinOrderQuantity() {
		return minOrderQuantity;
	}

	public void setMinOrderQuantity(Integer minOrderQuantity) {
		this.minOrderQuantity = minOrderQuantity;
	}

	public void setQuoteType(Integer quoteType) {
		this.quoteType = quoteType;
	}

	public Integer getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(Integer batchNumber) {
		this.batchNumber = batchNumber;
	}

	public List<PriceRange> getPriceSupplyRangeList() {
		return priceSupplyRangeList;
	}

	public void setPriceSupplyRangeList(List<PriceRange> priceSupplyRangeList) {
		this.priceSupplyRangeList = priceSupplyRangeList;
	}

	public int getQuoteType() {
		return quoteType;
	}

	public void setQuoteType(int quoteType) {
		this.quoteType = quoteType;
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

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}


	public boolean isProductIsFree() {
		return productIsFree;
	}

	public void setProductIsFree(boolean productIsFree) {
		this.productIsFree = productIsFree;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public void setQuantityOrderMaximum(int quantityOrderMaximum) {
		this.quantityOrderMaximum = quantityOrderMaximum;
	}

	public int getQuantityOrderMaximum() {
		return quantityOrderMaximum;
	}

	public void setProductVirtual(boolean productVirtual) {
		this.productVirtual = productVirtual;
	}

	public boolean isProductVirtual() {
		return productVirtual;
	}

	public int getQuantityOrderMinimum() {
		return quantityOrderMinimum;
	}

	public void setQuantityOrderMinimum(int quantityOrderMinimum) {
		this.quantityOrderMinimum = quantityOrderMinimum;
	}

	public int getRatingCount() {
		return ratingCount;
	}

	public void setRatingCount(int ratingCount) {
		this.ratingCount = ratingCount;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
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


	public ProductSpecification getProductSpecifications() {
		return productSpecifications;
	}

	public void setProductSpecifications(ProductSpecification productSpecifications) {
		this.productSpecifications = productSpecifications;
	}



	public String getCertificationDocument() {
		return certificationDocument;
	}

	public void setCertificationDocument(String certificationDocument) {
		this.certificationDocument = certificationDocument;
	}

	public String getIntellectualPropertyDocuments() {
		return intellectualPropertyDocuments;
	}

	public void setIntellectualPropertyDocuments(String intellectualPropertyDocuments) {
		this.intellectualPropertyDocuments = intellectualPropertyDocuments;
	}

	public String getExportDeclarationDocuments() {
		return exportDeclarationDocuments;
	}

	public void setExportDeclarationDocuments(String exportDeclarationDocuments) {
		this.exportDeclarationDocuments = exportDeclarationDocuments;
	}


}
