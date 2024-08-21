package com.salesmanager.shop.store.controller.email.facade;

import java.util.Map;

public interface EmailFacade {
	void emailVerification(String to);
	void sendEmail(String to, String subject, String templateName, Map<String, String> tokens);
}
