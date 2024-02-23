package com.zfinance.authmanager.dto.requests.signin;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequestDto {
	private String login;
	private String partnerId;
	private String password;
}
