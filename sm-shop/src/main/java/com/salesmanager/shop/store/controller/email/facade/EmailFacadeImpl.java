package com.salesmanager.shop.store.controller.email.facade;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.business.services.system.EmailService;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.shop.model.references.EmailVerificationDTO;
import com.salesmanager.shop.store.api.exception.GenericRuntimeException;
import com.salesmanager.shop.store.clients.external.ExternalClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailFacadeImpl implements EmailFacade{
	
	private final EmailService emailService;
	
	private final CustomerService customerService;
	
	private final ExternalClient emailClient;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailFacadeImpl.class);
	
	@Override
	public void emailVerification(String to) {
		try {
			Customer result = customerService.getByNick(to);
			if(result != null)
				throw new GenericRuntimeException("409", "Customer with email [" + result.getNick()+ "] is already registered");
			
			String code = createCode();
			emailService.sendVerificationEmail(to, code);
			emailClient.emailVerification(
					EmailVerificationDTO
					.builder()
					.email(to)
					.code(code)
					.build()
					);			
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	private String createCode() throws NoSuchAlgorithmException {
		int lenth = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < lenth; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
        	LOGGER.debug("EmailFacadeImpl.createCode() exception occur");
        	throw new NoSuchAlgorithmException();
        }
	}
}
