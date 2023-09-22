package com.zfinance.authmanager.orm;

import java.util.List;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.zfinance.authmanager.orm.userdefinedtype.UserContact;
import com.zfinance.authmanager.orm.userdefinedtype.UserMemberRecord;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table("zfin_user")
public class User {

	@PrimaryKey
	@Column("id")
	private String id;

	@Column("name")
	private String name;

	@Column("email")
	private String email;

	@Column("enc_password")
	private String encPassword;

	@Column("created_at")
	private String createdAt;

	@Column("active")
	private Boolean active;

	@Column("banned")
	private Boolean banned;

	@Column("ban_expiry_date")
	private String banExpiryDate;

	@Column("contact")
	private UserContact contact;

	@Column("members")
	private List<UserMemberRecord> members;

}
