package com.salesmanager.core.business.repositories.fulfillment;

import com.salesmanager.core.model.fulfillment.ShippingOrderProductQuery;
import com.salesmanager.core.model.fulfillment.ShippingDocumentOrderList;


public interface ShippingDocumentOrderRepositoryCustom  {



    ShippingDocumentOrderList queryShippingDocumentOrderList(ShippingOrderProductQuery orderProductShippingQuery);

}
