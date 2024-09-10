package com.salesmanager.shop.store.controller.erp.facade;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.PersistableMaterial;
import com.salesmanager.shop.model.catalog.ReadableMaterial;

import java.util.List;

public interface ErpFacade {

    List<ReadableMaterial> getMaterials(Language language);


    void saveMaterial(PersistableMaterial persistedMaterial) throws ConversionException, ServiceException;
}
