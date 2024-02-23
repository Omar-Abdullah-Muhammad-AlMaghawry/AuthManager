package com.zfinance.authmanager.enums;

public enum RoleEnum {
	INDIVIDUAL("individual"), MERCHANT("merchant"), ADMINISTRATOR("administrator"), CFO("cfo"), ACCOUNTANT(
			"accountant");

	private String code;

	private RoleEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
