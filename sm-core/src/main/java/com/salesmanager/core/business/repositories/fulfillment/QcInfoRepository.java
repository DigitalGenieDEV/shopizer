package com.salesmanager.core.business.repositories.fulfillment;

import com.salesmanager.core.enmus.QcStatusEnums;
import com.salesmanager.core.model.fulfillment.QcInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import javax.transaction.Transactional;
import java.util.List;


public interface QcInfoRepository extends JpaRepository<QcInfo, Long> {


    @Transactional
    @Modifying
    @Query("update QcInfo qc set qc.orderId = ?1, qc.passedDate = CURRENT_TIMESTAMP where qc.id = ?2")
    void updateQcOrderIdById(Long orderId, Long id);


    @Transactional
    @Modifying
    @Query("update QcInfo qc set qc.status = ?1, qc.passedDate = CURRENT_TIMESTAMP where qc.id = ?2")
    void updateQcStatusById(QcStatusEnums qcStatus, Long id);


    @Query("select  qc from QcInfo qc where qc.status = ?1")
    List<QcInfo> queryQcInfoByStatus(QcStatusEnums qcStatus);

}
