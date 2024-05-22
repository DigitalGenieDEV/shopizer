package com.salesmanager.shop.store.facade.usermenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.usermenu.UserMenuService;
import com.salesmanager.core.model.adminmenu.AdminMenu;
import com.salesmanager.core.model.adminmenu.ReadAdminMenu;
import com.salesmanager.core.model.usermenu.ReadUserMenu;
import com.salesmanager.core.model.usermenu.UserMenu;
import com.salesmanager.shop.model.adminmenu.ReadableAdminMenu;
import com.salesmanager.shop.model.common.ChangeOrdEntity;
import com.salesmanager.shop.model.common.PersistableChangeOrd;
import com.salesmanager.shop.model.usermenu.PersistableUserMenu;
import com.salesmanager.shop.model.usermenu.ReadableUserMenu;
import com.salesmanager.shop.populator.usermenu.PersistableUserMenuPopulator;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.usermenu.facade.UserMenuFacade;

@Service
public class UserMenuFacadeImpl implements UserMenuFacade {
	@Inject
	private UserMenuService userMenuService;
	
	@Inject
	private PersistableUserMenuPopulator persistableUserMenuPopulator;
	
	@Override
	public ReadableUserMenu getListUserMenu(int visible) throws Exception{
		try{
		  List<ReadUserMenu> dataList = userMenuService.getListUserMenu(visible);
		  List<ReadableUserMenu> tempList = new ArrayList<>();
	      if (dataList.isEmpty()) {
	    	  ReadableUserMenu rootDept = new ReadableUserMenu();
	    	  return rootDept;
	      }else {
	    	  for(ReadUserMenu data : dataList){
	    		  ReadableUserMenu sendData = new ReadableUserMenu();
	    		  sendData.setId(data.getId());
	    		  sendData.setParentId(data.getParent_Id());
	    		  sendData.setMenuName(data.getMenu_Name());
	    		  sendData.setUrl(data.getUrl());
	    		  sendData.setMenuDesc(data.getMenu_Desc());
	    		  sendData.setOrd(data.getOrd());
	    		  sendData.setVisible(data.getVisible());
	    		  sendData.setMemberTarget(data.getMember_Target());
	    		  sendData.setLinkTarget(data.getLink_Target());
	    		  sendData.setTop(data.getTop());
	    		  sendData.setSide(data.getSide());
	    		  sendData.setNavi(data.getNavi());
	    		  sendData.setTab(data.getTab());
	    		  sendData.setDepth(data.getDepth());
	    		  sendData.setMenuNamePath(data.getMenu_name_path().replaceAll("&gt;", " > "));
	    		  tempList.add(sendData);
	    	 }
	    	
	    	  ReadableUserMenu rootMenuVO = new ReadableUserMenu();
	    	rootMenuVO.setId(0);
	  		this.settingUserMenu(tempList, 0, rootMenuVO);
	    	  
	    	 return rootMenuVO;
	      }
	    
	    } catch (ServiceException e){
	    	throw new ServiceRuntimeException(e);
	    }
	}
	
	public PersistableUserMenu saveUserMenu(PersistableUserMenu userMenu) throws Exception{
		try {

			int userMenuId = userMenu.getId();
			UserMenu target = Optional.ofNullable(userMenuId)
					.filter(id -> id > 0)
					.map(userMenuService::getById)
					.orElse(new UserMenu());
			
		
			UserMenu dbUserMenu = populateUserMenu(userMenu, target);
			userMenuService.saveOrUpdate(dbUserMenu);

		
			if (userMenu.getId() > 0) {
				userMenu.setId(userMenu.getId());
			}else {
				userMenu.setId(userMenuService.getMaxId());
			}
			
			return userMenu;
		} catch (ServiceException e) {
			throw new ServiceRuntimeException("Error while updating adminMenu", e);
		}
	}
	
	public ReadableUserMenu getById(int id) throws Exception{
		ReadUserMenu data = userMenuService.getById(id);
		ReadableUserMenu sendData = new ReadableUserMenu();
		if(data != null) {
			  sendData.setId(data.getId());
    		  sendData.setParentId(data.getParent_Id());
    		  sendData.setMenuName(data.getMenu_Name());
    		  sendData.setUrl(data.getUrl());
    		  sendData.setMenuDesc(data.getMenu_Desc());
    		  sendData.setOrd(data.getOrd());
    		  sendData.setVisible(data.getVisible());
    		  sendData.setMemberTarget(data.getMember_Target());
    		  sendData.setLinkTarget(data.getLink_Target());
    		  sendData.setTop(data.getTop());
    		  sendData.setSide(data.getSide());
    		  sendData.setNavi(data.getNavi());
    		  sendData.setTab(data.getTab());
    		  sendData.setMenuNamePath(userMenuService.getNamePath(data.getId()).replaceAll("&gt;", " > "));
		}
		return sendData;
	}
	
	public void deleteUserMenu(int id) throws Exception{
		userMenuService.deleteUserMenu(id);
	}
	
	public void updateChangeOrd(PersistableChangeOrd userMenu, String ip, String userId) throws Exception{
		if(userMenu.getChangeOrdList().size() > 0) {
			for(ChangeOrdEntity data : userMenu.getChangeOrdList()) {
				UserMenu paramVO =  new UserMenu();
				paramVO.setId(data.getId());
				paramVO.setParentId(data.getParentId());
				paramVO.setOrd(data.getChangeOrd());
				paramVO.setMod_id(userId);
				paramVO.setMod_ip(ip);
				userMenuService.updateChangeOrd(paramVO);
			}
		}
	}
	
	
	
	private UserMenu populateUserMenu(PersistableUserMenu userMenu, UserMenu target) {
		try {
			return persistableUserMenuPopulator.populate(userMenu, target);
		} catch (ConversionException e) {
			throw new ServiceRuntimeException(e);
		}
	}
	
	private int settingUserMenu(List<ReadableUserMenu> menu_all_list, int list_pointer, ReadableUserMenu parent_domain)throws Exception  {
		int tmp_list_pointer = list_pointer;
		ReadableUserMenu front_menu_domain = null;
		for(int i = tmp_list_pointer ; i < menu_all_list.size() ; i++){
			tmp_list_pointer = i;
			ReadableUserMenu this_menu_domain = menu_all_list.get(i);
			
			if(parent_domain.getId() == this_menu_domain.getParentId()){
				
				if(parent_domain.getChildren() == null){
					parent_domain.setChildren(new ArrayList<ReadableUserMenu>());
				}
				parent_domain.getChildren().add(this_menu_domain);
				front_menu_domain = this_menu_domain;
			}else{
				if((front_menu_domain != null && (front_menu_domain.getDepth() > this_menu_domain.getDepth()))){
					tmp_list_pointer--;
					break;
				}else if(front_menu_domain == null){
					break; 
				}else{
					i = settingUserMenu(menu_all_list, tmp_list_pointer, front_menu_domain);
				}
			}
		}
		
		return tmp_list_pointer;
	}
}
