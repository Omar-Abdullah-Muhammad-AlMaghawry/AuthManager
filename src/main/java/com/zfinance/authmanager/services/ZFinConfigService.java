package com.zfinance.authmanager.services;

import com.zfinance.authmanager.exceptions.BusinessException;

public interface ZFinConfigService {

	public String getConfigValueByCode(String code);

	public String getTempPassValue() throws BusinessException;

	public String getLDAPAdminUsername() throws BusinessException;

	public String getLDAPAdminPassword() throws BusinessException;

	public String getLDAPBase() throws BusinessException;

	public String getLDAPIdentityAttribute() throws BusinessException;

	public String getLDAPDomain() throws BusinessException;

	public String getLDAPConnectionType() throws BusinessException;

	public String getLDAPPort() throws BusinessException;

	public String getLDAPIPS() throws BusinessException;

	public String getVerificationEmailBody() throws BusinessException;

	public String getDefaultSignInSubject() throws BusinessException;

	public String getDefaultPasswordBody() throws BusinessException;

	public String getDefaultPartnerIdBody() throws BusinessException;

	public String getQrCodeAuthSubject() throws BusinessException;

	public String getQrCodeAuthBody() throws BusinessException;

	public String getVerificationEmailSubject() throws BusinessException;

	public String getOtpSubject() throws BusinessException;

	public String getOtpBody() throws BusinessException;

}
