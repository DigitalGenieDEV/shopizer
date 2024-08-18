package com.salesmanager.shop.model.fulfillment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
public class QcInfoHistory {

    private String code;

    private Long qcInfoId;


    private Date dateCreated;

    private Date dateModified;


}
