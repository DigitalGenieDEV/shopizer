package com.salesmanager.shop.store.controller.customer.facade;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.customer.order.CustomerOrderService;
import com.salesmanager.core.business.services.customer.shoppingcart.CustomerShoppingCartService;
import com.salesmanager.core.business.services.merchant.MerchantStoreService;
import com.salesmanager.core.business.services.reference.language.LanguageService;
import com.salesmanager.core.business.services.shoppingcart.ShoppingCartCalculationService;
import com.salesmanager.core.business.services.shoppingcart.ShoppingCartService;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.customer.order.CustomerOrder;
import com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCart;
import com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.Order;
import com.salesmanager.core.model.order.OrderTotal;
import com.salesmanager.core.model.order.OrderTotalSummary;
import com.salesmanager.core.model.order.orderproduct.OrderProduct;
import com.salesmanager.core.model.payments.Payment;
import com.salesmanager.core.model.payments.TransactionType;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.shoppingcart.ShoppingCart;
import com.salesmanager.shop.mapper.customer.ReadableCustomerMapper;
import com.salesmanager.shop.mapper.order.ReadableOrderProductMapper;
import com.salesmanager.shop.mapper.order.ReadableOrderTotalMapper;
import com.salesmanager.shop.model.customer.ReadableCustomer;
import com.salesmanager.shop.model.customer.order.PersistableCustomerOrder;
import com.salesmanager.shop.model.customer.order.ReadableCustomerOrderConfirmation;
import com.salesmanager.shop.model.order.ReadableOrderProduct;
import com.salesmanager.shop.model.order.total.ReadableOrderTotal;
import com.salesmanager.shop.model.order.total.ReadableTotal;
import com.salesmanager.shop.model.order.transaction.PersistablePayment;
import com.salesmanager.shop.model.order.v1.PersistableOrder;
import com.salesmanager.shop.model.shoppingcart.PersistableShoppingCartItem;
import com.salesmanager.shop.populator.customer.PersistableCustomerOrderApiPopulator;
import com.salesmanager.shop.store.controller.order.facade.OrderFacade;
import com.salesmanager.shop.store.controller.shoppingCart.facade.ShoppingCartFacade;
import com.salesmanager.shop.utils.LabelUtils;
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

    @Autowired
    private ReadableCustomerMapper readableCustomerMapper;

    @Autowired
    private ReadableOrderTotalMapper readableOrderTotalMapper;

    @Autowired
    private ReadableOrderProductMapper readableOrderProductMapper;

    @Autowired
    private LabelUtils messages;

    @Autowired
    private LanguageService languageService;

    @Inject
    private ShoppingCartFacade shoppingCartFacade;

    @Inject
    private MerchantStoreService merchantStoreService;

    @Inject
    private CustomerShoppingCartService customerShoppingCartService;

    @Inject
    private OrderFacade orderFacade;

    @Inject
    private CustomerOrderService customerOrderService;

    @Inject
    private ShoppingCartService shoppingCartService;

    @Inject
    private ShoppingCartCalculationService shoppingCartCalculationService;

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
                ShoppingCart shoppingCart = shoppingCartFacade.getShoppingCartModel(customer, store); // 直接创建一个新的购物车

                if(shoppingCart==null){
                    shoppingCart=new ShoppingCart();
                    shoppingCart.setShoppingCartCode(uniqueShoppingCartCode());
                    shoppingCart.setMerchantStore( store );

                    if ( customer != null )
                    {
                        shoppingCart.setCustomerId( customer.getId() );
                    }
                    shoppingCartService.create( shoppingCart );
                }

                // 商品加入商户购物车
                shoppingCartFacade.modifyCartMulti(shoppingCart.getShoppingCartCode(), getPersistableShoppingCartItemsOfStore(store, lineItems), store, language);

                PersistableOrder persistableOrder = new PersistableOrder();
                persistableOrder.setCustomerId(customer.getId());
                persistableOrder.setShoppingCartId(shoppingCart.getId());

                persistableOrder.setCurrency(customerOrder.getCurrency());
                persistableOrder.setShippingQuote(customerOrder.getShippingQuote());

                OrderTotalSummary orderTotalSummary = shoppingCartCalculationService.calculate(shoppingCart, customer, store, language);

                PersistablePayment persistablePayment = new PersistablePayment();
                persistablePayment.setPaymentModule(customerOrder.getPayment().getPaymentModule());
                persistablePayment.setPaymentType(customerOrder.getPayment().getPaymentType());
                persistablePayment.setPaymentToken(customerOrder.getPayment().getPaymentToken());
                persistablePayment.setAmount(String.valueOf(orderTotalSummary.getTotal()));
                persistablePayment.setTransactionType(TransactionType.COMBINESTAMP.name());
                persistableOrder.setPayment(persistablePayment);


//                customerOrder.getPayment().setAmount(orderTotalSummary.ge);
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

    private String uniqueShoppingCartCode() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    @Override
    public ReadableCustomerOrderConfirmation orderConfirmation(CustomerOrder customerOrder, Customer customer, Language language) {
        Validate.notNull(customerOrder, "CustomerOrder cannot be null");
        Validate.notNull(customer, "Customer cannot be null");

        ReadableCustomerOrderConfirmation orderConfirmation = new ReadableCustomerOrderConfirmation();

        ReadableCustomer readableCustomer = readableCustomerMapper.convert(customer, null, language);
        orderConfirmation.setBilling(readableCustomer.getBilling());
        orderConfirmation.setDelivery(readableCustomer.getDelivery());

        ReadableTotal readableTotal = new ReadableTotal();

        List<Order> orders = customerOrder.getOrders();
        Set<OrderTotal> orderTotals = new HashSet<>();
        Set<OrderProduct> orderProducts = new HashSet<>();
        for (Order order : orders) {
            orderTotals.addAll(order.getOrderTotal());
            orderProducts.addAll(order.getOrderProducts());
        }

        MerchantStore merchantStore = merchantStoreService.getById(1);


        List<ReadableOrderTotal> readableTotals = orderTotals.stream()
                .sorted(Comparator.comparingInt(OrderTotal::getSortOrder))
                .map(tot -> convertOrderTotal(tot, merchantStore, language))
                .collect(Collectors.toList());

        readableTotal.setTotals(readableTotals);
        orderConfirmation.setTotal(readableTotal);

        List<ReadableOrderProduct> products = orderProducts.stream().map(pr -> convertOrderProduct(pr, merchantStore, language)).collect(Collectors.toList());
        orderConfirmation.setProducts(products);

        if (customerOrder.getPaymentType() != null) {
            orderConfirmation.setPayment(customerOrder.getPaymentType().name());
        }

        orderConfirmation.setId(customerOrder.getId());
        return orderConfirmation;
    }

    private ReadableOrderTotal convertOrderTotal(OrderTotal total, MerchantStore store, Language language) {

        return readableOrderTotalMapper.convert(total, store, language);
    }


    private ReadableOrderProduct convertOrderProduct(OrderProduct product, MerchantStore store, Language language) {

        return readableOrderProductMapper.convert(product, store, language);

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
