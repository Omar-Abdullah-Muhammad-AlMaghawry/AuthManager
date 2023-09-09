package com.zfinance.authmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public void sendEmail(SimpleMailMessage email) {
		javaMailSender.send(email);
	}

	@Override
	public void sendEmailDetailed(String sentTo, String subject, String body) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(sentTo);
		mailMessage.setSubject(subject);
		mailMessage.setText(body);
		this.sendEmail(mailMessage);

	}

}
