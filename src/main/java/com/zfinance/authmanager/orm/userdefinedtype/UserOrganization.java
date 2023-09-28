package com.zfinance.authmanager.orm.userdefinedtype;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserOrganization {

	@Field("status")
	private String status;

	@Field("message")
	private String message;

	@Field("id")
	private String id;

	@Field("organization_type")
	private String type;

	@Field("name")
	private String name;

	@Field("identification_status")
	private String identificationStatus;

	@Field("organization_status")
	private String organizationStatus;

}
