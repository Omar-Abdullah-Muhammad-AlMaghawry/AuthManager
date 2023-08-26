package com.zfinance.authmanager.orm.userdefinedtype;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@UserDefinedType("user_organization_type")
public class UserOrganization {

	@Column("STATUS")
	private String status;

	@Column("MESSAGE")
	private String message;

	@Column("ID")
	private String id;

	@Column("ORGANIZTION_TYPE")
	private String type;

	@Column("NAME")
	private String name;

	@Column("IDENTIFICATION_STATUS")
	private String identificationStatus;

	@Column("ORGANIZATION_STATUS")
	private String organizationStatus;

}
