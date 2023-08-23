package com.zfinance.authmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;

import com.zfinance.authmanager.dto.requests.login.LoginRequestDto;
import com.zfinance.authmanager.exceptions.BusinessException;
import com.zfinance.authmanager.orm.User;
import com.zfinance.authmanager.security.JwtTokenUtil;

public class SecurityServiceImpl implements SecurityService {

	@Value("${noLdapFlag}")
	private String noLdapFlag;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private ZFinConfigService zFinConfigService;

	@Autowired
	private UserService userService;

	@Override
	public String login(LoginRequestDto loginRequestDto) throws BusinessException {
		User user = authenticateUser(loginRequestDto.getUsername(), loginRequestDto.getPassword());
		return jwtTokenUtil.generateToken(user.getContact().getEmail());
	}

	@Override
	public User getAuthenticatedUser() throws BusinessException {
		try {
			if (SecurityContextHolder.getContext().getAuthentication() != null) {
				String userName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				User user = userService.getUserByEmail(userName);
				if (user == null) {
					throw new BusinessException("error_invalidUser");
				}
				return user;

			} else {
				throw new BusinessException("error_notAuthorized");

			}
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("error_general");
		}
	}

	@Override
	public User authenticateUser(String username, String password) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
