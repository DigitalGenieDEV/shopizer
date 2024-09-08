package com.salesmanager.core.business.services.customer.shoppingcart;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.order.OrderService;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.customer.order.CustomerOrderTotalSummary;
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
import org.apache.commons.lang3.Validate;
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

@Service("customerShoppingCartCalculationService")
public class CustomerShoppingCartCalculationServiceImpl implements CustomerShoppingCartCalculationService{

    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    @Inject
    private CustomerShoppingCartService customerShoppingCartService;

    @Inject
    private OrderService orderService;

    // 用户购物车总金额计算
    @Override
    public CustomerOrderTotalSummary calculate(CustomerShoppingCart cartModel, Customer customer, Language language) throws Exception {
        Validate.notNull(cartModel, "cart cannot be null");
        Validate.notNull(cartModel.getLineItems(), "Cart should have line items.");
//        Validate.notNull(customer, "Customer cannot be null");

        List<MerchantStore> stores = cartModel.getLineItems().stream().map(i -> i.getMerchantStore()).collect(Collectors.toList());
        Set<MerchantStore> uniqStores = new TreeSet<>(Comparator.comparing(MerchantStore::getId));
        uniqStores.addAll(stores);

        // 商户购物车
        List<ShoppingCart> shoppingCarts = uniqStores.stream().map(s -> getShoppingCart(cartModel, s, customer)).collect(Collectors.toList());
        // 商户购物车总金额列表
        List<OrderTotalSummary> orderSummaries = new ArrayList<>();
        for (ShoppingCart s: shoppingCarts) {
            orderSummaries.add(calculateShoppingCartTotal(s, customer, s.getMerchantStore(), language));
        }

        // 汇总商户购物车总金额
        CustomerOrderTotalSummary customerOrderTotalSummary = new CustomerOrderTotalSummary();

        for (OrderTotalSummary orderTotalSummary: orderSummaries) {
            customerOrderTotalSummary.addTotal(orderTotalSummary.getTotal());
            customerOrderTotalSummary.addSubTotal(orderTotalSummary.getSubTotal());
            customerOrderTotalSummary.addTaxTotal(orderTotalSummary.getTaxTotal());
            customerOrderTotalSummary.setAdditionalServicesPriceTotal(orderTotalSummary.getAdditionalServicesPriceTotal());
            customerOrderTotalSummary.setProductHandlingFeePriceTotal(orderTotalSummary.getProductHandlingFeePriceTotal());
            customerOrderTotalSummary.setErpPriceTotal(orderTotalSummary.getErpPriceTotal());
            customerOrderTotalSummary.setShippingPriceTotal(orderTotalSummary.getShippingPriceTotal());
        }
//        BigDecimal subTotal = orderSummaries.stream().reduce()
        return customerOrderTotalSummary;
    }

    // 单个商户购物车金额计算
    private OrderTotalSummary calculateShoppingCartTotal(ShoppingCart cartModel, Customer customer, MerchantStore store, Language language) throws Exception {
        Validate.notNull(cartModel, "cart cannot be null");
        Validate.notNull(cartModel.getLineItems(), "Cart should have line items.");
        Validate.notNull(store, "MerchantStore cannot be null");
        Validate.notNull(customer, "Customer cannot be null");
        try {
            return calculateShoppingCart(cartModel, customer, store, language);
        } catch (Exception e) {
            LOG.error( "Error while calculating shopping cart total" +e );
            throw new ServiceException(e);
        }
    }

    private ShoppingCart getShoppingCart(CustomerShoppingCart customerShoppingCart, MerchantStore store, Customer customer) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setMerchantStore(store);
        shoppingCart.setCustomerId(customer.getId());
        shoppingCart.setIpAddress(customerShoppingCart.getIpAddress());
        shoppingCart.setPromoCode(customerShoppingCart.getPromoCode());
        shoppingCart.setPromoAdded(customerShoppingCart.getPromoAdded());
        shoppingCart.setObsolete(customerShoppingCart.isObsolete());

        Set<ShoppingCartItem> shoppingCartItems = new HashSet<>(customerShoppingCart.getLineItems().stream()
                .filter(i -> i.getMerchantStore().getId() == store.getId()).map(s -> getShoppingCartItem(shoppingCart, s)).collect(Collectors.toList()));

        shoppingCart.setLineItems(shoppingCartItems);

        return shoppingCart;
    }

    private ShoppingCartItem getShoppingCartItem(ShoppingCart shoppingCart, CustomerShoppingCartItem customerShoppingCartItem) {
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

        shoppingCartItem.setTruckType(customerShoppingCartItem.getTruckType());
        shoppingCartItem.setTruckModel(customerShoppingCartItem.getTruckModel());
        shoppingCartItem.setPlayThroughOption(customerShoppingCartItem.getPlayThroughOption());
        shoppingCartItem.setShippingType(customerShoppingCartItem.getShippingType());
        shoppingCartItem.setShippingTransportationType(customerShoppingCartItem.getShippingTransportationType());
        shoppingCartItem.setNationalTransportationMethod(customerShoppingCartItem.getNationalTransportationMethod());
        shoppingCartItem.setAdditionalServicesMap(customerShoppingCartItem.getAdditionalServicesMap());
        shoppingCartItem.setInternationalTransportationMethod(customerShoppingCartItem.getInternationalTransportationMethod());

        return shoppingCartItem;
    }

    private OrderTotalSummary calculateShoppingCart( ShoppingCart shoppingCart, final Customer customer, final MerchantStore store, final Language language) throws Exception {
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
        // TODO product null
//        itemList = itemList.stream().filter(p -> p.getProduct().isAvailable()).collect(Collectors.toList());
        orderSummary.setProducts(itemList);


        return orderService.caculateOrder(orderSummary, customer, store, language);
    }

    @Override
    public CustomerOrderTotalSummary calculate(CustomerShoppingCart cartModel, Language language) throws Exception {
        return calculate(cartModel, null, language);
    }
}
