package com.salesmanager.core.business.services.usermenu;

import java.util.List;

import javax.inject.Inject;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.usermenu.UserMenuRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.usermenu.ReadUserMenu;
import com.salesmanager.core.model.usermenu.UserMenu;

@Service("userMenuService")
public class UserMenuServiceImpl extends SalesManagerEntityServiceImpl<Integer, UserMenu> implements UserMenuService {
	
	@Inject
	private UserMenuRepository uerMenuRepository;

	@Inject
	public UserMenuServiceImpl(UserMenuRepository uerMenuRepository) {
		super(uerMenuRepository);
		this.uerMenuRepository = uerMenuRepository;
	}

	@Cacheable(value = "userMenuCode")
	public List<ReadUserMenu> getListUserMenu(int visible) throws ServiceException {
		System.out.println("1111");
		return uerMenuRepository.getListUserMenu(visible);
	}

	public int getOrder(int parentId) throws ServiceException {
		return uerMenuRepository.getOrder(parentId);
	}

	public void saveOrUpdate(UserMenu userMenu) throws ServiceException {
		// save or update (persist and attach entities
		if (userMenu.getId() != null && userMenu.getId() > 0) {
			super.update(userMenu);
		} else {
			this.create(userMenu);
		}

	}

	public int getMaxId() throws ServiceException {
		return uerMenuRepository.getMaxId();
	}

	public ReadUserMenu getById(int id) throws ServiceException {
		return uerMenuRepository.getById(id);
	}

	public String getNamePath(int id) throws ServiceException {
		return uerMenuRepository.getNamePath(id);
	}

	public void deleteUserMenu(int id) throws ServiceException {
		uerMenuRepository.deleteUserMenu(id);

	}

	public void updateChangeOrd(UserMenu userMenu) throws ServiceException {
		uerMenuRepository.updateChangeOrd(userMenu);

	}
	
	@CacheEvict(value = "userMenuCode", allEntries = true)
	public void resetAllEntriesInCash() {
	}

}
