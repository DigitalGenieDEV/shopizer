package com.salesmanager.core.model.catalog.product.price;

public class PriceRange {

    /**
     * Minimum batch size
     */
    private Integer startQuantity;

    private String price;

    private String promotionPrice;

    public Integer getStartQuantity() {
        return startQuantity;
    }

    public void setStartQuantity(Integer startQuantity) {
        this.startQuantity = startQuantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(String promotionPrice) {
        this.promotionPrice = promotionPrice;
    }
}
