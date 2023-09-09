package com.zfinance.authmanager.orm.userdefinedtype;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@UserDefinedType("user_member_record_type")
public class UserMemberRecord {

	@Column("id")
	private String id;

	@Column("role")
	private String role;

	@Column("organization")
	private UserOrganization organization;

	@Column("contract_info")
	private UserContractInfo contractInfo;
}
