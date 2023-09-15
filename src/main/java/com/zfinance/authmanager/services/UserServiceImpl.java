package com.zfinance.authmanager.services;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zfinance.authmanager.exceptions.BusinessException;
import com.zfinance.authmanager.orm.ConfirmationToken;
import com.zfinance.authmanager.orm.User;
import com.zfinance.authmanager.orm.userdefinedtype.UserContact;
import com.zfinance.authmanager.repositories.ConfirmationTokenRepository;
import com.zfinance.authmanager.repositories.UserRepository;
import com.zfinance.authmanager.security.JwtTokenUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private ZFinConfigService zFinConfigService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	public User getUserByLogin(String login) {
		return userRepository.findByLogin(login);
	}

	@Override
	@Transactional
	public void saveUser(User user) throws BusinessException {
		if (userRepository.findByLogin(user.getEmail()) != null) {
			throw new BusinessException("error_emailExists");
		}

		userRepository.save(user);

		String token = jwtTokenUtil.generateToken(user.getEmail());
		ConfirmationToken confirmationToken = new ConfirmationToken();
		confirmationToken.setId(UUID.randomUUID().toString());
		confirmationToken.setEmail(user.getEmail());
		confirmationToken.setConfirmationToken(token);
		confirmationToken.setCreatedDate(new Date());
		confirmationTokenRepository.save(confirmationToken);

		String subject = zFinConfigService.getVerificationEmailSubject();
		String body = zFinConfigService.getVerificationEmailBody() + confirmationToken.getConfirmationToken();
		emailService.sendEmailDetailed(user.getEmail(), subject, body);
	}

	@Override
	public void confirmEmail(String confirmationToken) throws BusinessException {
		ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

		if (token != null) {
			User user = userRepository.findByLogin(token.getEmail());
			UserContact userContact = user.getContact();
			userContact.setEmailVerified(true);
			userRepository.save(user);
		} else {
			throw new BusinessException("error_cannotVerifyEmail");
		}
	}

	@Override
	public User getUserByLoginAndEncPassword(String login, String encPassword) {
		return userRepository.findByLoginAndEncPassword(login, encPassword);
	}

}
