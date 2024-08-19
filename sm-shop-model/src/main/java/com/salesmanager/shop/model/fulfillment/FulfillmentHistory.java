package com.salesmanager.shop.model.fulfillment;

import com.salesmanager.core.enmus.FulfillmentHistoryTypeEnums;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Data
public class FulfillmentHistory  {

    private Long id;

    private Long orderId;

    private Long productId;

    private String status;

    private String previousStatus;

    private Date dateCreated;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FulfillmentHistory that = (FulfillmentHistory) o;
        return Objects.equals(orderId, that.orderId) &&
                Objects.equals(status, that.status) &&
                Objects.equals(previousStatus, that.previousStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, status, previousStatus);
    }
}
