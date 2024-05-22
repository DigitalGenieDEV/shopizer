package com.salesmanager.shop.model.shipping;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.shipping.*;
import com.salesmanager.shop.model.catalog.product.ReadableProduct;
import com.salesmanager.shop.model.entity.ReadableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReadableMerchantShippingConfigurationList  extends ReadableList {
    private static final long serialVersionUID = 1L;

    private List<ReadableMerchantShippingConfiguration> merchantShippingConfigurations = new ArrayList<>();

    public List<ReadableMerchantShippingConfiguration> getMerchantShippingConfigurations() {
        return merchantShippingConfigurations;
    }

    public void setMerchantShippingConfigurations(List<ReadableMerchantShippingConfiguration> merchantShippingConfigurations) {
        this.merchantShippingConfigurations = merchantShippingConfigurations;
    }
}
