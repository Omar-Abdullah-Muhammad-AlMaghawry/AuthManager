package com.zfinance.authmanager.dto.response.signin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthData {
	private TokenData token;
	private UserData user;
	private OrganizationData organization;
	private String role;
}
