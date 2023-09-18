package com.zfinance.authmanager.services;

import com.zfinance.authmanager.exceptions.BusinessException;

public interface ZFinConfigService {

	public String getConfigValueByCode(String code) throws BusinessException;

	public String getTempPassValue() throws BusinessException;;

	public String getLDAPAdminUsername() throws BusinessException;;

	public String getLDAPAdminPassword() throws BusinessException;;

	public String getLDAPBase() throws BusinessException;;

	public String getLDAPIdentityAttribute() throws BusinessException;;

	public String getLDAPDomain() throws BusinessException;;

	public String getLDAPConnectionType() throws BusinessException;;

	public String getLDAPPort() throws BusinessException;;

	public String getLDAPIPS() throws BusinessException;

	public String getVerificationEmailBody() throws BusinessException;

	public String getVerificationEmailSubject() throws BusinessException;

	public String getPasswordRecoverySubject() throws BusinessException;

	public String getPasswordRecoveryBody() throws BusinessException;

}
