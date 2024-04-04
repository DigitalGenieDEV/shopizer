package com.salesmanager.core.business.alibaba.param;

import com.salesmanager.core.business.alibaba.rawsdk.client.APIId;
import com.salesmanager.core.business.alibaba.rawsdk.common.AbstractAPIRequest;


public class AlibabaLogisticsOpQueryLogisticCompanyListParam extends AbstractAPIRequest<AlibabaLogisticsOpQueryLogisticCompanyListResult> {

    public AlibabaLogisticsOpQueryLogisticCompanyListParam() {
        super();
        oceanApiId = new APIId("com.alibaba.logistics", "alibaba.logistics.OpQueryLogisticCompanyList", 1);
    }

}
