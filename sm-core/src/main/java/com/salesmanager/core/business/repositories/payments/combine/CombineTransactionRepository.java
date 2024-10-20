package com.salesmanager.core.business.repositories.payments.combine;

import com.salesmanager.core.model.payments.CombineTransaction;
import com.salesmanager.core.model.payments.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface CombineTransactionRepository extends JpaRepository<CombineTransaction, Long> {

    @Query("select t from CombineTransaction t join fetch t.customerOrder to where to.id = ?1")
    List<CombineTransaction> findByCusOrder(Long cusOrderId);

    @Query("select t from CombineTransaction t join fetch t.customerOrder to where to is not null and t.transactionDate BETWEEN :from AND :to")
    List<CombineTransaction> findByDates(
            @Param("from") @Temporal(javax.persistence.TemporalType.TIMESTAMP) Date startDate,
            @Param("to") @Temporal(javax.persistence.TemporalType.TIMESTAMP) Date endDate);

    List<CombineTransaction> findCombineTransactionByPayOrderNo(String payOrderNo);

}
