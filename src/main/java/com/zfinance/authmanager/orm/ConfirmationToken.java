package com.zfinance.authmanager.orm;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
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
@Document("zfin_confirmation_token")
public class ConfirmationToken {

	@Id
	@Field("id")
	private String id;

	@Field("confirmation_token")
	private String confirmationToken;

	@Field("email")
	private String email;

	@Field("created_date")
	private Date createdDate;

	@Transient
	public static final String SEQUENCE_NAME = "confirmation_token_sequence";

}
