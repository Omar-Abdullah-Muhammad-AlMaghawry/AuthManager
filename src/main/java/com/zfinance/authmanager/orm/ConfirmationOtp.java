package com.zfinance.authmanager.orm;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("zfin_confirmation_otp")
public class ConfirmationOtp {

	@Id
	@Field("id")
	private String id;

	@Field("confirmation_otp")
	private String confirmationOtp;

	@Field("email")
	private String email;

	@Field("created_date")
	private Date createdDate;

	@Field("expired_date")
	private Date expiredDate;

}
