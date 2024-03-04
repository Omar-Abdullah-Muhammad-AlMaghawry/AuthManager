package com.zfinance.authmanager.dto.totp.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class VerificationResponse {
	private String username;
	private String jwt;
	private boolean mfaRequired;
	private boolean authValid;
	private boolean tokenValid;
	private String message;
}
