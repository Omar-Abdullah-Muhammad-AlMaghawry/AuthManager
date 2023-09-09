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
@Table("zfin_confirmation_token")
public class ConfirmationToken {

	@PrimaryKey
	@Column("id")
	private String id;

	@Column("confirmation_token")
	private String confirmationToken;

	@Column("email")
	private String email;

	@Column("created_date")
	private Date createdDate;

}
