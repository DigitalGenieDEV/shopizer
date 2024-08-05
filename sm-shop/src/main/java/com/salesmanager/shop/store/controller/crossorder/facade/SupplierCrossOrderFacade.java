package com.salesmanager.shop.store.controller.crossorder.facade;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.shop.model.crossorder.ReadableSupplierCrossOrderLogistics;
import com.salesmanager.shop.model.crossorder.SupplierCrossOrderLogisticsMsg;
import com.salesmanager.shop.model.crossorder.SupplierCrossOrderLogisticsStatusChangeMsg;
import com.salesmanager.shop.utils.FieldMatch;

import java.util.List;

public interface SupplierCrossOrderFacade {

    List<ReadableSupplierCrossOrderLogistics> processSupplierCrossOrderLogisticsMsg(SupplierCrossOrderLogisticsMsg msg) throws ServiceException;

    ReadableSupplierCrossOrderLogistics processSupplierCrossOrderLogisticsStatusChangeMsg(SupplierCrossOrderLogisticsStatusChangeMsg msg) throws ServiceException;
}
