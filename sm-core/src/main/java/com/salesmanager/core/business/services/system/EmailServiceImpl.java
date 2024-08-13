package com.salesmanager.core.business.services.system;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesmanager.core.business.constants.Constants;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.modules.email.Email;
import com.salesmanager.core.business.modules.email.EmailConfig;
import com.salesmanager.core.business.modules.email.HtmlEmailSender;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.system.MerchantConfiguration;

import lombok.RequiredArgsConstructor;

@Service("emailService")
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

	@Inject
	private MerchantConfigurationService merchantConfigurationService;
	
	@Inject
	private HtmlEmailSender sender;
	
	@Inject
	private final EmailConfig config;
	
	@Override
	public void sendVerificationEmail(String to, String code) throws ServiceException {
		
		sender.setEmailConfig(config);
		Map<String, String> templateTokens = new HashMap<String,String>();
		templateTokens.put("EMAIL_AUTH_CODE", code);
		
		
		Email email = new Email();
		email.setFrom("Sourcing Root");
		email.setFromEmail("ckbridge.Dev1@gmail.com");
		email.setSubject("소싱루트에서 보낸 인증번호입니다.");
		email.setTo(to);
		email.setTemplateName("email_template_mail_auth.ftl");
		email.setTemplateTokens(templateTokens);
		
		try {
			sender.send(email);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void sendHtmlEmail(MerchantStore store, Email email) throws ServiceException, Exception {

		EmailConfig emailConfig = getEmailConfiguration(store);
		
		sender.setEmailConfig(emailConfig);
		sender.send(email);
	}

	@Override
	public EmailConfig getEmailConfiguration(MerchantStore store) throws ServiceException {
		
		MerchantConfiguration configuration = merchantConfigurationService.getMerchantConfiguration(Constants.EMAIL_CONFIG, store);
		EmailConfig emailConfig = null;
		if(configuration!=null) {
			String value = configuration.getValue();
			
			ObjectMapper mapper = new ObjectMapper();
			try {
				emailConfig = mapper.readValue(value, EmailConfig.class);
			} catch(Exception e) {
				throw new ServiceException("Cannot parse json string " + value);
			}
		}
		return emailConfig;
	}
	
	
	@Override
	public void saveEmailConfiguration(EmailConfig emailConfig, MerchantStore store) throws ServiceException {
		MerchantConfiguration configuration = merchantConfigurationService.getMerchantConfiguration(Constants.EMAIL_CONFIG, store);
		if(configuration==null) {
			configuration = new MerchantConfiguration();
			configuration.setMerchantStore(store);
			configuration.setKey(Constants.EMAIL_CONFIG);
		}
		
		String value = emailConfig.toJSONString();
		configuration.setValue(value);
		merchantConfigurationService.saveOrUpdate(configuration);
	}

}
