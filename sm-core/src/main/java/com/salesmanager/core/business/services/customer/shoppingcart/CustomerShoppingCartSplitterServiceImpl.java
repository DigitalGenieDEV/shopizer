package com.salesmanager.core.business.services.customer.shoppingcart;

import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.business.services.order.OrderService;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCart;
import com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.OrderSummary;
import com.salesmanager.core.model.order.OrderSummaryType;
import com.salesmanager.core.model.order.OrderTotalSummary;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.shoppingcart.ShoppingCart;
import com.salesmanager.core.model.shoppingcart.ShoppingCartItem;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service("customerShoppingCartSplitterService")
public class CustomerShoppingCartSplitterServiceImpl implements CustomerShoppingCartSplitterService{

    protected final Logger LOG = LoggerFactory.getLogger(CustomerShoppingCartSplitterServiceImpl.class);

    @Inject
    private OrderService orderService;

    @Inject
    private CustomerService customerService;

    @Override
    public List<ShoppingCart> splitCheckedItemsToShoppingCart(CustomerShoppingCart customerShoppingCart) {
        List<MerchantStore> stores = customerShoppingCart.getCheckedLineItems().stream().map(i -> i.getMerchantStore()).collect(Collectors.toList());
        Set<MerchantStore> uniqStores = new TreeSet<>(Comparator.comparing(MerchantStore::getId));
        uniqStores.addAll(stores);

        Customer customer = customerService.getById(customerShoppingCart.getCustomerId());

        // 商户购物车
        List<ShoppingCart> shoppingCarts = uniqStores.stream().map(s -> getCheckedItemsShoppingCart(customerShoppingCart, s, customer)).collect(Collectors.toList());
        return shoppingCarts;
    }

    @Override
    public List<ShoppingCart> splitUncheckedItemsToShoppingCart(CustomerShoppingCart customerShoppingCart) {
        List<MerchantStore> stores = customerShoppingCart.getUncheckedLineItems().stream().map(i -> i.getMerchantStore()).collect(Collectors.toList());
        Set<MerchantStore> uniqStores = new TreeSet<>(Comparator.comparing(MerchantStore::getId));
        uniqStores.addAll(stores);

        Customer customer = customerService.getById(customerShoppingCart.getCustomerId());

        // 商户购物车
        List<ShoppingCart> shoppingCarts = uniqStores.stream().map(s -> getUncheckedItemsShoppingCart(customerShoppingCart, s, customer)).collect(Collectors.toList());
        return shoppingCarts;
    }

    @Override
    public List<ShoppingCart> splitCheckedItemsToShoppingCart(CustomerShoppingCart customerShoppingCart, Customer customer) {
        List<MerchantStore> stores = customerShoppingCart.getCheckedLineItems().stream().map(i -> i.getMerchantStore()).collect(Collectors.toList());
        Set<MerchantStore> uniqStores = new TreeSet<>(Comparator.comparing(MerchantStore::getId));
        uniqStores.addAll(stores);

        // 商户购物车
        List<ShoppingCart> shoppingCarts = uniqStores.stream().map(s -> getCheckedItemsShoppingCart(customerShoppingCart, s, customer)).collect(Collectors.toList());
        return shoppingCarts;
    }

    @Override
    public ShoppingCart getCheckedItemsShoppingCart(CustomerShoppingCart customerShoppingCart, MerchantStore store, Customer customer) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setMerchantStore(store);
        shoppingCart.setCustomerId(customer.getId());
        shoppingCart.setIpAddress(customerShoppingCart.getIpAddress());
        shoppingCart.setPromoCode(customerShoppingCart.getPromoCode());
        shoppingCart.setPromoAdded(customerShoppingCart.getPromoAdded());
        shoppingCart.setObsolete(customerShoppingCart.isObsolete());

        Set<ShoppingCartItem> shoppingCartItems = new HashSet<>(customerShoppingCart.getCheckedLineItems().stream()
                .filter(i -> i.getMerchantStore().getId() == store.getId()).map(s -> getShoppingCartItem(shoppingCart, s)).collect(Collectors.toList()));

        shoppingCart.setLineItems(shoppingCartItems);

