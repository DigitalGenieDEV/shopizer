package com.salesmanager.shop.model.search;

import com.salesmanager.shop.model.catalog.category.ReadableCategory;
import com.salesmanager.shop.model.catalog.product.Product;
import com.salesmanager.shop.model.catalog.product.ReadableImage;
import com.salesmanager.shop.model.catalog.product.ReadableProductPrice;
import com.salesmanager.shop.model.catalog.product.product.ProductEntity;
import com.salesmanager.shop.model.catalog.product.type.ReadableProductType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReadableSearchProduct extends ProductEntity implements Serializable  {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private ReadableProductPrice productPrice;
    private String finalPrice = "0";
    private String originalPrice = null;
    private boolean discounted = false;
    private ReadableImage image;
    private List<ReadableImage> images = new ArrayList<ReadableImage>();
    private List<ReadableCategory> categories = new ArrayList<ReadableCategory>();
    private ReadableProductType type;

    public ReadableProductPrice getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(ReadableProductPrice productPrice) {
        this.productPrice = productPrice;
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

    public ReadableImage getImage() {
        return image;
    }

    public void setImage(ReadableImage image) {
        this.image = image;
    }

    public List<ReadableImage> getImages() {
        return images;
    }

    public void setImages(List<ReadableImage> images) {
        this.images = images;
    }

    public List<ReadableCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<ReadableCategory> categories) {
        this.categories = categories;
    }

    public ReadableProductType getType() {
        return type;
    }

    public void setType(ReadableProductType type) {
        this.type = type;
    }
}
