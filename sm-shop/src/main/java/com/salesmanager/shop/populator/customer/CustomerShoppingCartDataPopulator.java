package com.salesmanager.shop.populator.customer;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.services.catalog.pricing.PricingService;
import com.salesmanager.core.business.services.customer.shoppingcart.CustomerShoppingCartCalculationService;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCart;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.customer.shoppingcart.CustomerShoppingCartData;
import com.salesmanager.shop.model.customer.shoppingcart.CustomerShoppingCartItem;
import com.salesmanager.shop.utils.ImageFilePath;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class CustomerShoppingCartDataPopulator extends AbstractDataPopulator<CustomerShoppingCart, CustomerShoppingCartData> {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerShoppingCartDataPopulator.class);

    private PricingService pricingService;

    private CustomerShoppingCartCalculationService customerShoppingCartCalculationService;

    private ImageFilePath imageUtils;

    public PricingService getPricingService() {
        return pricingService;
    }

    public void setPricingService(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    public CustomerShoppingCartCalculationService getCustomerShoppingCartCalculationService() {
        return customerShoppingCartCalculationService;
    }

    public void setCustomerShoppingCartCalculationService(CustomerShoppingCartCalculationService customerShoppingCartCalculationService) {
        this.customerShoppingCartCalculationService = customerShoppingCartCalculationService;
    }

    public ImageFilePath getImageUtils() {
        return imageUtils;
    }

    public void setImageUtils(ImageFilePath imageUtils) {
        this.imageUtils = imageUtils;
    }

    @Override
    protected CustomerShoppingCartData createTarget() {
        return new CustomerShoppingCartData();
    }

    @Override
    public CustomerShoppingCartData populate(CustomerShoppingCart customerShoppingCart,
                                             CustomerShoppingCartData cart,
                                             MerchantStore store,
                                             Language language) throws ConversionException {
        Validate.notNull(cart, "Requires CustomerShoppingCart");
        Validate.notNull(language, "Requires Language not null");

        int cartQuantity = 0;
        cart.setCode(customerShoppingCart.getCustomerShoppingCartCode());
        Set<com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem> items = customerShoppingCart.getLineItems();
        List<CustomerShoppingCartItem> customerShoppingCartItemList = Collections.emptyList();

        try {

        } catch (Exception e) {
        }
        return null;
    }
}
