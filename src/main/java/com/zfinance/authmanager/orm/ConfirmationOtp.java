package com.zfinance.authmanager.orm;

import java.util.Date;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("zfin_confirmation_otp")
public class ConfirmationOtp {

	@PrimaryKey
	@Column("id")
	private String id;

	@Column("confirmation_otp")
	private String confirmationOtp;

	@Column("email")
	private String email;

	@Column("created_date")
	private Date createdDate;

	@Column("expired_date")
	private Date expiredDate;

}
