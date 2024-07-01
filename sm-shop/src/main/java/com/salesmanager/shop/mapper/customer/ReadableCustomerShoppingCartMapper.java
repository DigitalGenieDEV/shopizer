package com.salesmanager.shop.mapper.customer;

import com.salesmanager.core.business.services.catalog.pricing.PricingService;
import com.salesmanager.core.business.services.catalog.product.attribute.ProductAttributeService;
import com.salesmanager.core.business.services.catalog.product.variant.ProductVariantService;
import com.salesmanager.core.business.services.customer.shoppingcart.CustomerShoppingCartSplitterService;
import com.salesmanager.core.business.services.shoppingcart.ShoppingCartCalculationService;
import com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCart;
import com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.shoppingcart.ShoppingCart;
import com.salesmanager.core.utils.LogPermUtil;
import com.salesmanager.shop.mapper.Mapper;
import com.salesmanager.shop.mapper.cart.ReadableShoppingCartMapper;
import com.salesmanager.shop.mapper.catalog.ReadableMinimalProductMapper;
import com.salesmanager.shop.mapper.catalog.ReadableProductVariationMapper;
import com.salesmanager.shop.model.customer.shoppingcart.ReadableCustomerShoppingCart;
import com.salesmanager.shop.model.customer.shoppingcart.ReadableCustomerShoppingCartItem;
import com.salesmanager.shop.model.order.total.ReadableOrderTotal;
import com.salesmanager.shop.model.shoppingcart.ReadableShoppingCart;
import com.salesmanager.shop.model.shoppingcart.ReadableShoppingCartItem;
import com.salesmanager.shop.store.api.exception.ConversionRuntimeException;
import com.salesmanager.shop.utils.ImageFilePath;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private CustomerShoppingCartSplitterService customerShoppingCartSplitterService;

    @Autowired
    private ReadableShoppingCartMapper readableShoppingCartMapper;

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
        destination.setCustomer(source.getCustomerId());
        long start = LogPermUtil.start("ReadableCustomerShoppingCartMapper/merge, customer id:" + source.getCustomerId());
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

            // 将未选中商品 拆分为 商户购物车进行 购物车项价格计算
            LOG.debug("[ReadableCustomerShoppingCartMapper/merge] split unchecked items to stores");
            List<ShoppingCart> uncheckedItemsShoppingCarts = customerShoppingCartSplitterService.splitUncheckedItemsToShoppingCart(source);
            Map<String, ReadableCustomerShoppingCartItem> readableUncheckedCustomerShoppingCartItems = new HashMap<>();
            for (ShoppingCart shoppingCart : uncheckedItemsShoppingCarts) {
                ReadableShoppingCart readableShoppingCart = new ReadableShoppingCart();
                readableShoppingCartMapper.merge(shoppingCart, readableShoppingCart, shoppingCart.getMerchantStore(), language);

                readableShoppingCart.getProducts().stream().map(i -> convert2ReadableCustomerShoppingCartItem(i, false)).forEach(item -> {
                    readableUncheckedCustomerShoppingCartItems.put(item.getSku(), item);
                });
            }

            // 将选中商品 拆分为 商户购物车进行总价计算
            LOG.debug("[ReadableCustomerShoppingCartMapper/merge] split checked items to stores");
            List<ShoppingCart> checkedItemsShoppingCarts = customerShoppingCartSplitterService.splitCheckedItemsToShoppingCart(source);
            List<ReadableShoppingCart> readableCheckedItemsShoppingCarts = new ArrayList<>();
            Map<String, ReadableCustomerShoppingCartItem> readableCheckedCustomerShoppingCartItems = new HashMap<>();

            for (ShoppingCart shoppingCart : checkedItemsShoppingCarts) {
                ReadableShoppingCart readableShoppingCart = new ReadableShoppingCart();
                readableShoppingCartMapper.merge(shoppingCart, readableShoppingCart, shoppingCart.getMerchantStore(), language);
                readableCheckedItemsShoppingCarts.add(readableShoppingCart);

               readableShoppingCart.getProducts().stream().map(i -> convert2ReadableCustomerShoppingCartItem(i, true)).forEach(item -> {
                   readableCheckedCustomerShoppingCartItems.put(item.getSku(), item);
               });
            }


            // 选中商品计算总价
            LOG.debug("[ReadableCustomerShoppingCartMapper/merge] calculate checked order total");
            BigDecimal subTotal = readableCheckedItemsShoppingCarts.stream().map(s -> s.getSubtotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal total = readableCheckedItemsShoppingCarts.stream().map(s -> s.getTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);

            destination.setSubtotal(subTotal);
            destination.setTotal(total);

//            destination.setDisplaySubTotal(pricingService.getDisplayAmount(subTotal, store));
//            destination.setDisplayTotal(pricingService.getDisplayAmount(total, store));


            // order.total.shipping
            // order.total.subTotal
            // order.total.total
            // order.total.handling
            // order.total.discount
            Map<String, ReadableOrderTotal> orderTotalMaps = new HashMap<>();
            readableCheckedItemsShoppingCarts.stream().forEach(readableShoppingCart -> {
                List<ReadableOrderTotal> orderTotals = readableShoppingCart.getTotals();

                orderTotals.stream().forEach(readableOrderTotal -> {
                    ReadableOrderTotal orderTotal = orderTotalMaps.get(readableOrderTotal.getCode());

                    if (orderTotal == null) {
                        orderTotalMaps.put(readableOrderTotal.getCode(), readableOrderTotal);
                    } else {
                        orderTotal.setValue(readableOrderTotal.getValue().add(orderTotal.getValue()));
                        try {
                            orderTotal.setTotal(pricingService.getDisplayAmount(orderTotal.getValue(), store));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                });
            });

            List<ReadableOrderTotal> totals = orderTotalMaps.values().stream().collect(Collectors.toList());

            // 计算后的购车项合并
            LOG.debug("[ReadableCustomerShoppingCartMapper/merge] merge cart items");
            List<ReadableCustomerShoppingCartItem> readableCustomerShoppingCartItems = new ArrayList<>();
            for (CustomerShoppingCartItem customerShoppingCartItem : source.getLineItems()) {
                ReadableCustomerShoppingCartItem readableCustomerShoppingCartItem = readableCheckedCustomerShoppingCartItems.get(customerShoppingCartItem.getSku());

                if (readableCustomerShoppingCartItem == null) {
                    readableCustomerShoppingCartItem = readableUncheckedCustomerShoppingCartItems.get(customerShoppingCartItem.getSku());
                }
                
                if (readableCustomerShoppingCartItem != null) {
                    readableCustomerShoppingCartItems.add(readableCustomerShoppingCartItem);
                }
            }

            destination.setCartItems(readableCustomerShoppingCartItems);
            destination.setTotals(totals);
            destination.setLanguage(language.getCode());
            destination.setId(source.getId());
        } catch (Exception e) {
            throw new ConversionRuntimeException("An error occured while converting ReadableCustomerShoppingCart", e);
        }

        LogPermUtil.end("ReadableCustomerShoppingCartMapper/merge, customer id:" + source.getCustomerId(), start);

        return destination;
    }

    private ReadableCustomerShoppingCartItem convert2ReadableCustomerShoppingCartItem(ReadableShoppingCartItem readableShoppingCartItem, boolean checked) {
        ReadableCustomerShoppingCartItem readableCustomerShoppingCartItem = new ReadableCustomerShoppingCartItem();
        readableCustomerShoppingCartItem.setSubTotal(readableShoppingCartItem.getSubTotal());
        readableCustomerShoppingCartItem.setDisplaySubTotal(readableShoppingCartItem.getDisplaySubTotal());
        readableCustomerShoppingCartItem.setVariants(readableShoppingCartItem.getVariants());
        readableCustomerShoppingCartItem.setDescription(readableShoppingCartItem.getDescription());
        readableCustomerShoppingCartItem.setProductPrice(readableShoppingCartItem.getProductPrice());
        readableCustomerShoppingCartItem.setFinalPrice(readableShoppingCartItem.getFinalPrice());
        readableCustomerShoppingCartItem.setOriginalPrice(readableShoppingCartItem.getOriginalPrice());
        readableCustomerShoppingCartItem.setImage(readableShoppingCartItem.getImage());
        readableCustomerShoppingCartItem.setImages(readableShoppingCartItem.getImages());
        readableCustomerShoppingCartItem.setChecked(checked);
        readableCustomerShoppingCartItem.setSku(readableShoppingCartItem.getSku());
        readableCustomerShoppingCartItem.setPrice(readableShoppingCartItem.getPrice());
        readableCustomerShoppingCartItem.setQuantity(readableShoppingCartItem.getQuantity());
        readableCustomerShoppingCartItem.setMixAmount(readableShoppingCartItem.getMixAmount());
        readableCustomerShoppingCartItem.setMixNumber(readableShoppingCartItem.getMixNumber());
        readableCustomerShoppingCartItem.setProductShipeable(readableShoppingCartItem.isProductShipeable());

        return readableCustomerShoppingCartItem;
    }
}
