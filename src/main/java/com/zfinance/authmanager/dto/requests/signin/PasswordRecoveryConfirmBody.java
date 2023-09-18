package com.zfinance.authmanager.dto.requests.signin;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PasswordRecoveryConfirmBody {
	private String login;
	private String newUserPassword;
	private String otp;
}
