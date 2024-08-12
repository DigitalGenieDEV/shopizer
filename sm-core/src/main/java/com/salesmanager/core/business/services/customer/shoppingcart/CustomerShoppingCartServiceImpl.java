package com.salesmanager.core.business.services.customer.shoppingcart;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.customer.shoppingcart.CustomerShoppingCartItemRepository;
import com.salesmanager.core.business.repositories.customer.shoppingcart.CustomerShoppingCartRepository;
import com.salesmanager.core.business.services.catalog.pricing.PricingService;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.business.services.catalog.product.attribute.ProductAttributeService;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.price.FinalPrice;
import com.salesmanager.core.model.common.UserContext;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCart;
import com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem;
import com.salesmanager.core.model.merchant.MerchantStore;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service("customerShoppingCartService")
public class CustomerShoppingCartServiceImpl extends SalesManagerEntityServiceImpl<Long, CustomerShoppingCart> implements CustomerShoppingCartService{

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerShoppingCartServiceImpl.class);

    @Inject
    private CustomerShoppingCartRepository customerShoppingCartRepository;

    @Inject
    private ProductService productService;

    @Inject
    private CustomerShoppingCartItemRepository customerShoppingCartItemRepository;

    @Inject
    private PricingService pricingService;

    @Inject
    private ProductAttributeService productAttributeService;

    public CustomerShoppingCartServiceImpl(CustomerShoppingCartRepository customerShoppingCartRepository) {
        super(customerShoppingCartRepository);
        this.customerShoppingCartRepository = customerShoppingCartRepository;
    }

    @Override
    public CustomerShoppingCart getCustomerShoppingCart(Customer customer) throws ServiceException {
        LOGGER.info("get customer shopping cart [customer:" + customer.getId() +"]");
        CustomerShoppingCart cartModel = this.customerShoppingCartRepository.findByCustomer(customer.getId());

        LOGGER.info("get customer shopping cart [customer:" + customer.getId() +"], cartModel=" + cartModel);
        if (cartModel == null) {
            cartModel = new CustomerShoppingCart();
            cartModel.setCustomerId(customer.getId());
            cartModel.setCustomerShoppingCartCode(uniqueShoppingCartCode());

            this.customerShoppingCartRepository.save(cartModel);
        }
        else {
            LOGGER.info("get customer shopping cart populate [customer:" + customer.getId() +"]");
            getPopulatedCustomerShoppingCart(cartModel);
        }

//        cartModel = this.customerShoppingCartRepository.findByCode(cartModel.getCustomerShoppingCartCode());

        return cartModel;
    }

    private String uniqueShoppingCartCode() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    @Override
    public void saveOrUpdate(CustomerShoppingCart customerShoppingCart) throws ServiceException {
        Validate.notNull(customerShoppingCart, "CustomerShoppingCart must not be null");

        try {
            UserContext userContext = UserContext.getCurrentInstance();
            if (userContext != null) {
                customerShoppingCart.setIpAddress(userContext.getIpAddress());
            }
        } catch (Exception e) {
            LOGGER.error("Cannot add ip address to shopping cart ", e);
        }

        if (customerShoppingCart.getId() == null || customerShoppingCart.getId() == 0) {
            super.create(customerShoppingCart);
        } else {
            super.update(customerShoppingCart);
        }
    }

    @Override
    public CustomerShoppingCart getByCode(String code) throws ServiceException {
        try {
            CustomerShoppingCart customerShoppingCart = customerShoppingCartRepository.findByCode(code);
            if (customerShoppingCart == null) {
                return null;
            }

            getPopulatedCustomerShoppingCart(customerShoppingCart);

            if (customerShoppingCart.isObsolete()) {
                delete(customerShoppingCart);
                return null;
            } else {
                return customerShoppingCart;
            }
        } catch (javax.persistence.NoResultException nre) {
            return null;
        }  catch (Throwable e) {
            throw new ServiceException(e);
        }
    }

    @Transactional(noRollbackFor = { org.springframework.dao.EmptyResultDataAccessException.class })
    private CustomerShoppingCart getPopulatedCustomerShoppingCart(final CustomerShoppingCart customerShoppingCart) throws ServiceException {
        try {
            boolean cartIsObsolete = false;
            if (customerShoppingCart != null) {
                Set<CustomerShoppingCartItem> items = customerShoppingCart.getLineItems();

//                // TODO
//                if (items == null || items.size() == 0) {
//                    customerShoppingCart.setObsolete(true);
//                }

                for (CustomerShoppingCartItem item: items) {
                    LOGGER.info("Populate item " + item.getId());
                    getPopulatedItem(item);
                    LOGGER.info("Obsolete item ? " + item.isObsolete());
                    if (item.isObsolete()) {
                        cartIsObsolete = true;
                    }
                }

                Set<CustomerShoppingCartItem> refreshedItems = new HashSet<>(items);
                customerShoppingCart.setLineItems(refreshedItems);
//                update(customerShoppingCart);

                if (cartIsObsolete) {
                    customerShoppingCart.setObsolete(true);
                }

                return customerShoppingCart;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e);
        }

        return customerShoppingCart;
    }

//    @Transactional
    private void getPopulatedItem(final CustomerShoppingCartItem item) throws Exception {
        long start = System.currentTimeMillis();
        Product product = null;
        try {
            product = productService.getBySku(item.getSku());
        } catch (Exception e) {
            LOGGER.error("getPopulatedItem get product exception: [" + item.getSku() + "]", e);
        }
//        Product product = productService.getBySku(item.getSku(), item.getMerchantStore(), item.getMerchantStore().getDefaultLanguage());
        if (product == null) {
            item.setObsolete(true);
            return;
        }

        item.setProduct(product);
//        item.setSku(product.getSku());


        // TODO attributes
        // set item price
        FinalPrice price = pricingService.calculateProductPrice(product, false);
        item.setItemPrice(price.getFinalPrice());
        item.setFinalPrice(price);

        BigDecimal subTotal = item.getItemPrice().multiply(new BigDecimal(item.getQuantity()));
        item.setSubTotal(subTotal);
    }

    @Override
    public CustomerShoppingCartItem populateCustomerShoppingCartItem(Product product, MerchantStore store) throws ServiceException {
        Validate.notNull(product, "Product should not be null");
        Validate.notNull(product.getMerchantStore(), "Product.merchantStore should not be null");
        Validate.notNull(store, "MerchantStore should not be null");

        CustomerShoppingCartItem customerShoppingCartItem = new CustomerShoppingCartItem(product);
        customerShoppingCartItem.setSku(product.getSku());

        // set item price
        FinalPrice price = pricingService.calculateProductPrice(product, false);
        customerShoppingCartItem.setItemPrice(price.getFinalPrice());

        // set store
        customerShoppingCartItem.setMerchantStore(store);
        return customerShoppingCartItem;
    }

    @Override
    public void deleteCart(CustomerShoppingCart customerShoppingCart) throws ServiceException {
        CustomerShoppingCart cart = this.getById(customerShoppingCart.getId());
        if (cart != null) {
            super.delete(cart);
        }
    }

    @Override
    public void removeCart(CustomerShoppingCart customerShoppingCart) throws ServiceException {
        customerShoppingCartRepository.delete(customerShoppingCart);
    }

    @Override
    public void deleteCustomerShoppingCartItem(Long id) {
        CustomerShoppingCartItem item = customerShoppingCartItemRepository.findOne(id);
        if (item != null) {
            // TODO attributes

//            customerShoppingCartItemRepository.findOne(id);

            customerShoppingCartItemRepository.deleteById(id);
        }
    }
}
