package com.salesmanager.core.business.services.catalog.product.erp;

import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.catalog.product.Material;

import java.util.List;

public interface ErpService extends SalesManagerEntityService<Long, Material> {

    List<Material> queryList();
}
