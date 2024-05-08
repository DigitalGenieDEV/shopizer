package com.salesmanager.shop.store.facade.dept;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.dept.DeptService;
import com.salesmanager.core.model.dept.Dept;
import com.salesmanager.core.model.dept.ReadDept;
import com.salesmanager.shop.model.common.ChangeOrdEntity;
import com.salesmanager.shop.model.common.PersistableChangeOrd;
import com.salesmanager.shop.model.dept.PersistableDept;
import com.salesmanager.shop.model.dept.ReadableDept;
import com.salesmanager.shop.populator.dept.PersistableDeptPopulator;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.dept.facade.DeptFacade;


@Service
public class DeptFacadeImpl  implements DeptFacade {
	
	@Inject
	private DeptService deptService;
	
	@Inject
	private PersistableDeptPopulator persistableDeptPopulator;
	
	@Override
	public ReadableDept getListDept(int visible) throws Exception{
		try{
			List<ReadDept> dataList = deptService.getListDept(visible);
			List<ReadableDept> tempList = new ArrayList<>();
			if (dataList.isEmpty()) {
				ReadableDept rootDept = new ReadableDept();
				return rootDept;
			}else {
				for(ReadDept data : dataList){
					ReadableDept targetData = new ReadableDept();
					targetData.setId(data.getId());
					targetData.setParentId(data.getParent_id());
					targetData.setDeptCode(data.getDept_code());
					targetData.setDeptName(data.getDept_name());
					targetData.setTel(data.getTel());
					targetData.setVisible(data.getVisible());
					targetData.setDeptNamePath(data.getDept_name_path().replaceAll("&gt;", " > "));
					targetData.setOrd(data.getOrd());
					targetData.setDepth(data.getDepth());
					tempList.add(targetData);
				}
				
				ReadableDept rootDept = new ReadableDept();
				rootDept.setId(0);
				this.settingDept(tempList, 0, rootDept);
				return rootDept;
			}
		} catch (ServiceException e){
			throw new ServiceRuntimeException(e);
		}
	}
	
	
	public PersistableDept saveDept(PersistableDept dept){
		try {

			int deptId = dept.getId();
			Dept target = Optional.ofNullable(deptId)
					.filter(id -> id > 0)
					.map(deptService::getById)
					.orElse(new Dept());
			
		
			Dept dbDept = populateDept(dept, target);
			deptService.saveOrUpdate(dbDept);

			// set category id
			dept.setId(dept.getId());
			return dept;
		} catch (ServiceException e) {
			throw new ServiceRuntimeException("Error while updating Dept", e);
		}
	}
	
	public void deleteDept(int deptId) throws Exception{
		deptService.deleteDept(deptId);
	}
	
	private Dept populateDept( PersistableDept dept, Dept target) {
		try {
			return persistableDeptPopulator.populate(dept, target);
		} catch (ConversionException e) {
			throw new ServiceRuntimeException(e);
		}
	}
	
	

	public ReadableDept getById(int id) throws ServiceException {
		Validate.notNull(id, "dept id must not be null");
		
		ReadDept data = deptService.getById(id);
		ReadableDept targetData = new ReadableDept();
		if (data == null) {
			throw new ResourceNotFoundException("Dept with id [" + id + "] not found");
		}else {
			targetData.setId(data.getId());
			targetData.setParentId(data.getParent_id());
			targetData.setDeptCode(data.getDept_code());
			targetData.setDeptName(data.getDept_name());
			targetData.setTel(data.getTel());
			targetData.setVisible(data.getVisible());
			targetData.setContent(data.getContent());
			targetData.setDeptNamePath(deptService.getNamePath(data.getId()).replaceAll("&gt;", " > "));
			targetData.setOrd(data.getOrd());
		}
		return targetData;
	}
	
	public void updateChangeOrd(PersistableChangeOrd dept, String ip) throws Exception{
		if(dept.getChangeOrdList().size() > 0) {
			for(ChangeOrdEntity data : dept.getChangeOrdList()) {
				Dept paramVO =  new Dept();
				paramVO.setId(data.getId());
				paramVO.setParentId(data.getParentId());
				paramVO.setOrd(data.getChangeOrd());
				paramVO.setMod_id(data.getUserId());
				paramVO.setMod_ip(ip);
				deptService.updateChangeOrd(paramVO);
			}
		}
		
	}
	
	private int settingDept(List<ReadableDept> deptList, int list_pointer, ReadableDept parent_domain)throws Exception  {
		int tmp_list_pointer = list_pointer;
		ReadableDept front_dept_domain = null;
		for(int i = tmp_list_pointer ; i < deptList.size() ; i++){
			tmp_list_pointer = i;
			ReadableDept deptVO = deptList.get(i);
			if(parent_domain.getId() == deptVO.getParentId()){
				if(parent_domain.getChildren() == null){
					parent_domain.setChildren(new ArrayList<ReadableDept>());
				}
				parent_domain.getChildren().add(deptVO);
				front_dept_domain = deptVO;
			}else{
				if((front_dept_domain != null && (front_dept_domain.getDepth() > deptVO.getDepth()))){
					tmp_list_pointer--;
					break;
				}else if(front_dept_domain == null){
					break; 
				}else{
					i = settingDept(deptList, tmp_list_pointer, front_dept_domain);
				}
			}
		}
		
		return tmp_list_pointer;
	}
}
