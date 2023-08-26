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

	@Column("ID")
	private String id;

	@Column("ROLE")
	private String role;

	@Column("ORGANIZATION")
	@CassandraType(type = CassandraType.Name.UDT, userTypeName = "user_organization_type")
	private UserOrganization organization;

	@Column("CONTACT_INFO")
	@CassandraType(type = CassandraType.Name.UDT, userTypeName = "user_contact_info_type")
	private UserContractInfo contractInfo;
}
