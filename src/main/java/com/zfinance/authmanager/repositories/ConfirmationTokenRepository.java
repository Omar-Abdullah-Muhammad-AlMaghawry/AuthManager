package com.zfinance.authmanager.repositories;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zfinance.authmanager.orm.ConfirmationToken;

public interface ConfirmationTokenRepository extends CassandraRepository<ConfirmationToken, String> {

	@Query("SELECT * FROM zfin_confirmation_token WHERE (confirmation_token = :p_confirmation_Token) ALLOW FILTERING")
	ConfirmationToken findByConfirmationToken(@Param("p_confirmation_Token") String confirmationToken);

}
