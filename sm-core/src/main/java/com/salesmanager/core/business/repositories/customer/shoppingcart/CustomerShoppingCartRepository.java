package com.salesmanager.core.business.repositories.customer.shoppingcart;

import com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerShoppingCartRepository extends JpaRepository<CustomerShoppingCart, Long> {

    @Query("select c from CustomerShoppingCart c left join fetch c.lineItems cl where c.id = ?1")
    CustomerShoppingCart findOne(Long id);

    @Query("select c from CustomerShoppingCart c left join fetch c.lineItems cl where c.customerShoppingCartCode = ?1")
    CustomerShoppingCart findByCode(String code);

    @Query("select c from CustomerShoppingCart c left join fetch c.lineItems cl  where c.customerId = ?1")
    CustomerShoppingCart findByCustomer(Long customerId);
}
