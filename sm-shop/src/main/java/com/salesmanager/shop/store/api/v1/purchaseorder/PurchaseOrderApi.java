package com.salesmanager.shop.store.api.v1.purchaseorder;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.shop.model.purchaseorder.PersistablePurchaseOrder;
import com.salesmanager.shop.model.purchaseorder.ReadablePurchaseOrder;
import com.salesmanager.shop.store.api.v1.order.OrderApi;
import com.salesmanager.shop.store.controller.purchaseorder.facade.PurchaseOrderFacade;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Api(tags = { "Purchase Ordering api (Purchase Order Flow Api)" })
public class PurchaseOrderApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseOrderApi.class);

    @Autowired
    private PurchaseOrderFacade purchaseOrderFacade;

    @PostMapping("/private/purchase_orders")
    public ReadablePurchaseOrder create(@RequestBody PersistablePurchaseOrder persistablePurchaseOrder) throws ServiceException {
        return purchaseOrderFacade.createPurchaseOrder(persistablePurchaseOrder);
    }

    @PostMapping("/private/purchase_orders/{id}/confirm")
    public ReadablePurchaseOrder confirm(@PathVariable("id") Long id) throws ServiceException {
        return  purchaseOrderFacade.confirmPurchaseOrder(id);
    }
}
