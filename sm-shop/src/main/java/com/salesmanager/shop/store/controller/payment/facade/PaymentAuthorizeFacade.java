package com.salesmanager.shop.store.controller.payment.facade;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.customer.order.CustomerOrder;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.customer.order.transaction.ReadableCombineTransaction;

import java.util.Map;

public interface PaymentAuthorizeFacade {

    public ReadableCombineTransaction processNicepayAuthorizeResponse(Map<String, String> responseMap, MerchantStore store, Language language) throws Exception;

    public ReadableCombineTransaction saveNicepayToken(Customer customer, CustomerOrder customerOrder, String ncToken, MerchantStore store, Language language) throws ServiceException, ConversionException;

}
