package com.zfinance.authmanager.orm.userdefinedtype;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@UserDefinedType("user_contract_info_type")
public class UserContractInfo {

	@Column("id")
	private String id;

	@Column("person_type")
	private String personType;

	@Column("name")
	private String name;
}
