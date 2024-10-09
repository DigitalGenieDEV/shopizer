package com.salesmanager.core.business.fulfillment.service;


import com.salesmanager.core.model.fulfillment.outer.LogisticsTrackInformation;

import java.util.List;

public interface InternationalShippingInformationQueryService {

    List<LogisticsTrackInformation> queryByLogisticsNumber(String logisticsNumber, String phone);

    List<LogisticsTrackInformation> queryByLogisticsNumber(String logisticsCompany, String logisticsNumber, String phone);

}
