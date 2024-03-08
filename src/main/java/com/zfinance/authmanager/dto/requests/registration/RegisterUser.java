package com.zfinance.authmanager.dto.requests.registration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterUser {
	private String login;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String dateOfBirth;
	private String role;
	private String legalType;
	private String merchantId;
	private String id;
}
