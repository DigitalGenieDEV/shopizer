package com.salesmanager.core.business.fulfillment.service.impl;

import com.salesmanager.core.business.fulfillment.service.QcInfoService;
import com.salesmanager.core.business.repositories.fulfillment.QcInfoHistoryRepository;
import com.salesmanager.core.business.repositories.fulfillment.QcInfoRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.enmus.QcStatusEnums;
import com.salesmanager.core.model.fulfillment.QcInfo;
import com.salesmanager.core.model.fulfillment.QcInfoHistory;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class QcServiceImpl extends SalesManagerEntityServiceImpl<Long, QcInfo>  implements QcInfoService {

    private QcInfoRepository qcInfoRepository;

    @Inject
    public QcServiceImpl(QcInfoRepository qcInfoRepository) {
        super(qcInfoRepository);
        this.qcInfoRepository = qcInfoRepository;
    }

    @Override
    public Long saveQcInfo(QcInfo qcInfo) {
        qcInfoRepository.save(qcInfo);
        return qcInfo.getId();
    }

    @Override
    public void updateQcStatusById(String qcStatus, Long id) {
        Validate.notNull(qcStatus, "qcStatus must be no null");
        Validate.notNull(QcStatusEnums.valueOf(qcStatus), "qcStatus param error");
        qcInfoRepository.updateQcStatusById(QcStatusEnums.valueOf(qcStatus), id);
    }


}

