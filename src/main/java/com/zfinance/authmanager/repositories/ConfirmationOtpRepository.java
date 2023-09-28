package com.zfinance.authmanager.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.zfinance.authmanager.orm.ConfirmationOtp;

public interface ConfirmationOtpRepository extends MongoRepository<ConfirmationOtp, String> {

	ConfirmationOtp findByConfirmationOtp(String confirmationOtp);

}
