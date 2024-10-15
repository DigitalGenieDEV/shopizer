package com.salesmanager.core.business.services.payments.combine;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.payments.combine.CombineTransactionRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.customer.order.CustomerOrder;
import com.salesmanager.core.model.payments.CombineTransaction;
import com.salesmanager.core.model.payments.TransactionType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service("combineTransactionService")
public class CombineTransactionServiceImpl extends SalesManagerEntityServiceImpl<Long, CombineTransaction> implements CombineTransactionService {

    private final CombineTransactionRepository combineTransactionRepository;

    public CombineTransactionServiceImpl(CombineTransactionRepository combineTransactionRepository) {
        super(combineTransactionRepository);
        this.combineTransactionRepository = combineTransactionRepository;
    }

    @Override
    public void create(CombineTransaction combineTransaction) throws ServiceException {

        //parse JSON string
        String transactionDetails = combineTransaction.toJSONString();
        if(!StringUtils.isBlank(transactionDetails)) {
            combineTransaction.setDetails(transactionDetails);
        }

        super.create(combineTransaction);
    }

    @Override
    public CombineTransaction  getCapturableCombineTransaction(CustomerOrder customerOrder, TransactionType queryTransactionType) throws ServiceException {
        List<CombineTransaction> combineTransactions = combineTransactionRepository.findByCusOrder(customerOrder.getId());
        ObjectMapper mapper = new ObjectMapper();
        CombineTransaction combineTransaction = null;
        for(CombineTransaction transaction : combineTransactions) {
            if(transaction.getTransactionType().name().equals(queryTransactionType.name())) {
                if(!StringUtils.isBlank(transaction.getDetails())) {
                    try {
                        @SuppressWarnings("unchecked")
                        Map<String,String> objects = mapper.readValue(transaction.getDetails(), Map.class);
                        transaction.setTransactionDetails(objects);
                        combineTransaction = transaction;
                    } catch (Exception e) {
                        throw new ServiceException(e);
                    }
                }
            }
            if(transaction.getTransactionType().name().equals(TransactionType.CAPTURE.name())) {
                break;
            }
            if(transaction.getTransactionType().name().equals(TransactionType.REFUND.name())) {
                break;
            }
        }
        return combineTransaction;
    }

    @Override
    public CombineTransaction getRefundableCombineTransaction(CustomerOrder customerOrder) throws ServiceException {
        List<CombineTransaction> combineTransactions = combineTransactionRepository.findByCusOrder(customerOrder.getId());
        Map<String,CombineTransaction> finalTransactions = new HashMap<String,CombineTransaction>();
        CombineTransaction finalTransaction = null;
        for (CombineTransaction transaction: combineTransactions) {
            if(transaction.getTransactionType().name().equals(TransactionType.AUTHORIZECAPTURE.name())) {
                finalTransactions.put(TransactionType.AUTHORIZECAPTURE.name(),transaction);
                continue;
            }
            if(transaction.getTransactionType().name().equals(TransactionType.CAPTURE.name())) {
                finalTransactions.put(TransactionType.CAPTURE.name(),transaction);
                continue;
            }
            if(transaction.getTransactionType().name().equals(TransactionType.REFUND.name())) {
                //check transaction id
                CombineTransaction previousRefund = finalTransactions.get(TransactionType.REFUND.name());
                if(previousRefund!=null) {
                    Date previousDate = previousRefund.getTransactionDate();
                    Date currentDate = transaction.getTransactionDate();
                    if(previousDate.before(currentDate)) {
                        finalTransactions.put(TransactionType.REFUND.name(),transaction);
                        continue;
                    }
                } else {
                    finalTransactions.put(TransactionType.REFUND.name(),transaction);
                    continue;
                }
            }
        }

        if(finalTransactions.containsKey(TransactionType.AUTHORIZECAPTURE.name())) {
            finalTransaction = finalTransactions.get(TransactionType.AUTHORIZECAPTURE.name());
        }

        if(finalTransactions.containsKey(TransactionType.CAPTURE.name())) {
            finalTransaction = finalTransactions.get(TransactionType.CAPTURE.name());
        }

        if(finalTransaction!=null && !StringUtils.isBlank(finalTransaction.getDetails())) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                @SuppressWarnings("unchecked")
                Map<String,String> objects = mapper.readValue(finalTransaction.getDetails(), Map.class);
                finalTransaction.setTransactionDetails(objects);
            } catch (Exception e) {
                throw new ServiceException(e);
            }
        }
        return finalTransaction;
    }

    @Override
    public List<CombineTransaction> listCombineTransactionsByCustomerOrderId(Long customerOrderId) throws ServiceException {
        List<CombineTransaction> combineTransactions = combineTransactionRepository.findByCusOrder(customerOrderId);
        ObjectMapper mapper = new ObjectMapper();
        for(CombineTransaction combineTransaction : combineTransactions) {
            if(!StringUtils.isBlank(combineTransaction.getDetails())) {
                try {
                    @SuppressWarnings("unchecked")
                    Map<String,String> objects = mapper.readValue(combineTransaction.getDetails(), Map.class);
                    combineTransaction.setTransactionDetails(objects);
                } catch (Exception e) {
                    throw new ServiceException(e);
                }
            }
        }

        return combineTransactions;
    }

    @Override
    public List<CombineTransaction> listCombineTransactions(CustomerOrder customerOrder) throws ServiceException {
        return listCombineTransactionsByCustomerOrderId(customerOrder.getId());
    }

    @Override
    public List<CombineTransaction> listCombineTransactions(Date startDate, Date endDate) throws ServiceException {
        return combineTransactionRepository.findByDates(startDate, endDate);
    }

    @Override
    public CombineTransaction lastCombineTransaction(CustomerOrder customerOrder) throws ServiceException {
        List<CombineTransaction> combineTransactions = combineTransactionRepository.findByCusOrder(customerOrder.getId());

        List<CombineTransaction> newList = new ArrayList<>(combineTransactions);
        newList.sort(Comparator.comparing(CombineTransaction::getTransactionDate));
        CombineTransaction lastTransaction =  CollectionUtils.lastElement(newList);

        System.out.println("Current step " + lastTransaction.getTransactionTypeName());

        ObjectMapper mapper = new ObjectMapper();
        if(!StringUtils.isBlank(lastTransaction.getDetails())) {
            try {
                @SuppressWarnings("unchecked")
                Map<String,String> objects = mapper.readValue(lastTransaction.getDetails(), Map.class);
                lastTransaction.setTransactionDetails(objects);
            } catch (Exception e) {
                throw new ServiceException(e);
            }
        }

        return lastTransaction;
    }
}
