package com.salesmanager.shop.model.catalog;

import com.salesmanager.core.model.catalog.product.Material;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import lombok.Data;

import javax.persistence.*;

@Data
public class ProductMaterial {

    private Long id;

    private AuditSection auditSection = new AuditSection();

    private Long productId;


    private Long materialId;

    /**
     *
     */
    private Long weight;



}