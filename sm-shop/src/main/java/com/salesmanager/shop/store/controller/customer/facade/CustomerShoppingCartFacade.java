package com.salesmanager.shop.store.controller.customer.facade;

import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCart;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.customer.shoppingcart.CustomerShoppingCartData;
import com.salesmanager.shop.model.customer.shoppingcart.CustomerShoppingCartItem;
import com.salesmanager.shop.model.customer.shoppingcart.PersistableCustomerShoppingCartItem;
import com.salesmanager.shop.model.customer.shoppingcart.ReadableCustomerShoppingCart;

import java.util.List;

public interface CustomerShoppingCartFacade {

    CustomerShoppingCartData addItemsToCart(CustomerShoppingCartData customerShoppingCartData, final CustomerShoppingCartItem item, Language language, Customer customer) throws Exception;

    CustomerShoppingCart createCartModel(final String customerShoppingCartCode, final Customer customer) throws Exception;

    void deleteCart(final Long id) throws Exception;

    void deleteCart(String code) throws Exception;

    CustomerShoppingCart getCustomerShoppingCartModel(final String customerShoppingCartCode) throws Exception;

    CustomerShoppingCart getCustomerShoppingCartModel(Long id) throws Exception;

    void saveOrUpdateCustomerShoppingCart(CustomerShoppingCart cart) throws Exception;

    ReadableCustomerShoppingCart modifyCart(String cartCode, PersistableCustomerShoppingCartItem item, Language language) throws Exception;

    ReadableCustomerShoppingCart modifyCart(String cartCode, String promo, Language language) throws Exception;

    ReadableCustomerShoppingCart modifyCartMulti(String cartCode, List<PersistableCustomerShoppingCartItem> items, Language language) throws Exception;

    ReadableCustomerShoppingCart addToCart(Customer customer, PersistableCustomerShoppingCartItem item, Language language) throws Exception;

    ReadableCustomerShoppingCart addToCart(PersistableCustomerShoppingCartItem item, Language language);

    ReadableCustomerShoppingCart removeCustomerShoppingCartItem(String cartCode, String sku, Integer merchantId, Language language, boolean returnCart) throws Exception;;

    ReadableCustomerShoppingCart getById(Long id, Language language) throws Exception;

    ReadableCustomerShoppingCart getByCode(String code, Language language)  throws Exception;

    ReadableCustomerShoppingCart readableCart(CustomerShoppingCart cart, Language language);
}
