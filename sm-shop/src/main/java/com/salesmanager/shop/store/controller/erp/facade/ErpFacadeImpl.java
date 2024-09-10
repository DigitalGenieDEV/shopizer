package com.salesmanager.shop.store.controller.erp.facade;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.catalog.product.erp.ErpService;
import com.salesmanager.core.business.utils.ObjectConvert;
import com.salesmanager.core.model.catalog.product.Material;
import com.salesmanager.core.model.catalog.product.MaterialDescription;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.PersistableMaterial;
import com.salesmanager.shop.model.catalog.ReadableMaterial;
import com.salesmanager.shop.populator.erp.PersistableMaterialPopulator;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ErpFacadeImpl implements ErpFacade {

  @Autowired
  private ErpService erpService;


  @Autowired
  private PersistableMaterialPopulator persistableMaterialPopulator;


    @Override
    public List<ReadableMaterial> getMaterials(Language language) {
        List<Material> materials = erpService.queryList();
        if (CollectionUtils.isEmpty(materials)){
            return null;
        }
        return materials.stream().map(material -> {
            ReadableMaterial readableMaterial = ObjectConvert.convert(material, ReadableMaterial.class);
            List<com.salesmanager.shop.model.catalog.MaterialDescription> readableDescriptions = new ArrayList<>();
            for(MaterialDescription desc : material.getDescriptions()) {
                if (desc.getLanguage().getCode().equals(language.getCode())) {
                    readableMaterial.setDescription(description(desc));
                }
                readableDescriptions.add(description(desc));
            }
            readableMaterial.setType(material.getType().name());
            readableMaterial.setDescriptions(readableDescriptions);
            return readableMaterial;
        }).collect(toList());
    }

    @Override
    public void saveMaterial(PersistableMaterial persistedMaterial) throws ServiceException, ConversionException {
        Material populate = persistableMaterialPopulator.populate(persistedMaterial, new Material());
        erpService.save(populate);
    }


    com.salesmanager.shop.model.catalog.MaterialDescription description(MaterialDescription description) {
        com.salesmanager.shop.model.catalog.MaterialDescription desc = new com.salesmanager.shop.model.catalog.MaterialDescription();
        desc.setDescription(description.getDescription());
        desc.setName(description.getName());
        desc.setTitle(description.getTitle());
        desc.setSubTitle(description.getSubTitle());
        desc.setSubName(description.getSubName());
        desc.setId(description.getId());
        desc.setLanguage(description.getLanguage().getCode());
        return desc;
    }
}
