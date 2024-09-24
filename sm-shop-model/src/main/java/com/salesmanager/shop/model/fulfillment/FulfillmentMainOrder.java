package com.salesmanager.shop.model.fulfillment;

import com.salesmanager.core.model.common.Billing;
import com.salesmanager.core.model.common.Delivery;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.fulfillment.FulfillmentSubOrder;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.shop.model.customer.ReadableBilling;
import com.salesmanager.shop.model.customer.ReadableDelivery;
import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class FulfillmentMainOrder  {

    private Long id;

    /**
     * 部分发货
     */
    private Boolean partialDelivery;


    private ReadableBilling billing;

    private ReadableDelivery delivery;

    private List<ReadableFulfillmentSubOrder> fulfillSubOrders;


    private List<ReadableGeneralDocument> generalDocuments;


    private ReadableInvoicePackingForm invoicePackingForm;


}
