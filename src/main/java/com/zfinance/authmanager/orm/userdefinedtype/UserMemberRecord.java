package com.zfinance.authmanager.orm.userdefinedtype;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserMemberRecord {

	@Field("id")
	private String id;

	@Field("role")
	private String role;

	@Field("organization")
	private UserOrganization organization;

	@Field("contract_info")
	private UserContractInfo contractInfo;
}
