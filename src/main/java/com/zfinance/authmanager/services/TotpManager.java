package com.zfinance.authmanager.services;

import dev.samstevens.totp.exceptions.QrGenerationException;

public interface TotpManager {

	public String generateSecretKey();

	public String getQRCode(final String secret) throws QrGenerationException;

	public boolean verifyTotp(final String code, final String secret);

}