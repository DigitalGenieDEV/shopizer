package com.salesmanager.core.business.repositories.payments.combine;

import com.salesmanager.core.model.payments.CombineTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CombineTransactionRepository extends JpaRepository<CombineTransaction, Long> {

    @Query("select t from CombineTransaction t join fetch t.customerOrder to where to.id = ?1")
    List<CombineTransaction> findByCusOrder(Long cusOrderId);
}
