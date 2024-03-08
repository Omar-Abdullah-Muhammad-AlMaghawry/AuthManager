package com.zfinance.authmanager.dto.response.user;

import java.util.List;

import com.zfinance.authmanager.orm.userdefinedtype.UserContact;
import com.zfinance.authmanager.orm.userdefinedtype.UserMemberRecord;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserRecord {

	private String id;
	private String name;
	private String partnerId;
	private String merchantId;
	private String refName;
	private String createdAt;
	private Boolean active;
	private Boolean banned;
	private String banExpiryDate;
	private UserContact contact;
	private List<UserMemberRecord> members;
}
