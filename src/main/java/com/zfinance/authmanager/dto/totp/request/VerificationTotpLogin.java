package com.zfinance.authmanager.dto.totp.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class VerificationTotpLogin {
	private String login;
	private String totp;
}