        return shoppingCart;
    }

    @Override
    public ShoppingCart getUncheckedItemsShoppingCart(CustomerShoppingCart customerShoppingCart, MerchantStore store, Customer customer) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setMerchantStore(store);
        shoppingCart.setCustomerId(customer.getId());
        shoppingCart.setIpAddress(customerShoppingCart.getIpAddress());
        shoppingCart.setPromoCode(customerShoppingCart.getPromoCode());
        shoppingCart.setPromoAdded(customerShoppingCart.getPromoAdded());
        shoppingCart.setObsolete(customerShoppingCart.isObsolete());

        Set<ShoppingCartItem> shoppingCartItems = new HashSet<>(customerShoppingCart.getUncheckedLineItems().stream()
                .filter(i -> i.getMerchantStore().getId() == store.getId()).map(s -> getShoppingCartItem(shoppingCart, s)).collect(Collectors.toList()));

        shoppingCart.setLineItems(shoppingCartItems);

        return shoppingCart;
    }

    @Override
    public ShoppingCartItem getShoppingCartItem(ShoppingCart shoppingCart, CustomerShoppingCartItem customerShoppingCartItem) {
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
        shoppingCartItem.setShoppingCart(shoppingCart);
        shoppingCartItem.setProductId(customerShoppingCartItem.getProductId());
        shoppingCartItem.setSku(customerShoppingCartItem.getSku());
        shoppingCartItem.setVariant(customerShoppingCartItem.getVariant());
        shoppingCartItem.setQuantity(customerShoppingCartItem.getQuantity());
//        shoppingCartItem.setAttributes();
        shoppingCartItem.setVariant(customerShoppingCartItem.getVariant());
        shoppingCartItem.setItemPrice(customerShoppingCartItem.getItemPrice());
        shoppingCartItem.setSubTotal(customerShoppingCartItem.getSubTotal());
        shoppingCartItem.setFinalPrice(customerShoppingCartItem.getFinalPrice());
        shoppingCartItem.setProduct(customerShoppingCartItem.getProduct());
        shoppingCartItem.setObsolete(customerShoppingCartItem.isObsolete());
        shoppingCartItem.setProduct(customerShoppingCartItem.getProduct());
        shoppingCartItem.setSku(customerShoppingCartItem.getSku());
        shoppingCartItem.setVariant(customerShoppingCartItem.getVariant());

        shoppingCartItem.setAdditionalServicesIds(customerShoppingCartItem.getAdditionalServicesIds());
        shoppingCartItem.setInternationalTransportationMethod(customerShoppingCartItem.getInternationalTransportationMethod());
        shoppingCartItem.setNationalTransportationMethod(customerShoppingCartItem.getNationalTransportationMethod());
        shoppingCartItem.setShippingType(customerShoppingCartItem.getShippingType());
        shoppingCartItem.setShippingTransportationType(customerShoppingCartItem.getShippingTransportationType());
        shoppingCartItem.setTruckModel(customerShoppingCartItem.getTruckModel());
        shoppingCartItem.setPlayThroughOption(customerShoppingCartItem.getPlayThroughOption());
        shoppingCartItem.setTruckType(customerShoppingCartItem.getTruckType());
        return shoppingCartItem;
    }

    @Override
    public OrderTotalSummary calculateShoppingCart(ShoppingCart shoppingCart, Customer customer, MerchantStore store, Language language) throws Exception {
        OrderSummary orderSummary = new OrderSummary();
        orderSummary.setOrderSummaryType(OrderSummaryType.SHOPPINGCART);

        if(!StringUtils.isBlank(shoppingCart.getPromoCode())) {
            Date promoDateAdded = shoppingCart.getPromoAdded();//promo valid 1 day
            if(promoDateAdded == null) {
                promoDateAdded = new Date();
            }
            Instant instant = promoDateAdded.toInstant();
            ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
            LocalDate date = zdt.toLocalDate();
            //date added < date + 1 day
            LocalDate tomorrow = LocalDate.now().plusDays(1);
            if(date.isBefore(tomorrow)) {
                orderSummary.setPromoCode(shoppingCart.getPromoCode());
            } else {
                //clear promo
                shoppingCart.setPromoCode(null);
//                shoppingCartService.saveOrUpdate(shoppingCart);
            }
        }

        List<ShoppingCartItem> itemList = new ArrayList<ShoppingCartItem>(shoppingCart.getLineItems());
        //filter out unavailable
        itemList = itemList.stream().filter(p -> p.getProduct().isAvailable()).collect(Collectors.toList());
        orderSummary.setProducts(itemList);


        return orderService.caculateOrder(orderSummary, customer, store, language);
    }
}
