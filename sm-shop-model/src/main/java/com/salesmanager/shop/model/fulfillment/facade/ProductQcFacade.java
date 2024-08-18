package com.salesmanager.shop.model.fulfillment.facade;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.shop.model.fulfillment.PersistableQcInfo;
import com.salesmanager.shop.model.fulfillment.PersistableQcInfoHistory;
import com.salesmanager.shop.model.fulfillment.ReadableQcInfo;


public interface ProductQcFacade {


    Long createQcInfo(PersistableQcInfo qcInfo) throws ConversionException;


    Long updateQcInfo(PersistableQcInfo qcInfo) throws ConversionException, ServiceException;


    void updateQcStatusById(String qcStatus, Long id) throws ServiceException;

    void saveQcInfoHistory(PersistableQcInfoHistory qcInfoHistory) throws ServiceException;

    ReadableQcInfo queryQcInfoById(Long id) throws ConversionException;

}

