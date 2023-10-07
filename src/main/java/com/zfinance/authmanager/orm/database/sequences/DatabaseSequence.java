package com.zfinance.authmanager.orm.database.sequences;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Document("database_sequences")
public class DatabaseSequence {

	@Id
	@Field("id")
	private String id;

	@Field("seq")
	private long seq;
}
