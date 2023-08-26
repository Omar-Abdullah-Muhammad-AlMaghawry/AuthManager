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

	@Column("ID")
	private String id;

	@Column("PERSON_TYPE")
	private String personType;

	@Column("NAME")
	private String name;
}
