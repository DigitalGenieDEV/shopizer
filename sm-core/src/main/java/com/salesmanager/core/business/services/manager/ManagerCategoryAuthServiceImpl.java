package com.salesmanager.core.business.services.manager;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.manager.ManagerCategoryAuthRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.manager.CategoryAuth;
import com.salesmanager.core.model.manager.ReadCategoryAuth;


@Service("managerCategoryAuthService")
public class ManagerCategoryAuthServiceImpl extends SalesManagerEntityServiceImpl<Integer, CategoryAuth>  implements ManagerCategoryAuthService {
	
	@Inject
	private ManagerCategoryAuthRepository managerCategoryAuthRepository;

	@Inject
	public ManagerCategoryAuthServiceImpl(ManagerCategoryAuthRepository managerCategoryAuthRepository) {
		super(managerCategoryAuthRepository);
		this.managerCategoryAuthRepository = managerCategoryAuthRepository;
	}
	
	
	public List<ReadCategoryAuth> getCategoryAuthFullList(int grpId) throws ServiceException{
		return managerCategoryAuthRepository.getCategoryAuthFullList(grpId);
	}
	
	public List<ReadCategoryAuth> getCategoryList() throws ServiceException{
		return managerCategoryAuthRepository.getCategoryList();
	}
	
	public List<CategoryAuth> getCategoryAuthList(int grpId) throws ServiceException{
		return managerCategoryAuthRepository.getCategoryAuthList(grpId);
	}
	
	public void deleteCategoryAuth(int grpId) throws ServiceException{
		managerCategoryAuthRepository.deleteCategoryAuth(grpId);
	}
	
	public void saveOrUpdate(CategoryAuth categoryAuth) throws ServiceException {
		// save or update (persist and attach entities
		if (categoryAuth.getId() != null && categoryAuth.getId() > 0) {
			super.update(categoryAuth);
		} else {
			this.create(categoryAuth);
		}
	}

}
