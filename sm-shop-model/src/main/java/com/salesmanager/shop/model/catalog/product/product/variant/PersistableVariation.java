package com.salesmanager.shop.model.catalog.product.product.variant;

import java.util.List;

public class PersistableVariation {

    private static final long serialVersionUID = 1L;

    private Long variationId;

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

    public Long getVariationId() {
        return variationId;
    }

    public void setVariationId(Long variationId) {
        this.variationId = variationId;
    }
}
