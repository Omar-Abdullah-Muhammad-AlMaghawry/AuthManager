package com.zfinance.authmanager.orm;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table("ZFIN_User")
public class User {

	@Id
	@PrimaryKey
	@Column("ID")
	private String id;

	@Column("NAME")
	private String name;

	@Column("CREATED_AT")
	private String createdAt;

	@Column("ACTIVE")
	private boolean active;

	@Column("BANNED")
	private boolean banned;

	@Column("BAN_EXPIRY_DATE")
	private String banExpiryDate;

	@Column("CONTACT")
	private UserContact contact;

	@Column("MEMBERS")
	private List<UserMemberRecord> members;

}