package com.salesmanager.core.business.repositories.shipping;

import com.salesmanager.core.model.system.MerchantShippingConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MerchantShippingConfigurationRepository extends JpaRepository<MerchantShippingConfiguration, Long>, MerchantShippingConfigurationRepositoryCustom {

    @Query("select m from MerchantShippingConfiguration m join fetch m.merchantStore ms where ms.id=?1")
    List<MerchantShippingConfiguration> findByMerchantStore(Integer id);

//    @Query("select m from MerchantShippingConfiguration m join fetch m.merchantStore ms where ms.id=?1 and m.key=?2")
//    MerchantShippingConfiguration findByMerchantStoreAndKey(Integer id, String key);

    @Query("select m from MerchantShippingConfiguration m join fetch m.merchantStore ms where ms.id=?1 and m.defaultShipping=true")
    List<MerchantShippingConfiguration> findDefaultShippingByMerchantStore(Integer id);
}
