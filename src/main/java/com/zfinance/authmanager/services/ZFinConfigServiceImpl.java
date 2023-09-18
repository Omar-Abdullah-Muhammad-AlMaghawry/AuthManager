package com.zfinance.authmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zfinance.authmanager.enums.ZFinConfigsEnum;
import com.zfinance.authmanager.exceptions.BusinessException;
import com.zfinance.authmanager.orm.ZFinConfig;
import com.zfinance.authmanager.repositories.ZFinConfigRepository;

@Service
public class ZFinConfigServiceImpl implements ZFinConfigService {
	@Autowired
	private ZFinConfigRepository zFinConfigRepository;

	@Override
	public String getConfigValueByCode(String code) throws BusinessException {
		ZFinConfig etrConfigOptional = zFinConfigRepository.findByCode(code);
		if (etrConfigOptional != null)
			return etrConfigOptional.getValue();
		else
			return null;
	}

	@Override
	public String getTempPassValue() throws BusinessException {
		return getConfigValueByCode(ZFinConfigsEnum.TEMP_NO_LDAP_PASS_KEY.getCode());
	}

	@Override
	public String getLDAPAdminUsername() throws BusinessException {
		return getConfigValueByCode(ZFinConfigsEnum.LDAP_ADMIN_USER.getCode());
	}

	@Override
	public String getLDAPAdminPassword() throws BusinessException {
		return getConfigValueByCode(ZFinConfigsEnum.LDAP_ADMIN_PASSWORD.getCode());
	}

	@Override
	public String getLDAPBase() throws BusinessException {
		return getConfigValueByCode(ZFinConfigsEnum.LDAP_BASE.getCode());
	}

	@Override
	public String getLDAPIdentityAttribute() throws BusinessException {
		return getConfigValueByCode(ZFinConfigsEnum.LDAP_IDENTITY_ATTRIBUTE.getCode());
	}

	@Override
	public String getLDAPDomain() throws BusinessException {
		return getConfigValueByCode(ZFinConfigsEnum.LDAP_DOMAIN.getCode());
	}

	@Override
	public String getLDAPIPS() throws BusinessException {
		return getConfigValueByCode(ZFinConfigsEnum.LDAP_IPS.getCode());
	}

	@Override
	public String getLDAPConnectionType() throws BusinessException {
		return getConfigValueByCode(ZFinConfigsEnum.LDAP_CONNECTION_TYPE.getCode());
	}

	@Override
	public String getLDAPPort() throws BusinessException {
		return getConfigValueByCode(ZFinConfigsEnum.LDAP_PORT.getCode());
	}

	@Override
	public String getVerificationEmailBody() throws BusinessException {
		return getConfigValueByCode(ZFinConfigsEnum.VERIFICATION_EMAIL_BODY.getCode());
	}

	@Override
	public String getVerificationEmailSubject() throws BusinessException {
		return getConfigValueByCode(ZFinConfigsEnum.VERIFICATION_EMAIL_SUBJECT.getCode());
	}

	@Override
	public String getPasswordRecoverySubject() throws BusinessException {
		return getConfigValueByCode(ZFinConfigsEnum.PASSWORD_RECOVERY_SUBJECT.getCode());

	}

	@Override
	public String getPasswordRecoveryBody() throws BusinessException {
		return getConfigValueByCode(ZFinConfigsEnum.PASSWORD_RECOVERY_BODY.getCode());

	}

}
