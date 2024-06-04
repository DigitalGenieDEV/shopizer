package com.salesmanager.shop.store.facade.manager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.adminmenu.AdminMenuService;
import com.salesmanager.core.business.services.manager.ManagerMenuAuthService;
import com.salesmanager.core.business.services.manager.ManagerService;
import com.salesmanager.core.business.services.merchant.MerchantStoreService;
import com.salesmanager.core.model.manager.Manager;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.shop.model.manager.PersistableManager;
import com.salesmanager.shop.model.manager.ReadableManager;
import com.salesmanager.shop.model.manager.ReadableManagerList;
import com.salesmanager.shop.populator.manager.PersistableManagerPopulator;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.api.exception.UnauthorizedException;
import com.salesmanager.shop.store.controller.manager.facade.ManagerFacade;
import com.salesmanager.shop.store.controller.security.facade.SecurityFacade;

@Service
public class ManagerFacadeImpl implements ManagerFacade {

	private static final String PRIVATE_PATH = "/private/";

	@Inject
	private ManagerService managerService;

	@Inject
	private MerchantStoreService merchantStoreService;

	@Inject
	private SecurityFacade securityFacade;

	@Inject
	private ManagerMenuAuthService managerMenuAuthService;

	@Inject
	private AdminMenuService adminMenuService;

	@Inject
	private ObjectMapper objectMapper;

	@Inject
	private PersistableManagerPopulator persistableManagerPopulator;

	private static final Logger LOGGER = LoggerFactory.getLogger(ManagerFacadeImpl.class);

	@Override
	public ReadableManagerList getManagerList(String keyword, String gbn, int deptId, int page, int count)
			throws Exception {
		try {
			List<Manager> manager = null;
			List<ReadableManager> targetList = new ArrayList<ReadableManager>();
			ReadableManagerList returnList = new ReadableManagerList();
			org.springframework.data.domain.Page<Manager> pageable = managerService.getManagerList(keyword, gbn, deptId,
					page, count);
			manager = pageable.getContent();
			returnList.setRecordsTotal(pageable.getTotalElements());
			returnList.setTotalPages(pageable.getTotalPages());
			returnList.setNumber(manager.size());
			if (manager.size() > 0) {
				for (Manager data : manager) {
					ReadableManager targetData = objectMapper.convertValue(data, ReadableManager.class);
					targetList.add(targetData);

				}
			}
			returnList.setData(targetList);
			return returnList;
		} catch (ServiceException e) {
			throw new ServiceRuntimeException(e);
		}
	}

	public void updateEnabled(PersistableManager manager) throws Exception {
		Validate.notNull(manager, "Manager cannot be null");
		try {
			Manager modelManager = managerService.getId(manager.getId());

			if (modelManager == null) {
				throw new ResourceNotFoundException("Manager with id [" + manager.getId() + "] not found ");
			}

			modelManager.setActive(manager.isActive());
			managerService.saveOrUpdate(modelManager);

		} catch (ServiceException e) {
			throw new ServiceRuntimeException("Error while updating user enable flag", e);
		}
	}

	public boolean isManagerExist(String userId) throws Exception {
		return managerService.getDupEmplIdCount(userId) > 0 ? true : false;

	}

	public PersistableManager create(PersistableManager manager) throws Exception {
		try {

			// check if user exists
			int dupCnt = managerService.getDupEmplIdCount(manager.getEmplId());
			if (dupCnt > 0) {
				throw new ServiceRuntimeException("Manager [" + manager.getEmplId() + "] already exists ");
			}

			/**
			 * validate password
			 */
			if (!securityFacade.matchRawPasswords(manager.getPassword(), manager.getRepeatPassword())) {
				throw new ServiceRuntimeException(
						"Passwords dos not match, make sure password and repeat password are equals");
			}

			/**
			 * Validate new password
			 */
			if (!securityFacade.validateUserPassword(manager.getPassword())) {
				throw new ServiceRuntimeException("New password does not apply to format policy");
			}

			String newPasswordEncoded = securityFacade.encodePassword(manager.getPassword());

			Long managerId = manager.getId();
			Manager target = Optional.ofNullable(managerId).filter(id -> id > 0).map(managerService::getById)
					.orElse(new Manager());

			Manager dbManager = populateManager(manager, target);
			manager.setPassword(newPasswordEncoded);
			managerService.saveOrUpdate(dbManager);

			// set category id
			manager.setId(manager.getId());

			return manager;
		} catch (ServiceException e) {
			throw new ServiceRuntimeException("Error while updating Manager", e);
		}
	}

