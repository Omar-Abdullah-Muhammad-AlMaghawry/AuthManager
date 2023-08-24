package com.zfinance.authmanager.orm;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserOrganization {

	private String status;
	private String message;
	private String id;
	private String type;
	private String name;
	private String identificationStatus;
	private String organizationStatus;

}
