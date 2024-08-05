package com.salesmanager.core.business.repositories.fulfillment;

import com.salesmanager.core.model.fulfillment.AdditionalServices;
import com.salesmanager.core.model.reference.language.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface AdditionalServicesRepository extends JpaRepository<AdditionalServices, Long> {


    @Query("select distinct p from AdditionalServices p " +
            "left join fetch p.descriptions pd where p.merchantId = ?1 order by p.sort")
    List<AdditionalServices> queryAdditionalServicesByMerchantId(Long merchantId);
}
