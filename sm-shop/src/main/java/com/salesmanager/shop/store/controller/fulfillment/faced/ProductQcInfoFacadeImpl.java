package com.salesmanager.shop.store.controller.fulfillment.faced;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.fulfillment.service.*;
import com.salesmanager.core.business.utils.ObjectConvert;
import com.salesmanager.core.enmus.DocumentTypeEnums;
import com.salesmanager.core.enmus.FulfillmentTypeEnums;
import com.salesmanager.core.enmus.QcInfoHistoryCodeEnums;
import com.salesmanager.core.enmus.QcStatusEnums;
import com.salesmanager.core.model.common.Billing;
import com.salesmanager.core.model.common.Delivery;
import com.salesmanager.core.model.fulfillment.QcInfo;
import com.salesmanager.core.model.fulfillment.QcInfoHistory;
import com.salesmanager.shop.model.customer.ReadableBilling;
import com.salesmanager.shop.model.customer.ReadableDelivery;
import com.salesmanager.shop.model.fulfillment.*;
import com.salesmanager.shop.model.fulfillment.facade.FulfillmentFacade;
import com.salesmanager.shop.model.fulfillment.facade.ProductQcFacade;
import com.salesmanager.shop.populator.qc.PersistableQcInfoPopulator;
import org.apache.commons.collections.CollectionUtils;
import org.drools.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class ProductQcInfoFacadeImpl implements ProductQcFacade {

    @Autowired
    private QcInfoHistoryService qcinfoHistoryService;

    @Autowired
    private QcInfoService qcInfoService;

    @Autowired
    private PersistableQcInfoPopulator persistableQcInfoPopulator;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductQcInfoFacadeImpl.class);


    @Override
    public Long createQcInfo(PersistableQcInfo qcInfo) throws ConversionException {
        if (qcInfo == null){
            return null;
        }
        QcInfo populate = persistableQcInfoPopulator.populate(qcInfo);
        qcInfoService.saveQcInfo(populate);
        return populate.getId();
    }

    @Override
    public Long updateQcInfo(PersistableQcInfo qcInfo) throws ConversionException, ServiceException {
        if (qcInfo == null){
            return null;
        }
        if (!StringUtils.isEmpty(qcInfo.getProductImages())){
            com.salesmanager.core.model.fulfillment.QcInfoHistory history = new com.salesmanager.core.model.fulfillment.QcInfoHistory();
            history.setCode(QcInfoHistoryCodeEnums.PRODUCT_IMAGE);
            history.setQcInfoId(qcInfo.getId());
            qcinfoHistoryService.save(history);
        }
        if (!StringUtils.isEmpty(qcInfo.getPackagePictures())){
            com.salesmanager.core.model.fulfillment.QcInfoHistory history = new com.salesmanager.core.model.fulfillment.QcInfoHistory();
            history.setCode(QcInfoHistoryCodeEnums.PACKAGE_PICTURE);
            history.setQcInfoId(qcInfo.getId());
            qcinfoHistoryService.save(history);
        }
        if (!StringUtils.isEmpty(qcInfo.getVideoUrl())){
            com.salesmanager.core.model.fulfillment.QcInfoHistory history = new com.salesmanager.core.model.fulfillment.QcInfoHistory();
            history.setCode(QcInfoHistoryCodeEnums.VIDEO);
            history.setQcInfoId(qcInfo.getId());
            qcinfoHistoryService.save(history);
        }

        QcInfo populate = persistableQcInfoPopulator.populate(qcInfo);
        if (populate == null){
            return null;
        }
        qcInfoService.saveQcInfo(populate);
        return populate.getId();
    }

    @Override
    public void updateQcStatusById(String qcStatus, Long id) throws ServiceException {
        if (!StringUtils.isEmpty(qcStatus)){
            com.salesmanager.core.model.fulfillment.QcInfoHistory history = new com.salesmanager.core.model.fulfillment.QcInfoHistory();
            history.setCode(QcInfoHistoryCodeEnums.valueOf(qcStatus));
            history.setQcInfoId(id);
            qcinfoHistoryService.save(history);
        }
        qcInfoService.updateQcStatusById(qcStatus, id);
    }

    @Override
    public void saveQcInfoHistory(PersistableQcInfoHistory qcInfoHistory) throws ServiceException {
        com.salesmanager.core.model.fulfillment.QcInfoHistory history = new com.salesmanager.core.model.fulfillment.QcInfoHistory();
        history.setCode(QcInfoHistoryCodeEnums.valueOf(qcInfoHistory.getCode()));
        history.setQcInfoId(qcInfoHistory.getQcInfoId());
        qcinfoHistoryService.save(history);
    }

    @Override
    public ReadableQcInfo queryQcInfoById(Long id) throws ConversionException {
        QcInfo qcInfo = qcInfoService.getById(id);
        if (qcInfo == null){
            return null;
        }
        List<QcInfoHistory> qcInfoHistories = qcinfoHistoryService.queryByQcInfoId(id);

        ReadableQcInfo convert = ObjectConvert.convert(qcInfo, ReadableQcInfo.class);
        if (CollectionUtils.isNotEmpty(qcInfoHistories)){
            List<ReadableQcInfoHistory> collect = qcInfoHistories.stream().map(qcInfoHistory -> {
                ReadableQcInfoHistory readableQcInfoHistory = ObjectConvert.convert(qcInfoHistory, ReadableQcInfoHistory.class);
                readableQcInfoHistory.setCode(qcInfoHistory.getCode().name());
                readableQcInfoHistory.setDateCreated(qcInfoHistory.getAuditSection().getDateCreated());
                return readableQcInfoHistory;
            }).collect(Collectors.toList());
            convert.setReadableQcInfoHistoryList(collect);
        }
        convert.setDateCreated(qcInfo.getAuditSection().getDateCreated());
        return convert;
    }
}
