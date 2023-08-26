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
@Table("ZFIN_CONFIG")
public class ZFinConfig {

	@PrimaryKey
	@Column("ID")
	private Long id;

	@Column("CODE")
	private String code;

	@Column("VALUE")
	private String value;

	@Column("REMARKS")
	private String remarks;

	@Column("TRANSACTION_CLASS")
	private Integer transactionClass;
}
