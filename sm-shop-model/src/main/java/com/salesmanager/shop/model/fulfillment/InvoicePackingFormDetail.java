package com.salesmanager.shop.model.fulfillment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import lombok.Data;

import javax.persistence.*;

@Data
public class InvoicePackingFormDetail{

    private Long id;

    private String numbers;

    private String description;

    private String cartonQty;

    private String unitQty;

    private String unit;

    private String unitPrice;

    private String netWeight;

    private String grossWeight;

    private String cbm;

}
