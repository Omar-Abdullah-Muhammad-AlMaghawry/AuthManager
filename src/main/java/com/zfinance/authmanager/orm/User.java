package com.zfinance.authmanager.orm;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ZFIN_User")
public class User {

	@Id
	@Column(name = "ID")
	private String id;

	@Basic
	@Column(name = "NAME")
	private String name;

	@Basic
	@Column(name = "CREATED_AT")
	private String createdAt;

	@Basic
	@Column(name = "ACTIVE")
	private boolean active;

	@Basic
	@Column(name = "BANNED")
	private boolean banned;

	@Basic
	@Column(name = "BAN_EXPIRY_DATE")
	private String banExpiryDate;

	@Basic
	@Column(name = "CONTACT")
	private UserContact contact;
//
//	@Basic
//	@Column(name = "MEMBERS")
//	private List<UserMemberRecord> members;

}