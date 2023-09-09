package com.zfinance.authmanager.services;

import com.zfinance.authmanager.exceptions.BusinessException;
import com.zfinance.authmanager.orm.User;

public interface UserService {

	public User getUserByLogin(String login);

	public User getUserByLoginAndEncPassword(String login, String encPassword);

	public void saveUser(User user) throws BusinessException;

	public void confirmEmail(String confirmationToken) throws BusinessException;

}
