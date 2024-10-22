package com.salesmanager.shop.store.controller.customer.facade;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.catalog.pricing.PricingService;
import com.salesmanager.core.business.services.customer.order.CustomerOrderService;
import com.salesmanager.core.business.services.customer.shoppingcart.CustomerShoppingCartService;
import com.salesmanager.core.business.services.merchant.MerchantStoreService;
import com.salesmanager.core.business.services.payments.combine.CombinePaymentService;
import com.salesmanager.core.business.services.payments.combine.CombineTransactionService;
import com.salesmanager.core.business.services.reference.language.LanguageService;
import com.salesmanager.core.business.services.shoppingcart.ShoppingCartCalculationService;
import com.salesmanager.core.business.services.shoppingcart.ShoppingCartService;
import com.salesmanager.core.constants.ProductType;
import com.salesmanager.core.model.catalog.product.PublishWayEnums;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.customer.order.CustomerOrderCriteria;
import com.salesmanager.core.model.customer.order.CustomerOrder;
import com.salesmanager.core.model.customer.order.CustomerOrderList;
import com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCart;
import com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.Order;
import com.salesmanager.core.model.order.OrderTotal;
import com.salesmanager.core.model.order.OrderTotalSummary;
import com.salesmanager.core.model.order.OrderType;
import com.salesmanager.core.model.order.orderproduct.OrderProduct;
import com.salesmanager.core.model.order.orderstatus.OrderStatus;
import com.salesmanager.core.model.payments.CombineTransaction;
import com.salesmanager.core.model.payments.Payment;
import com.salesmanager.core.model.payments.TransactionType;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.shipping.CartItemType;
import com.salesmanager.core.model.shoppingcart.ShoppingCart;
import com.salesmanager.core.utils.LogPermUtil;
import com.salesmanager.shop.mapper.customer.ReadableCustomerMapper;
import com.salesmanager.shop.mapper.order.ReadableOrderProductMapper;
import com.salesmanager.shop.mapper.order.ReadableOrderTotalMapper;
import com.salesmanager.shop.model.customer.ReadableCustomer;
import com.salesmanager.shop.model.customer.order.*;
import com.salesmanager.shop.model.customer.order.transaction.ReadableCombineTransaction;
import com.salesmanager.shop.model.customer.shoppingcart.PersistableCustomerShoppingCartItem;
import com.salesmanager.shop.model.order.ReadableOrderProduct;
import com.salesmanager.shop.model.order.total.ReadableOrderTotal;
import com.salesmanager.shop.model.order.total.ReadableTotal;
import com.salesmanager.shop.model.order.transaction.PersistablePayment;
import com.salesmanager.shop.model.order.v1.PersistableOrder;
import com.salesmanager.shop.model.shoppingcart.PersistableShoppingCartItem;
import com.salesmanager.shop.populator.customer.PersistableCustomerOrderApiPopulator;
import com.salesmanager.shop.populator.customer.ReadableCombineTransactionPopulator;
import com.salesmanager.shop.populator.customer.ReadableCustomerOrderPopulator;
import com.salesmanager.shop.populator.order.transaction.PersistablePaymentPopulator;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.order.facade.OrderFacade;
import com.salesmanager.shop.store.controller.shoppingCart.facade.ShoppingCartFacade;
import com.salesmanager.shop.utils.LabelUtils;
import com.salesmanager.shop.utils.UniqueIdGenerator;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.salesmanager.core.model.merchant.MerchantStore.DEFAULT_STORE;

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

    @Autowired
    private ReadableCustomerOrderPopulator readableCustomerOrderPopulator;

    @Inject
    private CombineTransactionService combineTransactionService;

    @Inject
    private CustomerFacade customerFacade;

    @Inject
    private PricingService pricingService;

    @Inject
    private PersistablePaymentPopulator paymentPopulator;

    @Inject
    private CombinePaymentService combinePaymentService;

    @Inject
    private CustomerShoppingCartFacadeImpl customerShoppingCartFacade;

    @Override
    public CustomerOrder processCustomerOrder(PersistableCustomerOrder customerOrder, Customer customer, Language language, Locale locale) throws ServiceException {
        Validate.notNull(customerOrder, "CustomerOrder cannot be null");
        Validate.notNull(customer, "Customer cannot be null");
        Validate.notNull(language, "Language cannot be null");
        Validate.notNull(locale, "Locale cannot be null");

        LogPermUtil.start("processCustomerOrder");

        LOGGER.debug("[processCustomerOrder] start process customer order, [customer id: " + customer.getId() + "]");
        long start = System.currentTimeMillis();

        try {
            CustomerOrder modelCustomerOrder = new CustomerOrder();


            CustomerShoppingCart cart = customerShoppingCartService.getCustomerShoppingCart(customer);

            if (cart == null) {
                throw new ServiceException("Customer Shopping cart with Customer id " + customer.getId() + " does not exist");
            }

            Set<CustomerShoppingCartItem> checkedCartItems = cart.getCheckedLineItems();

            if (checkedCartItems.size() <= 0) {
                throw new ServiceException("Customer Shopping cart with Customer id " + customer.getId() + " does not have checked cart items");
            }

            // check whether sample order type
            boolean isSampleType = checkedCartItems.stream().allMatch(p -> CartItemType.SAMPLE.equals(p.getCartItemType()));
            // check whether oem order type
            boolean isOEMProducts = checkedCartItems.stream()
                    .map(CustomerShoppingCartItem::getProduct)
                    .allMatch(product -> product.getType() != null && Objects.equals(product.getType().getCode(), ProductType.OEM.name()));
            // check whether 1688 order type
            boolean allAre1688Products = checkedCartItems.stream()
                    .map(CustomerShoppingCartItem::getProduct)
                    .allMatch(product -> product.getPublishWay() != null && product.getPublishWay().equals(PublishWayEnums.IMPORT_BY_1688));
            if (isSampleType) {
                customerOrder.setOrderType(OrderType.SAMPLE.name());
            } else if (isOEMProducts) {
                customerOrder.setStatus(OrderStatus.PENDING_REVIEW.getValue());
                customerOrder.setOrderType(OrderType.OEM.name());
            } else if (allAre1688Products){
                customerOrder.setStatus(OrderStatus.PENDING_REVIEW.getValue());
                customerOrder.setOrderType(OrderType.PRODUCT_1688.name());
            }


            LOGGER.info("[processCustomerOrder] populate customer order model");
            persistableCustomerOrderApiPopulator.populate(customerOrder, modelCustomerOrder, null, language);

            // 将选中商品 按商户拆分
            LOGGER.info("[processCustomerOrder] split checked items to uniq stores");
            List<MerchantStore> stores = cart.getCheckedLineItems().stream().map(i -> i.getMerchantStore()).collect(Collectors.toList());
            Set<MerchantStore> uniqStores = new TreeSet<>(Comparator.comparing(MerchantStore::getId));
            uniqStores.addAll(stores);

            List<Order> orders = new ArrayList<>();
            List<CustomerShoppingCartItem> lineItems = new ArrayList(cart.getLineItems());
            for (MerchantStore store: uniqStores) {
                // 创建商户购物车
                LOGGER.debug("[processCustomerOrder] create shopping cart, [store id: " + store.getId() + "]");
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
                LOGGER.debug("[processCustomerOrder] modify shopping cart multi, [store id: " + store.getId() + "]");
                List<PersistableShoppingCartItem> persistableShoppingCartItems = getPersistableShoppingCartItemsOfStore(store, lineItems);
                shoppingCartFacade.modifyCartMulti(shoppingCart.getShoppingCartCode(), persistableShoppingCartItems, store, language);

                PersistableOrder persistableOrder = new PersistableOrder();
                persistableOrder.setCustomerId(customer.getId());
                persistableOrder.setShoppingCartId(shoppingCart.getId());
                persistableOrder.setImportMain(customerOrder.getImportMain());
                persistableOrder.setOrderNo(modelCustomerOrder.getOrderNo());
                persistableOrder.setCustomsClearanceNumber(customerOrder.getCustomsClearanceNumber());

                // OrderType is not get from front-end, use product type to replace it.
                // check if whether type
                boolean isMerchantSampleType = persistableShoppingCartItems.stream().allMatch(p -> CartItemType.SAMPLE.name().equals(p.getItemType()));
                persistableOrder.setOrderType(isMerchantSampleType ? OrderType.SAMPLE.name() : customerOrder.getOrderType());
                persistableOrder.setCurrency(customerOrder.getCurrency());
                persistableOrder.setShippingQuote(customerOrder.getShippingQuote());
                persistableOrder.setAddress(customerOrder.getAddress());
                persistableOrder.setStatus(customerOrder.getStatus());
                persistableOrder.setSource(customerOrder.getSource());


                persistableOrder.setTruckTransportationCompany(customerOrder.getTruckTransportationCompany());
                persistableOrder.setTruckType(customerOrder.getTruckType());
                persistableOrder.setTruckModel(customerOrder.getTruckModel());
                persistableOrder.setShippingType(customerOrder.getShippingType());
                persistableOrder.setShippingTransportationType(customerOrder.getShippingTransportationType());
                persistableOrder.setNationalTransportationMethod(customerOrder.getNationalTransportationMethod());
                persistableOrder.setInternationalTransportationMethod(customerOrder.getInternationalTransportationMethod());
                persistableOrder.setPlayThroughOption(customerOrder.getPlayThroughOption());


                LOGGER.debug("[processCustomerOrder] calculate order total summary, [store id: " + store.getId() + "]");
                OrderTotalSummary orderTotalSummary = shoppingCartCalculationService.calculate(shoppingCart, customer, store, language);

                PersistablePayment persistablePayment = new PersistablePayment();
                persistablePayment.setPaymentModule(customerOrder.getPayment().getPaymentModule());
                persistablePayment.setPaymentType(customerOrder.getPayment().getPaymentType());
                persistablePayment.setPaymentToken(customerOrder.getPayment().getPaymentToken());
                persistablePayment.setAmount(String.valueOf(orderTotalSummary.getTotal()));
                persistablePayment.setTransactionType(TransactionType.COMBINESTAMP.name());
                persistableOrder.setPayment(persistablePayment);

                // 创建商户订单
                LOGGER.info("[processCustomerOrder] create order, [store id: " + store.getId() + "]");
                Order order = orderFacade.processOrder(persistableOrder, customer, store, language, locale);
                orders.add(order);
            }

            // 所有商户订单总金额汇总
            BigDecimal total = orders.stream().map(o -> o.getTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);

            modelCustomerOrder.setTotal(total);
            modelCustomerOrder.setOrders(orders);


            LOGGER.info("[processCustomerOrder] process customer order payment");
            customerOrder.getPayment().setAmount(String.valueOf(total));
            Payment paymentModel = paymentPopulator.populate(customerOrder.getPayment(), null, language);

            modelCustomerOrder = customerOrderService.processCustomerOrder(modelCustomerOrder, customer, lineItems, paymentModel);


            try {
                LOGGER.info("[processCustomerOrder] save update customer cart");
                customerShoppingCartService.saveOrUpdate(cart);
            } catch (Exception e) {
                LOGGER.error("Cannot save cart " + cart.getId(), e);
            }

            // 移除购物车项
            Set<CustomerShoppingCartItem> existsItems = cart.getCheckedLineItems();
            List<String> skus = lineItems.stream().map(i -> i.getSku()).collect(Collectors.toList());
            for (CustomerShoppingCartItem item: existsItems) {
                if (skus.contains(item.getSku())) {
                    customerShoppingCartService.deleteCustomerShoppingCartItem(item.getId());
                }
            }

            LogPermUtil.end("processCustomerOrder", start);
            return  modelCustomerOrder;
        } catch (Exception e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public CustomerOrder processDirectCustomerOrder(PersistableDirectCustomerOrder persistableDirectCustomerOrder, Customer customer, MerchantStore merchantStore, Language language, Locale locale) throws ServiceException {
        try {
            CustomerShoppingCart cartModel = customerShoppingCartFacade.getCustomerShoppingCartModel(customer);
            Set<com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem> items = cartModel.getLineItems();

            for (com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem anItem: items) {
                anItem.setChecked(false);
            }

            customerShoppingCartFacade.saveOrUpdateCustomerShoppingCart(cartModel);

            PersistableCustomerShoppingCartItem cartItem = new PersistableCustomerShoppingCartItem();
            cartItem.setSku(persistableDirectCustomerOrder.getSku());
            cartItem.setQuantity(persistableDirectCustomerOrder.getQuantity());
            cartItem.setChecked(true);
            customerShoppingCartFacade.modifyCart(customer, cartItem, merchantStore, language);

            CustomerOrder customerOrder = this.processCustomerOrder(persistableDirectCustomerOrder, customer, language, locale);

            return customerOrder;
        } catch (Exception e) {
            throw new ServiceException(e);
        }

    }

    private String uniqueShoppingCartCode() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    @Override
    public ReadableCustomerOrderConfirmation orderConfirmation(CustomerOrder customerOrder, Customer customer, Language language, Long orderId) throws Exception {
        Validate.notNull(customerOrder, "CustomerOrder cannot be null");
        Validate.notNull(customer, "Customer cannot be null");

        long start = LogPermUtil.start("CustomerOrderFacade/orderConfirmation, customer order id:" + customerOrder.getId());
        ReadableCustomerOrderConfirmation orderConfirmation = new ReadableCustomerOrderConfirmation();

        ReadableCustomer readableCustomer = readableCustomerMapper.convert(customer, null, language);
        orderConfirmation.setBilling(readableCustomer.getBilling());
        orderConfirmation.setDelivery(readableCustomer.getDelivery());
        orderConfirmation.setOrderNo(customerOrder.getOrderNo());
        LOGGER.debug("[CustomerOrderFacade/orderConfirmation] set order total");
        ReadableTotal readableTotal = new ReadableTotal();

        List<Order> orders = customerOrder.getOrders();
        if (orderId != null) {
            // if order id is not null, means partial confirm
            orders = orders.stream().filter(o -> Objects.equals(o.getId(), orderId)).collect(Collectors.toList());
        }

        Set<OrderTotal> orderTotals = new HashSet<>();
        Set<OrderProduct> orderProducts = new HashSet<>();
        for (Order order : orders) {
            orderTotals.addAll(order.getOrderTotal());
            orderProducts.addAll(order.getOrderProducts());
        }

        MerchantStore merchantStore = merchantStoreService.getByCode(DEFAULT_STORE);

        List<ReadableOrderTotal> readableTotals = orderTotals.stream()
                .sorted(Comparator.comparingInt(OrderTotal::getSortOrder))
                .map(tot -> convertOrderTotal(tot, merchantStore, language))
                .collect(Collectors.toList());


        readableTotal.setTotals(readableTotals);
        orderConfirmation.setTotal(readableTotal);

        LOGGER.debug("[CustomerOrderFacade/orderConfirmation] set order product");
        List<ReadableOrderProduct> products = orderProducts.stream().map(pr -> convertOrderProduct(pr, merchantStore, language)).collect(Collectors.toList());
        orderConfirmation.setProducts(products);

        if (customerOrder.getPaymentType() != null) {
            orderConfirmation.setPayment(customerOrder.getPaymentType().name());
        }

        orderConfirmation.setId(customerOrder.getId());

        LOGGER.debug("[CustomerOrderFacade/orderConfirmation] set order transaction");
        CombineTransaction combineTransaction = combineTransactionService.lastCombineTransaction(customerOrder);
        if (orderId != null) {
            // return partial pay amount
            combineTransaction.setAmount(orders.get(0).getTotal());
        }

        ReadableCombineTransaction readableCombineTransaction = new ReadableCombineTransaction();
        ReadableCombineTransactionPopulator populator = new ReadableCombineTransactionPopulator();
        populator.setPricingService(pricingService);
        populator.populate(combineTransaction, readableCombineTransaction, merchantStore, language);

        orderConfirmation.setTransaction(readableCombineTransaction);

        LogPermUtil.end("CustomerOrderFacade/orderConfirmation, customer order id:" + customerOrder.getId(), start);
        return orderConfirmation;
    }

    @Override
    public ReadableCustomerOrderList getReadableCustomerOrderList(Customer customer, int start, int maxCount, Language language) throws Exception {
        CustomerOrderCriteria criteria = new CustomerOrderCriteria();
        criteria.setStartIndex(start);
        criteria.setMaxCount(maxCount);
        criteria.setCustomerId(customer.getId());

        return getReadableCustomerOrderList(criteria, language);
    }

    private ReadableCustomerOrderList getReadableCustomerOrderList(CustomerOrderCriteria criteria, Language language) throws ConversionException {
        CustomerOrderList customerOrderList = customerOrderService.getCustomerOrders(criteria);

        List<CustomerOrder> customerOrders = customerOrderList.getCustomerOrders();
        ReadableCustomerOrderList returnList = new ReadableCustomerOrderList();

        if (CollectionUtils.isEmpty(customerOrders)) {
            returnList.setRecordsTotal(0);
            return returnList;
        }

        List<ReadableCustomerOrder> readableCustomerOrders = new ArrayList<>();
        for (CustomerOrder customerOrder : customerOrders) {
            ReadableCustomerOrder readableCustomerOrder = new ReadableCustomerOrder();
            readableCustomerOrderPopulator.populate(customerOrder, readableCustomerOrder, null, language);
            readableCustomerOrders.add(readableCustomerOrder);
        }

        returnList.setCustomerOrders(readableCustomerOrders);
        returnList.setRecordsTotal(customerOrderList.getTotalCount());
        return returnList;
    }

    @Override
    public ReadableCustomerOrder getReadableCustomerOrder(Long customerOrderId, MerchantStore store, Language language) {
        CustomerOrder modelOrder = customerOrderService.getById(customerOrderId);
        if (modelOrder == null) {
            throw new ResourceNotFoundException("CustomerOrder not found with id " + customerOrderId);
        }

        ReadableCustomerOrder readableCustomerOrder = new ReadableCustomerOrder();
        Long customerId = modelOrder.getCustomerId();
        if (customerId != null) {
            ReadableCustomer readableCustomer = customerFacade.getCustomerById(customerId, store, language);
            if (readableCustomer == null) {
                LOGGER.warn("Customer id " + customerId + " not found in order " + customerOrderId);
            } else {
                readableCustomerOrder.setCustomer(readableCustomer);
            }
        }

        try {
            readableCustomerOrderPopulator.populate(modelOrder, readableCustomerOrder, store, language);
        } catch (Exception e) {
            throw new ServiceRuntimeException("Error while getting customer order [" + customerOrderId + "]");
        }

        return readableCustomerOrder;
    }

    @Override
    public ReadableCombineTransaction captureCustomerOrder(MerchantStore store, CustomerOrder customerOrder, Customer customer, Language language) throws Exception {
        CombineTransaction combineTransaction = combinePaymentService.processCapturePayment(customerOrder, customer, store);

        ReadableCombineTransaction transaction = new ReadableCombineTransaction();
        ReadableCombineTransactionPopulator trxPopulator = new ReadableCombineTransactionPopulator();
        trxPopulator.setPricingService(pricingService);

        trxPopulator.populate(combineTransaction, transaction, store, language);

        return transaction;
    }

    @Override
    public ReadableCombineTransaction authorizeCaptureCustomerOrder(MerchantStore store, CustomerOrder customerOrder, Customer customer, Language language) throws Exception {
        CombineTransaction combineTransaction = combinePaymentService.processAuthorizeAndCapturePayment(customerOrder, customer, store);

        ReadableCombineTransaction transaction = new ReadableCombineTransaction();
        ReadableCombineTransactionPopulator trxPopulator = new ReadableCombineTransactionPopulator();
        trxPopulator.setPricingService(pricingService);

        trxPopulator.populate(combineTransaction, transaction, store, language);

        customerOrderService.updateCustomerOrderStatus(customerOrder, OrderStatus.PAYMENT_COMPLETED);

        return transaction;
    }

    @Override
    public ReadableCombineTransaction processPostPayment(MerchantStore store, CustomerOrder customerOrder, Customer customer, Payment payment, Language language) throws Exception {
        CombineTransaction combineTransaction = combinePaymentService.processPostPayment(customerOrder, customer, store, payment);

        ReadableCombineTransaction transaction = new ReadableCombineTransaction();
        ReadableCombineTransactionPopulator trxPopulator = new ReadableCombineTransactionPopulator();
        trxPopulator.setPricingService(pricingService);

        trxPopulator.populate(combineTransaction, transaction, store, language);

        customerOrderService.updateCustomerOrderStatus(customerOrder, OrderStatus.PAYMENT_COMPLETED);

        return transaction;
    }

    private ReadableOrderTotal convertOrderTotal(OrderTotal total, MerchantStore store, Language language) {

        return readableOrderTotalMapper.convert(total, store, language);
    }


    private ReadableOrderProduct convertOrderProduct(OrderProduct product, MerchantStore store, Language language) {

        return readableOrderProductMapper.convert(product, store, language);

    }

    private List<PersistableShoppingCartItem> getPersistableShoppingCartItemsOfStore(MerchantStore store, List<CustomerShoppingCartItem> lineItems) {
        return new ArrayList<>(lineItems.stream().filter(i -> i.getMerchantStore().getId() == store.getId()).filter(i -> i.isChecked())
                .map(s -> this.getPersistableShoppingCartItem(s)).collect(Collectors.toList()));
    }

    private PersistableShoppingCartItem getPersistableShoppingCartItem(CustomerShoppingCartItem lineItem) {
        PersistableShoppingCartItem item = new PersistableShoppingCartItem();
        item.setProduct(lineItem.getSku());
        item.setQuantity(lineItem.getQuantity());
        item.setInternationalTransportationMethod(lineItem.getInternationalTransportationMethod());
        item.setNationalTransportationMethod(lineItem.getNationalTransportationMethod());
        item.setShippingType(lineItem.getShippingType());
        item.setShippingTransportationType(lineItem.getShippingTransportationType());
        item.setTruckModel(lineItem.getTruckModel());
        item.setPlayThroughOption(lineItem.getPlayThroughOption());
        item.setTruckType(lineItem.getTruckType());
        item.setTruckTransportationCompany(lineItem.getTruckTransportationCompany());
        item.setAdditionalServicesIdMap(lineItem.getAdditionalServicesIdMap());
        item.setItemType(lineItem.getCartItemType() == null ? null : lineItem.getCartItemType().name());

//                item.setAttributes(lineItem.getProduct().getAttributes());
//                item.setPromoCode(lineItem.);

        return item;

    }

    @Override
    public CustomerOrder replicateCustomerOrder(CustomerOrder customerOrder, PersistablePayment persistablePayment, Customer customer, Language language) {

        CustomerOrder newCustomerOrder = new CustomerOrder();
        try {
            BeanUtils.copyProperties(newCustomerOrder, customerOrder);
            newCustomerOrder.setId(null);
            newCustomerOrder.getAuditSection().setDateCreated(null);
            newCustomerOrder.getAuditSection().setDateModified(null);
            newCustomerOrder.setOrderNo(UniqueIdGenerator.generateOrderNumber());


            persistablePayment.setAmount(newCustomerOrder.getTotal().toString());

            Payment payment = paymentPopulator.populate(persistablePayment, new Payment(), null, language);

            newCustomerOrder = customerOrderService.processCustomerOrder(newCustomerOrder, customer, null, payment);
        } catch (ConversionException | ServiceException | IllegalAccessException | InvocationTargetException e) {
            LOGGER.error("Error while replicate pay order", e);
            throw new ServiceRuntimeException("Error while replicate pay order", e);
        }

        return newCustomerOrder;
    }
}
