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

	@Column("phone_number")
	private String phoneNumber;

	@Column("phone_verified")
	private boolean phoneVerified;

	@Column("email")
	private String email;

	@Column("email_verified")
	private boolean emailVerified;

	@Column("country_code")
	private String countryCode;
}
