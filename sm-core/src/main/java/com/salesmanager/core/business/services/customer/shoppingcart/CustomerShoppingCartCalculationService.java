package com.salesmanager.core.business.services.customer.shoppingcart;

import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.customer.order.CustomerOrderTotalSummary;
import com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCart;
import com.salesmanager.core.model.reference.language.Language;

public interface CustomerShoppingCartCalculationService {


    CustomerOrderTotalSummary calculate(final CustomerShoppingCart cartModel, final Customer customer, final Language language) throws Exception;

    CustomerOrderTotalSummary calculate(final CustomerShoppingCart cartModel, final Language language) throws Exception;
}
