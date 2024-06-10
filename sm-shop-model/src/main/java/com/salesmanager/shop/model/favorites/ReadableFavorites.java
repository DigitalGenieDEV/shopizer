package com.salesmanager.shop.model.favorites;

import com.salesmanager.shop.model.catalog.manufacturer.ReadableManufacturer;

public class ReadableFavorites extends FavoritesEntity{

    private static final long serialVersionUID = 1L;

    private String image;

    private String productTitle;

    private String origin;

    private String manufacturer;

    private String price;


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
