package com.zfinance.authmanager.services;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

	public void sendEmail(SimpleMailMessage email);

	public void sendEmailDetailed(String sentTo, String subject, String body);

}