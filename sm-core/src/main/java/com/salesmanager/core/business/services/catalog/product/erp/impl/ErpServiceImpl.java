package com.salesmanager.core.business.services.catalog.product.erp.impl;

import com.salesmanager.core.business.repositories.material.MaterialRepository;
import com.salesmanager.core.business.services.catalog.product.erp.ErpService;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.catalog.product.Material;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class ErpServiceImpl extends SalesManagerEntityServiceImpl<Long, Material> implements ErpService {

    private MaterialRepository materialRepository;

    @Inject
    public ErpServiceImpl(MaterialRepository materialRepository) {
        super(materialRepository);
        this.materialRepository = materialRepository;
    }

    @Override
    public List<Material> queryList() {
        return materialRepository.queryList();
    }



}
