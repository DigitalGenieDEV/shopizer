package com.salesmanager.core.business.services.manager;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.manager.ManagerMenuAuthRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.manager.MenuAuth;
import com.salesmanager.core.model.manager.ReadMenuAuth;

@Service("managerMenuAuthService")
public class ManagerMenuAuthServiceImpl extends SalesManagerEntityServiceImpl<Integer, MenuAuth>
		implements ManagerMenuAuthService {

	@Inject
	private ManagerMenuAuthRepository managerMenuAuthRepository;

	@Inject
	public ManagerMenuAuthServiceImpl(ManagerMenuAuthRepository managerMenuAuthRepository) {
		super(managerMenuAuthRepository);
		this.managerMenuAuthRepository = managerMenuAuthRepository;
	}
	
	public List<ReadMenuAuth> getManagerAdminMenuAuthList(int grpId) throws ServiceException {
		return managerMenuAuthRepository.getManagerAdminMenuAuthList(grpId);
	}

	public void deleteMenuAuth(int grpId) throws ServiceException {
		managerMenuAuthRepository.deleteMenuAuth(grpId);
	}

	public void saveOrUpdate(MenuAuth menuAuth) throws ServiceException {
		// save or update (persist and attach entities
		if (menuAuth.getId() != null && menuAuth.getId() > 0) {
			super.update(menuAuth);
		} else {
			this.create(menuAuth);
		}
	}
	
	public int getMenuAuthCnt(int grpId, int menuId)  throws ServiceException{
		return managerMenuAuthRepository.getMenuAuthCnt(grpId, menuId);
	}
}
