package com.salesmanager.core.business.repositories.customer.shoppingcart;

import com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CustomerShoppingCartItemRepository extends JpaRepository<CustomerShoppingCartItem, Long> {

    @Query("select i from CustomerShoppingCartItem i where i.id = ?1")
    CustomerShoppingCartItem findOne(Long id);

    @Modifying
    @Query("delete from CustomerShoppingCartItem i where i.id = ?1")
    void deleteById(Long id);
}
