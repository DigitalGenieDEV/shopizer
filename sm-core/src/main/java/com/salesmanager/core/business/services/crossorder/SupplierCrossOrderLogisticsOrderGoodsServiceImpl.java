package com.salesmanager.core.business.services.crossorder;

import com.salesmanager.core.business.repositories.crossorder.SupplierCrossOrderLogisticsOrderGoodsRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.crossorder.logistics.SupplierCrossOrderLogisticsOrderGoods;
import org.springframework.stereotype.Service;

@Service
public class SupplierCrossOrderLogisticsOrderGoodsServiceImpl extends SalesManagerEntityServiceImpl<Long, SupplierCrossOrderLogisticsOrderGoods> implements SupplierCrossOrderLogisticsOrderGoodsService  {

    private SupplierCrossOrderLogisticsOrderGoodsRepository repository;

    public SupplierCrossOrderLogisticsOrderGoodsServiceImpl(SupplierCrossOrderLogisticsOrderGoodsRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public SupplierCrossOrderLogisticsOrderGoods saveAndUpdate(SupplierCrossOrderLogisticsOrderGoods supplierCrossOrderLogisticsOrderGoods) {
        return saveAndFlush(supplierCrossOrderLogisticsOrderGoods);
    }
}
