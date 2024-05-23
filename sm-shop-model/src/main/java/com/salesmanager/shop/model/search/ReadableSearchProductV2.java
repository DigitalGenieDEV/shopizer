package com.salesmanager.shop.model.search;

import com.salesmanager.shop.model.catalog.manufacturer.ReadableManufacturer;
import com.salesmanager.shop.model.catalog.product.ProductDescription;
import com.salesmanager.shop.model.catalog.product.ReadableImage;
import com.salesmanager.shop.model.catalog.product.ReadableProductPrice;
import com.salesmanager.shop.model.catalog.product.product.definition.PriceRange;
import com.salesmanager.shop.model.catalog.product.type.ReadableProductType;
import com.salesmanager.shop.model.entity.Entity;
import com.salesmanager.shop.model.store.ReadableMerchantStore;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class ReadableSearchProductV2 extends Entity implements Serializable  {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private ReadableProductPrice productPrice;
    private ReadableImage image;
    private List<ReadableImage> images;
    private String sku;
    private BigDecimal price;
    /**
     * 0-无sku按商品数量报价，1-按sku规格报价 2-有sku按商品数量报价
     *
     * 0-No sku. Quote based on product quantity.
     * 1-Quotation based on SKU specifications
     *  2- If there is a SKU, the quotation is based on the quantity of the product.
     */
    private Integer quoteType = 2;

    private List<PriceRange> priceSupplyRangeList;

    private ProductDescription description;

    private List<String> productTags;

    private ReadableMerchantStore merchantStore;

    private ReadableManufacturer manufacturer;

    private ReadableProductType type;

    private boolean available;

    private boolean productShipeable;

    private String finalPrice = "0";
    private String originalPrice = null;
    private boolean discounted = false;

    private boolean canBePurchased = false;

    private boolean productVirtual = false;
    private int quantityOrderMaximum = -1;// default unlimited
    private int quantityOrderMinimum = 1;// default 1

    private int quantity = 0;

    public List<ReadableImage> getImages() {
        return images;
    }

    public void setImages(List<ReadableImage> images) {
        this.images = images;
    }

    public List<String> getProductTags() {
        return productTags;
    }

    public void setProductTags(List<String> productTags) {
        this.productTags = productTags;
    }

    public ReadableMerchantStore getMerchantStore() {
        return merchantStore;
    }

    public void setMerchantStore(ReadableMerchantStore merchantStore) {
        this.merchantStore = merchantStore;
    }

    public ReadableProductType getType() {
        return type;
    }

    public void setType(ReadableProductType type) {
        this.type = type;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isProductShipeable() {
        return productShipeable;
    }

    public void setProductShipeable(boolean productShipeable) {
        this.productShipeable = productShipeable;
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

    public boolean isCanBePurchased() {
        return canBePurchased;
    }

    public void setCanBePurchased(boolean canBePurchased) {
        this.canBePurchased = canBePurchased;
    }

    public boolean isProductVirtual() {
        return productVirtual;
    }

    public void setProductVirtual(boolean productVirtual) {
        this.productVirtual = productVirtual;
    }

    public int getQuantityOrderMaximum() {
        return quantityOrderMaximum;
    }

    public void setQuantityOrderMaximum(int quantityOrderMaximum) {
        this.quantityOrderMaximum = quantityOrderMaximum;
    }

    public int getQuantityOrderMinimum() {
        return quantityOrderMinimum;
    }

    public void setQuantityOrderMinimum(int quantityOrderMinimum) {
        this.quantityOrderMinimum = quantityOrderMinimum;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ReadableProductPrice getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(ReadableProductPrice productPrice) {
        this.productPrice = productPrice;
    }

    public ReadableImage getImage() {
        return image;
    }

    public void setImage(ReadableImage image) {
        this.image = image;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuoteType() {
        return quoteType;
    }

    public void setQuoteType(Integer quoteType) {
        this.quoteType = quoteType;
    }

    public List<PriceRange> getPriceSupplyRangeList() {
        return priceSupplyRangeList;
    }

    public void setPriceSupplyRangeList(List<PriceRange> priceSupplyRangeList) {
        this.priceSupplyRangeList = priceSupplyRangeList;
    }

    public ProductDescription getDescription() {
        return description;
    }

    public void setDescription(ProductDescription description) {
        this.description = description;
    }

    public ReadableManufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(ReadableManufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }
}
