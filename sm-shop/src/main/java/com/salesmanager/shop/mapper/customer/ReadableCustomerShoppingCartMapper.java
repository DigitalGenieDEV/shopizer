package com.salesmanager.shop.mapper.customer;

import com.salesmanager.core.business.services.catalog.pricing.PricingService;
import com.salesmanager.core.business.services.catalog.product.attribute.ProductAttributeService;
import com.salesmanager.core.business.services.catalog.product.variant.ProductVariantService;
import com.salesmanager.core.business.services.shoppingcart.ShoppingCartCalculationService;
import com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCart;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.mapper.Mapper;
import com.salesmanager.shop.mapper.catalog.ReadableMinimalProductMapper;
import com.salesmanager.shop.mapper.catalog.ReadableProductVariationMapper;
import com.salesmanager.shop.model.customer.shoppingcart.ReadableCustomerShoppingCart;
import com.salesmanager.shop.store.api.exception.ConversionRuntimeException;
import com.salesmanager.shop.utils.ImageFilePath;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class ReadableCustomerShoppingCartMapper implements Mapper<CustomerShoppingCart, ReadableCustomerShoppingCart> {

    private static final Logger LOG = LoggerFactory.getLogger(ReadableCustomerShoppingCartMapper.class);

    @Autowired
    private ShoppingCartCalculationService shoppingCartCalculationService;

    @Autowired
    private PricingService pricingService;

    @Autowired
    private ProductAttributeService productAttributeService;

    @Autowired
    private ProductVariantService productVariantService;

    @Autowired
    private ReadableMinimalProductMapper readableMinimalProductMapper;

    @Autowired
    private ReadableProductVariationMapper readableProductVariationMapper;

    @Autowired
    @Qualifier("img")
    private ImageFilePath imageUtils;


    @Override
    public ReadableCustomerShoppingCart convert(CustomerShoppingCart source, MerchantStore store, Language language) {
        ReadableCustomerShoppingCart destination = new ReadableCustomerShoppingCart();
        return this.merge(source, destination, store, language);
    }

    @Override
    public ReadableCustomerShoppingCart merge(CustomerShoppingCart source, ReadableCustomerShoppingCart destination, MerchantStore store, Language language) {
        Validate.notNull(source, "ShoppingCart cannot be null");
        Validate.notNull(destination, "ReadableShoppingCart cannot be null");
        Validate.notNull(store, "MerchantStore cannot be null");
        Validate.notNull(language, "Language cannot be null");

        destination.setCode(source.getCustomerShoppingCartCode());
        int cartQuantity = 0;

        destination.setCustomer(source.getCustomerId());

        try {
            if (!StringUtils.isBlank(source.getPromoCode())) {
                Date promoDateAdded = source.getPromoAdded();// promo valid 1 day
                if (promoDateAdded == null) {
                    promoDateAdded = new Date();
                }
                Instant instant = promoDateAdded.toInstant();
                ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
                LocalDate date = zdt.toLocalDate();
                // date added < date + 1 day
                LocalDate tomorrow = LocalDate.now().plusDays(1);
                if (date.isBefore(tomorrow)) {
                    destination.setPromoCode(source.getPromoCode());
                }
            }

        } catch (Exception e) {
            throw new ConversionRuntimeException("An error occured while converting ReadableCustomerShoppingCart", e);
        }

        return destination;
    }
}
