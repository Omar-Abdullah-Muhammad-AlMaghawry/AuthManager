package com.zfinance.authmanager.orm.userdefinedtype;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@UserDefinedType("user_membe_record_type")
public class UserMemberRecord {

	@Column("id")
	private String id;

	@Column("role")
	private String role;

	@Column("organization")
	@CassandraType(type = CassandraType.Name.UDT, userTypeName = "user_organization_type")
	private UserOrganization organization;

	@Column("contract_info")
	@CassandraType(type = CassandraType.Name.UDT, userTypeName = "user_contact_info_type")
	private UserContractInfo contractInfo;
}
