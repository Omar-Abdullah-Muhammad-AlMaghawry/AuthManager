package com.zfinance.authmanager.services;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.zfinance.authmanager.dto.requests.login.LoginRequestDto;
import com.zfinance.authmanager.enums.FlagsEnum;
import com.zfinance.authmanager.exceptions.BusinessException;
import com.zfinance.authmanager.orm.User;
import com.zfinance.authmanager.orm.UserContact;
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

	private User authUser(String username, String password) throws Exception {

		User user = userService.getUserByEmail(username);

//		if (user != null && passwordEncoder.matches(password, user.getPassword())) {
		return user;
//		} else {
//			throw new BusinessException("error_invalidUserOrPassword");
//		}

	}

	@Override
	public User authenticateUser(String username, String password) throws BusinessException {
		if ((FlagsEnum.ON.getCode() + "").equals(noLdapFlag)) {
			String tempPassValue = zFinConfigService.getTempPassValue();
			if (tempPassValue != null && !tempPassValue.equals(password)) {
				throw new BusinessException("error_invalidUser");
			}
			return userService.getUserByEmail(username);
		}

		try {
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

	private User getUserData(String userAccount) throws BusinessException {
		try {
			User user = null;
			String identity;

			DirContext context = getContext(zFinConfigService.getLDAPAdminUsername(),
					zFinConfigService.getLDAPAdminPassword());

			String baseSearch = zFinConfigService.getLDAPBase();
			SearchControls searchControls = new SearchControls();

			String identityAttributeName = zFinConfigService.getLDAPIdentityAttribute();

			String[] returnedAtts = new String[] { identityAttributeName };

			searchControls.setReturningAttributes(returnedAtts);

			// Specify the scope of my search (one level down, recursive
			// subtree, etc.)
			searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

			// My LDAP search filter...what am I looking for?
			if (!userAccount.contains("@"))
				userAccount = userAccount + "@" + zFinConfigService.getLDAPDomain();
			String searchFilter = "(userPrincipalName=" + userAccount + ")";

			// Actually perform the search telling JNDI where to start
			// the search, what to search for, what how to filter.
			NamingEnumeration<SearchResult> results = context.search(baseSearch, searchFilter, searchControls);

			// Loop through the search results
			if (results.hasMoreElements()) {
				SearchResult searchResult = results.next();

				Attributes attributes = searchResult.getAttributes();

				if (attributes.get(identityAttributeName) != null) {
					identity = attributes.get(identityAttributeName).toString().replace(identityAttributeName + ": ",
							"");
				} else {
					throw new BusinessException("error_invalidUser");
				}

				user = userService.getUserByEmail(identity);

				if (user == null)
					throw new BusinessException("error_noAuthentictedUserPrivilege");

				if (user.getContact().getEmail() == null) {
					try {
						UserContact userContact = user.getContact();
						userContact.setEmail(userAccount);
						user.setContact(userContact);
						userService.save(user);
					} catch (Exception e) {
						System.out.println("#### Failed to update user in login due to : " + e.getMessage());
					}
				} else {
					if (!userAccount.equalsIgnoreCase(user.getContact().getEmail())) {
						throw new BusinessException("error_userAccountMismatch");
					}
				}
			} else {
				throw new BusinessException("error_invalidUser");
			}

			context.close();
			System.out.println("#:Finished loading user");
			return user;
		} catch (AuthenticationException e) {
			throw new BusinessException("error_invalidAdminUser");
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("error_LDAPError");
		}
	}

	private DirContext getContext(String username, String password) throws Exception {
		Hashtable<String, String> env = new Hashtable<>();

		if (zFinConfigService.getLDAPDomain() == null || zFinConfigService.getLDAPConnectionType() == null
				|| zFinConfigService.getLDAPPort() == null)
			throw new BusinessException("error_DBError");

		String domain = zFinConfigService.getLDAPDomain();

		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");

		if (!username.contains("@"))
			username = username + "@" + domain;

		env.put(Context.SECURITY_PRINCIPAL, username);
		env.put(Context.SECURITY_CREDENTIALS, password);

		Exception exceptionIfAny = null;

		String[] LDAPIPS = zFinConfigService.getLDAPIPS().split(",");

		for (int i = 0; i < LDAPIPS.length; i++) {
			String url = zFinConfigService.getLDAPConnectionType() + "://" + LDAPIPS[i]
					+ zFinConfigService.getLDAPPort();
			env.put(Context.PROVIDER_URL, url);
			try {
				return new InitialLdapContext(env, null);
			} catch (Exception e) {
				System.out.println("#:LDAP ERRORR : " + LDAPIPS[i]);
				exceptionIfAny = e;
			}
		}

		throw exceptionIfAny;
	}

}
