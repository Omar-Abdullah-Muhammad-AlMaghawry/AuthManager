package com.zfinance.authmanager.orm.userdefinedtype;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserContact {

	@Field("phone_number")
	private String phoneNumber;

	@Field("phone_verified")
	private boolean phoneVerified;

	@Field("email")
	private String email;

	@Field("email_verified")
	private boolean emailVerified;

	@Field("country_code")
	private String countryCode;
}
