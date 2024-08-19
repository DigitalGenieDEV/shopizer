package com.salesmanager.core.business.fulfillment.service;

import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.fulfillment.QcInfo;
import com.salesmanager.core.model.fulfillment.QcInfoHistory;

import java.util.List;


public interface QcInfoHistoryService extends SalesManagerEntityService<Long, QcInfoHistory> {


    List<QcInfoHistory> queryByQcInfoId(Long qcInfoId);

}

