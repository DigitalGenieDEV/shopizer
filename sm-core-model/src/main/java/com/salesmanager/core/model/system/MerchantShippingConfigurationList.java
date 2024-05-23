package com.salesmanager.core.model.system;

import com.salesmanager.core.model.common.EntityList;
import com.salesmanager.core.model.order.Order;

import java.util.List;

public class MerchantShippingConfigurationList extends EntityList {

    /**
     *
     */
    private static final long serialVersionUID = -6645927228659963628L;
    private List<MerchantShippingConfiguration> merchantShippingConfigurations;

    public List<MerchantShippingConfiguration> getMerchantShippingConfigurations() {
        return merchantShippingConfigurations;
    }

    public void setMerchantShippingConfigurations(List<MerchantShippingConfiguration> merchantShippingConfigurations) {
        this.merchantShippingConfigurations = merchantShippingConfigurations;
    }
}
