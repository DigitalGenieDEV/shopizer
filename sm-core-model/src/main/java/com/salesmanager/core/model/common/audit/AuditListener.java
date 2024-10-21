package com.salesmanager.core.model.common.audit;

import com.salesmanager.core.model.common.UserContext;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class AuditListener {

  @PrePersist
  public void onSave(Object o) {
    if (o instanceof Auditable) {
      Auditable audit = (Auditable) o;
      AuditSection auditSection = audit.getAuditSection();

      auditSection.setDateModified(new Date());
      if (auditSection.getDateCreated() == null) {
        auditSection.setDateCreated(new Date());
      }

      UserContext currentInstance = UserContext.getCurrentInstance();
      if (currentInstance != null && StringUtils.isNotBlank(currentInstance.getIpAddress())) {
        String ipAddress = currentInstance.getIpAddress();
        auditSection.setOperatorIp(ipAddress);
      }

      audit.setAuditSection(auditSection);
    }
  }

  @PreUpdate
  public void onUpdate(Object o) {
    if (o instanceof Auditable) {
      Auditable audit = (Auditable) o;
      AuditSection auditSection = audit.getAuditSection();

      auditSection.setDateModified(new Date());
      if (auditSection.getDateCreated() == null) {
        auditSection.setDateCreated(new Date());
      }

      UserContext currentInstance = UserContext.getCurrentInstance();
      if (currentInstance != null && StringUtils.isNotBlank(currentInstance.getIpAddress())) {
        String ipAddress = currentInstance.getIpAddress();
        auditSection.setOperatorIp(ipAddress);
      }

      audit.setAuditSection(auditSection);
    }
  }
}
