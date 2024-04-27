package com.salesmanager.core.business.services.payments.combine;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.payments.combine.CombineTransactionRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.customer.order.CustomerOrder;
import com.salesmanager.core.model.payments.CombineTransaction;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

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
    public CombineTransaction getCapturableCombineTransaction(CustomerOrder customerOrder) throws ServiceException {
        return null;
    }

    @Override
    public CombineTransaction getRefundableCombineTransaction(CustomerOrder customerOrder) throws ServiceException {
        return null;
    }

    @Override
    public List<CombineTransaction> listCombineTransactions(CustomerOrder customerOrder) throws ServiceException {
        List<CombineTransaction> combineTransactions = combineTransactionRepository.findByCusOrder(customerOrder.getId());
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
    public List<CombineTransaction> listCombineTransactions(Date startDate, Date endDate) throws ServiceException {
        return null;
    }

    @Override
    public CombineTransaction lastCombineTransaction(CustomerOrder customerOrder) throws ServiceException {
        List<CombineTransaction> combineTransactions = combineTransactionRepository.findByCusOrder(customerOrder.getId());

        //TODO order by date
        TreeMap<String, CombineTransaction> map = combineTransactions.stream()
                .collect(

                        Collectors.toMap(
                                CombineTransaction::getTransactionTypeName, transaction -> transaction,(o1, o2) -> o1, TreeMap::new)


                );

        //get last transaction
        Map.Entry<String, CombineTransaction> last = map.lastEntry();

        String currentStep = last.getKey();

        System.out.println("Current step " + currentStep);

        return last.getValue();
    }
}
