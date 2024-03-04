package com.zfinance.authmanager.services;

import static com.zfinance.authmanager.constant.TOTPConstants.CODE_VALIDITY_IN_SECONDS;
import static com.zfinance.authmanager.constant.TOTPConstants.DIGITS;
import static com.zfinance.authmanager.constant.TOTPConstants.HASHING_ALGO;

import org.springframework.stereotype.Service;

import dev.samstevens.totp.code.CodeVerifier;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import dev.samstevens.totp.util.Utils;

@Service
public class TotpManagerImpl implements TotpManager {

	private final SecretGenerator secretGenerator;
	private final QrGenerator qrGenerator;
	private final CodeVerifier myCodeVerifier;

	public TotpManagerImpl(SecretGenerator secretGenerator, QrGenerator qrGenerator, CodeVerifier myCodeVerifier) {
		this.secretGenerator = secretGenerator;
		this.qrGenerator = qrGenerator;
		this.myCodeVerifier = myCodeVerifier;
	}

	@Override
	public String generateSecretKey() {
		return secretGenerator.generate(); // 32 Byte Secret Key
	}

	@Override
	public String getQRCode(String login, String secret) throws QrGenerationException {
		QrData qrData = new QrData.Builder().label(login).issuer("Z_finance").secret(secret).digits(DIGITS).period(
				CODE_VALIDITY_IN_SECONDS).algorithm(HASHING_ALGO).build();

		return Utils.getDataUriForImage(qrGenerator.generate(qrData), qrGenerator.getImageMimeType());
	}

	@Override
	public boolean verifyTotp(String code, String secret) {
		return myCodeVerifier.isValidCode(secret, code);
	}
}
