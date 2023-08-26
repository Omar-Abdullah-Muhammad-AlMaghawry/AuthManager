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

	@Column("status")
	private String status;

	@Column("message")
	private String message;

	@Column("id")
	private String id;

	@Column("type")
	private String type;

	@Column("name")
	private String name;

	@Column("identificationStatus")
	private String identificationStatus;

	@Column("organizationStatus")
	private String organizationStatus;

}
