package com.salesmanager.core.model.catalog.product;

import com.salesmanager.core.model.common.audit.AuditSection;

import java.math.BigDecimal;

public interface ExchangeRatePOJO {
    String getBaseCurrency();
    String getTargetCurrency();

    Long getId();

    AuditSection getAuditSection();

    BigDecimal getRate();
}
