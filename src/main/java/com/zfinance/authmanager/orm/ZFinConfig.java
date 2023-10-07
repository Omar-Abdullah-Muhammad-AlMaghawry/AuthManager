package com.zfinance.authmanager.orm;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Document("zfin_config")
public class ZFinConfig {

	@Id
	@Field("id")
	private String id;

	@Field("code")
	private String code;

	@Field("value")
	private String value;

	@Field("remarks")
	private String remarks;

	@Field("transaction_class")
	private Integer transactionClass;

	@Transient
	public static final String SEQUENCE_NAME = "config_sequence";

}
