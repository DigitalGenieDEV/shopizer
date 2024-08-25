package com.salesmanager.core.business.services.system;



import java.util.Map;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.modules.email.Email;
import com.salesmanager.core.business.modules.email.EmailConfig;
import com.salesmanager.core.model.merchant.MerchantStore;


public interface EmailService {
	
	void sendVerificationEmail(String to, String code) throws ServiceException;
	
	void sendEmail(String to, String subject, String templateName, Map<String, String> tokens);

	void sendHtmlEmail(MerchantStore store, Email email) throws ServiceException, Exception;
	
	EmailConfig getEmailConfiguration(MerchantStore store) throws ServiceException;
	
	void saveEmailConfiguration(EmailConfig emailConfig, MerchantStore store) throws ServiceException;
	
}
