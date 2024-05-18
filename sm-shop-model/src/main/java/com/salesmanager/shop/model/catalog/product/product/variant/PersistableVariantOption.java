package com.salesmanager.shop.model.catalog.product.product.variant;

public class PersistableVariantOption {

    private static final long serialVersionUID = 1L;

    private Long optionId;

    private Long optionValueId;

    public Long getOptionId() {
        return optionId;
    }

    public void setOptionId(Long optionId) {
        this.optionId = optionId;
    }

    public Long getOptionValueId() {
        return optionValueId;
    }

    public void setOptionValueId(Long optionValueId) {
        this.optionValueId = optionValueId;
    }
}
