package com.salesmanager.core.business.repositories.fulfillment;

import com.salesmanager.core.model.fulfillment.InvoicePackingForm;
import com.salesmanager.core.model.fulfillment.QcInfoHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface QcInfoHistoryRepository extends JpaRepository<QcInfoHistory, Long> {


    @Query("select c from QcInfoHistory c "
            + " where c.qcInfoId=?1 ")
    List<QcInfoHistory> queryByQcInfoId(Long qcInfoId);

}
