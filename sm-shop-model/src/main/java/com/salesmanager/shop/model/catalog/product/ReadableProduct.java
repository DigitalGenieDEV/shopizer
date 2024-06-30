package com.salesmanager.shop.model.catalog.product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.shop.model.catalog.category.ReadableCategory;
import com.salesmanager.shop.model.catalog.manufacturer.ReadableManufacturer;
import com.salesmanager.shop.model.catalog.product.attribute.ReadableProductAnnouncement;
import com.salesmanager.shop.model.catalog.product.attribute.ReadableProductAttribute;
import com.salesmanager.shop.model.catalog.product.attribute.ReadableProductOption;
import com.salesmanager.shop.model.catalog.product.attribute.ReadableProductProperty;
import com.salesmanager.shop.model.catalog.product.product.ProductEntity;
import com.salesmanager.shop.model.catalog.product.product.variant.ReadableProductVariant;
import com.salesmanager.shop.model.catalog.product.type.ReadableProductType;
import com.salesmanager.shop.model.store.ReadableMerchantStore;

public class ReadableProduct extends ProductEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ReadableMerchantStore merchantStore;

	private ProductDescription description;
	private ReadableProductPrice productPrice;
	private String finalPrice = "0";
	private String originalPrice = null;
	private boolean discounted = false;
	private ReadableImage image;

	private String identifier;


	private String auditStatus;

	private String storeName;

	private Long salesVolume;
	private List<ReadableImage> images = new ArrayList<ReadableImage>();
	private ReadableManufacturer manufacturer;

	private List<ProductDescription> descriptions = new ArrayList<>();

	private List<ReadableProductOption> options = new ArrayList<ReadableProductOption>();
	private List<ReadableProductVariant> variants = new ArrayList<ReadableProductVariant>();
	private List<ReadableProductProperty> properties = new ArrayList<ReadableProductProperty>();
	private List<ReadableCategory> categories = new ArrayList<ReadableCategory>();
	private ReadableProductType type;


	private List<ReadableProductAnnouncement>  productAnnouncements = new ArrayList<>();

	private List<String> tags;

	private boolean canBePurchased = false;


	public ProductDescription getDescription() {
		return description;
	}

	public void setDescription(ProductDescription description) {
		this.description = description;
	}

	public String getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(String finalPrice) {
		this.finalPrice = finalPrice;
	}

	public String getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(String originalPrice) {
		this.originalPrice = originalPrice;
	}

	public boolean isDiscounted() {
		return discounted;
	}

	public void setDiscounted(boolean discounted) {
		this.discounted = discounted;
	}

	public void setImages(List<ReadableImage> images) {
		this.images = images;
	}

	public List<ReadableImage> getImages() {
		return images;
	}

	public void setImage(ReadableImage image) {
		this.image = image;
	}

	public ReadableImage getImage() {
		return image;
	}


	public void setManufacturer(ReadableManufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}

	public ReadableManufacturer getManufacturer() {
		return manufacturer;
	}

	public boolean isCanBePurchased() {
		return canBePurchased;
	}

	public void setCanBePurchased(boolean canBePurchased) {
		this.canBePurchased = canBePurchased;
	}


	public List<ReadableCategory> getCategories() {
		return categories;
	}

	public void setCategories(List<ReadableCategory> categories) {
		this.categories = categories;
	}

	public List<ReadableProductOption> getOptions() {
		return options;
	}

	public void setOptions(List<ReadableProductOption> options) {
		this.options = options;
	}

	public Long getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(Long salesVolume) {
		this.salesVolume = salesVolume;
	}

	public ReadableProductType getType() {
		return type;
	}

	public void setType(ReadableProductType type) {
		this.type = type;
	}

	public ReadableProductPrice getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(ReadableProductPrice productPrice) {
		this.productPrice = productPrice;
	}

	public List<ReadableProductProperty> getProperties() {
		return properties;
	}

	public void setProperties(List<ReadableProductProperty> properties) {
		this.properties = properties;
	}

	public List<ReadableProductVariant> getVariants() {
		return variants;
	}

	public void setVariants(List<ReadableProductVariant> variants) {
		this.variants = variants;
	}

	public String getIdentifier() {
		return identifier;
	}

	public ReadableMerchantStore getMerchantStore() {
		return merchantStore;
	}

	public void setMerchantStore(ReadableMerchantStore merchantStore) {
		this.merchantStore = merchantStore;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public List<ProductDescription> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(List<ProductDescription> descriptions) {
		this.descriptions = descriptions;
	}

	public List<ReadableProductAnnouncement> getProductAnnouncements() {
		return productAnnouncements;
	}

	public void setProductAnnouncements(List<ReadableProductAnnouncement> productAnnouncements) {
		this.productAnnouncements = productAnnouncements;
	}
}
