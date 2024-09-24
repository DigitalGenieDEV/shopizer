package com.salesmanager.core.business.services.order;

import com.alibaba.fastjson.JSON;
import com.salesmanager.core.business.constants.Constants;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.fulfillment.service.AdditionalServicesService;
import com.salesmanager.core.business.modules.order.InvoiceModule;
import com.salesmanager.core.business.repositories.order.OrderRepository;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.business.services.catalog.product.erp.ErpService;
import com.salesmanager.core.business.services.catalog.product.erp.ProductMaterialService;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.business.services.order.ordertotal.OrderTotalService;
import com.salesmanager.core.business.services.payments.PaymentService;
import com.salesmanager.core.business.services.payments.TransactionService;
import com.salesmanager.core.business.services.shipping.ShippingService;
import com.salesmanager.core.business.services.shoppingcart.ShoppingCartService;
import com.salesmanager.core.business.services.tax.TaxService;
import com.salesmanager.core.business.utils.AdditionalServicesUtils;
import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.PublishWayEnums;
import com.salesmanager.core.model.catalog.product.availability.ProductAvailability;
import com.salesmanager.core.model.catalog.product.price.FinalPrice;
import com.salesmanager.core.model.common.UserContext;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.fulfillment.AdditionalServices;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.*;
import com.salesmanager.core.model.order.orderproduct.OrderProduct;
import com.salesmanager.core.model.order.orderstatus.OrderStatus;
import com.salesmanager.core.model.order.orderstatus.OrderStatusHistory;
import com.salesmanager.core.model.payments.Payment;
import com.salesmanager.core.model.payments.Transaction;
import com.salesmanager.core.model.payments.TransactionType;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.shipping.ShippingConfiguration;
import com.salesmanager.core.model.shipping.ShippingTransportationType;
import com.salesmanager.core.model.shipping.ShippingType;
import com.salesmanager.core.model.shoppingcart.ShoppingCart;
import com.salesmanager.core.model.shoppingcart.ShoppingCartItem;
import com.salesmanager.core.model.tax.TaxItem;
import com.salesmanager.core.utils.LogPermUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import org.springframework.transaction.annotation.Transactional;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service("orderService")
public class OrderServiceImpl  extends SalesManagerEntityServiceImpl<Long, Order> implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Inject
    private InvoiceModule invoiceModule;

    @Inject
    private ShippingService shippingService;

    @Inject
    private PaymentService paymentService;

    @Inject
    private ProductService productService;

    @Inject
    private TaxService taxService;

    @Inject
    private CustomerService customerService;

    @Inject
    private ShoppingCartService shoppingCartService;

    @Inject
    private TransactionService transactionService;

    @Inject
    private OrderTotalService orderTotalService;

    private final OrderRepository orderRepository;

    @Autowired
    private AdditionalServicesService additionalServicesService;


    @Autowired
    private ProductMaterialService productMaterialService;



    @Autowired
    private ErpService erpService;

    @Inject
    public OrderServiceImpl(OrderRepository orderRepository) {
        super(orderRepository);
        this.orderRepository = orderRepository;
    }

    @Override
    public void addOrderStatusHistory(Order order, OrderStatusHistory history) throws ServiceException {
        order.getOrderHistory().add(history);
        history.setOrder(order);
        update(order);
    }

    @Override
    public void updateOrderStatus(Long orderId, OrderStatus status) {
        orderRepository.updateOrderStatus(orderId, status);
    }

    @Override
    @Transactional
    public Order processOrder(Order order, Customer customer, List<ShoppingCartItem> items, OrderTotalSummary summary, Payment payment, MerchantStore store) throws ServiceException {

        return process(order, customer, items, summary, payment, null, store);
    }

    @Override
    public Order processOrder(Order order, Customer customer, List<ShoppingCartItem> items, OrderTotalSummary summary, Payment payment, Transaction transaction, MerchantStore store) throws ServiceException {
        return process(order, customer, items, summary, payment, transaction, store);
    }

    private Order process(Order order, Customer customer, List<ShoppingCartItem> items, OrderTotalSummary summary, Payment payment, Transaction transaction, MerchantStore store) throws ServiceException {


        Validate.notNull(order, "Order cannot be null");
        Validate.notNull(customer, "Customer cannot be null (even if anonymous order)");
//        Validate.notEmpty(items, "ShoppingCart items cannot be null");
//        Validate.notNull(payment, "Payment cannot be null");
        Validate.notNull(store, "MerchantStore cannot be null");
//        Validate.notNull(summary, "Order total Summary cannot be null");

        UserContext context = UserContext.getCurrentInstance();
        if(context != null) {
            String ipAddress = context.getIpAddress();
            if(!StringUtils.isBlank(ipAddress)) {
                order.setIpAddress(ipAddress);
            }
        }


        //first process payment
//        Transaction processTransaction = paymentService.processPayment(customer, store, payment, items, order);

        if(order.getOrderHistory()==null || order.getOrderHistory().size()==0 || order.getStatus()==null) {
            OrderStatus status = order.getStatus();
            if(status==null) {
                status = OrderStatus.ORDERED;
                order.setStatus(status);
            }
            Set<OrderStatusHistory> statusHistorySet = new HashSet<OrderStatusHistory>();
            OrderStatusHistory statusHistory = new OrderStatusHistory();
            statusHistory.setStatus(status);
            statusHistory.setDateAdded(new Date());
            statusHistory.setOrder(order);
            statusHistorySet.add(statusHistory);
            order.setOrderHistory(statusHistorySet);

        }

        if(customer.getId()==null || customer.getId()==0) {
            customerService.create(customer);
        }

        order.setCustomerId(customer.getId());
        this.create(order);

//        if(transaction!=null) {
//            transaction.setOrder(order);
//            if(transaction.getId()==null || transaction.getId()==0) {
//                transactionService.create(transaction);
//            } else {
//                transactionService.update(transaction);
//            }
//        }

//        if(processTransaction!=null) {
//            processTransaction.setOrder(order);
//            if(processTransaction.getId()==null || processTransaction.getId()==0) {
//                transactionService.create(processTransaction);
//            } else {
//                transactionService.update(processTransaction);
//            }
//        }

        /**
         * decrement inventory
         */
        LOGGER.debug( "Update inventory" );
        Set<OrderProduct> products = order.getOrderProducts();
        for(OrderProduct orderProduct : products) {
//            orderProduct.getProductQuantity();
//            Product p = productService.getById(orderProduct.getId());
            Product p = productService.getBySku(orderProduct.getSku());
            if(p == null)
                throw new ServiceException(ServiceException.EXCEPTION_INVENTORY_MISMATCH);
            for(ProductAvailability availability : p.getAvailabilities()) {
                int qty = availability.getProductQuantity();
                if(qty < orderProduct.getProductQuantity()) {
                    //throw new ServiceException(ServiceException.EXCEPTION_INVENTORY_MISMATCH);
                    LOGGER.error("APP-BACKEND [" + ServiceException.EXCEPTION_INVENTORY_MISMATCH + "]");
                }
                qty = qty - orderProduct.getProductQuantity();
                availability.setProductQuantity(qty);
            }
            productService.update(p);
        }



        return order;
    }

    public OrderTotalSummary caculateOrder(OrderSummary summary, Customer customer, final MerchantStore store, final Language language) throws Exception {
        long start = LogPermUtil.start("OrderService/caculateOrder");
        OrderTotalSummary totalSummary = new OrderTotalSummary();
        List<OrderTotal> orderTotals = new ArrayList<OrderTotal>();
        Map<String,OrderTotal> otherPricesTotals = new HashMap<String,OrderTotal>();

        ShippingConfiguration shippingConfiguration = null;

        BigDecimal grandTotal = new BigDecimal(0);
        grandTotal.setScale(0, RoundingMode.HALF_UP);

        LOGGER.debug("[caculateOrder] calculate order qty price");
        //price by item
        /**
         * qty * price
         * subtotal
         */
        BigDecimal subTotal = new BigDecimal(0);

        //手续费
        BigDecimal totalProductHandlingFeePrice = BigDecimal.ZERO.setScale(0, RoundingMode.UP);

        //运费
        BigDecimal totalShippingPrice = BigDecimal.ZERO.setScale(0, RoundingMode.UP);

        //增值服务费
        BigDecimal totalAdditionalServicesPrice = BigDecimal.ZERO.setScale(0, RoundingMode.UP);

        //erp费用
        BigDecimal erpPrice = BigDecimal.ZERO.setScale(0, RoundingMode.UP);



        subTotal.setScale(0, RoundingMode.UP);
        for(ShoppingCartItem item : summary.getProducts()) {
            if (item.getItemPrice() == null) {
                throw new ServiceException("shopping cart item sku = [" +item.getSku()+"] price is null");
            }

            BigDecimal st = item.getItemPrice().multiply(new BigDecimal(item.getQuantity()));
            item.setSubTotal(st);
            subTotal = subTotal.add(st);
            //Other prices
            FinalPrice finalPrice = item.getFinalPrice();
            if(finalPrice!=null) {
                List<FinalPrice> otherPrices = finalPrice.getAdditionalPrices();
                if(otherPrices!=null) {
                    for(FinalPrice price : otherPrices) {
                        if(!price.isDefaultPrice()) {
                            OrderTotal itemSubTotal = otherPricesTotals.get(price.getProductPrice().getCode());

                            if(itemSubTotal==null) {
                                itemSubTotal = new OrderTotal();
                                itemSubTotal.setModule(Constants.OT_ITEM_PRICE_MODULE_CODE);
                                itemSubTotal.setTitle(Constants.OT_ITEM_PRICE_MODULE_CODE);
                                itemSubTotal.setOrderTotalCode(price.getProductPrice().getCode());
                                itemSubTotal.setOrderTotalType(OrderTotalType.PRODUCT);
                                itemSubTotal.setSortOrder(0);
                                otherPricesTotals.put(price.getProductPrice().getCode(), itemSubTotal);
                            }

                            BigDecimal orderTotalValue = itemSubTotal.getValue();
                            if(orderTotalValue==null) {
                                orderTotalValue = new BigDecimal(0);
                                orderTotalValue.setScale(0, RoundingMode.UP);
                            }

                            orderTotalValue = orderTotalValue.add(price.getFinalPrice());
                            itemSubTotal.setValue(orderTotalValue);
                            if(price.getProductPrice().getProductPriceType().name().equals(OrderValueType.ONE_TIME)) {
                                subTotal = subTotal.add(price.getFinalPrice());
                            }
                        }
                    }
                }
            }


            Set<Category> categories = item.getProduct().getCategories();
            List<Category> sortedCategories = new ArrayList<>(categories);
            sortedCategories.sort((c1, c2) -> c2.getDepth().compareTo(c1.getDepth()));

            for (Category category : sortedCategories) {
                if (category != null ) {
                    if (StringUtils.isNotEmpty(category.getHandlingFeeFor1688())
                            && item.getProduct().getPublishWay() != null
                            && item.getProduct().getPublishWay() == PublishWayEnums.IMPORT_BY_1688 ){
                        BigDecimal handlingFee = new BigDecimal(category.getHandlingFeeFor1688()).setScale(3, RoundingMode.UP);
                        BigDecimal handlingFeeDecimal = handlingFee.divide(new BigDecimal("100"));
                        BigDecimal itemPrice = item.getItemPrice();
                        BigDecimal finalHandlingFeePrice = itemPrice.multiply(handlingFeeDecimal).setScale(0, RoundingMode.UP);
                        finalHandlingFeePrice = finalHandlingFeePrice.multiply(new BigDecimal(item.getQuantity()));
                        totalProductHandlingFeePrice = totalProductHandlingFeePrice.add(finalHandlingFeePrice);
                        break;
                    }
                    if (StringUtils.isNotEmpty(category.getHandlingFee())){
                        BigDecimal handlingFee = new BigDecimal(category.getHandlingFee()).setScale(3, RoundingMode.UP);
                        BigDecimal handlingFeeDecimal = handlingFee.divide(new BigDecimal("100"));
                        BigDecimal itemPrice = item.getItemPrice();
                        BigDecimal finalHandlingFeePrice = itemPrice.multiply(handlingFeeDecimal).setScale(0, RoundingMode.UP);
                        finalHandlingFeePrice = finalHandlingFeePrice.multiply(new BigDecimal(item.getQuantity()));
                        totalProductHandlingFeePrice = totalProductHandlingFeePrice.add(finalHandlingFeePrice);
                        break;
                    }
                }
            }


            //运费
            ShippingType shippingType = item.getShippingType();
            //跨境运费处理
            if (shippingType != null && shippingType == shippingType.INTERNATIONAL){
                switch(item.getNationalTransportationMethod()){
                    case SHIPPING:
                        //todo 计算价格
                        break;
                    case AIR_TRANSPORTATION:
                        //todo 计算价格
                        break;
                }

            }

            //国内运费处理逻辑
            if (shippingType != null && shippingType == shippingType.NATIONAL){
                //委托配送价格
                if (ShippingTransportationType.COMMISSIONED_DELIVERY == item.getShippingTransportationType()
                    && item.getNationalTransportationMethod() !=null){
                    switch(item.getNationalTransportationMethod()){
                        case TRUCK:
                            String truckModel = item.getTruckModel();
                            String truckType = item.getTruckType();
                            //todo计算金额
                            break;
                        case SHIPPING:
                            break;

                        case LOGISTICS:
                            break;

                        case URGENT_DELIVERY:
                            break;

                        case DIRECT_DELIVERY:
                            break;
                    }
                    //todo 计算费用
                }
            }

            //additionalServicePrice
            String additionalServicesMap = item.getAdditionalServicesIdMap();
            if (StringUtils.isNotEmpty(additionalServicesMap)){
                Map<String, String> additionalServiceMapFromJson = (Map<String, String>) JSON.parse(additionalServicesMap);

                Set<String> keySet = additionalServiceMapFromJson.keySet();

                for(String id : keySet){
                    AdditionalServices additionalServices = additionalServicesService.queryAdditionalServicesById(Long.valueOf(id));

                    String price = AdditionalServicesUtils.getPrice(additionalServices, additionalServiceMapFromJson.get(id) == null ? 0 :Integer.valueOf(additionalServiceMapFromJson.get(id)), item.getQuantity());

                    totalAdditionalServicesPrice = totalAdditionalServicesPrice.add(new BigDecimal(price).setScale(0, RoundingMode.UP));
                }
            }


//            //erp
//            List<ProductMaterial> productMaterials = productMaterialService.queryByProductId(item.getProductId());
//            if (CollectionUtils.isNotEmpty(productMaterials)){
//                for (ProductMaterial productMaterial : productMaterials ){
//                    Long materialId = productMaterial.getMaterialId();
//                    Material material  = erpService.getById(materialId);
//                    BigDecimal price = material.getPrice();
//                    Long weight = productMaterial.getWeight();
//                    BigDecimal productErpPrice = price.multiply(BigDecimal.valueOf(weight)).setScale(2, RoundingMode.HALF_UP);
//                    erpPrice = erpPrice.add(productErpPrice);
//                }
//            }


        }

        LOGGER.debug("[caculateOrder] calculate order total");
        //only in order page, otherwise invokes too many processing
        if(
                OrderSummaryType.ORDERTOTAL.name().equals(summary.getOrderSummaryType().name()) ||
                        OrderSummaryType.SHOPPINGCART.name().equals(summary.getOrderSummaryType().name())

        ) {

            //Post processing order total variation modules for sub total calculation - drools, custom modules
            //may affect the sub total
            OrderTotalVariation orderTotalVariation = orderTotalService.findOrderTotalVariation(summary, customer, store, language);

            int currentCount = 10;

            if(CollectionUtils.isNotEmpty(orderTotalVariation.getVariations())) {
                for(OrderTotal variation : orderTotalVariation.getVariations()) {
                    variation.setSortOrder(currentCount++);
                    orderTotals.add(variation);
                    subTotal = subTotal.subtract(variation.getValue());
                }
            }
        }


        totalSummary.setSubTotal(subTotal);


        //商品费用
        OrderTotal orderTotalSubTotal = new OrderTotal();
        orderTotalSubTotal.setModule(Constants.OT_SUBTOTAL_MODULE_CODE);
        orderTotalSubTotal.setOrderTotalType(OrderTotalType.SUBTOTAL);
        orderTotalSubTotal.setOrderTotalCode("order.total.subtotal");
        orderTotalSubTotal.setTitle(Constants.OT_SUBTOTAL_MODULE_CODE);
        orderTotalSubTotal.setSortOrder(5);
        orderTotalSubTotal.setValue(subTotal);
        orderTotals.add(orderTotalSubTotal);


        //加价费用
        OrderTotal handlingubTotal = new OrderTotal();
        handlingubTotal.setModule(Constants.OT_HANDLING_MODULE_CODE);
        handlingubTotal.setOrderTotalType(OrderTotalType.HANDLING);
        handlingubTotal.setOrderTotalCode("order.total.handling");
        handlingubTotal.setTitle(Constants.OT_HANDLING_MODULE_CODE);
        handlingubTotal.setText("order.total.handling");
        handlingubTotal.setSortOrder(100);
        handlingubTotal.setValue(totalProductHandlingFeePrice);
        orderTotals.add(handlingubTotal);


        //运费
        OrderTotal shippingSubTotal = new OrderTotal();
        shippingSubTotal.setModule(Constants.OT_SHIPPING_MODULE_CODE);
        shippingSubTotal.setOrderTotalType(OrderTotalType.SHIPPING);
        shippingSubTotal.setOrderTotalCode("order.total.shipping");
        shippingSubTotal.setTitle(Constants.OT_SHIPPING_MODULE_CODE);
        shippingSubTotal.setSortOrder(103);
        shippingSubTotal.setText("order.total.shipping");
        shippingSubTotal.setValue(totalShippingPrice);
        orderTotals.add(shippingSubTotal);


        //增值服务费用
        OrderTotal additionalServicesSubTotal = new OrderTotal();
        additionalServicesSubTotal.setModule(Constants.OT_ADDITIONAL_SERVICE_PRICE_MODULE_CODE);
        additionalServicesSubTotal.setOrderTotalType(OrderTotalType.ADDITIONAL_SERVICE);
        additionalServicesSubTotal.setOrderTotalCode("order.total.additionalServices");
        additionalServicesSubTotal.setSortOrder(102);
        additionalServicesSubTotal.setText("order.total.additionalServices");
        additionalServicesSubTotal.setValue(totalAdditionalServicesPrice);
        additionalServicesSubTotal.setTitle(Constants.OT_ADDITIONAL_SERVICE_PRICE_MODULE_CODE);
        orderTotals.add(additionalServicesSubTotal);



        //erp费用
//        OrderTotal erpSubTotal = new OrderTotal();
//        erpSubTotal.setModule(Constants.OT_ERP_MODULE_CODE);
//        erpSubTotal.setOrderTotalType(OrderTotalType.ERP);
//        erpSubTotal.setOrderTotalCode("order.total.erp");
//        erpSubTotal.setSortOrder(102);
//        erpSubTotal.setText("order.total.erp");
//        erpSubTotal.setValue(erpPrice);
//        erpSubTotal.setTitle(Constants.OT_ERP_MODULE_CODE);
//        grandTotal=grandTotal.add(erpPrice);

        LOGGER.debug("[caculateOrder] calculate order shipping");
        //shipping
//        if(summary.getShippingSummary()!=null) {
//            //check handling fees
//            shippingConfiguration = shippingService.getShippingConfiguration(store);
//            if(summary.getShippingSummary().getHandling()!=null && summary.getShippingSummary().getHandling().doubleValue()>0) {
//                if(shippingConfiguration.getHandlingFees()!=null && shippingConfiguration.getHandlingFees().doubleValue()>0) {
//
//                }
//            }
//        }

        LOGGER.info("[caculateOrder] calculate order tax");
        //tax
        List<TaxItem> taxes = taxService.calculateTax(summary, customer, store, language);
        if(taxes!=null && taxes.size()>0) {
            BigDecimal totalTaxes = new BigDecimal(0);
            totalTaxes.setScale(0, RoundingMode.UP);
            int taxCount = 200;
            for(TaxItem tax : taxes) {

                OrderTotal taxLine = new OrderTotal();
                taxLine.setModule(Constants.OT_TAX_MODULE_CODE);
                taxLine.setOrderTotalType(OrderTotalType.TAX);
                taxLine.setOrderTotalCode(tax.getLabel());
                taxLine.setSortOrder(taxCount);
                taxLine.setTitle(Constants.OT_TAX_MODULE_CODE);
                taxLine.setText(tax.getLabel());
                taxLine.setValue(tax.getItemPrice());

                totalTaxes = totalTaxes.add(tax.getItemPrice());
                orderTotals.add(taxLine);
                //grandTotal=grandTotal.add(tax.getItemPrice());

                taxCount ++;

            }
            totalSummary.setTaxTotal(totalTaxes);
        }

        grandTotal = grandTotal.add(totalProductHandlingFeePrice)
                .add(erpPrice).add(subTotal)
                .add(totalShippingPrice)
                .add(totalAdditionalServicesPrice);

        // grand total
        OrderTotal orderTotal = new OrderTotal();
        orderTotal.setModule(Constants.OT_TOTAL_MODULE_CODE);
        orderTotal.setOrderTotalType(OrderTotalType.TOTAL);
        orderTotal.setOrderTotalCode("order.total.total");
        orderTotal.setTitle(Constants.OT_TOTAL_MODULE_CODE);
        //orderTotal.setText("order.total.total");
        orderTotal.setSortOrder(500);
        orderTotal.setValue(grandTotal);

        orderTotals.add(orderTotal);

        totalSummary.setProductHandlingFeePriceTotal(totalProductHandlingFeePrice);
        totalSummary.setErpPriceTotal(erpPrice);
        totalSummary.setShippingPriceTotal(totalShippingPrice);
        totalSummary.setAdditionalServicesPriceTotal(totalAdditionalServicesPrice);
        totalSummary.setTotal(grandTotal);
        totalSummary.setTotals(orderTotals);

        LogPermUtil.end("OrderService/caculateOrder", start);
        return totalSummary;

    }




    static void buildShippingSummary(List<OrderTotal> orderTotals, BigDecimal grandTotal,
                              Boolean isFreeShipping, BigDecimal shippingPrice){

    }


    @Override
    public OrderTotalSummary caculateOrderTotal(final OrderSummary orderSummary, final Customer customer, final MerchantStore store, final Language language) throws ServiceException {
        Validate.notNull(orderSummary,"Order summary cannot be null");
        Validate.notNull(orderSummary.getProducts(),"Order summary.products cannot be null");
        Validate.notNull(store,"MerchantStore cannot be null");
        Validate.notNull(customer,"Customer cannot be null");

        try {
            return caculateOrder(orderSummary, customer, store, language);
        } catch (Exception e) {
            throw new ServiceException(e);
        }

    }



    @Override
    public OrderTotalSummary caculateOrderTotal(final OrderSummary orderSummary, final MerchantStore store, final Language language) throws ServiceException {
        Validate.notNull(orderSummary,"Order summary cannot be null");
        Validate.notNull(orderSummary.getProducts(),"Order summary.products cannot be null");
        Validate.notNull(store,"MerchantStore cannot be null");

        try {
            return caculateOrder(orderSummary, null, store, language);
        } catch (Exception e) {
            throw new ServiceException(e);
        }

    }

    private OrderTotalSummary caculateShoppingCart( ShoppingCart shoppingCart, final Customer customer, final MerchantStore store, final Language language) throws Exception {

        long start = LogPermUtil.start("OrderService/caculateShoppingCart");
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
                // TODO 商户购物车仅做计算 不更新数据库
//                shoppingCartService.saveOrUpdate(shoppingCart);
            }
        }

        List<ShoppingCartItem> itemList = new ArrayList<ShoppingCartItem>(shoppingCart.getLineItems());
        //filter out unavailable
        itemList = itemList.stream().filter(p -> !p.isObsolete()).filter(p -> p.getProduct().isAvailable()).collect(Collectors.toList());
        orderSummary.setProducts(itemList);


        OrderTotalSummary orderTotalSummary = caculateOrder(orderSummary, customer, store, language);
        LogPermUtil.end("OrderService/caculateShoppingCart", start);
        return orderTotalSummary;

    }


    /**
     * <p>Method will be used to calculate Shopping cart total as well will update price for each
     * line items.
     * </p>
     * @param shoppingCart
     * @param customer
     * @param store
     * @param language
     * @return {@link OrderTotalSummary}
     * @throws ServiceException
     *
     */
    @Override
    public OrderTotalSummary calculateShoppingCartTotal(
            final ShoppingCart shoppingCart, final Customer customer, final MerchantStore store,
            final Language language) throws ServiceException {
        Validate.notNull(shoppingCart,"Order summary cannot be null");
        Validate.notNull(customer,"Customery cannot be null");
        Validate.notNull(store,"MerchantStore cannot be null.");
        try {
            return caculateShoppingCart(shoppingCart, customer, store, language);
        } catch (Exception e) {
            LOGGER.error( "Error while calculating shopping cart total" +e );
            throw new ServiceException(e);
        }

    }




    /**
     * <p>Method will be used to calculate Shopping cart total as well will update price for each
     * line items.
     * </p>
     * @param shoppingCart
     * @param store
     * @param language
     * @return {@link OrderTotalSummary}
     * @throws ServiceException
     *
     */
    @Override
    public OrderTotalSummary calculateShoppingCartTotal(
            final ShoppingCart shoppingCart, final MerchantStore store, final Language language)
            throws ServiceException {
        Validate.notNull(shoppingCart,"Order summary cannot be null");
        Validate.notNull(store,"MerchantStore cannot be null");

        try {
            return caculateShoppingCart(shoppingCart, null, store, language);
        } catch (Exception e) {
            LOGGER.error( "Error while calculating shopping cart total" +e );
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(final Order order) throws ServiceException {


        super.delete(order);
    }


    @Override
    public ByteArrayOutputStream generateInvoice(final MerchantStore store, final Order order, final Language language) throws ServiceException {

        Validate.notNull(order.getOrderProducts(),"Order products cannot be null");
        Validate.notNull(order.getOrderTotal(),"Order totals cannot be null");

        try {
            return invoiceModule.createInvoice(store, order, language);
        } catch(Exception e) {
            throw new ServiceException(e);
        }



    }

    @Override
    public Order getOrder(final Long orderId, MerchantStore store ) {
        Validate.notNull(orderId, "Order id cannot be null");
        return orderRepository.findOne(orderId);
    }

    @Override
    public Order getOrder(final Long orderId, Customer customer) {
        Validate.notNull(orderId, "Order id cannot be null");
        Validate.notNull(customer, "Customer cannot be null");
        return orderRepository.findOneByCustomer(orderId, customer.getId());
    }


    /** legacy **/
    @Override
    public OrderList listByStore(final MerchantStore store, final OrderCriteria criteria) {
        return orderRepository.listByStore(store, criteria);
    }

    @Override
    public OrderList listByCustomer(Customer customer, OrderCustomerCriteria criteria) {
        return orderRepository.listByCustomer(customer, criteria);
    }

    @Override
    public Map<String, Integer> countCustomerOrderByType(Customer customer, OrderCustomerCriteria criteria) {
        return orderRepository.countCustomerOrderByType(customer, criteria);
    }

    @Override
    public OrderList getOrders(final OrderCriteria criteria, MerchantStore store) {
        return orderRepository.listOrders(store, criteria);
    }


    @Override
    public void saveOrUpdate(final Order order) throws ServiceException {

        if(order.getId()!=null && order.getId()>0) {
            LOGGER.debug("Updating Order");
            super.update(order);

        } else {
            LOGGER.debug("Creating Order");
            super.create(order);

        }
    }

    @Override
    public boolean hasDownloadFiles(Order order) throws ServiceException {

        Validate.notNull(order,"Order cannot be null");
        Validate.notNull(order.getOrderProducts(),"Order products cannot be null");
        Validate.notEmpty(order.getOrderProducts(),"Order products cannot be empty");

        boolean hasDownloads = false;
        for(OrderProduct orderProduct : order.getOrderProducts()) {

            if(CollectionUtils.isNotEmpty(orderProduct.getDownloads())) {
                hasDownloads = true;
                break;
            }
        }

        return hasDownloads;
    }

    @Override
    public List<Order> getCapturableOrders(MerchantStore store, Date startDate, Date endDate) throws ServiceException {

        List<Transaction> transactions = transactionService.listTransactions(startDate, endDate);

        List<Order> returnOrders = null;

        if(!CollectionUtils.isEmpty(transactions)) {

            returnOrders = new ArrayList<Order>();

            //order id
            Map<Long,Order> preAuthOrders = new HashMap<Long,Order> ();
            //order id
            Map<Long,List<Transaction>> processingTransactions = new HashMap<Long,List<Transaction>> ();

            for(Transaction trx : transactions) {
                Order order = trx.getOrder();
                if(TransactionType.AUTHORIZE.name().equals(trx.getTransactionType().name())) {
                    preAuthOrders.put(order.getId(), order);
                }

                //put transaction
                List<Transaction> listTransactions = null;
                if(processingTransactions.containsKey(order.getId())) {
                    listTransactions = processingTransactions.get(order.getId());
                } else {
                    listTransactions = new ArrayList<Transaction>();
                    processingTransactions.put(order.getId(), listTransactions);
                }
                listTransactions.add(trx);
            }

            //should have when captured
            /**
             * Order id  Transaction type
             * 1          AUTHORIZE
             * 1          CAPTURE
             */

            //should have when not captured
            /**
             * Order id  Transaction type
             * 2          AUTHORIZE
             */

            for(Long orderId : processingTransactions.keySet()) {

                List<Transaction> trx = processingTransactions.get(orderId);
                if(CollectionUtils.isNotEmpty(trx)) {

                    boolean capturable = true;
                    for(Transaction t : trx) {

                        if(TransactionType.CAPTURE.name().equals(t.getTransactionType().name())) {
                            capturable = false;
                        } else if(TransactionType.AUTHORIZECAPTURE.name().equals(t.getTransactionType().name())) {
                            capturable = false;
                        } else if(TransactionType.REFUND.name().equals(t.getTransactionType().name())) {
                            capturable = false;
                        }

                    }

                    if(capturable) {
                        Order o = preAuthOrders.get(orderId);
                        returnOrders.add(o);
                    }

                }


            }
        }

        return returnOrders;
    }


    public static void main(String[] args) {

        List<OrderTotal> orderTotals = new ArrayList<>();
        BigDecimal grandTotal = new BigDecimal(10);
        BigDecimal shippingPrice = new BigDecimal(20);
        buildShippingSummary(orderTotals, grandTotal, false, shippingPrice);

        grandTotal.add(new BigDecimal(33));
        System.out.println(grandTotal.toString());


    }



}
