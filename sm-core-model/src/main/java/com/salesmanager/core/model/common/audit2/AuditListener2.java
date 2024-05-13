package com.salesmanager.core.model.common.audit2;

import java.util.Date;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class AuditListener2 {

  @PrePersist
  public void onSave(Object o) {
    if (o instanceof Auditable2) {
      Auditable2 audit = (Auditable2) o;
      AuditSection2 auditSection = audit.getAuditSection();

      auditSection.setModDate(new Date());
      if (auditSection.getRegDate() == null) {
        auditSection.setRegDate(new Date());
      }
      audit.setAuditSection(auditSection);
    }
  }

  @PreUpdate
  public void onUpdate(Object o) {
    if (o instanceof Auditable2) {
      Auditable2 audit = (Auditable2) o;
      AuditSection2 auditSection = audit.getAuditSection();

      auditSection.setModDate(new Date());
      if (auditSection.getRegDate() == null) {
        auditSection.setRegDate(new Date());
      }
      audit.setAuditSection(auditSection);
    }
  }
}
