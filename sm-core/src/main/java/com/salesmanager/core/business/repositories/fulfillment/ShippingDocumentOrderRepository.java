package com.salesmanager.core.business.repositories.fulfillment;

import com.salesmanager.core.enmus.QcStatusEnums;
import com.salesmanager.core.model.fulfillment.QcInfo;
import com.salesmanager.core.model.fulfillment.QcInfoHistory;
import com.salesmanager.core.model.fulfillment.ShippingDocumentOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;


public interface ShippingDocumentOrderRepository extends JpaRepository<ShippingDocumentOrder, Long>, ShippingDocumentOrderRepositoryCustom {


    @Query("select count(*) from ShippingDocumentOrder c "
            + " where c.auditSection.dateCreated=?1 ")
    Integer queryCountByCreateDate(Date createTime);



}
