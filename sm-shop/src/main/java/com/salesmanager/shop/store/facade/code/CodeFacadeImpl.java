package com.salesmanager.shop.store.facade.code;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.code.CodeService;
import com.salesmanager.core.model.code.Code;
import com.salesmanager.core.model.code.ReadCode;
import com.salesmanager.shop.model.code.PersistableCode;
import com.salesmanager.shop.model.code.ReadableCode;
import com.salesmanager.shop.model.common.ChangeOrdEntity;
import com.salesmanager.shop.model.common.PersistableChangeOrd;
import com.salesmanager.shop.populator.code.PersistableCodePopulator;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.code.facade.CodeFacade;

@Service
public class CodeFacadeImpl implements CodeFacade {
	@Inject
	private CodeService codeService;
	
	@Inject
	private PersistableCodePopulator persistableCodePopulator;

	public ReadableCode getListCode(int visible) throws Exception {
		try {
			List<ReadCode> dataList = codeService.getListCode(visible);
			List<ReadableCode> tempList = new ArrayList<>();
			if (dataList.isEmpty()) {
				ReadableCode rootDept = new ReadableCode();
				return rootDept;
			} else {
				for (ReadCode data : dataList) {
					ReadableCode sendData = new ReadableCode();
					sendData.setId(data.getId());
					sendData.setParentId(data.getParent_id());
					sendData.setCode(data.getCode());
					sendData.setCodeNameKr(data.getCode_Name_Kr());
					sendData.setCodeNameEn(data.getCode_Name_En());
					sendData.setCodeNameCn(data.getCode_Name_Cn());
					sendData.setCodeNameJp(data.getCode_Name_Jp());
					sendData.setCodeNamePath(data.getCode_Name_Path().replaceAll("&gt;", " > "));
					sendData.setCodeDesc(data.getCode_Desc());
					sendData.setValue(data.getValue());
					sendData.setOrd(data.getOrd());
					sendData.setDepth(data.getDepth());
					sendData.setVisible(data.getVisible());
					tempList.add(sendData);
				}

				ReadableCode rootCodeVO = new ReadableCode();
				rootCodeVO.setId(0);
				this.settingCode(tempList, 0, rootCodeVO);

				return rootCodeVO;
			}

		} catch (ServiceException e) {
			throw new ServiceRuntimeException(e);
		}

	}
	
	
	public List<ReadableCode> getListCodeDetail(String code) throws Exception {
		try {
			List<ReadCode> dataList = codeService.getListCodeDetail(code);
			List<ReadableCode> sendList = new ArrayList<>();
			for (ReadCode data : dataList) {
				ReadableCode sendData = new ReadableCode();
				sendData.setId(data.getId());
				sendData.setCodeNameKr(data.getCode_Name_Kr());
				sendData.setCodeNameEn(data.getCode_Name_En());
				sendData.setCodeNameCn(data.getCode_Name_Cn());
				sendData.setCodeNameJp(data.getCode_Name_Jp());
				sendData.setValue(data.getValue());
				sendData.setParentId(data.getParent_id());
				sendList.add(sendData);
			}

			return sendList;
		} catch (ServiceException e) {
			throw new ServiceRuntimeException(e);
		}

	}

	public PersistableCode saveCode(PersistableCode code) throws Exception{
		try {

			int codeId = code.getId();
			Code target = Optional.ofNullable(codeId)
					.filter(id -> id > 0)
					.map(codeService::getById)
					.orElse(new Code());
			
		
			Code dbCode = populateCode(code, target);
			codeService.saveOrUpdate(dbCode);

			if (code.getId() > 0) {
				code.setId(code.getId());
			}else {
				code.setId(codeService.getMaxId());
			}
			return code;
		} catch (ServiceException e) {
			throw new ServiceRuntimeException("Error while updating Dept", e);
		}
	}
	
	
	public ReadableCode getById(int id) throws Exception{
		Validate.notNull(id, "dept id must not be null");
		
		ReadCode data = codeService.getById(id);
		ReadableCode targetData = new ReadableCode();
		if (data == null) {
			throw new ResourceNotFoundException("Code with id [" + id + "] not found");
		}else {
			targetData.setId(data.getId());
			targetData.setParentId(data.getParent_id());
			targetData.setCode(data.getCode());
			targetData.setCodeNameKr(data.getCode_Name_Kr());
			targetData.setCodeNameEn(data.getCode_Name_En());
			targetData.setCodeNameCn(data.getCode_Name_Cn());
			targetData.setCodeNameJp(data.getCode_Name_Jp());
			targetData.setCodeNamePath(codeService.getNamePath(data.getId()).replaceAll("&gt;", " > "));
			targetData.setCodeDesc(data.getCode_Desc());
			targetData.setValue(data.getValue());
			targetData.setOrd(data.getOrd());
			targetData.setVisible(data.getVisible());
		}
		return targetData;
	}
	
	public void deleteCode(int codeId) throws Exception{
		codeService.deleteCode(codeId);
	}
	
	public void updateChangeOrd(PersistableChangeOrd code, String ip) throws Exception{
		if(code.getChangeOrdList().size() > 0) {
			for(ChangeOrdEntity data : code.getChangeOrdList()) {
				Code paramVO =  new Code();
				paramVO.setId(data.getId());
				paramVO.setParentId(data.getParentId());
				paramVO.setOrd(data.getChangeOrd());
				paramVO.setMod_id(data.getUserId());
				paramVO.setMod_ip(ip);
				codeService.updateChangeOrd(paramVO);
			}
		}
		
	}
	
	private Code populateCode(PersistableCode code, Code target) {
		try {
			return persistableCodePopulator.populate(code, target);
		} catch (ConversionException e) {
			throw new ServiceRuntimeException(e);
		}
	}
	
	
	private int settingCode(List<ReadableCode> dataList, int list_pointer, ReadableCode parent_domain)throws Exception  {
		int tmp_list_pointer = list_pointer;
		ReadableCode front_menu_domain = null;
		for(int i = tmp_list_pointer ; i < dataList.size() ; i++){
			tmp_list_pointer = i;
			ReadableCode this_menu_domain = dataList.get(i);
			
			if(parent_domain.getId() == this_menu_domain.getParentId()){
				
				if(parent_domain.getChildren() == null){
					parent_domain.setChildren(new ArrayList<ReadableCode>());
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
					i = settingCode(dataList, tmp_list_pointer, front_menu_domain);
				}
			}
		}
		
		return tmp_list_pointer;
	}
}
