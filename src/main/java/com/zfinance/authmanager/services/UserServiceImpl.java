package com.zfinance.authmanager.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zfinance.authmanager.dto.MfaTokenData;
import com.zfinance.authmanager.dto.requests.signin.PasswordRecoveryConfirmBody;
import com.zfinance.authmanager.exceptions.BusinessException;
import com.zfinance.authmanager.orm.ConfirmationOtp;
import com.zfinance.authmanager.orm.ConfirmationToken;
import com.zfinance.authmanager.orm.User;
import com.zfinance.authmanager.orm.userdefinedtype.UserContact;
import com.zfinance.authmanager.repositories.ConfirmationOtpRepository;
import com.zfinance.authmanager.repositories.ConfirmationTokenRepository;
import com.zfinance.authmanager.repositories.UserRepository;
import com.zfinance.authmanager.security.JwtTokenUtil;
import com.zfinance.authmanager.services.database.sequence.SequenceGeneratorService;
import com.zfinance.authmanager.services.external.ExternalUserService;

import dev.samstevens.totp.exceptions.QrGenerationException;

@Service
public class UserServiceImpl implements UserService {

	@Value("${otp.secondsValidity}")
	private Long otpValiditySeconds;

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

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ConfirmationOtpRepository confirmationOtpRepository;

	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;

	@Autowired
	private ExternalUserService externalUserService;

	@Autowired
	private TotpManager totpManager;

	@Override
	public User getUserByLogin(String login) {
		return userRepository.findByEmail(login);
	}

	@Override
	@Transactional
	public MfaTokenData saveUser(User user) throws BusinessException, QrGenerationException {
		if (userRepository.findByEmail(user.getEmail()) != null) {
			throw new BusinessException("error_emailExists");
		}

		String token = jwtTokenUtil.generateToken(user.getEmail());
		ConfirmationToken confirmationToken = new ConfirmationToken();
		confirmationToken.setId(sequenceGeneratorService.generateSequence(ConfirmationToken.SEQUENCE_NAME));
		confirmationToken.setEmail(user.getEmail());
		confirmationToken.setConfirmationToken(token);
		confirmationToken.setCreatedDate(new Date());

		confirmationTokenRepository.save(confirmationToken);

		String subject = zFinConfigService.getVerificationEmailSubject();
		String verificationEmailBody = zFinConfigService.getVerificationEmailBody();
		String confirmationToken2 = confirmationToken.getConfirmationToken();
		String body = verificationEmailBody + confirmationToken2;
		if (user.getEmail() != null && subject != null && verificationEmailBody != null && confirmationToken2 != null) {
			emailService.sendEmailDetailed(user.getEmail(), subject, body);

			// some additional work
			user.setSecretKey(totpManager.generateSecretKey()); // generating the secret and store with profile

			User savedUser = userRepository.save(user);

			// Generate the QR Code
			String qrCode = totpManager.getQRCode(savedUser.getSecretKey());
			return MfaTokenData.builder().mfaCode(savedUser.getSecretKey()).qrCode(qrCode).build();
		} else {
			throw new BusinessException("error_cannotSendEmail");
		}
	}

	@Override
	public void confirmEmail(String confirmationToken) throws BusinessException {
		ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

		if (token != null) {
			User user = userRepository.findByEmail(token.getEmail());
			UserContact userContact = user.getContact();
			userContact.setEmailVerified(true);
			userRepository.save(user);
			externalUserService.verifyUserProfileEmail(user.getId());
		} else {
			throw new BusinessException("error_cannotVerifyEmail");
		}
	}

	@Override
	public User getUserByLoginAndEncPassword(String login, String encPassword) {
		return userRepository.findByEmailAndEncPassword(login, encPassword);
	}

	private String generateOTP() {
		// declare randomNo to store the otp
		// generate 4 digits otp
		int randomNo = (int) (Math.random() * 9000) + 1000;
		String otp = String.valueOf(randomNo);
		// return otp
		return otp;
	}

	@Override
	public void passwordRecovery(String login) throws BusinessException {
		User user = this.getUserByLogin(login);
		if (user == null) {
			throw new BusinessException("error_emailNotExists");
		}

		String otp = generateOTP();

		ConfirmationOtp confirmationOtp = new ConfirmationOtp();
		confirmationOtp.setId(sequenceGeneratorService.generateSequence(ConfirmationOtp.SEQUENCE_NAME));
		confirmationOtp.setEmail(user.getEmail());
		confirmationOtp.setConfirmationOtp(otp);
		confirmationOtp.setCreatedDate(new Date());
		confirmationOtp.setExpiredDate(new Date(System.currentTimeMillis() + otpValiditySeconds * 1000));
		confirmationOtpRepository.save(confirmationOtp);

		String subject = zFinConfigService.getPasswordRecoverySubject();
		String body = zFinConfigService.getPasswordRecoveryBody() + otp;
		emailService.sendEmailDetailed(user.getEmail(), subject, body);

	}

	@Override
	public void passwordRecoveryConfirm(PasswordRecoveryConfirmBody passwordRecoveryConfirmBody)
			throws BusinessException {
		ConfirmationOtp otp = confirmationOtpRepository.findByConfirmationOtp(passwordRecoveryConfirmBody.getOtp());

		if (otp != null && otp.getExpiredDate().after(new Date())) {
			User user = this.getUserByLogin(passwordRecoveryConfirmBody.getLogin());
			String newEncPassword = passwordEncoder.encode(passwordRecoveryConfirmBody.getNewUserPassword());
			user.setEncPassword(newEncPassword);
			userRepository.save(user);
		} else {
			throw new BusinessException("error_cannotRecoverPassword");
		}
	}

	@Override
	public User getUserFromToken(String token) {
		String login = jwtTokenUtil.getLoginFromToken(token);
		return getUserByLogin(login);
	}

	@Override
	public Boolean verifyTotp(String code, String email) {
		User user = userRepository.findByEmail(email);
		return totpManager.verifyTotp(code, user.getSecretKey());
	}
}
