package com.salesmanager.core.business.services.payments.combine;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.customer.order.CustomerOrder;
import com.salesmanager.core.model.payments.CombineTransaction;

import java.util.Date;
import java.util.List;

public interface CombineTransactionService extends SalesManagerEntityService<Long, CombineTransaction> {

    CombineTransaction getCapturableCombineTransaction(CustomerOrder customerOrder) throws ServiceException;

    CombineTransaction getRefundableCombineTransaction(CustomerOrder customerOrder) throws ServiceException;

    List<CombineTransaction> listCombineTransactions(CustomerOrder customerOrder) throws ServiceException;

    List<CombineTransaction> listCombineTransactions(Date startDate, Date endDate) throws ServiceException;

    CombineTransaction lastCombineTransaction(CustomerOrder customerOrder) throws ServiceException;
}
