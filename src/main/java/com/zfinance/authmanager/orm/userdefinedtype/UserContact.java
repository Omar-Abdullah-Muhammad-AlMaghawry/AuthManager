package com.zfinance.authmanager.orm.userdefinedtype;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@UserDefinedType("user_contact_type")
public class UserContact {

	@Column("PHONE_NUMBER")
	private String phoneNumber;

	@Column("PHONE_VERIFIED")
	private boolean phoneVerified;

	@Column("EMAIL")
	private String email;

	@Column("EMAIL_VERIFIED")
	private boolean emailVerified;

	@Column("COUNTRY_CODE")
	private String countryCode;
}
