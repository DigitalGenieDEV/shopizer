package com.salesmanager.core.business.services.storecertificationfile;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.storecertificationfile.CertificationConfigRepository;
import com.salesmanager.core.business.repositories.storecertificationfile.CertificationFileRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.business.services.customer.CustomerServiceImpl;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.merchant.certificationfile.CertificationFileEntity;
import com.salesmanager.core.model.merchant.certificationfile.PersistableCertificationFile;

@Service("certificationFileService")
public class CertificationFileServiceImpl extends SalesManagerEntityServiceImpl<Long, CertificationFileEntity> implements CertificationFileService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);
	
	@Inject
	private CertificationFileRepository certificationFileRepository;
	
	public CertificationFileServiceImpl(JpaRepository<CertificationFileEntity, Long> repository) {
		super(repository);
		// TODO Auto-generated constructor stub
		this.certificationFileRepository = certificationFileRepository;
	}
	
	@Override
	public void save(CertificationFileEntity entity) throws ServiceException {
		// TODO Auto-generated method stub
		LOGGER.debug("Creating cetification files in templete");
		super.save(entity);
	}

	@Override
	public List<CertificationFileEntity> listByTempletId(Long templetId) {
		// TODO Auto-generated method stub
		return certificationFileRepository.listByTempletId(templetId);
	}

	@Override
	public void updateFileState(PersistableCertificationFile file) {
		// TODO Auto-generated method stub
		certificationFileRepository.updateFileState(file.getId(), file.getBaseYn(), file.getAuditSection().getModifiedBy(), file.getAuditSection().getDateModified());
	}

	@Override
	public CertificationFileEntity getById(Long id) {
		// TODO Auto-generated method stub
		return certificationFileRepository.getById(id);
	}

}
