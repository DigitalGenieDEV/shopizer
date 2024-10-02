package com.salesmanager.shop.store.controller.order.facade;

import com.salesmanager.core.enmus.AdditionalServiceInstanceStatusEnums;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.order.ReadableOrderProduct;
import com.salesmanager.shop.model.order.v1.PersistableOrderProductAdditionalServiceInstance;
import com.salesmanager.shop.model.order.v1.ReadableOrderProductAdditionalServiceInstance;

import java.util.List;

public interface OrderProductAdditionalServiceInstanceFacade {

    ReadableOrderProductAdditionalServiceInstance getById(Long id, MerchantStore merchantStore, Language language);

    List<ReadableOrderProductAdditionalServiceInstance> listReadableOrderProductAdditionalServiceInstance(ReadableOrderProduct readableOrderProduct, MerchantStore merchantStore, Language language);

    Boolean saveAdditionalServiceInstance(ReadableOrderProduct readableOrderProduct, PersistableOrderProductAdditionalServiceInstance persistableOrderProductAdditionalService, MerchantStore merchantStore, Language language);

    Boolean replyAdditionalServiceInstance(ReadableOrderProduct readableOrderProduct, PersistableOrderProductAdditionalServiceInstance persistableOrderProductAdditionalService, MerchantStore merchantStore, Language language);

    Boolean delAdditionalServiceInstanceReply(ReadableOrderProduct readableOrderProduct, PersistableOrderProductAdditionalServiceInstance persistableOrderProductAdditionalService, MerchantStore merchantStore, Language language);

    Boolean updateAdditionalServiceInstanceReply(ReadableOrderProduct readableOrderProduct, PersistableOrderProductAdditionalServiceInstance persistableOrderProductAdditionalService, MerchantStore merchantStore, Language language);

    Boolean confirmAdditionalServiceInstance(Long id, AdditionalServiceInstanceStatusEnums additionalServiceInstanceStatusEnums, MerchantStore merchantStore, Language language);

}
