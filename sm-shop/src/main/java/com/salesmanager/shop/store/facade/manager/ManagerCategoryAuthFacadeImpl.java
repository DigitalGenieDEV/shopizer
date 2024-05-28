package com.salesmanager.shop.store.facade.manager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.salesmanager.core.business.services.manager.ManagerCategoryAuthService;
import com.salesmanager.core.model.manager.CategoryAuth;
import com.salesmanager.core.model.manager.ReadCategoryAuth;
import com.salesmanager.shop.model.manager.CategoryAuthEntity;
import com.salesmanager.shop.model.manager.ReadableManagerCategoryAuth;
import com.salesmanager.shop.model.manager.ReadableManagerCategoryAuthList;
import com.salesmanager.shop.store.controller.manager.facade.ManagerCategoryAuthFacade;

@Service
public class ManagerCategoryAuthFacadeImpl implements ManagerCategoryAuthFacade {

	@Inject
	private ManagerCategoryAuthService managerCategoryAuthService;

	@Inject
	private ObjectMapper objectMapper;

	public ReadableManagerCategoryAuth getCategoryAuthFullList(int grpId) throws Exception {

		List<ReadCategoryAuth> categoryDataList = managerCategoryAuthService.getCategoryList();
		List<ReadCategoryAuth> dataList = managerCategoryAuthService.getCategoryAuthFullList(grpId);
		List<ReadableManagerCategoryAuth> tempList = new ArrayList<>();
		if (dataList.isEmpty()) {
			ReadableManagerCategoryAuth rootDept = new ReadableManagerCategoryAuth();
			return rootDept;
		} else {
			if (dataList.size() > 0) {
				for (ReadCategoryAuth data : dataList) {
					for (ReadCategoryAuth category : categoryDataList) {
						if (data.getDepth().equals(1)) {
							if (category.getLineage().indexOf(data.getLineage()) != -1) {
								ReadableManagerCategoryAuth sendData = new ReadableManagerCategoryAuth();
								setTempList(tempList, sendData, category);
							}
						} else if (data.getDepth().equals(2)) {
							if (category.getId().equals(data.getParentId())) {
								ReadableManagerCategoryAuth sendData = new ReadableManagerCategoryAuth();
								setTempList(tempList, sendData, category);
							}

							if (category.getLineage().indexOf(data.getLineage()) != -1) {
								ReadableManagerCategoryAuth sendData = new ReadableManagerCategoryAuth();
								setTempList(tempList, sendData, category);
							}

						} else if (data.getDepth().equals(3)) {
							String arrayLineage[] = data.getLineage().substring(1).split("/");
							if (category.getId().equals(Integer.parseInt(arrayLineage[0]))) {
								ReadableManagerCategoryAuth sendData = new ReadableManagerCategoryAuth();
								setTempList(tempList, sendData, category);
							}
							if (category.getId().equals(Integer.parseInt(arrayLineage[1]))) {
								ReadableManagerCategoryAuth sendData = new ReadableManagerCategoryAuth();
								setTempList(tempList, sendData, category);
							}

							if (category.getLineage().indexOf(data.getLineage()) != -1) {
								ReadableManagerCategoryAuth sendData = new ReadableManagerCategoryAuth();
								setTempList(tempList, sendData, category);
							}
						}
					}
				}
			}
			ReadableManagerCategoryAuth rootMenuVO = new ReadableManagerCategoryAuth();
			rootMenuVO.setId(0);
			this.settingCateogoryAuth(tempList, 0, rootMenuVO);
			return rootMenuVO;
		}
	}

	public ReadableManagerCategoryAuthList getCategoryAuthList(int grpId) throws Exception {
		ReadableManagerCategoryAuthList target = new ReadableManagerCategoryAuthList();
		ArrayList<CategoryAuthEntity> categoryList = new ArrayList<CategoryAuthEntity>();
		List<ReadCategoryAuth> categoryDataList = managerCategoryAuthService.getCategoryList();
		List<CategoryAuth> dataList = managerCategoryAuthService.getCategoryAuthList(grpId);

		if (dataList.size() > 0) {
			for (CategoryAuth data : dataList) {
				for (ReadCategoryAuth category : categoryDataList) {
					if (category.getId().equals(data.getCategoryId())) {
						objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
						CategoryAuthEntity targetData = objectMapper.convertValue(category, CategoryAuthEntity.class);
						categoryList.add(targetData);
					}
				}
			}
		}
		target.setData(categoryList);
		return target;
	}

	private List<ReadableManagerCategoryAuth> setTempList(List<ReadableManagerCategoryAuth> tempList, ReadableManagerCategoryAuth sendData, ReadCategoryAuth category){
		sendData.setId(category.getId());
		sendData.setParentId(category.getParentId());
		sendData.setCategoryName(category.getCategoryName());
		sendData.setCategoryNamePath(category.getCategoryPathName());
		sendData.setLineage(category.getLineage());
		sendData.setDepth(category.getDepth());
		sendData.setCode(category.getCode());
		tempList.add(sendData);
		return tempList;
	}

	private int settingCateogoryAuth(List<ReadableManagerCategoryAuth> menu_all_list, int list_pointer,ReadableManagerCategoryAuth parent_domain) throws Exception {
		int tmp_list_pointer = list_pointer;
		ReadableManagerCategoryAuth front_menu_domain = null;
		for (int i = tmp_list_pointer; i < menu_all_list.size(); i++) {
			tmp_list_pointer = i;
			ReadableManagerCategoryAuth this_menu_domain = menu_all_list.get(i);

			if (parent_domain.getId() == this_menu_domain.getParentId()) {

				if (parent_domain.getChildren() == null) {
					parent_domain.setChildren(new ArrayList<ReadableManagerCategoryAuth>());
				}
				parent_domain.getChildren().add(this_menu_domain);
				front_menu_domain = this_menu_domain;
			} else {
				if ((front_menu_domain != null && (front_menu_domain.getDepth() > this_menu_domain.getDepth()))) {
					tmp_list_pointer--;
					break;
				} else if (front_menu_domain == null) {
					break;
				} else {
					i = settingCateogoryAuth(menu_all_list, tmp_list_pointer, front_menu_domain);
				}
			}
		}

		return tmp_list_pointer;
	}
}
