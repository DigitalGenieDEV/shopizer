package com.salesmanager.shop.store.controller.customer.facade;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.catalog.pricing.PricingService;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.business.services.catalog.product.attribute.ProductAttributeService;
import com.salesmanager.core.business.services.customer.shoppingcart.CustomerShoppingCartCalculationService;
import com.salesmanager.core.business.services.customer.shoppingcart.CustomerShoppingCartService;
import com.salesmanager.core.business.services.merchant.MerchantStoreService;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.availability.ProductAvailability;
import com.salesmanager.core.model.catalog.product.price.FinalPrice;
import com.salesmanager.core.model.catalog.product.variant.ProductVariant;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCart;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.mapper.customer.ReadableCustomerShoppingCartMapper;
import com.salesmanager.shop.model.customer.shoppingcart.CustomerShoppingCartData;
import com.salesmanager.shop.model.customer.shoppingcart.CustomerShoppingCartItem;
import com.salesmanager.shop.model.customer.shoppingcart.PersistableCustomerShoppingCartItem;
import com.salesmanager.shop.model.customer.shoppingcart.ReadableCustomerShoppingCart;
import com.salesmanager.shop.populator.customer.CustomerShoppingCartDataPopulator;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.utils.DateUtil;
import com.salesmanager.shop.utils.ImageFilePath;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.salesmanager.core.business.constants.Constants.DEFAULT_STORE;

