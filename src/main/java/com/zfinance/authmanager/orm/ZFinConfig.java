package com.zfinance.authmanager.orm;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table("zfin_config")
public class ZFinConfig {

	@PrimaryKey
	@Column("id")
	private Long id;

	@Column("code")
	private String code;

	@Column("value")
	private String value;

	@Column("remarks")
	private String remarks;

	@Column("transaction_class")
	private Integer transactionClass;
}
