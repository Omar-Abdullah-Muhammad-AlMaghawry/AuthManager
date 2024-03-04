package com.zfinance.authmanager.constant;

import dev.samstevens.totp.code.HashingAlgorithm;

public interface TOTPConstants {

	int DIGITS = 6;
	int SECRET_CHARACTER_LENGTH = 32;
	int CODE_VALIDITY_IN_SECONDS = 30;
	int TIME_PERIOD_DISCREPANCY = 1;
	HashingAlgorithm HASHING_ALGO = HashingAlgorithm.SHA1;

}