	public ReadableManager getById(Long id) throws Exception {
		Manager manager = managerService.getById(id);

		if (manager == null) {
			throw new ResourceNotFoundException("Manager [" + id + "] not found");
		}

		objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		ReadableManager readableManager = objectMapper.convertValue(manager, ReadableManager.class);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (manager.getAuditSection().getModDate() != null) {
			String modDate = dateFormat.format(manager.getAuditSection().getModDate());
			readableManager.setModDate(modDate);
		}
		if (manager.getLoginDate() != null) {
			String loginDate = dateFormat.format(manager.getLoginDate());
			readableManager.setLoginDate(loginDate);
		}
		return readableManager;
	}

	public PersistableManager update(PersistableManager persistableManager) throws Exception {
		Validate.notNull(persistableManager, "Manager cannot be null");
		try {

			String newPasswordEncoded = "";
			Manager data = managerService.getById(persistableManager.getId());
			if (data != null) {

				if (!persistableManager.getPassword().equals("") || !persistableManager.getPassword().isEmpty()) {
					/**
					 * validate password
					 */
					if (!securityFacade.matchRawPasswords(persistableManager.getPassword(),
							persistableManager.getRepeatPassword())) {
						throw new ServiceRuntimeException(
								"Passwords dos not match, make sure password and repeat password are equals");
					}

					/**
					 * Validate new password
					 */
					if (!securityFacade.validateUserPassword(persistableManager.getPassword())) {
						throw new ServiceRuntimeException("New password does not apply to format policy");
					}

					newPasswordEncoded = securityFacade.encodePassword(persistableManager.getPassword());
					persistableManager.setPassword(newPasswordEncoded);
				} else {
					persistableManager.setPassword(data.getAdminPassword());
				}

				Long managerId = persistableManager.getId();
				Manager target = Optional.ofNullable(managerId).filter(id -> id > 0).map(managerService::getById)
						.orElse(new Manager());

				Manager dbManager = populateManager(persistableManager, target);
				dbManager.setAdminPassword(persistableManager.getPassword());
				managerService.saveOrUpdate(dbManager);

				// set category id
				persistableManager.setId(persistableManager.getId());

			}

			return persistableManager;
		} catch (ServiceException e) {
			throw new ServiceRuntimeException("Error while updating Manager", e);
		}
	}

	public void delete(Long id) throws Exception {
		Manager manager = managerService.getById(id);
		if (manager == null) {
			throw new ResourceNotFoundException("Manager [" + id + "] not found");
		}
		managerService.delete(manager);
	}

	public void updateLoginFailCount(String emplId) throws Exception {
		managerService.updateLoginFailCount(emplId);
	}

	public void updateLoginDate(String emplId) throws Exception {
		managerService.updateLoginDate(emplId);
	}

	private Manager populateManager(PersistableManager manager, Manager target) {
		try {
			return persistableManagerPopulator.populate(manager, target);
		} catch (ConversionException e) {
			throw new ServiceRuntimeException(e);
		}
	}

