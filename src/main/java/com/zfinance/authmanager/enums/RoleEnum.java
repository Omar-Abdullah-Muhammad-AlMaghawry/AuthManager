package com.zfinance.authmanager.enums;

public enum RoleEnum {
	INDIVIDUAL("2", "individual"), MERCHANT("3", "merchant"), ADMINISTRATOR("1", "administrator"), CFO("1", "cfo"),
	ACCOUNTANT("18", "accountant");

	private String id;
	private String code;

	private RoleEnum(String id, String code) {
		this.id = id;
		this.code = code;
	}

	public String getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

}
