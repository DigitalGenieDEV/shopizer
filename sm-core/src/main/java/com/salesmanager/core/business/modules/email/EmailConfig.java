package com.salesmanager.core.business.modules.email;

import java.util.Properties;

import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfig implements JSONAware {

	@Value("${mailSender.host}")
	private String host;
	
	@Value("${mailSender.port}")
	private String port;
	
	@Value("${mailSender.protocol}")
	private String protocol;
	
	@Value("${mailSender.username}")
	private String username;
	
	@Value("${mailSender.password}")
	private String password;
	
	@Value("${mailSender.mail.smtp.auth}")
	private Boolean smtpAuth = true;
	
	@Value("${mailSender.mail.smtp.starttls.enable}")
	private Boolean starttls = true;
	
	@Value("${mailSender.mail.smtp.starttls.required}")
	private Boolean starttlsRequired = true;
	
	@Value("${mailSender.mail.smtp.connectiontimeout}")
	private int connectionTimeout;
	
	@Value("${mailSender.mail.smtp.writetimeout}")
	private int writeTimeout;

	@Value("${mailSender.mail.smtp.timeout}")
	private int timeout;
	
	private String emailTemplatesPath = null;
	
	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost(host);
		sender.setPort(Integer.parseInt(port));
		sender.setUsername(username);
		sender.setProtocol(protocol);
		sender.setPassword(password);
		sender.setJavaMailProperties(getMailProperties());
		return sender;
	}
	
	private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", smtpAuth);
        properties.put("mail.smtp.starttls.enable", starttls);
        properties.put("mail.smtp.starttls.required", starttlsRequired);
        properties.put("mail.smtp.connectiontimeout", connectionTimeout);
        properties.put("mail.smtp.timeout", timeout);
        properties.put("mail.smtp.writetimeout", writeTimeout);

        return properties;
    }

	
	@SuppressWarnings("unchecked")
	@Override
	public String toJSONString() {
		JSONObject data = new JSONObject();
		data.put("host", this.getHost());
		data.put("port", this.getPort());
		data.put("protocol", this.getProtocol());
		data.put("username", this.getUsername());
		data.put("smtpAuth", this.isSmtpAuth());
		data.put("starttls", this.isStarttls());
		data.put("password", this.getPassword());
		return data.toJSONString();
	}
	
	

	public boolean isSmtpAuth() {
		return smtpAuth;
	}
	public void setSmtpAuth(boolean smtpAuth) {
		this.smtpAuth = smtpAuth;
	}
	public boolean isStarttls() {
		return starttls;
	}
	public void setStarttls(boolean starttls) {
		this.starttls = starttls;
	}
	public void setEmailTemplatesPath(String emailTemplatesPath) {
		this.emailTemplatesPath = emailTemplatesPath;
	}
	public String getEmailTemplatesPath() {
		return emailTemplatesPath;
	}



	public String getHost() {
		return host;
	}



	public void setHost(String host) {
		this.host = host;
	}



	public String getPort() {
		return port;
	}



	public void setPort(String port) {
		this.port = port;
	}



	public String getProtocol() {
		return protocol;
	}



	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}

}
