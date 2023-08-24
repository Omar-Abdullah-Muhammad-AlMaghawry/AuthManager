package com.zfinance.authmanager.orm;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserMemberRecord {
	private String id;
	private String role;
	private UserOrganization organization;
	private UserContractInfo contractInfo;
}
