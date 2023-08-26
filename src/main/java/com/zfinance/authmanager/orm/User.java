package com.zfinance.authmanager.orm;

import java.util.List;

import org.springframework.data.cassandra.core.mapping.CassandraType;
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
@Table("ZFIN_USER")
public class User {

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
	@CassandraType(type = CassandraType.Name.UDT, userTypeName = "user_contact_type")
	private UserContact contact;

	@Column("MEMBERS")
	private List<UserMemberRecord> members;

}
