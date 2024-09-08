package com.salesmanager.shop.mapper;

import com.salesmanager.core.model.common.UserContext;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.shop.populator.PersistableAuditAspect;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * save operator ip when entity create/modified
 * @author yuxunhui
 */
@Aspect
@Component
public class PersistableMapperAuditAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersistableAuditAspect.class);

    @AfterReturning(value = "execution(* com.salesmanager.shop.mapper..*.convert(..))", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        try {
            if (result instanceof Auditable) {
                Auditable entity = (Auditable) result;
                AuditSection audit = entity.getAuditSection();
                UserContext currentInstance = UserContext.getCurrentInstance();
                if (currentInstance != null && StringUtils.isNotBlank(currentInstance.getIpAddress())) {
                    String ipAddress = currentInstance.getIpAddress();
                    audit.setOperatorIp(ipAddress);
                    LOGGER.info("get IP from UserContext:" + ipAddress);
                }
            }
        } catch (Throwable e) {
            LOGGER.error("Error while setting audit values" + e.getMessage());
        }
    }
}
