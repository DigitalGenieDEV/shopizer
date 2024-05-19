package com.salesmanager.core.business.services.customer.shoppingcart;

import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCart;
import com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.OrderTotalSummary;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.shoppingcart.ShoppingCart;
import com.salesmanager.core.model.shoppingcart.ShoppingCartItem;

import java.util.List;

public interface CustomerShoppingCartSplitterService {

    public List<ShoppingCart> splitCheckedItemsToShoppingCart(CustomerShoppingCart customerShoppingCart);

    public List<ShoppingCart> splitUncheckedItemsToShoppingCart(CustomerShoppingCart customerShoppingCart);

    public List<ShoppingCart> splitCheckedItemsToShoppingCart(CustomerShoppingCart customerShoppingCart, Customer customer);

    public ShoppingCart getCheckedItemsShoppingCart(CustomerShoppingCart customerShoppingCart, MerchantStore store, Customer customer);

    public ShoppingCart getUncheckedItemsShoppingCart(CustomerShoppingCart customerShoppingCart, MerchantStore store, Customer customer);

    public ShoppingCartItem getShoppingCartItem(ShoppingCart shoppingCart, CustomerShoppingCartItem customerShoppingCartItem);

    public OrderTotalSummary calculateShoppingCart(ShoppingCart shoppingCart, final Customer customer, final MerchantStore store, final Language language) throws Exception;
}
