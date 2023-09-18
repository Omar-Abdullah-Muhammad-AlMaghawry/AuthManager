package com.zfinance.authmanager.services;

import com.zfinance.authmanager.dto.requests.signin.LoginRequestDto;
import com.zfinance.authmanager.dto.response.signin.AuthData;
import com.zfinance.authmanager.exceptions.BusinessException;
import com.zfinance.authmanager.orm.User;

public interface SecurityService {

	public String login(LoginRequestDto loginRequestDto) throws BusinessException;

	public User getAuthenticatedUser() throws BusinessException;

	public User authenticateUser(String username, String password) throws BusinessException;

	AuthData authorization(LoginRequestDto loginRequestDto) throws BusinessException;

}
