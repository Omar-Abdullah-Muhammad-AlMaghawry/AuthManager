package com.zfinance.authmanager.enums;

public enum ZFinConfigsEnum {
	TEMP_NO_LDAP_PASS_KEY("TEMP_NO_LDAP_PASS_KEY"), LDAP_ADMIN_USER("LDAP_ADMIN_USER"),
	LDAP_ADMIN_PASSWORD("LDAP_ADMIN_PASSWORD"), LDAP_BASE("LDAP_BASE"),
	LDAP_IDENTITY_ATTRIBUTE("LDAP_IDENTITY_ATTRIBUTE"), LDAP_DOMAIN("LDAP_DOMAIN"), LDAP_IPS("LDAP_IPS"),
	LDAP_CONNECTION_TYPE("LDAP_CONNECTION_TYPE"), LDAP_PORT("LDAP_PORT");

	private String code;

	private ZFinConfigsEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
