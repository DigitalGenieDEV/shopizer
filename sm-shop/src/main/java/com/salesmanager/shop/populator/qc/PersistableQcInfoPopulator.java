package com.salesmanager.shop.populator.qc;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.fulfillment.service.QcInfoService;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.business.utils.ObjectConvert;
import com.salesmanager.core.enmus.QcStatusEnums;
import com.salesmanager.core.model.fulfillment.QcInfo;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.privacy.Privacy;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.fulfillment.PersistableQcInfo;
import com.salesmanager.shop.model.privacy.PersistablePrivacy;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PersistableQcInfoPopulator extends AbstractDataPopulator<PersistableQcInfo, QcInfo> {

	@Autowired
	private QcInfoService qcInfoService;


	public QcInfo populate(PersistableQcInfo source) throws ConversionException {
		try {
			QcInfo qcInfo = new QcInfo();

			// 检查是否存在 ID，从数据库获取现有的 QcInfo
			if (source.getId() != null) {
				qcInfo = qcInfoService.getById(source.getId());
			}

			// 仅在 source 中有值的情况下进行覆盖
			if (source.getVideoUrl() != null) {
				qcInfo.setVideoUrl(source.getVideoUrl());
			}
			if (source.getProductImages() != null) {
				qcInfo.setProductImages(source.getProductImages());
			}
			if (source.getPackagePictures() != null) {
				qcInfo.setPackagePictures(source.getPackagePictures());
			}
			if (source.getBuyerComment() != null) {
				qcInfo.setBuyerComment(source.getBuyerComment());
			}
			if (source.getStatus() != null) {
				qcInfo.setStatus(QcStatusEnums.valueOf(source.getStatus()));
			}
			if (source.getPassedDate() != null) {
				qcInfo.setPassedDate(new Date(source.getPassedDate()));
			}

			// 返回最终的 QcInfo 对象
			return qcInfo;
		} catch (Exception e) {
			throw new ConversionException(e);
		}
	}


	@Override
	public QcInfo populate(PersistableQcInfo source, QcInfo target, MerchantStore store, Language language)
			throws ConversionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected QcInfo createTarget() {
		// TODO Auto-generated method stub
		return null;
	}

}
