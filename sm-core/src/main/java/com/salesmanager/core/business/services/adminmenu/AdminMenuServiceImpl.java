package com.salesmanager.core.business.services.adminmenu;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.adminmenu.AdminMenuRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.adminmenu.AdminMenu;
import com.salesmanager.core.model.adminmenu.ReadAdminMenu;

@Service("adminMenuService")
public class AdminMenuServiceImpl extends SalesManagerEntityServiceImpl<Integer, AdminMenu>
		implements AdminMenuService {
	private AdminMenuRepository adminMenuRepository;

	@Inject
	public AdminMenuServiceImpl(AdminMenuRepository adminMenuRepository) {
		super(adminMenuRepository);
		this.adminMenuRepository = adminMenuRepository;
	}

	public List<ReadAdminMenu> getListAdminMenu(int visible) throws ServiceException {
		return adminMenuRepository.getListAdminMenu(visible);
	}

	public int getOrder(int parentId) throws ServiceException {
		return adminMenuRepository.getOrder(parentId);
	}

	public String getNamePath(int id) throws ServiceException {
		return adminMenuRepository.getNamePath(id);
	}

	public ReadAdminMenu getById(int adminMenuId) throws ServiceException {
		return adminMenuRepository.getById(adminMenuId);
	}

	@Override
	public void saveOrUpdate(AdminMenu adminMenu) throws ServiceException {
		// save or update (persist and attach entities
		if (adminMenu.getId() != null && adminMenu.getId() > 0) {
			super.update(adminMenu);
		} else {
			this.create(adminMenu);
		}

	}
	
	public int getMaxId() throws ServiceException{
		return adminMenuRepository.getMaxId();
	}

	public void deleteAdminMenu(int adminMenuId) throws ServiceException {
		adminMenuRepository.deleteAdminMenu(adminMenuId);
	}

	public void updateChangeOrd(AdminMenu adminMenu) throws ServiceException {
		adminMenuRepository.updateChangeOrd(adminMenu);
	}
}
