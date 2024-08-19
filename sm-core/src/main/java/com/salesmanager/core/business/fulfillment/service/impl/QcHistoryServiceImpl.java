package com.salesmanager.core.business.fulfillment.service.impl;

import com.salesmanager.core.business.fulfillment.service.QcInfoHistoryService;
import com.salesmanager.core.business.fulfillment.service.QcInfoService;
import com.salesmanager.core.business.repositories.fulfillment.QcInfoHistoryRepository;
import com.salesmanager.core.business.repositories.fulfillment.QcInfoRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.enmus.QcStatusEnums;
import com.salesmanager.core.model.fulfillment.QcInfo;
import com.salesmanager.core.model.fulfillment.QcInfoHistory;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class QcHistoryServiceImpl extends SalesManagerEntityServiceImpl<Long, QcInfoHistory>  implements QcInfoHistoryService {

    private QcInfoHistoryRepository qcInfoHistoryRepository;

    @Inject
    public QcHistoryServiceImpl(QcInfoHistoryRepository qcInfoHistoryRepository) {
        super(qcInfoHistoryRepository);
        this.qcInfoHistoryRepository = qcInfoHistoryRepository;
    }

    @Override
    public List<QcInfoHistory> queryByQcInfoId(Long qcInfoId){
        return qcInfoHistoryRepository.queryByQcInfoId(qcInfoId);
    }
}

