package com.salesmanager.shop.store.facade.adminmenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.adminmenu.AdminMenuService;
import com.salesmanager.core.model.adminmenu.AdminMenu;
import com.salesmanager.core.model.adminmenu.ReadAdminMenu;
import com.salesmanager.shop.model.adminmenu.PersistableAdminMenu;
import com.salesmanager.shop.model.adminmenu.ReadableAdminMenu;
import com.salesmanager.shop.model.common.ChangeOrdEntity;
import com.salesmanager.shop.model.common.PersistableChangeOrd;
import com.salesmanager.shop.populator.adminmenu.PersistableAdminMenuPopulator;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.adminmenu.facade.AdminMenuFacade;


@Service
public class AdminMenuFacadeImpl implements AdminMenuFacade {
	
	@Inject
	private AdminMenuService adminMenuService;
	
	@Inject
	private PersistableAdminMenuPopulator persistableAdminMenuPopulator;
	 
	
	@Override
	public ReadableAdminMenu getListAdminMenu(int visible) throws Exception{
		try{
		  List<ReadAdminMenu> dataList = adminMenuService.getListAdminMenu(visible);
		  List<ReadableAdminMenu> tempList = new ArrayList<>();
	      if (dataList.isEmpty()) {
	    	  ReadableAdminMenu rootDept = new ReadableAdminMenu();
	    	  return rootDept;
	      }else {
	    	  for(ReadAdminMenu data : dataList){
	    		  ReadableAdminMenu sendData = new ReadableAdminMenu();
	    		  sendData.setId(data.getId());
	    		  sendData.setParentId(data.getParent_Id());
	    		  sendData.setMenuName(data.getMenu_Name());
	    		  sendData.setMenuDesc(data.getMenu_Desc());
	    		  sendData.setMenuNamePath(data.getMenu_name_path().replaceAll("&gt;", " > "));
	    		  sendData.setMenuUrl(data.getMenu_Url());
	    		  sendData.setOrd(data.getOrd());
	    		  sendData.setDepth(data.getDepth());
	    		  sendData.setVisible(data.getVisible());
	    		  tempList.add(sendData);
	    	 }
	    	
	    	ReadableAdminMenu rootMenuVO = new ReadableAdminMenu();
	    	rootMenuVO.setId(0);
	  		this.settingAdminMenu(tempList, 0, rootMenuVO);
	    	  
	    	 return rootMenuVO;
	      }
	    
	    } catch (ServiceException e){
	    	throw new ServiceRuntimeException(e);
	    }
	}
	
	public PersistableAdminMenu saveAdminMenu(PersistableAdminMenu adminMenu){
		try {

			int adminMenuId = adminMenu.getId();
			AdminMenu target = Optional.ofNullable(adminMenuId)
					.filter(id -> id > 0)
					.map(adminMenuService::getById)
					.orElse(new AdminMenu());
			
		
			AdminMenu dbAdminMenu = populateAdminMenu(adminMenu, target);
			adminMenuService.saveOrUpdate(dbAdminMenu);

			// set category id
			adminMenu.setId(adminMenu.getId());
			return adminMenu;
		} catch (ServiceException e) {
			throw new ServiceRuntimeException("Error while updating adminMenu", e);
		}
	}
	
	
	
	public ReadableAdminMenu getById(int adminMenuId) throws Exception{
		ReadAdminMenu data = adminMenuService.getById(adminMenuId);
		ReadableAdminMenu sendData = new ReadableAdminMenu();
		if(data != null) {
			  sendData.setId(data.getId());
			  sendData.setParentId(data.getParent_Id());
			  sendData.setMenuName(data.getMenu_Name());
			  sendData.setMenuDesc(data.getMenu_Desc());
			  sendData.setMenuNamePath(adminMenuService.getNamePath(data.getId()).replaceAll("&gt;", " > "));
			  sendData.setMenuUrl(data.getMenu_Url());
			  sendData.setVisible(data.getVisible());
		}
		return sendData;
	}
	

	public void deleteAdminMenu(int adminMenuId) throws Exception{
		adminMenuService.deleteAdminMenu(adminMenuId);
	}
	
	public void updateChangeOrd(PersistableChangeOrd adminMenu, String ip) throws Exception{
		if(adminMenu.getChangeOrdList().size() > 0) {
			for(ChangeOrdEntity data : adminMenu.getChangeOrdList()) {
				AdminMenu paramVO =  new AdminMenu();
				paramVO.setId(data.getId());
				paramVO.setParentId(data.getParentId());
				paramVO.setOrd(data.getChangeOrd());
				paramVO.setMod_id(data.getUserId());
				paramVO.setMod_ip(ip);
				adminMenuService.updateChangeOrd(paramVO);
			}
		}
		
	}
	
	private AdminMenu populateAdminMenu(PersistableAdminMenu adminMenu, AdminMenu target) {
		try {
			return persistableAdminMenuPopulator.populate(adminMenu, target);
		} catch (ConversionException e) {
			throw new ServiceRuntimeException(e);
		}
	}

	
	private int settingAdminMenu(List<ReadableAdminMenu> menu_all_list, int list_pointer, ReadableAdminMenu parent_domain)throws Exception  {
		int tmp_list_pointer = list_pointer;
		ReadableAdminMenu front_menu_domain = null;
		for(int i = tmp_list_pointer ; i < menu_all_list.size() ; i++){
			tmp_list_pointer = i;
			ReadableAdminMenu this_menu_domain = menu_all_list.get(i);
			
			if(parent_domain.getId() == this_menu_domain.getParentId()){
				
				if(parent_domain.getChildren() == null){
					parent_domain.setChildren(new ArrayList<ReadableAdminMenu>());
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
					i = settingAdminMenu(menu_all_list, tmp_list_pointer, front_menu_domain);
				}
			}
		}
		
		return tmp_list_pointer;
	}
}