	@Override
	public String authenticatedManager() throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null) {
			throw new UnauthorizedException("Manager Not authorized");
		}

		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUserName = authentication.getName();
			return currentUserName;
		}
		return null;
	}

	public String authorizedMenu(String userName, String url) throws Exception {
		com.salesmanager.core.model.manager.ReadableManager manager = managerService.getByUserName(userName);
		boolean authFlag = false;
		if (manager.getGrpId() == 1) {
			authFlag = true;
		} else {
			int menuId = adminMenuService.getApiMenuFindId(url);
			if (menuId > 0) {
				int authCnt = managerMenuAuthService.getMenuAuthCnt(manager.getGrpId(), menuId);
				if (authCnt > 0) {
					authFlag = true;
				} else {
					authFlag = false;
				}
			} else {
				authFlag = true;
			}
		}
		if (!authFlag) {
			throw new UnauthorizedException("User " + userName + " not authorized");
		}

		return "";
	}

	@Override
	public boolean authorizeStore(MerchantStore store, String path) throws Exception {

		if (store == null) {
			throw new ResourceNotFoundException("MerchantStore is not found");
		}

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (!StringUtils.isBlank(path) && path.contains(PRIVATE_PATH)) {

			Validate.notNull(authentication, "Don't call ths method if a user is not authenticated");

			try {

				String currentPrincipalName = authentication.getName();

				LOGGER.info("Principal " + currentPrincipalName);

				com.salesmanager.core.model.manager.ReadableManager manager = managerService
						.getByUserName(currentPrincipalName);
				// ReadableUser readableUser = findByUserName(currentPrincipalName,
				// store.getCode(), store.getDefaultLanguage());
				if (manager == null) {
					return false;
				}

				// current user match;
				String merchant = manager.getCode();

				// user store is store request param
				if (store.getCode().equalsIgnoreCase(merchant)) {
					return true;
				}

				// Set<String> roles = authentication.getAuthorities().stream().map(r ->
				// r.getAuthority())
				// .collect(Collectors.toSet());

				// is superadmin
				if (manager.getGrpId() == 1) {
					return true;
				}
//				for (ReadableGroup group : readableUser.getGroups()) {
//					if (Constants.GROUP_SUPERADMIN.equals(group.getName())) {
//						return true;
//					}
//				}

				boolean authorized = false;

				// user store can be parent and requested store is child
				// get parent
				// TODO CACHE
				MerchantStore parent = null;

				if (store.getParent() != null) {
					parent = merchantStoreService.getParent(merchant);
				}

				// user can be in parent
				if (parent != null && parent.getCode().equals(store.getCode())) {
					authorized = true;
				}

				// else false
				return authorized;
			} catch (Exception e) {
				throw new UnauthorizedException("Cannot authorize user " + authentication.getPrincipal().toString()
						+ " for store " + store.getCode(), e.getMessage());
			}

		}

		return true;
	}

	@Override
	public boolean userInRoles(String userName) throws Exception {
		com.salesmanager.core.model.manager.ReadableManager manager = managerService.getByUserName(userName);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<String> roles = authentication.getAuthorities().stream()
				.filter(x -> manager.getGrpName().contains(x.getAuthority())).map(r -> r.getAuthority())
				.collect(Collectors.toList());

		return roles.size() > 0;

	}

	@Override
	public boolean authorizedStore(String userName, String merchantStoreCode) {

		try {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			Set<String> roles = authentication.getAuthorities().stream().map(r -> r.getAuthority())
					.collect(Collectors.toSet());

			com.salesmanager.core.model.manager.ReadableManager manager = managerService.getByUserName(userName);

			// is superadmin
			if (manager.getGrpId() == 1) {
				return true;
			}

			boolean authorized = false;

			if (manager != null) {
				authorized = true;
			}

			if (manager != null && !authorized) {

				// get parent
				MerchantStore store = merchantStoreService.getParent(merchantStoreCode);

				// user can be in parent
				if (store != null && store.getCode().equals(merchantStoreCode)) {
					authorized = true;
				}

			}

			return authorized;
		} catch (Exception e) {
			throw new ServiceRuntimeException("Cannot authorize user " + userName + " for store " + merchantStoreCode,
					e.getMessage());
		}
	}

}
