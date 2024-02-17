package com.zfinance.authmanager.services;

import com.zfinance.authmanager.dto.MfaTokenData;
import com.zfinance.authmanager.dto.requests.signin.PasswordRecoveryConfirmBody;
import com.zfinance.authmanager.exceptions.BusinessException;
import com.zfinance.authmanager.orm.User;

import dev.samstevens.totp.exceptions.QrGenerationException;

public interface UserService {

	public User getUserByLogin(String login);

	public User getUserByLoginAndEncPassword(String login, String encPassword);

	public MfaTokenData saveUser(User user) throws BusinessException, QrGenerationException;

	public void confirmEmail(String confirmationToken) throws BusinessException;

	public void passwordRecovery(String login) throws BusinessException;

	public void passwordRecoveryConfirm(PasswordRecoveryConfirmBody passwordRecoveryConfirmBody)
			throws BusinessException;

	public User getUserFromToken(String token);

	public Boolean verifyTotp(final String code, String username);

}
