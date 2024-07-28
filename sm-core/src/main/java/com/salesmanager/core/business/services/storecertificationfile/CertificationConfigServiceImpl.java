package com.salesmanager.core.business.services.storecertificationfile;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.storecertificationfile.CertificationConfigRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.business.services.customer.CustomerServiceImpl;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.merchant.certificationfile.CertificationConfigEntity;
import com.salesmanager.core.model.merchant.certificationfile.PersistableCertificationConfig;

@Service("cetificationConfigService")
public class CertificationConfigServiceImpl extends SalesManagerEntityServiceImpl<Long, CertificationConfigEntity> implements CertificationConfigService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);
	
	@Inject
	private CertificationConfigRepository certificationConfigRepository;
	
	public CertificationConfigServiceImpl(JpaRepository<CertificationConfigEntity, Long> repository) {
		super(repository);
		// TODO Auto-generated constructor stub
		this.certificationConfigRepository = certificationConfigRepository;
	}
	
	@Override
	public void save(CertificationConfigEntity certificationConfigEntity) throws ServiceException {
		// TODO Auto-generated method stub
		LOGGER.debug("Creating cetification file's templete");
		super.save(certificationConfigEntity);
	}

	@Override
	public void deleteTemplete(MerchantStore store, String userName, Long id) {
		// TODO Auto-generated method stub 
		certificationConfigRepository.deleteTemplete(id, userName);
	}

	@Override
	public List<CertificationConfigEntity> listByStoreId(Integer storeId) {
		// TODO Auto-generated method stub
		return certificationConfigRepository.listByStoreId(storeId);
	}

	@Override
	public void updateTemplete(CertificationConfigEntity certificationConfigEntity) throws ServiceException {
		// TODO Auto-generated method stub
		super.update(certificationConfigEntity);
	}

}
