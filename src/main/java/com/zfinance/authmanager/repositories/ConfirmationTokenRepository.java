package com.zfinance.authmanager.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import com.zfinance.authmanager.orm.ConfirmationToken;

public interface ConfirmationTokenRepository extends MongoRepository<ConfirmationToken, String> {

	ConfirmationToken findByConfirmationToken(@Param("p_confirmation_Token") String confirmationToken);

}
