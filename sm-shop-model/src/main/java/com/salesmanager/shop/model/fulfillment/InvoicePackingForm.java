package com.salesmanager.shop.model.fulfillment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class InvoicePackingForm {

    private Long id;

    private String invoiceNo;


    private Long invoiceDate;

    private String shipperCompany;

    private String shipperAddress;

    private String shipperTelephone;

    private String consigneeCompany;

    private String consigneeAddress;

    private String consigneeTelephone;

    private String loadingPort;


    private String finalDestination;


    private String vessel;


    private Set<InvoicePackingFormDetail> invoicePackingFormDetails = new HashSet<>();

    /**
     * 履约子单号
     */
    private List<FulfillmentSubOrder> fulfillmentSubOrders;

    /**
     * 交易单id
     */
    private Long orderId;


}
