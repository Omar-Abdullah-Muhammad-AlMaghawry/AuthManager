package com.zfinance.authmanager.orm;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserContractInfo {
	private String id;
	private String personType;
	private String name;
}