@Service(value = "customerShoppingCartFacade")
public class CustomerShoppingCartFacadeImpl implements CustomerShoppingCartFacade {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerShoppingCartFacadeImpl.class);

    @Inject
    private CustomerShoppingCartService customerShoppingCartService;

    @Inject
    private CustomerShoppingCartCalculationService customerShoppingCartCalculationService;

    @Inject
    private ProductService productService;

    @Inject
    private PricingService pricingService;

    @Inject
    private MerchantStoreService merchantStoreService;

    @Inject
    private ProductAttributeService productAttributeService;

    @Inject
    @Qualifier("img")
    private ImageFilePath imageUtils;

    @Autowired
    private ReadableCustomerShoppingCartMapper readableCustomerShoppingCartMapper;

    @Override
    public CustomerShoppingCartData addItemsToCart(CustomerShoppingCartData customerShoppingCartData, CustomerShoppingCartItem item, Language language, Customer customer) throws Exception {
        CustomerShoppingCart cartModel = null;

        if (customerShoppingCartData != null && StringUtils.isBlank(item.getCode())) {
            item.setCode(customerShoppingCartData.getCode());
        }

        if (!StringUtils.isBlank(item.getCode())) {
            cartModel = getCustomerShoppingCartModel(item.getCode());
            if (cartModel == null) {
                cartModel = createCartModel(customerShoppingCartData.getCode(), customer);
            }
        }

        if (cartModel == null) {
            final String customerShoppingCartCode = StringUtils.isNotBlank(customerShoppingCartData.getCode()) ? customerShoppingCartData.getCode(): null;
            cartModel = createCartModel(customerShoppingCartCode, customer);
        }

        MerchantStore store = merchantStoreService.getById(item.getMerchantId());

        if (store == null) {
            throw new ServiceException("merchantStore missing error");
        }

        com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem customerShoppingCartItem = createCartItem(cartModel, item, store);

        cartModel.getLineItems().add(customerShoppingCartItem);

        customerShoppingCartService.saveOrUpdate(cartModel);

        cartModel = customerShoppingCartService.getById(cartModel.getId());

        //
        customerShoppingCartCalculationService.calculate(cartModel, language);

        CustomerShoppingCartDataPopulator customerShoppingCartDataPopulator = new CustomerShoppingCartDataPopulator();
        customerShoppingCartDataPopulator.setCustomerShoppingCartCalculationService(customerShoppingCartCalculationService);
        customerShoppingCartDataPopulator.setPricingService(pricingService);
        customerShoppingCartDataPopulator.setImageUtils(imageUtils);

        return customerShoppingCartDataPopulator.populate(cartModel, store, language);
    }

    private com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem createCartItem(final CustomerShoppingCart cartModel, final CustomerShoppingCartItem customerShoppingCartItem, final MerchantStore store) throws Exception {
        Product product = productService.getBySku(customerShoppingCartItem.getSku(), store, store.getDefaultLanguage());

        if (product == null) {
            throw new Exception("Item with sku " + customerShoppingCartItem.getSku() + " does not exist");
        }

        if (product.getMerchantStore().getId().intValue() != store.getId().intValue()) {
            throw new Exception(
                    "Item with sku " + customerShoppingCartItem.getSku() + " does not belong to merchant " + store.getId());
        }

        /**
         * Check if product quantity is 0 Check if product is available Check if date
         * available <= now
         */

        Set<ProductAvailability> availabilities = product.getAvailabilities();
        if (availabilities == null) {

            throw new Exception("Item with id " + product.getId() + " is not properly configured");

        }

        for (ProductAvailability availability : availabilities) {
            if (availability.getProductQuantity() == null || availability.getProductQuantity().intValue() == 0) {
                throw new Exception("Item with id " + product.getId() + " is not available");
            }
        }

        if (!product.isAvailable()) {
            throw new Exception("Item with id " + product.getId() + " is not available");
        }

        if (!DateUtil.dateBeforeEqualsDate(product.getDateAvailable(), new Date())) {
            throw new Exception("Item with id " + product.getId() + " is not available");
        }

        com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem item = customerShoppingCartService.populateCustomerShoppingCartItem(product, store);
        item.setQuantity(customerShoppingCartItem.getQuantity());
        item.setCustomerShoppingCart(cartModel);

        // TODO attributes

        return item;
    }

    private com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem createCartItem(CustomerShoppingCart cartModel, PersistableCustomerShoppingCartItem customerShoppingCartItem, MerchantStore store) throws Exception {
        // USE Product sku
        Product product = null;

        product = productService.getBySku(customerShoppingCartItem.getProduct(), store, store.getDefaultLanguage());// todo use
        // language
        // from api
        // request
        if (product == null) {
            throw new ResourceNotFoundException(
                    "Product with sku " + customerShoppingCartItem.getProduct() + " does not exist");
        }

        if (product.getMerchantStore().getId().intValue() != store.getId().intValue()) {
            throw new ResourceNotFoundException(
                    "Item with id " + customerShoppingCartItem.getProduct() + " does not belong to merchant " + store.getId());
        }

        if (!product.isAvailable()) {
            throw new Exception("Product with sku " + product.getSku() + " is not available");
        }

        if (!DateUtil.dateBeforeEqualsDate(product.getDateAvailable(), new Date())) {
            throw new Exception("Item with sku " + product.getSku() + " is not available");
        }

        Set<ProductAvailability> availabilities = product.getAvailabilities();

        ProductVariant instance = null;
        if (CollectionUtils.isNotEmpty(product.getVariants())) {
            instance = product.getVariants().iterator().next();
            Set<ProductAvailability> instanceAvailabilities = instance.getAvailabilities();
            if(!CollectionUtils.isEmpty(instanceAvailabilities)) {
                availabilities = instanceAvailabilities;
            }

        }

        if (CollectionUtils.isEmpty(availabilities)) {
            throw new Exception(
                    "Item with id " + product.getId() + " is not properly configured. It contains no inventory");
        }

        //todo filter sku and store
        for (ProductAvailability availability : availabilities) {
            if (availability.getProductQuantity() == null || availability.getProductQuantity().intValue() == 0) {
                throw new Exception("Product with id " + product.getId() + " is not available");
            }
        }

        /**
         * Check if product quantity is 0 Check if product is available Check if date
         * available <= now
         */

        // use a mapper
        com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem item = customerShoppingCartService
                .populateCustomerShoppingCartItem(product, store);

        item.setQuantity(customerShoppingCartItem.getQuantity());
        item.setCustomerShoppingCart(cartModel);
        item.setSku(customerShoppingCartItem.getProduct());
//        item.setSku(product.getSku());
        item.setProduct(product);

        if (instance != null) {
            item.setVariant(instance.getId());
        }

        // TODO attributes
        // set item price
        FinalPrice price = pricingService.calculateProductPrice(product);
        item.setItemPrice(price.getFinalPrice());
        item.setFinalPrice(price);

        BigDecimal subTotal = item.getItemPrice().multiply(new BigDecimal(item.getQuantity()));
        item.setSubTotal(subTotal);

        // set attributes
//        List<com.salesmanager.shop.model.catalog.product.attribute.ProductAttribute> attributes = shoppingCartItem
//                .getAttributes();
//        if (!CollectionUtils.isEmpty(attributes)) {
//            for (com.salesmanager.shop.model.catalog.product.attribute.ProductAttribute attribute : attributes) {
//
//                ProductAttribute productAttribute = productAttributeService.getById(attribute.getId());
//
//                if (productAttribute != null
//                        && productAttribute.getProduct().getId().longValue() == product.getId().longValue()) {
//
//                    com.salesmanager.core.model.shoppingcart.ShoppingCartAttributeItem attributeItem = new com.salesmanager.core.model.shoppingcart.ShoppingCartAttributeItem(
//                            item, productAttribute);
//
//                    item.addAttributes(attributeItem);
//                }
//            }
//        }

        return item;
    }

    @Override
    public CustomerShoppingCart createCartModel(String customerShoppingCartCode, Customer customer) throws Exception {
        final Long customerId = customer != null ? customer.getId() : null;
        CustomerShoppingCart cartModel = new CustomerShoppingCart();
        if (StringUtils.isNotBlank(customerShoppingCartCode)) {
            cartModel.setCustomerShoppingCartCode(customerShoppingCartCode);
        } else {
            cartModel.setCustomerShoppingCartCode(uniqueShoppingCartCode());
        }

        if (customerId != null) {
            cartModel.setCustomerId(customerId);
        }

        customerShoppingCartService.create(cartModel);
        return cartModel;
    }

    private String uniqueShoppingCartCode() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    @Override
    public void deleteCart(Long id) throws Exception {
        CustomerShoppingCart cart = customerShoppingCartService.getById(id);
        if (cart != null) {
            customerShoppingCartService.deleteCart(cart);
        }
    }

    @Override
    public void deleteCart(String code) throws Exception {
        CustomerShoppingCart cart = customerShoppingCartService.getByCode(code);
        if (cart != null) {
            customerShoppingCartService.deleteCart(cart);
        }
    }

    @Override
    public CustomerShoppingCart getCustomerShoppingCartModel(String customerShoppingCartCode) throws Exception {
        return customerShoppingCartService.getByCode(customerShoppingCartCode);
    }

    @Override
    public CustomerShoppingCart getCustomerShoppingCartModel(Long id) throws Exception {
        return customerShoppingCartService.getById(id);
    }

    @Override
    public void saveOrUpdateCustomerShoppingCart(CustomerShoppingCart cart) throws Exception {
        customerShoppingCartService.saveOrUpdate(cart);
    }

    @Override
    public ReadableCustomerShoppingCart modifyCart(Customer customer, String cartCode, PersistableCustomerShoppingCartItem item, Language language) throws Exception {
        Validate.notNull(cartCode, "String cart code cannot be null");
        Validate.notNull(item, "PersistableCustomerShoppingCartItem cannot be null");

        CustomerShoppingCart cartModel = getCustomerShoppingCartModel(cartCode);
        if (cartModel == null) {
            throw new ResourceNotFoundException("Cart code [" + cartCode + "] not found");
        }

        return modifyCart(customer, cartModel, item, language);
    }

    private ReadableCustomerShoppingCart modifyCart(Customer customer, CustomerShoppingCart cartModel, PersistableCustomerShoppingCartItem item, Language language) throws Exception{
        MerchantStore store = merchantStoreService.getById(item.getMerchantId());
        if (store == null) {
            throw new ServiceException("merchant store " + item.getMerchantId() + " missing exception");
        }

        com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem itemModel = createCartItem(cartModel, item, store);

        boolean itemModified = false;
        Set<com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem> items = cartModel.getLineItems();
        if (!CollectionUtils.isEmpty(items)) {
            Set<com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem> newItems = new HashSet<>();
            Set<com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem> removeItems = new HashSet<>();

            for (com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem anItem: items) {
                // take care of existing
                // product
                if (itemModel.getProduct().getId().longValue() == anItem.getProduct().getId()) {
                    if (item.getQuantity() == 0) {
                        // left aside item to be removed
                        // don't add it to new list of item
                        removeItems.add(anItem);
                    } else {
                        // new quantity
                        anItem.setQuantity(item.getQuantity());
                        newItems.add(anItem);
                    }
                    itemModified = true;
                } else {
                    newItems.add(anItem);
                }
            }

            if (!removeItems.isEmpty()) {
                for (com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem emptyItem : removeItems) {
                    customerShoppingCartService.deleteCustomerShoppingCartItem(emptyItem.getId());
                }
            }

            if (!itemModified) {
                newItems.add(itemModel);
            }

            if (newItems.isEmpty()) {
                newItems = null;
            }

            cartModel.setLineItems(newItems);
        } else {
            // new item
            if (item.getQuantity() > 0) {
                cartModel.getLineItems().add(itemModel);
            }
        }

        // promo code added to the cart but no promo cart exists
        if (!StringUtils.isBlank(item.getPromoCode()) && StringUtils.isBlank(cartModel.getPromoCode())) {
            cartModel.setPromoCode(item.getPromoCode());
            cartModel.setPromoAdded(new Date());
        }

        customerShoppingCartCalculationService.calculate(cartModel, customer, language);

        saveCustomerShoppingCart(cartModel);

        cartModel = customerShoppingCartService.getById(cartModel.getId());

        if (cartModel == null) {
            return null;
        }


        ReadableCustomerShoppingCart readableCart = new ReadableCustomerShoppingCart();
        readableCart = readableCustomerShoppingCartMapper.convert(cartModel, store, language);

        return readableCart;
    }

    private void saveCustomerShoppingCart(CustomerShoppingCart cart) throws Exception {
        customerShoppingCartService.saveOrUpdate(cart);
    }

    @Override
    public ReadableCustomerShoppingCart modifyCart(Customer customer, String cartCode, String promo, Language language) throws Exception {
        CustomerShoppingCart cart = customerShoppingCartService.getByCode(cartCode);

        cart.setPromoCode(promo);
        cart.setPromoAdded(new Date());

        customerShoppingCartService.save(cart);
        return readableCustomerShoppingCartMapper.convert(cart, null, language);
    }

    @Override
    public ReadableCustomerShoppingCart modifyCartMulti(Customer customer, String cartCode, List<PersistableCustomerShoppingCartItem> items, Language language) throws Exception {
        Validate.notNull(cartCode, "String cart code cannot be null");
        Validate.notNull(items, "PersistableCustomerShoppingCartItem cannot be null");

        CustomerShoppingCart cartModel = getCustomerShoppingCartModel(cartCode);
        if (cartModel == null) {
            throw new IllegalArgumentException("Cart code not valid");
        }

        return modifyCartMulti(customer, cartModel, items, language);
    }

    private ReadableCustomerShoppingCart modifyCartMulti(Customer customer, CustomerShoppingCart cartModel, List<PersistableCustomerShoppingCartItem> cartItems, Language language) throws Exception {
        MerchantStore store = merchantStoreService.getById(cartItems.get(0).getMerchantId());
        if (store == null) {
            throw new ServiceException("merchant store missing exception");
        }

        int itemUpdatedCnt = 0;
        List<com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem> inCartItemList = createCartItems(cartModel, cartItems);

        Set<com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem> existingItems = cartModel.getLineItems();
        for (com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem newItemValue : inCartItemList) {
            Optional<com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem> oOldItem = existingItems.stream().filter(i -> i.getSku().equals(newItemValue.getSku())).findFirst();

            if (oOldItem.isPresent()) {
                com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem oldCartItem = oOldItem.get();
                if (oldCartItem.getQuantity().intValue() == newItemValue.getQuantity()) {
                    continue;
                }

                if (newItemValue.getQuantity() == 0) {
                    customerShoppingCartService.deleteCustomerShoppingCartItem(oldCartItem.getId());
                    cartModel.getLineItems().remove(oldCartItem);
                    ++itemUpdatedCnt;
                    continue;
                }

                oldCartItem.setQuantity(newItemValue.getQuantity());
                ++itemUpdatedCnt;
            } else {
                cartModel.getLineItems().add(newItemValue);
                ++itemUpdatedCnt;
            }
        }

        customerShoppingCartCalculationService.calculate(cartModel, customer, language);

        saveCustomerShoppingCart(cartModel);

        cartModel = customerShoppingCartService.getById(cartModel.getId());

        if (cartModel == null) {
            return null;
        }

        // TODO default store
        return readableCustomerShoppingCartMapper.convert(cartModel, store, language);
    }

    private List<com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem> createCartItems(CustomerShoppingCart cartModel, List<PersistableCustomerShoppingCartItem> customerShoppingCartItems) throws Exception {
        List<ProductSkuStorePair> productSkuStorePairs = customerShoppingCartItems.stream().map(s -> new ProductSkuStorePair(s.getProduct(), s.getMerchantId())).collect(Collectors.toList());
        List<Product> products = productSkuStorePairs.stream().map(p -> {
            MerchantStore store = merchantStoreService.getById(p.getMerchantId());

            Product product = this.fetchProduct(p.getSku(), store, store.getDefaultLanguage());
            product.setSku(p.getSku());
            return product;
        }).collect(Collectors.toList());

        if (products == null || products.size() != customerShoppingCartItems.size()) {
            LOG.warn("----------------------- Items with in id-list " + productSkuStorePairs + " does not exist");
            throw new ResourceNotFoundException("Item with skus " + productSkuStorePairs + " does not exist");
        }

        List<com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem> items = new ArrayList<>();

        for (Product p: products) {
            com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem item = customerShoppingCartService.populateCustomerShoppingCartItem(p, p.getMerchantStore());
            Optional<PersistableCustomerShoppingCartItem> oCustomerShoppingCartItem = customerShoppingCartItems.stream().filter(i -> i.getProduct().equals(p.getSku())).findFirst();

            if (!oCustomerShoppingCartItem.isPresent()) {
                LOG.warn("Missing customerShoppingCartItem for product " + p.getSku() + " ( " + p.getId() + " )");
                continue;
            }

            PersistableCustomerShoppingCartItem customerShoppingCartItem = oCustomerShoppingCartItem.get();
            item.setQuantity(customerShoppingCartItem.getQuantity());
            item.setCustomerShoppingCart(cartModel);

            /**
             * Check if product is available Check if product quantity is 0 Check if date
             * available <= now
             */
            if (customerShoppingCartItem.getQuantity() > 0 && !p.isAvailable()) {
                throw new Exception("Item with id " + p.getId() + " is not available");
            }

            Set<ProductAvailability> availabilities = p.getAvailabilities();
            if (availabilities == null) {
                throw new Exception("Item with id " + p.getId() + " is not properly configured");
            }

            for (ProductAvailability availability : availabilities) {
                if (customerShoppingCartItem.getQuantity() > 0 && availability.getProductQuantity() == null || availability.getProductQuantity().intValue() == 0) {
                    throw new Exception("Item with id " + p.getId() + " is not available");
                }
            }

            if (!DateUtil.dateBeforeEqualsDate(p.getDateAvailable(), new Date())) {
                throw new Exception("Item with id " + p.getId() + " is not available");
            }

            // TODO
            // set attributes
//            List<com.salesmanager.shop.model.catalog.product.attribute.ProductAttribute> attributes = shoppingCartItem
//                    .getAttributes();
//            if (!CollectionUtils.isEmpty(attributes)) {
//                for (com.salesmanager.shop.model.catalog.product.attribute.ProductAttribute attribute : attributes) {
//
//                    ProductAttribute productAttribute = productAttributeService.getById(attribute.getId());
//
//                    if (productAttribute != null
//                            && productAttribute.getProduct().getId().longValue() == p.getId().longValue()) {
//
//                        com.salesmanager.core.model.shoppingcart.ShoppingCartAttributeItem attributeItem = new com.salesmanager.core.model.shoppingcart.ShoppingCartAttributeItem(
//                                item, productAttribute);
//
//                        item.addAttributes(attributeItem);
//                    }
//                }
//            }

            items.add(item);
        }

        return items;
    }

    public static class ProductSkuStorePair {
        private String sku;

        private Integer merchantId;

        public ProductSkuStorePair(String sku, Integer merchantId) {
            this.sku = sku;
            this.merchantId = merchantId;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public Integer getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(Integer merchantId) {
            this.merchantId = merchantId;
        }
    }

    private Product fetchProduct(String sku, MerchantStore store, Language language) {
        try {
            return productService.getBySku(sku, store, language);
        } catch (ServiceException e) {
            throw new ServiceRuntimeException(e);
        }
    }

    @Override
    public ReadableCustomerShoppingCart addToCart(Customer customer, PersistableCustomerShoppingCartItem item, Language language) throws Exception {
        Validate.notNull(customer, "Customer cannot be null");
        Validate.notNull(customer.getId(), "Customer.id cannot be null or empty");

        CustomerShoppingCart cartModel = customerShoppingCartService.getCustomerShoppingCart(customer);

        return readableCustomerShoppingCart(customer, cartModel, item, language);
    }

//    @Override
//    public ReadableCustomerShoppingCart addToCart(PersistableCustomerShoppingCartItem item, Language language) {
//        Validate.notNull(item, "PersistableCustomerShoppingCartItem cannot be null");
//
//        CustomerShoppingCart cartModel = new CustomerShoppingCart();
//        cartModel.setCustomerShoppingCartCode(uniqueShoppingCartCode());
//
//        if (!StringUtils.isBlank(item.getPromoCode())) {
//            cartModel.setPromoCode(item.getPromoCode());
//            cartModel.setPromoAdded(new Date());
//        }
//        try {
//            return readableCustomerShoppingCart(null, cartModel, item, language);
//        } catch (Exception e) {
//            if (e instanceof ResourceNotFoundException) {
//                throw (ResourceNotFoundException) e;
//            } else {
//                throw new ServiceRuntimeException(e.getMessage(),e);
//            }
//        }
//    }

    private ReadableCustomerShoppingCart readableCustomerShoppingCart(Customer customer, CustomerShoppingCart cartModel, PersistableCustomerShoppingCartItem item, Language language) throws Exception {
        MerchantStore store = merchantStoreService.getById(item.getMerchantId());

        if (store == null) {
            throw new ServiceException("merchant store " + item.getMerchantId() + " missing exception");
        }
        com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem itemModel = createCartItem(cartModel, item, store);

        // need to check if the item is already in the cart
        boolean duplicateFound = false;
        if (CollectionUtils.isEmpty(item.getAttributes())) {
            Set<com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem> cartModelItems = cartModel.getLineItems();
            for (com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem cartItem : cartModelItems) {
                if (cartItem.getSku().equals(item.getProduct())) {
                    if (!duplicateFound) {
                        cartItem.setQuantity(cartItem.getQuantity() + item.getQuantity());
                        duplicateFound = true;
                        break;
                    }
                }
            }
        }

        if (!duplicateFound) {
            cartModel.getLineItems().add(itemModel);
        }

        customerShoppingCartCalculationService.calculate(cartModel, customer, language);

        saveCustomerShoppingCart(cartModel);

        cartModel = customerShoppingCartService.getById(cartModel.getId());

        return readableCustomerShoppingCartMapper.convert(cartModel, store, language);
    }

    @Override
    public ReadableCustomerShoppingCart removeCustomerShoppingCartItem(String cartCode, String sku, Language language, boolean returnCart) throws Exception {
        Validate.notNull(cartCode, "Shopping cart code must not be null");
        Validate.notNull(sku, "product sku must not be null");

        CustomerShoppingCart cart = getCustomerShoppingCartModel(cartCode);

        if (cart == null) {
            throw new ResourceNotFoundException("Cart code [ " + cartCode + " ] not found");
        }
        Set<com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem> items = new HashSet<>();
        com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem itemToDelete = null;
        for (com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem customerShoppingCartItem : cart.getLineItems()) {
            if (customerShoppingCartItem.getSku().equals(sku)) {
                itemToDelete = getEntryToUpadte(customerShoppingCartItem.getId(), cart);
            } else {
                items.add(customerShoppingCartItem);
            }
        }

        if (itemToDelete != null) {
            customerShoppingCartService.deleteCustomerShoppingCartItem(itemToDelete.getId());
        }

        if (items.size() > 0) {
            cart.setLineItems(items);
        } else  {
            cart.getLineItems().clear();
        }

        customerShoppingCartService.saveOrUpdate(cart);

        if (items.size() > 0 && returnCart) {
            return this.getByCode(cartCode, language);
        }
        return null;
    }

    private com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem getEntryToUpadte(final long entryId, final CustomerShoppingCart cartModel) {
        if (CollectionUtils.isNotEmpty(cartModel.getLineItems())) {
            for (com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem customerShoppingCartItem : cartModel.getLineItems()) {
                if (customerShoppingCartItem.getId().longValue() == entryId) {
                    LOG.info("Found line item  for given entry id: " + entryId);
                    return customerShoppingCartItem;
                }
            }
        }

        LOG.info("Unable to find any entry for given Id: " + entryId);
        return null;
    }

    @Override
    public ReadableCustomerShoppingCart getById(Long id, Language language) throws Exception {
        CustomerShoppingCart cart = customerShoppingCartService.getById(id);

        ReadableCustomerShoppingCart readableCart = null;

        if (cart != null) {
            readableCart = readableCustomerShoppingCartMapper.convert(cart, null, language);
        }

        return readableCart;
    }

    @Override
    public ReadableCustomerShoppingCart getByCode(String code, Language language) throws Exception {
        CustomerShoppingCart cart = customerShoppingCartService.getByCode(code);
        ReadableCustomerShoppingCart readableCart = null;

        if (cart != null) {
            MerchantStore store = merchantStoreService.getByCode(DEFAULT_STORE);
            readableCart = readableCustomerShoppingCartMapper.convert(cart, store, language);

            if (!StringUtils.isBlank(cart.getPromoCode())) {
                Date promoDateAdded = cart.getPromoAdded();
                if (promoDateAdded == null) {
                    promoDateAdded = new Date();
                }

                Instant instant = promoDateAdded.toInstant();
                ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
                LocalDate date = zdt.toLocalDate();
                // date added < date + 1 day
                LocalDate tomorrow = LocalDate.now().plusDays(1);
                if (date.isBefore(tomorrow)) {
                    readableCart.setPromoCode(cart.getPromoCode());
                }
            }
        }
        return readableCart;
    }

    @Override
    public ReadableCustomerShoppingCart readableCart(CustomerShoppingCart cart, Language language) {
        return readableCustomerShoppingCartMapper.convert(cart, null, language);
    }
}