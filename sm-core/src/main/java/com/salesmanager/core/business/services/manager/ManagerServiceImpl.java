package com.salesmanager.core.business.services.manager;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.manager.ManagerRepository;
import com.salesmanager.core.business.repositories.manager.PageableManagerRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.manager.Manager;
import com.salesmanager.core.model.manager.ReadableManager;

@Service("managerService")
public class ManagerServiceImpl extends SalesManagerEntityServiceImpl<Long, Manager> implements ManagerService {

	@Inject
	private ManagerRepository managerRepository;

	@Inject
	private PageableManagerRepository pageableManagerRepository;

	@Inject
	public ManagerServiceImpl(ManagerRepository managerRepository) {
		super(managerRepository);
		this.managerRepository = managerRepository;
	}

	public Page<Manager> getManagerList(String keyword, String gbn, int deptId, int page, int count)
			throws ServiceException {
		Pageable pageRequest = PageRequest.of(page, count);
		return pageableManagerRepository.getManagerList(keyword, gbn, deptId, pageRequest);
	}

	public Manager getId(Long id) throws ServiceException {
		return managerRepository.getById(id);
	}

	public ReadableManager getByUserName(String userName) throws ServiceException {
		return managerRepository.getByUserName(userName);
	}

	public int getDupEmplIdCount(String userId) throws ServiceException {
		return managerRepository.getDupEmplIdCount(userId);
	}

	@Override
	public void saveOrUpdate(Manager manger) throws ServiceException {
		// save or update (persist and attach entities
		if (manger.getId() != null && manger.getId() > 0) {
			super.update(manger);
		} else {
			this.create(manger);
		}
	}

	public void deleteManager(int grpId) throws ServiceException {
		managerRepository.deleteManager(grpId);
	}

	public void updateLoginFailCount(String emplId) throws ServiceException {
		managerRepository.updateLoginFailCount(emplId);
	}

	public void updateLoginDate(String emplId) throws ServiceException {
		managerRepository.updateLoginDate(emplId);
	}

}
