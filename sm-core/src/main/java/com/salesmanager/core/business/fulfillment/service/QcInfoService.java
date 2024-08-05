package com.salesmanager.core.business.fulfillment.service;

import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.fulfillment.QcInfo;
import com.salesmanager.core.model.fulfillment.QcInfoHistory;


public interface QcInfoService extends SalesManagerEntityService<Long, QcInfo> {

    Long saveQcInfo(QcInfo qcInfo);


    Long saveQcInfoHistory(QcInfoHistory qcInfoHistory);
}

