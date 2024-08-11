package com.salesmanager.core.business.services.alibaba.payment;

import com.salesmanager.core.business.alibaba.param.AlibabaAlipayUrlGetParam;
import com.salesmanager.core.business.alibaba.param.AlibabaAlipayUrlGetResult;

public interface AlibabaPaymentService {

    AlibabaAlipayUrlGetResult getAlipayUrl(AlibabaAlipayUrlGetParam param);
}
