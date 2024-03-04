package com.zfinance.authmanager.services;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.zfinance.authmanager.dto.requests.signin.LoginRequestDto;
import com.zfinance.authmanager.dto.response.signin.AuthData;
import com.zfinance.authmanager.dto.response.signin.OrganizationData;
import com.zfinance.authmanager.dto.response.signin.TokenData;
import com.zfinance.authmanager.dto.response.signin.UserData;
import com.zfinance.authmanager.enums.FlagsEnum;
import com.zfinance.authmanager.enums.RoleEnum;
import com.zfinance.authmanager.exceptions.BusinessException;
import com.zfinance.authmanager.orm.User;
import com.zfinance.authmanager.security.JwtTokenUtil;

@Service
public class SecurityServiceImpl implements SecurityService {

	@Value("${noLdapFlag}")
	private String noLdapFlag;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private ZFinConfigService zFinConfigService;

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public String login(LoginRequestDto loginRequestDto) throws BusinessException {
		authenticateUser(loginRequestDto.getLogin(), loginRequestDto.getPartnerId(), loginRequestDto.getPassword());
		return jwtTokenUtil.generateToken(loginRequestDto.getLogin());
	}

	@Override
	public AuthData authorization(LoginRequestDto loginRequestDto) throws BusinessException {
		User user = authenticateUser(loginRequestDto.getLogin(), loginRequestDto.getPartnerId(), loginRequestDto
				.getPassword());
		String token = jwtTokenUtil.generateToken(loginRequestDto.getLogin());
		TokenData tokenData = new TokenData(token, jwtTokenUtil.getExpirationDateFromToken(token).toString());
		UserData userData = new UserData(user.getId(), user.getName());
		OrganizationData organizationData = new OrganizationData();
		String role = null;
		if (user.getMembers() != null && user.getMembers().size() > 0) {
			if (user.getMembers().get(0).getOrganization() != null)
				organizationData = new OrganizationData(user.getMembers().get(0).getOrganization().getId(), user
						.getMembers().get(0).getOrganization().getType());
			role = user.getMembers().get(0).getRole();
		}
		AuthData authData = new AuthData(tokenData, userData, organizationData, role);

		return authData;

	}

	@Override
	public User getAuthenticatedUser() throws BusinessException {
		try {
			if (SecurityContextHolder.getContext().getAuthentication() != null) {
				String login = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				User user = userService.getUserByLogin(login);
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

	private User authUser(String login, String partnerId, String password) throws Exception {

		User user = userService.getUserByLogin(login);
		if ((user.getMembers().get(0).getRole().equals(RoleEnum.MERCHANT.getCode()) || (user.getUserRole() != null
				&& user.getUserRole().getName().equals(RoleEnum.MERCHANT.getCode()))) && user.getPartnerId() != null
				&& partnerId != null && user.getPartnerId().equals(partnerId) && passwordEncoder.matches(password, user
						.getEncPassword())) {
			return user;
		} else if (passwordEncoder.matches(password, user.getEncPassword())) {
			return user;
		} else {
			throw new BusinessException("error_invalidUserOrPassword");
		}

	}

	@Override
	public User authenticateUser(String username, String partnerId, String password) throws BusinessException {

		try {
			if ((FlagsEnum.ON.getCode() + "").equals(noLdapFlag)) {
				String tempPassValue = zFinConfigService.getTempPassValue();
				if (tempPassValue != null && !tempPassValue.equals(password)) {
					throw new BusinessException("error_invalidUser");
				}
				return userService.getUserByLogin(username);
			}
//			System.out.println("#:LDAP authentication for : " + username);
//			getContext(username, password);
//			return getUserData(username);
			return authUser(username, partnerId, password);
		} catch (AuthenticationException e) {
			throw new BusinessException("error_invalidUser");
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("error_LDAPError");
		}
	}

	@Override
	public String generateJwt(String username) {
		return jwtTokenUtil.generateToken(username);
	}
}
