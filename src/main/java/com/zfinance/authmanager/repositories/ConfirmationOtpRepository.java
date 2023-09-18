package com.zfinance.authmanager.repositories;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zfinance.authmanager.orm.ConfirmationOtp;

public interface ConfirmationOtpRepository extends CassandraRepository<ConfirmationOtp, String> {

	@Query("SELECT * FROM zfin_confirmation_otp WHERE (confirmation_otp = :p_confirmation_otp) ALLOW FILTERING")
	ConfirmationOtp findByConfirmationOtp(@Param("p_confirmation_otp") String confirmationOtp);

}
