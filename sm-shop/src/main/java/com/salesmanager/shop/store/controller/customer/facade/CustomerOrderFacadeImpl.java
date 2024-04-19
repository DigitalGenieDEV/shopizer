package com.salesmanager.shop.store.controller.customer.facade;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.customer.order.CustomerOrderService;
import com.salesmanager.core.business.services.customer.shoppingcart.CustomerShoppingCartService;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.customer.order.CustomerOrder;
import com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCart;
import com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.Order;
import com.salesmanager.core.model.payments.Payment;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.shoppingcart.ShoppingCart;
import com.salesmanager.shop.model.customer.order.PersistableCustomerOrder;
import com.salesmanager.shop.model.order.v1.PersistableOrder;
import com.salesmanager.shop.model.shoppingcart.PersistableShoppingCartItem;
import com.salesmanager.shop.populator.customer.PersistableCustomerOrderApiPopulator;
import com.salesmanager.shop.store.controller.order.facade.OrderFacade;
import com.salesmanager.shop.store.controller.shoppingCart.facade.ShoppingCartFacade;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service("customerOrderFacade")
public class CustomerOrderFacadeImpl implements CustomerOrderFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerOrderFacadeImpl.class);

    @Inject
    private ShoppingCartFacade shoppingCartFacade;

    @Inject
    private CustomerShoppingCartService customerShoppingCartService;

    @Inject
    private OrderFacade orderFacade;

    @Inject
    private CustomerOrderService customerOrderService;

    @Autowired
    private PersistableCustomerOrderApiPopulator persistableCustomerOrderApiPopulator;

    @Override
    public CustomerOrder processCustomerOrder(PersistableCustomerOrder customerOrder, Customer customer, Language language, Locale locale) throws ServiceException {
        Validate.notNull(customerOrder, "CustomerOrder cannot be null");
        Validate.notNull(customer, "Customer cannot be null");
        Validate.notNull(language, "Language cannot be null");
        Validate.notNull(locale, "Locale cannot be null");

        try {
            CustomerOrder modelCustomerOrder = new CustomerOrder();
            persistableCustomerOrderApiPopulator.populate(customerOrder, modelCustomerOrder, null, language);

            Long customerShoppingCartId = customerOrder.getCustomerShoppingCartId();
            CustomerShoppingCart cart = customerShoppingCartService.getById(customerShoppingCartId);

            if (cart == null) {
                throw new ServiceException("Shopping cart with id " + customerShoppingCartId + " does not exist");
            }

            // 用户购物车按商户拆分
            List<MerchantStore> stores = cart.getLineItems().stream().map(i -> i.getMerchantStore()).collect(Collectors.toList());
            Set<MerchantStore> uniqStores = new TreeSet<>(Comparator.comparing(MerchantStore::getId));
            uniqStores.addAll(stores);

            List<Order> orders = new ArrayList<>();
            List<CustomerShoppingCartItem> lineItems = new ArrayList(cart.getLineItems());
            for (MerchantStore store: uniqStores) {
                // 创建商户购物车
                ShoppingCart shoppingCart = shoppingCartFacade.getShoppingCartModel(customer, store);

                // 商品加入商户购物车
                shoppingCartFacade.modifyCartMulti(shoppingCart.getShoppingCartCode(), getPersistableShoppingCartItemsOfStore(store, lineItems), store, language);

                PersistableOrder persistableOrder = new PersistableOrder();
                persistableOrder.setCustomerId(customer.getId());
                persistableOrder.setShoppingCartId(shoppingCart.getId());
                persistableOrder.setPayment(customerOrder.getPayment());

                // 创建商户订单
                Order order = orderFacade.processOrder(persistableOrder, customer, store, language, locale);
                orders.add(order);
            }

            // 所有商户订单总金额汇总
            BigDecimal total = orders.stream().map(o -> o.getTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);

            modelCustomerOrder.setTotal(total);
            modelCustomerOrder.setOrders(orders);

            Payment paymentModule = new Payment();

            modelCustomerOrder = customerOrderService.processCustomerOrder(modelCustomerOrder, customer, lineItems, paymentModule);

            try {
                customerShoppingCartService.saveOrUpdate(cart);
            } catch (Exception e) {
                LOGGER.error("Cannot save cart " + cart.getId(), e);
            }

            return  modelCustomerOrder;
        } catch (Exception e) {
            throw new ServiceException(e);
        }

    }

    private List<PersistableShoppingCartItem> getPersistableShoppingCartItemsOfStore(MerchantStore store, List<CustomerShoppingCartItem> lineItems) {
        return new ArrayList<>(lineItems.stream().filter(i -> i.getMerchantStore().getId() == store.getId())
                .map(s -> this.getPersistableShoppingCartItem(s)).collect(Collectors.toList()));
    }

    private PersistableShoppingCartItem getPersistableShoppingCartItem(CustomerShoppingCartItem lineItem) {
        PersistableShoppingCartItem item = new PersistableShoppingCartItem();
        item.setProduct(lineItem.getSku());
        item.setQuantity(lineItem.getQuantity());
//                item.setAttributes(lineItem.getProduct().getAttributes());
//                item.setPromoCode(lineItem.);

        return item;

    }
}
