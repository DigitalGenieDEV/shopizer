package com.salesmanager.core.model.catalog.product;

import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

public interface ExchangeRatePOJO {
    String getBaseCurrency();
    String getTargetCurrency();

    Long getId();

    AuditSection getAuditSection();

    BigDecimal getRate();
}
