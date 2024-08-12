package com.salesmanager.shop.store.controller.customer.facade;

import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCart;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.customer.shoppingcart.CustomerShoppingCartData;
import com.salesmanager.shop.model.customer.shoppingcart.CustomerShoppingCartItem;
import com.salesmanager.shop.model.customer.shoppingcart.PersistableCustomerShoppingCartItem;
import com.salesmanager.shop.model.customer.shoppingcart.ReadableCustomerShoppingCart;

import java.util.List;

public interface CustomerShoppingCartFacade {

    CustomerShoppingCartData addItemsToCart(CustomerShoppingCartData customerShoppingCartData, final CustomerShoppingCartItem item, Language language, Customer customer) throws Exception;

    CustomerShoppingCart createCartModel(final String customerShoppingCartCode, final Customer customer) throws Exception;

    CustomerShoppingCart createCartModel(final Customer customer) throws Exception;

    void deleteCart(final Long id) throws Exception;

    void deleteCart(String code) throws Exception;

    CustomerShoppingCart getCustomerShoppingCartModel(final String customerShoppingCartCode) throws Exception;

    CustomerShoppingCart getCustomerShoppingCartModel(Long id) throws Exception;

    CustomerShoppingCart getCustomerShoppingCartModel(Customer customer) throws Exception;

    void saveOrUpdateCustomerShoppingCart(CustomerShoppingCart cart) throws Exception;

    ReadableCustomerShoppingCart modifyCart(Customer customer, PersistableCustomerShoppingCartItem item, MerchantStore store, Language language) throws Exception;

    ReadableCustomerShoppingCart modifyCart(Customer customer,String promo, MerchantStore store, Language language) throws Exception;

    ReadableCustomerShoppingCart modifyCartMulti(Customer customer, List<PersistableCustomerShoppingCartItem> items, MerchantStore store, Language language) throws Exception;

    ReadableCustomerShoppingCart addToCart(Customer customer, PersistableCustomerShoppingCartItem item, MerchantStore store, Language language) throws Exception;

//    ReadableCustomerShoppingCart addToCart(PersistableCustomerShoppingCartItem item, Language language);

    ReadableCustomerShoppingCart removeCartItem(Customer customer, String sku, MerchantStore store, Language language, boolean returnCart) throws Exception;;

    ReadableCustomerShoppingCart exclusiveSelectCartItem(Customer customer, String sku, MerchantStore store, Language language) throws Exception;;

    ReadableCustomerShoppingCart removeCartItemMulti(Customer customer, List<PersistableCustomerShoppingCartItem> items, MerchantStore store, Language language, boolean returnCart) throws Exception;

    ReadableCustomerShoppingCart cleanCart(Customer customer, MerchantStore store, Language language) throws Exception;

    ReadableCustomerShoppingCart getById(Long id, MerchantStore merchantStore, Language language) throws Exception;

    ReadableCustomerShoppingCart getByCode(String code, MerchantStore merchantStore, Language language)  throws Exception;

    ReadableCustomerShoppingCart getByCustomer(Customer customer, MerchantStore merchantStore, Language language)  throws Exception;

    ReadableCustomerShoppingCart readableCart(CustomerShoppingCart cart, MerchantStore store, Language language);
}
