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
		authenticateUser(loginRequestDto.getLogin(), loginRequestDto.getPassword());
		return jwtTokenUtil.generateToken(loginRequestDto.getLogin());
	}

	@Override
	public AuthData authorization(LoginRequestDto loginRequestDto) throws BusinessException {
		User user = authenticateUser(loginRequestDto.getLogin(), loginRequestDto.getPassword());
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

	private User authUser(String login, String password) throws Exception {

		User user = userService.getUserByLogin(login);

		if (passwordEncoder.matches(password, user.getEncPassword())) {
			return user;
		} else {
			throw new BusinessException("error_invalidUserOrPassword");
		}

	}

	@Override
	public User authenticateUser(String username, String password) throws BusinessException {

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
			return authUser(username, password);
		} catch (AuthenticationException e) {
			throw new BusinessException("error_invalidUser");
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("error_LDAPError");
		}
	}

//	private User getUserData(String userAccount) throws BusinessException {
//		try {
//			User user = null;
//			String identity;
//
//			DirContext context = getContext(zFinConfigService.getLDAPAdminUsername(),
//					zFinConfigService.getLDAPAdminPassword());
//
//			String baseSearch = zFinConfigService.getLDAPBase();
//			SearchControls searchControls = new SearchControls();
//
//			String identityAttributeName = zFinConfigService.getLDAPIdentityAttribute();
//
//			String[] returnedAtts = new String[] { identityAttributeName };
//
//			searchControls.setReturningAttributes(returnedAtts);
//
//			// Specify the scope of my search (one level down, recursive
//			// subtree, etc.)
//			searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
//
//			// My LDAP search filter...what am I looking for?
//			if (!userAccount.contains("@"))
//				userAccount = userAccount + "@" + zFinConfigService.getLDAPDomain();
//			String searchFilter = "(userPrincipalName=" + userAccount + ")";
//
//			// Actually perform the search telling JNDI where to start
//			// the search, what to search for, what how to filter.
//			NamingEnumeration<SearchResult> results = context.search(baseSearch, searchFilter, searchControls);
//
//			// Loop through the search results
//			if (results.hasMoreElements()) {
//				SearchResult searchResult = results.next();
//
//				Attributes attributes = searchResult.getAttributes();
//
//				if (attributes.get(identityAttributeName) != null) {
//					identity = attributes.get(identityAttributeName).toString().replace(identityAttributeName + ": ",
//							"");
//				} else {
//					throw new BusinessException("error_invalidUser");
//				}
//
//				user = userService.getUserByLogin(identity);
//
//				if (user == null)
//					throw new BusinessException("error_noAuthentictedUserPrivilege");
//
//				if (user.getContact().getEmail() == null) {
//					try {
//						UserContact userContact = user.getContact();
//						userContact.setEmail(userAccount);
//						user.setContact(userContact);
//						userService.save(user);
//					} catch (Exception e) {
//						System.out.println("#### Failed to update user in login due to : " + e.getMessage());
//					}
//				} else {
//					if (!userAccount.equalsIgnoreCase(user.getContact().getEmail())) {
//						throw new BusinessException("error_userAccountMismatch");
//					}
//				}
//			} else {
//				throw new BusinessException("error_invalidUser");
//			}
//
//			context.close();
//			System.out.println("#:Finished loading user");
//			return user;
//		} catch (AuthenticationException e) {
//			throw new BusinessException("error_invalidAdminUser");
//		} catch (BusinessException e) {
//			throw e;
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new BusinessException("error_LDAPError");
//		}
//	}
//
//	private DirContext getContext(String username, String password) throws Exception {
//		Hashtable<String, String> env = new Hashtable<>();
//
//		if (zFinConfigService.getLDAPDomain() == null || zFinConfigService.getLDAPConnectionType() == null
//				|| zFinConfigService.getLDAPPort() == null)
//			throw new BusinessException("error_DBError");
//
//		String domain = zFinConfigService.getLDAPDomain();
//
//		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
//		env.put(Context.SECURITY_AUTHENTICATION, "simple");
//
//		if (!username.contains("@"))
//			username = username + "@" + domain;
//
//		env.put(Context.SECURITY_PRINCIPAL, username);
//		env.put(Context.SECURITY_CREDENTIALS, password);
//
//		Exception exceptionIfAny = null;
//
//		String[] LDAPIPS = zFinConfigService.getLDAPIPS().split(",");
//
//		for (int i = 0; i < LDAPIPS.length; i++) {
//			String url = zFinConfigService.getLDAPConnectionType() + "://" + LDAPIPS[i]
//					+ zFinConfigService.getLDAPPort();
//			env.put(Context.PROVIDER_URL, url);
//			try {
//				return new InitialLdapContext(env, null);
//			} catch (Exception e) {
//				System.out.println("#:LDAP ERRORR : " + LDAPIPS[i]);
//				exceptionIfAny = e;
//			}
//		}
//
//		throw exceptionIfAny;
//	}

}
