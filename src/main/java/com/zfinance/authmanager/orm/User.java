package com.zfinance.authmanager.orm;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zfinance.authmanager.orm.userdefinedtype.UserContact;
import com.zfinance.authmanager.orm.userdefinedtype.UserMemberRecord;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Document("zfin_user")
public class User {

	@Id
	@Field("id")
	private String id;

	@Field("name")
	private String name;

	@Field("email")
	private String email;

	@Field("enc_password")
	private String encPassword;

	@Field("mfa_enabled")
	boolean mfaEnabled;

	@JsonIgnore
	@Field("secret_key")
	private String secretKey;

	@Field("created_at")
	private String createdAt;

	@Field("active")
	private Boolean active;

	@Field("banned")
	private Boolean banned;

	@Field("ban_expiry_date")
	private String banExpiryDate;

	@Field("contact")
	private UserContact contact;

	@Field("members")
	private List<UserMemberRecord> members;

	@Transient
	public static final String SEQUENCE_NAME = "user_sequence";

}
