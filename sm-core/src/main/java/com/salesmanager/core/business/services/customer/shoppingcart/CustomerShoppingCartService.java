package com.salesmanager.core.business.services.customer.shoppingcart;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCart;
import com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem;
import com.salesmanager.core.model.merchant.MerchantStore;

public interface CustomerShoppingCartService extends SalesManagerEntityService<Long, CustomerShoppingCart> {

    CustomerShoppingCart getCustomerShoppingCart(Customer customer) throws ServiceException;

    void saveOrUpdate(CustomerShoppingCart customerShoppingCart) throws ServiceException;

    CustomerShoppingCart getById(Long id);

    CustomerShoppingCart getByCode(String code) throws ServiceException;

    CustomerShoppingCartItem populateCustomerShoppingCartItem(Product product, MerchantStore store)  throws ServiceException;

    void deleteCart(CustomerShoppingCart customerShoppingCart) throws ServiceException;

    void removeCart(CustomerShoppingCart customerShoppingCart) throws ServiceException;

    void deleteCustomerShoppingCartItem(Long id);
}
